package kr.co.comes.intranet.api.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kr.co.comes.intranet.api.auth.model.AuthUser;
import kr.co.comes.intranet.api.user.UserService;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.common.exception.CommonException;
import kr.co.comes.intranet.common.type.CookieType;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuthService {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final UserService userService;
    private final AuthMapper mapper;

    public static String generateAccessToken(String userId) {
        val expirationDateTime = LocalDateTime.now().plusMinutes(30); // 30 min from now

        return generateToken(userId, expirationDateTime);
    }

    public static String generateRefreshToken(String userId) {
        val expirationDateTime = LocalDateTime.now().plusDays(7); // 7 days from now

        return generateToken(userId, expirationDateTime);
    }

    public static String generateToken(String userId, LocalDateTime expirationDateTime) {
        val expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String refreshAccessToken(String refreshToken) {
        val claims = getClaims(refreshToken);

        val userId = claims.getSubject();

        return generateAccessToken(userId);
    }

    public static boolean isTokenExpired(String token) {
        try {
            val claims = getClaims(token);

            return claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true; // Parsing 실패 또는 토큰 만료 시간 이전에 발생한 경우도 만료로 처리
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public AuthUser getAuthUser(String token) throws CommonException {
        val claims = getClaims(token);
        val userId = claims.getSubject();

        return mapper.toAuthUser(userService.getUser(userId));
    }

    public void applyAuthInfo(HttpServletResponse response, String userId) {
        val accessToken = generateAccessToken(userId);
        val refreshToken = generateRefreshToken(userId);

        applyCookie(response, CookieType.ACCESS_TOKEN.getText(), accessToken);
        applyCookie(response, "refreshToken", refreshToken);
    }

    public AuthUser authentication(UserDto.LoginRequest request) throws CommonException {
        val user = userService.getUser(request.getUserId());
        user.verifyPassword(request.getPwd());

        return mapper.toAuthUser(user);
    }

    public void applyCookie(HttpServletResponse response, String name, String token) {
        Cookie accessTokenCookie = new Cookie(name, token);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/"); // 전체 애플리케이션 경로에 쿠키 사용 가능
        response.addCookie(accessTokenCookie);
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        val cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void revokeTokens(HttpServletRequest request, HttpServletResponse response) {
        // 만료된 액세스 토큰 및 리프레시 토큰을 무효화
        val expiredAccessTokenCookie = new Cookie(CookieType.ACCESS_TOKEN.getText(), null);
        expiredAccessTokenCookie.setHttpOnly(true);
        expiredAccessTokenCookie.setPath("/");
        expiredAccessTokenCookie.setMaxAge(0); // 쿠키 만료 시간을 0으로 설정하여 삭제
        response.addCookie(expiredAccessTokenCookie);

        val expiredRefreshTokenCookie = new Cookie(CookieType.REFRESH_TOKEN.getText(), null);
        expiredRefreshTokenCookie.setHttpOnly(true);
        expiredRefreshTokenCookie.setPath("/");
        expiredRefreshTokenCookie.setMaxAge(0);
        response.addCookie(expiredRefreshTokenCookie);

        // 세션의 인증 정보를 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}

package kr.co.comes.intranet.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CookieType {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String text;
}

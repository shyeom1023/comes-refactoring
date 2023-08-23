package kr.co.comes.intranet.api.user;

import kr.co.comes.intranet.api.user.domain.User;
import kr.co.comes.intranet.api.user.dto.UserDto;
import kr.co.comes.intranet.api.user.persistence.UserRepository;
import kr.co.comes.intranet.common.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.co.comes.intranet.common.exception.ResponseCode.NOT_FOUND_USER;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public void create(UserDto.UserCreateRequest request) {
        val entity = mapper.toEntity(request);
        entity.encrypt();
        repository.save(entity);
    }

    public User getUser(String userId) throws CommonException {
        val entity = repository.findByUserId(userId).orElseThrow(() -> new CommonException(NOT_FOUND_USER));
        return mapper.toModel(entity);
    }
}

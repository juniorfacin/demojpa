package tech.buildrun.demojpa.service;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.buildrun.demojpa.controller.dto.CreateUserDto;
import tech.buildrun.demojpa.controller.dto.UpdateUserDto;
import tech.buildrun.demojpa.entity.UserEntity;
import tech.buildrun.demojpa.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserDto dto) {

        // Converter um DTO (vem da API) para uma Entidade (queremos gravar na base)
        var entity = new UserEntity();

        entity.setName(dto.name());
        entity.setAge(dto.age());
        entity.setCreatedAt(LocalDateTime.now());

        // Spring JPA que vai gerar o valor automaticamente
        // O save() é o metodo INSERT
        return userRepository.save(entity);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> updateById(Long userId,
                                     UpdateUserDto dto) {

        var user = userRepository.findById(userId);

        if (user.isPresent()) {
            UpdateFields(dto, user);

            userRepository.save(user.get());
        }

        return user;
    }

    private void UpdateFields(UpdateUserDto dto, Optional<UserEntity> user) {
        // converter o DTO recebido da API para Entidade
        // uso do hasText do Java Utils
        if (hasText(dto.name())) {
            user.get().setName(dto.name());
        }

        if (!isNull(dto.age())) {
            user.get().setAge(dto.age());
        }
    }
}

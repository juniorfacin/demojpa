package tech.buildrun.demojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.demojpa.entity.UserEntity;

// Usar a anotação @Repository não é mais necessária
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}

package tech.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.jwt.model.Role;
import tech.jwt.model.ERole;

import java.util.Optional;

@Repository
public interface RoleReposity extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}


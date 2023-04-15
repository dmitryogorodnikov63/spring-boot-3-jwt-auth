package ru.authservice.Auth.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.authservice.Auth.service.entity.Role;
import ru.authservice.Auth.service.enums.ERole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByName(ERole name);

}
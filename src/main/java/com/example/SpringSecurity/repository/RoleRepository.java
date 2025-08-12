// repository/RoleRepository.java
package com.example.SpringSecurity.repository;

import com.example.SpringSecurity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

package pl.ust.tr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ust.tr.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}

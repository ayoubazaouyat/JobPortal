package teapot.collat_hbrs.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);
}

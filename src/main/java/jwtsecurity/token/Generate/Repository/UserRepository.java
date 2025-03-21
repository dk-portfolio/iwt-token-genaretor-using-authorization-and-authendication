package jwtsecurity.token.Generate.Repository;
import jwtsecurity.token.Generate.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}

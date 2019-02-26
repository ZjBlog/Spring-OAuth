package serviceha.demo.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import serviceha.demo.entity.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

}

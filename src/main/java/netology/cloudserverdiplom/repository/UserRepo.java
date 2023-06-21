package netology.cloudserverdiplom.repository;


import netology.cloudserverdiplom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByLogin(String login);
    User findUserByAuthToken(String authToken);
}

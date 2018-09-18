package warehouse.repository;

import warehouse.entity.User;

import java.util.List;

public interface UserInterface {
    void save(User user);
    List<User> findall();
    void delete(User user);
    User find(Long id);
}

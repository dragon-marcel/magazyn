package warehouse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import warehouse.dao.UserDAO;
import warehouse.entity.User;

import java.util.List;

@Repository
public class UserRepository implements UserInterface {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void save(User user) {
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(pass);
        userDAO.save(user);
    }

    @Override
    public List<User> findall() {
        List<User>users = (List<User>) userDAO.findAll();
        return users;
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public User find(Long id) {
        return userDAO.findById(id).orElse(null);
    }
}

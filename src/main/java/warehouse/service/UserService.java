package warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.entity.User;
import warehouse.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public boolean chackExistUserByName(User user){
        List<User> users = userRepository.findall();
        boolean check = users.stream().map(User::getName).anyMatch(a->a.equals(user.getName()));
        return check;
    }
}

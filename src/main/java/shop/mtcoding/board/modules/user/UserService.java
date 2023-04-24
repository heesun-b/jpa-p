package shop.mtcoding.board.modules.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getPage(Pageable page) {
        return null;
    }

    public Optional<User> getUser(Integer id) {
        return null;
        // return userRepository.findById(id);
    }

    public User save(UserSaveRequest request) {

        return null;
//        return userRepository.save(new User(null, request.getUsername(), request.getPassword()));

    }
}

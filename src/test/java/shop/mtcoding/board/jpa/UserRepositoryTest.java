package shop.mtcoding.board.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import shop.mtcoding.board.modules.user.User;
import shop.mtcoding.board.modules.user.UserRepository;

@DataJpaTest
// @SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndSelect_test() {
        User user1 = new User(
                1,
                "ssar",
                "1234");

        User user2 = new User(
                2,
                "cos",
                "1234");

        User saveUser1 = userRepository.save(user1);
        User saveUser2 = userRepository.save(user2);

        Optional<User> findUser = userRepository.findById(saveUser1.getId());
        if (findUser.isPresent()) {
            Assertions.assertThat(findUser.get().getUsername()).isEqualTo("ssar");
        }

        List<User> users = userRepository.findAll();
        Assertions.assertThat(users.size()).isNotEqualTo(0);

        boolean match1 = users.stream().anyMatch(user -> user.getUsername().equals("ssar"));
        boolean match2 = users.stream().allMatch(user -> user.getPassword().equals("1234"));

        Assertions.assertThat(match1).isEqualTo(true);
        Assertions.assertThat(match2).isEqualTo(true);

        List<User> filterList = users.stream().filter(user -> user.getId().equals(1)).toList();
        filterList.forEach(user -> {
            Assertions.assertThat(user.getUsername()).isEqualTo("ssar");
            Assertions.assertThat(user.getPassword()).isEqualTo("1234");
        });

        List<User> filterList2 = users.stream().filter(user -> user.getPassword().equals("1234")).toList();
        Assertions.assertThat(filterList2.size()).isEqualTo(2);

        List<String> nameList = users.stream().map(user -> user.getUsername()).toList();

        List<String> newNameList = new ArrayList<>();
        for (User user : users) {
            newNameList.add(user.getUsername());
        }

        saveUser1.setUsername("love");
        saveUser1.setPassword("4567");

        User updateUser = userRepository.save(saveUser1);
        Assertions.assertThat(updateUser.getUsername()).isEqualTo("love");
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("4567");

        userRepository.delete(updateUser);

        Optional<User> optionalUser = userRepository.findById(updateUser.getId());

        Assertions.assertThat(optionalUser.isEmpty()).isEqualTo(true);

    }

    public void findAll_test() {

    }
}

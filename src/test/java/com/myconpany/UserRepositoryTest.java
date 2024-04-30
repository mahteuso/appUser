package com.myconpany;

import com.myconpany.repository.UserRepository;
import com.myconpany.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("mateus@gmail.com");
        user.setPassword("surftesk8");
        user.setFirstName("Mateus");
        user.setLastName("Laranjeira");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAll() {
        Iterable<User> user = repo.findAll();
        Assertions.assertThat(user).hasSizeGreaterThan(0);

        for (User us : user) {
            System.out.println(us);
        }
    }

    @Test
    public void testListUserById() {
        int id = 4;
        try {
            Optional<User> optionalUser = repo.findById(id);
            Assertions.assertThat(optionalUser).isPresent();
            System.out.println(optionalUser.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUpdate() {
        int userId = 1;
        String newLastName = "Silva";
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setLastName(newLastName);
        repo.save(user);
        User userFound = repo.findById(userId).get();
        Assertions.assertThat(userFound.getLastName()).isEqualTo(newLastName);
    }

    @Test
    public void testDelete() {
        int userId = 1;
        Boolean isPresent = repo.findById(userId).isPresent();
        if (isPresent) {
            try {
                User user = repo.findById(userId).get();
                repo.delete(user);
                isPresent = repo.findById(userId).isPresent();
                Assertions.assertThat(isPresent).isFalse();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}

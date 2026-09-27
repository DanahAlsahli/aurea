package org.example.aurea.Service;

import lombok.RequiredArgsConstructor;
import org.example.aurea.Model.User;
import org.example.aurea.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Create
    public void addUser(User user) {
        userRepository.save(user);
    }

    // Read All
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read One
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    // Update
    public boolean updateUser(Integer id, User user) {

        User oldUser = userRepository.findById(id).orElse(null);

        if (oldUser == null) {
            return false;
        }

        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());

        userRepository.save(oldUser);

        return true;
    }

    // Delete
    public boolean deleteUser(Integer id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        userRepository.delete(user);

        return true;
    }
}

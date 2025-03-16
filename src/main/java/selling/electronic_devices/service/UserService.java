package selling.electronic_devices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selling.electronic_devices.model.User;
import selling.electronic_devices.repository.UserRepository;


import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUserIfNotExist(String email, String name, String picture) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFullName(name);
            newUser.setPicture(picture);
            return userRepository.save(newUser);
        });
    }
    public void saveJwtToken(String email, String token) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setJwtToken(token); // Cập nhật token
        userRepository.save(user); // Lưu lại vào DB
    }
}

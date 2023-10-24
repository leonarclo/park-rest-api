package com.leonardolima.parkrestapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonardolima.parkrestapi.entities.User;
import com.leonardolima.parkrestapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    @Transactional
    public User saveUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Usuário já existe no banco de dados!"));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Transactional
    public User updatePassword(Long id, String current_password, String new_password, String confirm_new_password) {
        User user = getUserById(id);
        if (!user.getPassword().equals(current_password)) {
            throw new RuntimeException("A senha atual não confere.");            
        }
        if (!new_password.equals(confirm_new_password)) {
            throw new RuntimeException("A nova senha não confere com a confirmação da nova senha.");
        }

        user.setPassword(confirm_new_password);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

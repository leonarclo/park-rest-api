package com.leonardolima.parkrestapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonardolima.parkrestapi.entities.User;
import com.leonardolima.parkrestapi.exceptions.UserUniqueViolationException;
import com.leonardolima.parkrestapi.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    @Transactional
    public User saveUser(User user) {
        try {
            
        } catch (DataIntegrityViolationException exception) {
            throw new UserUniqueViolationException(String.format("O usuário " + user.getName() + " já está cadastrado."));
        }

        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent()) {
            throw new RuntimeException("Este usuário já existe no banco de dados");
        }
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

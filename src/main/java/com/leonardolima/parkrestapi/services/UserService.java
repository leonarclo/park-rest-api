package com.leonardolima.parkrestapi.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonardolima.parkrestapi.entities.User;
import com.leonardolima.parkrestapi.exceptions.EntityNotFoundException;
import com.leonardolima.parkrestapi.exceptions.PasswordInvalidException;
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
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserUniqueViolationException("O usuário com o email '" + user.getEmail() + "' já tem cadastro no banco de dados.");
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    @Transactional
    public User updatePassword(Long id, String current_password, String new_password, String confirm_new_password) {
        User user = getUserById(id);
        if (!user.getPassword().equals(current_password)) {
            throw new PasswordInvalidException("A senha atual não confere.");
        }
        if (!new_password.equals(confirm_new_password)) {
            throw new PasswordInvalidException("A nova senha não confere com a confirmação da nova senha.");
        }

        user.setPassword(confirm_new_password);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

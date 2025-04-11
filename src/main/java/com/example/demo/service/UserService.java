package com.example.demo.service;

import com.example.demo.exception.*;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User getUser(String username)
    {   User user= userRepository.findByUsername(username).orElseThrow(()-> new InvalidLogin("Credenciales incorrectas"));
        return user;
    }
    public User registerUser(String username, String password, String email) {

        String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailFormat("El formato del correo electrónico no es válido.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyInUse("El nombre de usuario ya está en uso.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyInUse("El correo electrónico ya está en uso.");
        }
        if ( !Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$")
                .matcher(password)
                .find()) {
            throw new InvalidPasswordFormat("La contraseña no cumple con los requisitos.");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user= userRepository.findByUsername(username).orElseThrow(()-> new InvalidLogin("Credenciales incorrectas"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new InvalidLogin("Credenciales incorrectas");
    }

    @Transactional
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

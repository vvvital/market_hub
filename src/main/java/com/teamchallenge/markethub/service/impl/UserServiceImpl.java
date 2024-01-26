package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.error.ErrorMessages;
import com.teamchallenge.markethub.error.exception.UserExistException;
import com.teamchallenge.markethub.error.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.repository.UserRepository;
import com.teamchallenge.markethub.service.UserService;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(@NonNull User user) {
        String encodePassword = PasswordEncoderFactories.createDelegatingPasswordEncoder()
                .encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }


    @Override
    public void remove(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(user.orElseThrow(() ->
                new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.text())));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean findByEmailAndPhone(String email, String phone) throws UserExistException {
        User user = userRepository.findByEmailOrPhone(email, phone);
        if (user != null) {
            throw new UserExistException();
        }
        return false;
    }

    @Override
    public User findById(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.text()));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

package com.teamchallenge.markethub.service.impl;

import com.teamchallenge.markethub.exception.UserNotFoundException;
import com.teamchallenge.markethub.model.User;
import com.teamchallenge.markethub.model.role.Role.java.Role;
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
import java.util.SimpleTimeZone;

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
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Integer id) {
        Optional<User> user = userRepository.findById(id);
        User removeUser = user.get();
        userRepository.delete(removeUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User findById(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User with this 'id' does not exist"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByRole() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

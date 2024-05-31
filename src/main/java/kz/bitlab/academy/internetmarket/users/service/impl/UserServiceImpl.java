package kz.bitlab.academy.internetmarket.users.service.impl;

import kz.bitlab.academy.internetmarket.core.exception.NotFoundException;
import kz.bitlab.academy.internetmarket.users.dto.UserChangePassword;
import kz.bitlab.academy.internetmarket.users.dto.UserCreate;
import kz.bitlab.academy.internetmarket.users.dto.UserUpdate;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import kz.bitlab.academy.internetmarket.users.mapper.UserMapper;
import kz.bitlab.academy.internetmarket.users.repository.UserRepository;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public String create(UserCreate input) {

        var response = validateUserCreation(input);
        var entity = UserMapper.INSTANCE.toEntity(input);


        entity.setPassword(passwordEncoder.encode(input.getPassword()));
        repository.save(entity);

        return response;
    }

    @Transactional
    @Override
    public String update(UserUpdate input) {
        UserEntity entity = UserMapper.INSTANCE.toEntity(getCurrentUser(), input);
        repository.save(entity);

        return "redirect:/profile";
    }

    @Transactional(readOnly = true)
    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserEntity) {
            return (UserEntity) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    private UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public String changePassword(UserChangePassword input) {
        var entity = getCurrentUser();
        var response = validateUserChangePass(entity.getPassword(), input);

        if (response == null) {

            entity.setPassword(passwordEncoder.encode(input.getNewPassword()));
            repository.save(entity);
            return "redirect:/profile?success";
        }

        return response;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    private UserEntity getEntityByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
    }

    private String validateUserCreation(UserCreate input) {

        if (repository.existsByUsername(input.getUsername()))
            return "redirect:/signUp?alreadyExists";

        if (!input.getPassword().equals(input.getRePassword()))
            return "redirect:/signUp?passwordsNotSame";

        return "redirect:/login";
    }

    private String validateUserChangePass(String oldPassword, UserChangePassword input) {

        if (!passwordEncoder.matches((input.getOldPassword()),oldPassword))
            return "redirect:/profile?wrongOldPassword";

        if (!input.getNewPassword().equals(input.getReNewPassword()))
            return "redirect:/profile?passwordsNotSame";

        return null;
    }

}

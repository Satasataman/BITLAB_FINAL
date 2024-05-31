package kz.bitlab.academy.internetmarket.users.service;

import kz.bitlab.academy.internetmarket.users.dto.UserChangePassword;
import kz.bitlab.academy.internetmarket.users.dto.UserCreate;
import kz.bitlab.academy.internetmarket.users.dto.UserUpdate;
import kz.bitlab.academy.internetmarket.users.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

        String create(UserCreate input);

        String update(UserUpdate input);

        String changePassword(UserChangePassword input);

        void delete(Long id);

        UserEntity getCurrentUser();

}

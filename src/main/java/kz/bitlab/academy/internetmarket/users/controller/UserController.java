package kz.bitlab.academy.internetmarket.users.controller;

import kz.bitlab.academy.internetmarket.users.dto.UserChangePassword;
import kz.bitlab.academy.internetmarket.users.dto.UserCreate;
import kz.bitlab.academy.internetmarket.users.dto.UserUpdate;
import kz.bitlab.academy.internetmarket.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //ошибка не пропадает при смене пароля
    @GetMapping("/profile")
    public String profilePage(@RequestParam(value = "wrongOldPassword", required = false) String wrongOldPassword,
                              @RequestParam(value = "passwordsNotSame", required = false) String passwordsNotSame,
                              Model model) {

        if (wrongOldPassword != null) {
            model.addAttribute("errorMessage", "You entered wrong old password.");
        } else if (passwordsNotSame != null) {
            model.addAttribute("errorMessage", "New passwords are not the same");
        }

        model.addAttribute("user", userService.getCurrentUser());

        return "profile";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", userService.getCurrentUser());

        return "register";
    }

    @PostMapping("/profile/create")
    public String createUser(UserCreate input) {

        return userService.create(input);
    }

    @PostMapping("/profile/update")
    public String updateUser(UserUpdate input) {

        return userService.update(input);
    }

    @PostMapping("/profile/change-pass")
    public String changePass(UserChangePassword input) {

        return userService.changePassword(input);
    }

    @PostMapping("/profile/delete")
    public String deleteUser(Long id) {

        userService.delete(id);

        return "redirect:/login";
    }

}

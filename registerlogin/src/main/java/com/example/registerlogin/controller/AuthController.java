package com.example.registerlogin.controller;

import ch.qos.logback.core.model.Model;
import com.example.registerlogin.dto.UserDto;
import com.example.registerlogin.entity.User;
import com.example.registerlogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    @ModelAttribute("user")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAtribute("user", user);
        return "register";
    }

    @PostMapping("register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail((userDto.getEmail()));

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "Ezzel az email címmel már regisztráltak!");
        }

        if(result.hasErrors()){
            model.addAtribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";

    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAtribute("users", users);
        return "users";
    }

}

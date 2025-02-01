package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.entity.UserType;
import com.jobportal.service.UserService;
import com.jobportal.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private UserTypeService userTypeService;
    private final UserService userService;
    @Autowired
    public UserController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UserType> listUserTypes = userTypeService.getAllUserTypes();
        model.addAttribute("getAllTypes", listUserTypes);
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid User user, Model model){
        // System.out.println("User :: "+user);

        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            model.addAttribute("error", "Email already exists");
            List<UserType> listUserTypes = userTypeService.getAllUserTypes();
            model.addAttribute("getAllTypes", listUserTypes);
            model.addAttribute("user", new User());
            return "register";
        }

        userService.createNewUser(user);
        return "dashboard";
    }
}

package com.jobportal.controller;

import com.jobportal.entity.User;
import com.jobportal.entity.UserType;
import com.jobportal.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    private UserTypeService userTypeService;
    @Autowired
    public UserController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/register")
    public String register(Model model){
        List<UserType> listUserTypes = userTypeService.getAllUserTypes();
        model.addAttribute("getAllTypes", listUserTypes);
        model.addAttribute("user", new User());
        return "register";
    }
}

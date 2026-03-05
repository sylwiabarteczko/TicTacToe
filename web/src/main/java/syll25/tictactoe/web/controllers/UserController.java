package syll25.tictactoe.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import syll25.tictactoe.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,@RequestParam int age, Model model) {
        try {
            userService.register(username, password, age);
            return "redirect:/user/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        } return "registration";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

}
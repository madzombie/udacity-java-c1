package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService=userService;
    }
    @GetMapping
    public String getSignup() {
        return "signup";
    }

    @PostMapping
    public String signupUser (@ModelAttribute User user, Model model) {
        String signupError=null;
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError="The username already exists.";
        }
        if (signupError==null) {
           int userId= userService.createUser(user);
           if (userId<0) {
               signupError="Please try again...";
           }
        }

        if (signupError==null) {
            model.addAttribute("signupSuccess",true);
            return "redirect:/login?ok=true";

        }else {
            model.addAttribute("signupError",signupError);
        }


        return "signup";
    }
}

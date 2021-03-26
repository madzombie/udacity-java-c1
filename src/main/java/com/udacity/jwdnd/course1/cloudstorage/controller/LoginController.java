package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    // @RequestParam(value = "success", required=false) String success,
    @GetMapping
    public String loginView () {
        return "login";
    }


}

package zhumaniyazov.boot.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.RoleService;
import zhumaniyazov.boot.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String show(Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("authUser",authUser);
        model.addAttribute("users", userService.getAllUsers());
        return "user";
    }

}








































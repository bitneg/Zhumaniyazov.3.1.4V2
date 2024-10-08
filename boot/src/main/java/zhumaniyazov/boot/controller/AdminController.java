package zhumaniyazov.boot.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.RoleService;
import zhumaniyazov.boot.service.UserService;
import zhumaniyazov.boot.util.UserValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserValidator userValidator;
    private final UserService userService;

    private final RoleService roleService;


    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String index(Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authUser", authUser);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());


        return "admin";

    }


    @PostMapping
    public String create(Model model, @ModelAttribute("newUser") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/admin";
        }
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUsers());

        return "redirect:/admin";

    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


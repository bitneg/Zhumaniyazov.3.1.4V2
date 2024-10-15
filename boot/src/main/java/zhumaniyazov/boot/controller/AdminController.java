package zhumaniyazov.boot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhumaniyazov.boot.exception.UserNotFoundException;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.UserService;
import zhumaniyazov.boot.util.UserValidator;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserValidator userValidator;
    private final UserService userService;




    public AdminController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }


    @GetMapping()
    public String index(@AuthenticationPrincipal User authUser, Model model) {
                model.addAttribute("authUser",authUser);
        logger.info("Пользователь {} зашел в админку", authUser.getUsername());
        return "admin";

    }


    @PostMapping
    public String create(Model model, @ModelAttribute("newUser") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()) {
            logger.warn("Ошибка валидации при создании пользователя: {}", user.getUsername());
            return "/admin";
        }
        userService.saveUser(user);
        model.addAttribute("users", userService.getAllUsers());
        logger.info("Пользователь {} успешно создан", user.getUsername());
        return "redirect:/admin";

    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, @PathVariable("id") long id) {
        try {
            userService.updateUser(id, user);
            logger.info("Пользователь с ID {} обновлен", id);
        } catch (UserNotFoundException e) {
            logger.warn("Попытка обновить несуществующего пользователя с ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    private String delete(@PathVariable("id") long id){
        userService.deleteUser(id);
        logger.info("Пользователь с ID {} удален", id);
        return "redirect:/admin";
    }



}

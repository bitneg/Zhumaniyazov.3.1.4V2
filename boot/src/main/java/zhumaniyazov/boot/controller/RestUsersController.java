package zhumaniyazov.boot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import zhumaniyazov.boot.exception.UserNotFoundException;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class RestUsersController {
    private static final Logger logger = LoggerFactory.getLogger(RestUsersController.class);
    private final UserService userService;

    @Autowired
    public RestUsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Запрос на получение всех пользователей");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
        logger.info("Пользователь {} успешно создан", user.getUsername());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            logger.info("Пользователь с ID {} удален", id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.warn("Попытка удалить несуществующего пользователя с ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            logger.info("Запрос на получение пользователя с ID {}", id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logger.warn("Пользователь с ID {} не найден", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable("id") Long id) {
        try {
            userService.updateUser(id, user);
            logger.info("Пользователь с ID {} обновлен", id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            logger.warn("Попытка обновить несуществующего пользователя с ID {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    }
}
package zhumaniyazov.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zhumaniyazov.boot.model.User;
import zhumaniyazov.boot.service.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class RestUsersController {
    private final UserService userService;


    @Autowired
    public RestUsersController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping ("/{id}")
    public ResponseEntity<User> update (@RequestBody User user, @PathVariable("id") Long id) {
        userService.updateUser(id,user);
        return ResponseEntity.ok(user);
    }




}

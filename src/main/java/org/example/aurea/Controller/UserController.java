package org.example.aurea.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.aurea.Model.User;
import org.example.aurea.Service.UserService;
import org.example.aurea.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        userService.addUser(user);

        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers() {

        List<User> users = userService.getAllUsers();

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {

        User user = userService.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("User not found"));
        }

        return ResponseEntity.status(200).body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Integer id,
            @RequestBody @Valid User user,
            Errors errors) {

        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean updated = userService.updateUser(id, user);

        if (!updated) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("User not found"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        boolean deleted = userService.deleteUser(id);

        if (!deleted) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("User not found"));
        }

        return ResponseEntity.status(200)
                .body(new ApiResponse("User deleted successfully"));
    }
}

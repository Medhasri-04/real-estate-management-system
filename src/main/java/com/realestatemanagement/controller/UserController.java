package com.realestatemanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update-phone")
    public ResponseEntity<?> updatePhone(@RequestParam String newPhone) {
        userService.updatePhone(getLoggedInUserId(), newPhone);
        return ResponseEntity.ok(new ApiResponse("Phone updated"));
    }

    @PutMapping("/update-email")
    public ResponseEntity<?> updateEmail(@RequestParam String newEmail) {
        userService.updateEmail(getLoggedInUserId(), newEmail);
        return ResponseEntity.ok(new ApiResponse("Email updated"));
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestParam String oldPassword,
                                            @RequestParam String newPassword) {
        userService.updatePassword(getLoggedInUserId(), oldPassword, newPassword);
        return ResponseEntity.ok(new ApiResponse("Password updated"));
    }

    private Long getLoggedInUserId() {
        // extract userId from SecurityContextHolder (JWT)
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    static class ApiResponse {
        private String message;
        public ApiResponse(String message) { this.message = message; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}

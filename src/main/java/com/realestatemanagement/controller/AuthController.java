package com.realestatemanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.realestatemanagement.dto.request.LoginRequestDTO;
import com.realestatemanagement.dto.request.RegisterRequestDTO;
import com.realestatemanagement.dto.response.JwtResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ================= 1) Register User =================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO dto) {
        String message = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(message)
        );
    }

    // ================= 2) Login =================
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        JwtResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    // ================= 3) Logout =================
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        // JWT is stateless; client just deletes token. We can optionally blacklist tokens.
        return ResponseEntity.ok(new ApiResponse("Logged out successfully"));
    }

    // ================= 4) Forgot Password =================
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email) {
        authService.sendResetLink(email);
        return ResponseEntity.ok(new ApiResponse("Password reset link sent to email"));
    }

    // ================= 5) Reset Password =================
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String email,
                                                     @RequestParam String newPassword) {
        authService.resetPassword(email, newPassword);
        return ResponseEntity.ok(new ApiResponse("Password reset successful"));
    }

    // ================= 6) Get Profile =================
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        User user = authService.getProfile();
        return ResponseEntity.ok(user);
    }

    // ================= 7) Register Agent (ADMIN only) =================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/agent")
    public ResponseEntity<?> registerAgent(@RequestBody RegisterRequestDTO dto) {
        String message = authService.registerAgent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(message)
        );
    }

    // ================= Helper class for simple message response =================
    static class ApiResponse {
        private String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

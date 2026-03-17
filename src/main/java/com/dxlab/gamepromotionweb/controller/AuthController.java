package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.entity.User;
import com.dxlab.gamepromotionweb.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.UUID;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/redirectUser";
    }
    @GetMapping("/redirectUser")
    public String redirectUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ADMIN"));
        if (isAdmin) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPass() {
        return "ForgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassSubmit(@RequestParam("email") String email, Model model) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email không tồn tại trong hệ thống.");
        } else {
            String token = UUID.randomUUID().toString();
            //userService.saveVerificationToken(user, token);
            //emailService.sendResetMail(email, token);
            model.addAttribute("success", "Liên kết đặt lại mật khẩu đã được gửi đến email.");
        }
        return "ForgotPassword";
    }
}

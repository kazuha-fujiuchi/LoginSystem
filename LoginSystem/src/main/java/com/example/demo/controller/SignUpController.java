package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Account;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.SignUpService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    @Autowired
    private final SignUpService signUpService;

    @GetMapping("/signup")
    public String signup(@ModelAttribute SignUpForm signUpForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@ModelAttribute SignUpForm signUpForm, Model model) {
        
        // 登録処理の実行
        Account account = new Account(
            signUpForm.getUserId(), 
            signUpForm.getPass(), 
            signUpForm.getMail(), 
            signUpForm.getName(), 
            signUpForm.getAge()
        );
        boolean result = signUpService.execute(account);

        // 登録処理の成否によって処理を分岐
        if (result) { // 登録成功時
            // Modelにユーザー情報を追加
            model.addAttribute("userId", signUpForm.getUserId());
            return "signupOK";
        } else { // 登録失敗時
            model.addAttribute("errorMessage", "登録に失敗しました。既に登録されているユーザーIDの可能性があります。");
            return "signup";
        }
    }
}
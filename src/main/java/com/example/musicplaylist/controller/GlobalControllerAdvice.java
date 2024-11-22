package com.example.musicplaylist.controller;

import com.example.musicplaylist.service.SecurityService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final SecurityService securityService;

    public GlobalControllerAdvice(SecurityService securityService) {
        this.securityService = securityService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("auth", securityService.getPrincipal());
    }
}

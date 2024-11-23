package com.example.musicplaylist.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null && !requestUri.startsWith("/api/")) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }else if(statusCode == HttpStatus.FORBIDDEN.value()){
                return "redirect:/";
            }
        }

        // 에러 메시지가 있다면 모델에 추가
        if (errorMessage != null) {
            model.addAttribute("errorMsg", errorMessage.toString());
        }

        return "error/default";  // 일반 에러 페이지로 이동
    }
}
package com.example.musicplaylist.controller;

import com.example.musicplaylist.dto.request.member.MemberSignUpDtoRequest;
import com.example.musicplaylist.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "userId", required = false) String userId,
                        HttpServletRequest request, Model model) {
        basicFlashProcess(request, model, "");
        if (error != null) {
            model.addAttribute("error", true);
            model.addAttribute("userId", userId);
        }
        return "login/index";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup/index";
    }

    @PostMapping("/signup")
    public String signUpAction(@Valid @ModelAttribute MemberSignUpDtoRequest request, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (message1, message2) -> message1));

            if(request.getPasswordRe() != null && !request.getPassword().equals(request.getPasswordRe())){
                errors.put("passwordRe", "비밀번호가 일치하지 않습니다.");
            }

            redirectAttributes.addFlashAttribute("errors", errors);
        }else {
            try {
                memberService.signUp(request);
                redirectAttributes.addFlashAttribute("message", "회원가입이 완료 되었습니다! 로그인하세요.");

                return "redirect:/member/login";
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "서버에 오류가 발생했습니다");
            }
        }
        redirectAttributes.addFlashAttribute("data", request);

        return "redirect:/member/signup";
    }

    private static void basicFlashProcess(HttpServletRequest request, Model model, Object data) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            model.addAllAttributes(inputFlashMap);
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "");
        }
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", "");
        }
        if (!model.containsAttribute("data")) {
            model.addAttribute("data", data);
        }
    }
}

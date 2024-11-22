package com.example.musicplaylist.config;

import com.example.musicplaylist.common.handler.CustomAuthenticationFailureHandler;
import com.example.musicplaylist.dto.security.SnapPrincipal;
import com.example.musicplaylist.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private static final String[] requestPermitStaticUrl = new String[]{
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/js/**",
            "/scss/**",
            "/favicon.ico",
            "/member/login",
            "/member/login/**",
    };

    private static final String[] requestPermitAllUrl = new String[]{
            "/member/signup/**",
            "/member/signup",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(requestPermitStaticUrl)
                        .permitAll()
                        .requestMatchers(requestPermitAllUrl)
                        .anonymous()
//                        .requestMatchers("/api/map/place/delete", "/api/event/delete").hasRole("ADMIN")
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login/action")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")  // 쿠키 삭제
                        .permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService) {
        return username -> memberService
                .searchUser(username)
                .map(SnapPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

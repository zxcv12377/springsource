package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.board.security.CustomLoginSuccessHandler;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class securityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/sample/guest").permitAll()
                // .requestMatchers("/sample/member").hasRole("USER")
                // .requestMatchers("/sample/admin").hasRole("ADMIN")
                // .anyRequest().authenticated())
                // .httpBasic(Customizer.withDefaults());
                // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 로그인 폼 페이지
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                        .anyRequest().permitAll())

                .formLogin(login -> login.loginPage("/member/login")
                        .successHandler(successHandler())
                        .permitAll());
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));
        http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        return http.build();
    }

    @Bean // = new 한 뒤 스프링 컨데이너가 관리
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomLoginSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey",
                userDetailsService, encodingAlgorithm);
        rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);// 일주일 기억
        return rememberMeServices;
    }

    // @Bean
    // UserDetailsService users() {
    // UserDetails user = User.builder()
    // .username("user")
    // .password("{bcrypt}$2a$10$G863bZJ4d1fApTa58nqcAe59NwS2AWj4aJukp7DIhr61/s5tVkXIK")
    // .roles("USER") // ROLE_권한명
    // .build();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password("{bcrypt}$2a$10$G863bZJ4d1fApTa58nqcAe59NwS2AWj4aJukp7DIhr61/s5tVkXIK")
    // .roles("USER", "ADMIN") // ROLE_권한명
    // .build();
    // return new InMemoryUserDetailsManager(user, admin);
    // }

}
package kz.bitlab.academy.internetmarket.core.config;

import kz.bitlab.academy.internetmarket.users.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true)
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        http.authorizeRequests().requestMatchers("/css/**", "/js/**").permitAll();
        http.authorizeRequests().requestMatchers("/profile").authenticated();
        http.authorizeRequests().requestMatchers("/cart/add").authenticated();
        http.authorizeRequests().requestMatchers("/cart").authenticated();

        http
                .formLogin(form -> form
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profile")
                )
                .logout(form -> form
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}

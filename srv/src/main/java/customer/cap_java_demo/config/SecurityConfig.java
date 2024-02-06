package customer.cap_java_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http.formLogin(login -> login
                                .loginProcessingUrl("/login")
                                .loginPage("/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login?error")
                                .permitAll()).logout(logout -> logout
                                                .logoutSuccessUrl("/"))
                                .authorizeHttpRequests(authz -> authz

                                                // URLごとの認可設定記述開始

                                                .anyRequest().permitAll()
                                // 他のURLはログイン後のみアクセス可能

                                );
                return http.build();
        }
}
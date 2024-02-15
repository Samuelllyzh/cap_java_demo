package customer.cap_java_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                                return http      
                                .securityMatcher(AntPathRequestMatcher.antMatcher("/**"))      
                                .csrf(c -> c.disable()) // don't insist on csrf tokens in put, post etc.      
                                .authorizeHttpRequests(r -> r.anyRequest().permitAll())      
                                .build();
        }
}
package IssueTracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //disables csrf for restApis(It is recommended for form data)
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")  // Allow H2 access
                        .disable()
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())  // Fix H2 console frame issue
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Or use formLogin()

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var admin = User.withUsername("vedant")
                .password("{noop}password")
                .roles("ADMIN").build();

        var user = User.withUsername("user")
                .password("{noop}password123")
                .roles("USER").build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}

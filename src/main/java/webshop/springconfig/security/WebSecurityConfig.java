package webshop.springconfig.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    public static final String AUTHCOOKIENAME = "AUTH";

    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.headers(headers -> headers.disable())
            .authorizeRequests()
            .requestMatchers("/", "/home", "/categories/**", "/products/**","/h2-console/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .formLogin(form -> form.loginPage("/login")
                    .permitAll()
                    .successHandler(new CustomAuthSuccessHandler())) // login
            .logout(logout ->
                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/loggedOut")
                            .deleteCookies(AUTHCOOKIENAME)
                            .permitAll()) // /login?logout

            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select member_id, password, 'true' from member where member_id = ?")
                .authoritiesByUsernameQuery("select member_id, authority from member_authorities where member_id = ?")
                .passwordEncoder(passwordEncoder());
        return auth.build();
    }


    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/vendor/**",
                "/api/**",
                "/images/**",
                "/favicon.ico",
                "/h2-console/**");
    }
}

package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import web.config.handler.SuccessUserHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable(); - попробуйте выяснить сами, что это даёт
        http
                .authorizeRequests()
                .antMatchers("/login*").permitAll() // доступность всем
                .antMatchers( "/public/**").permitAll()
                .antMatchers( "/private/**").authenticated()
                .antMatchers("/user")
                .access("hasAnyRole('ROLE_USER','ROLE_ADMIN')") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/admin",
                        "/user/**",
                        "/addUser",
                        "/updateUser",
                        "/addRole",
                        "/removeUser")
                .access("hasAnyRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and().formLogin()  // Spring сам подставит свою логин форму
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .successHandler(successUserHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login"); // подключаем наш SuccessHandler для перенеправления по ролям


    }

    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

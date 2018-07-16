package br.com.casadocodigo.loja.conf;

import br.com.casadocodigo.loja.daos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/** Classe que configura as regras de segurança */

@EnableWebMvcSecurity //Ao contrário da @EnableWebSecurity, a @EnableWebMvcSecurity já inputa tokens CSRF nas tags <form:form> das JSPs
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDAO userDAO;

    // Configura as regras de acesso às URLs da aplicação
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/products/form").hasRole("ADMIN")
                .antMatchers("/shopping/**").permitAll()
                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .antMatchers("/products/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .anyRequest()
                    .authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/products").permitAll()
            .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/WEB-INF/views/errors/403.jsp");
    }

    // Configura a classe responsável por carregar usuários no banco e o tipo de criptografia que será usada em seu password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDAO).passwordEncoder(new BCryptPasswordEncoder());
    }

}

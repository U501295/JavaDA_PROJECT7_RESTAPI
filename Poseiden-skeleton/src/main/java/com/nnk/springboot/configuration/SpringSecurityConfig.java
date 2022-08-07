package com.nnk.springboot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Couche de sécurité apportée par Spring Security.
 * <p>
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                //.ignoringAntMatchers("/api/**")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                //.antMatchers("/api/**").permitAll()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/add/**").hasAuthority("ADMIN")
                .antMatchers("/user/validate/**").hasAuthority("ADMIN")
                .antMatchers("/user/delete/**").hasAuthority("ADMIN")
                .antMatchers("/user/update/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutSuccessUrl("/login").permitAll().invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .and()
                .exceptionHandling().accessDeniedPage("/403");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password,'true' as enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username,role from users where username=?");
        log.debug("Successful authentication");


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.debug("Successful encoding");
        return passwordEncoder;
    }

}

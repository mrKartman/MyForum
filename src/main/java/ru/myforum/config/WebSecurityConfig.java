package ru.myforum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//Класс, который при старте кофигурирует Spring Security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    //Система заходит сюда, передает на вход объект (http) и мы в нем включаем ...
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //авторизацию
                    .antMatchers("/", "/registration").permitAll()// на эту страницу полный доступ
                    .anyRequest().authenticated() // для всех остальных требуется авторизация
                .and()
                    .formLogin()
                    .loginPage("/login") // маппинг для страницы логина
                    .permitAll() // им можно пользоваться всем
                .and()
                    .logout()
                    .permitAll(); // логаутом тоже можно пользоваться всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())  // пароль не шифруется
                .usersByUsernameQuery("select username, password, active from users where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from users u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }
}

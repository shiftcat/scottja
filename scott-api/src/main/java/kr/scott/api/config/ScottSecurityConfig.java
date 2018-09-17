package kr.scott.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;


@Configuration
@EnableGlobalAuthentication
public class ScottSecurityConfig extends WebSecurityConfigurerAdapter
{


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("user1").password("user123").roles("USER1").and()
                .withUser("user2").password("user123").roles("USER2").and()
                .withUser("scott").password("scott123").roles("ADMIN");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/dept", "/api/dept/**").hasRole("USER1")
                .antMatchers("/api/emp", "/api/emp/**").hasRole("USER2")
                .and().httpBasic();
    }

}

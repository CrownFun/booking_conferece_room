package pl.filewicz.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/users/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/rooms/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/rooms/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/rooms/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/rooms/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/booking/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}

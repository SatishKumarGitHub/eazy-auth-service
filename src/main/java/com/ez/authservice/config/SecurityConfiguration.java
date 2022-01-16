package com.ez.authservice.config;

import com.ez.authservice.filter.JwtTokenGeneratorFilter;
import com.ez.authservice.filter.JwtTokenValidatorFilter;
import com.ez.authservice.filter.RequestValidationBeforeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Collections;

import static java.util.Collections.*;

@Configuration
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors().configurationSource(req -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(singletonList("http://localhost:4200/"));
            cors.setAllowedMethods(singletonList("*"));
            cors.setAllowCredentials(true);
            cors.setAllowedHeaders(singletonList("*"));
            cors.setExposedHeaders(singletonList("Authorization"));
            cors.setMaxAge(3600L);
            return cors;
        });

       /* http.csrf().ignoringAntMatchers("/contact", "/notices").
                csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())*/
        ;
        http.csrf().disable();
       /* http.authorizeRequests().
                antMatchers("/api/dashboard").authenticated()
                .antMatchers("/api/welcome").permitAll();*/
       /* http.authorizeRequests(requests -> {
            ((AuthorizedUrl)
                    requests.antMatchers("/api/dashboard", "/myAccount", "/myBalance", "/myLoans", "/myCards", "/user")).authenticated();
            requests.antMatchers("/api/welcome", "/contact", "/notices").permitAll();
        });*/

        // http.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class);

        http.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class);
        http.authorizeRequests().
                /*antMatchers("/myAccount").hasAuthority("UPDATE")
                .antMatchers("/myBalance").hasAuthority("WRITE").*/
                        antMatchers("/myAccount").hasAnyRole("USER", "ADMIN")
                .antMatchers("/myBalance").hasRole("ADMIN").
                antMatchers("/api/dashboard", "/myLoans", "/myCards", "/user")
                .authenticated().and().authorizeRequests().
                antMatchers("/api/welcome", "/contact", "/notices").permitAll();

        // http.formLogin();
        http.httpBasic();
    }


   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info(" in memory authentication called..! ");

        auth.inMemoryAuthentication().withUser("admin")
                .password("admin123").authorities("admin")
                .and()
                .withUser("user").password("user123").authorities("read")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());

    }
*/


/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails user1 = User.withUsername("admin").password("admin123").authorities("admin").build();
        UserDetails user2 = User.withUsername("user").password("user123").authorities("user").build();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        auth.userDetailsService(userDetailsManager);

    }*/

/*    @Bean
    public UserDetailsService userDetailsManager(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

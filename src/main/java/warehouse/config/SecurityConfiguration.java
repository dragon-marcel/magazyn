package warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Qualifier("dataSource")
        @Autowired
        DataSource dataSource;
        @Autowired
        BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeRequests().antMatchers("/").hasAnyAuthority("ADMIN")
                        .antMatchers("/css/**", "/js/**").permitAll()
                        .anyRequest().permitAll().
                        and().
                        formLogin().loginPage("/login").defaultSuccessUrl("/users").permitAll()
                        .and().logout().permitAll()
                        .and()
                        .exceptionHandling().accessDeniedPage("/error_403");

        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.jdbcAuthentication().dataSource(dataSource)
                        .usersByUsernameQuery("select name,password,enabled from users where name = ?")
                        .authoritiesByUsernameQuery("select name,role from users where name = ?");

        }
}
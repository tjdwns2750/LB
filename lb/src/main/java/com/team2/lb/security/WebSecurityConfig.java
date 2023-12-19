package com.team2.lb.security;


    import javax.sql.DataSource;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.crypto.factory.PasswordEncoderFactories;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;

    /**
     * Security 설정
     */
    @Configuration
    public class WebSecurityConfig {
        @Autowired
        private DataSource dataSource;

        //설정
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf().disable()
            .authorizeRequests()
            .antMatchers(
            		"/",
            		"/join",
            		"/about",
            		"/service",
            		"/package",
            		"/destination",
            		"/hometest",
            		"/serachBook",
            		"/service",
                    "/test2",
                    "/test",
                    "/member/join",
                    "/member/idCheck",
                    "/member/goSign",
                    "/img/**",
                    "/css/**",
                    "/image/**",
                    "/error",
                    "/lib/**",
                    "/js/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()					
            .loginPage("/member/loginForm")	
            .loginProcessingUrl("/member/login").permitAll()
            .usernameParameter("id")
            .passwordParameter("pw")
            .and()
            .logout()
            .logoutUrl("/member/logout")
            .logoutSuccessUrl("/").permitAll()
            .and()
            .cors()
            .and()
            .httpBasic();

            return http.build();
        }

        //인증을 위한 쿼리
        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication()
            .dataSource(dataSource)
            // 인증 (로그인)
            .usersByUsernameQuery(
                    "select id username, pw password, enabled " +
                    "from member " +
                    "where id = ?")
            // 권한
            .authoritiesByUsernameQuery(
                    "select id username, rolename role_name " +
                    "from member " +
                    "where id = ?");
        }

        // 단방향 비밀번호 암호화
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }

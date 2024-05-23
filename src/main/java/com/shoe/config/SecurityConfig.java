package com.shoe.config;

import com.shoe.Utils.Containts;
import com.shoe.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer :: disable)
                .authorizeHttpRequests(request -> request
                    .requestMatchers(HttpMethod.POST, "/api/user/**").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/user/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/user/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/api/user/**").hasAnyAuthority(Containts.ADMIN)

                    .requestMatchers(HttpMethod.GET, "/api/pay/**").permitAll()



                    .requestMatchers(HttpMethod.GET, "/api/brand/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/brand/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.PUT,"/api/brand/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,"/api/brand/**").hasAnyAuthority(Containts.ADMIN)

                    .requestMatchers(HttpMethod.GET, "/api/catetory/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/catetory/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.PUT,"/api/catetory/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.DELETE,"/api/catetory/**").hasAnyAuthority(Containts.ADMIN)

                    .requestMatchers(HttpMethod.GET,"/api/product/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/product/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/api/product/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, "/api/product/**").hasAnyAuthority(Containts.ADMIN)

                    .requestMatchers(HttpMethod.POST, "/api/order/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/order/**").hasAnyAuthority(Containts.ADMIN, Containts.USER)
                    .requestMatchers(HttpMethod.PUT, "/api/order/**").hasAnyAuthority(Containts.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, "/api/order/**").hasAnyAuthority(Containts.ADMIN)

                    .requestMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/comment/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**",configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncode());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

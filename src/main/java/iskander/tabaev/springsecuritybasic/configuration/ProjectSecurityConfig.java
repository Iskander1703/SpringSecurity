package iskander.tabaev.springsecuritybasic.configuration;


import iskander.tabaev.springsecuritybasic.customSecurity.CustomerAuthenticationProvider;
import iskander.tabaev.springsecuritybasic.filter.CsrfCookieFilter;
import iskander.tabaev.springsecuritybasic.filter.CustomAtFilter;
import iskander.tabaev.springsecuritybasic.filter.CustomLoggingAuthenticationUserFilter;
import iskander.tabaev.springsecuritybasic.filter.CustomLoggingFilter;
import iskander.tabaev.springsecuritybasic.filter.RequestValidationBeforeFilter;
import iskander.tabaev.springsecuritybasic.filter.jwt.JWTTokenGeneratorFilter;
import iskander.tabaev.springsecuritybasic.filter.jwt.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(prePostEnabled = true)
public class ProjectSecurityConfig {

    @Bean
    @ConditionalOnMissingBean(AuthenticationEventPublisher.class)
    DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
        return new DefaultAuthenticationEventPublisher(delegate);
    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//        config.setAllowedMethods(Collections.singletonList("*"));
//        config.setAllowCredentials(true);
//        config.setAllowedHeaders(Collections.singletonList("*"));
//        config.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        /**
         * Конфигурация на уроки по Method Level Security
         */

        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf((csrf) -> csrf.disable())
                //Фильтр для вставки csrf в header
                .addFilterBefore(new CustomLoggingAuthenticationUserFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new CustomAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new CustomLoggingFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myAccount", "/myBalance").hasAuthority("UPDATE")
                        .requestMatchers("/myCards", "/user").hasAuthority("WRITE")
                        .requestMatchers("/myLoans").authenticated()
                        .requestMatchers("/notices", "/contact", "/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();


        /**
         * Конфигурация на уроки по JWT
         */

//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        requestHandler.setCsrfRequestAttributeName("_csrf");
//
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//                        config.setAllowedMethods(Collections.singletonList("*"));
//                        config.setAllowCredentials(true);
//                        config.setAllowedHeaders(Collections.singletonList("*"));
//                        config.setExposedHeaders(Arrays.asList("Authorization"));
//                        config.setMaxAge(3600L);
//                        return config;
//                    }
//                }))
//                .csrf((csrf) -> csrf.disable())
//                //Фильтр для вставки csrf в header
//                .addFilterBefore(new CustomLoggingAuthenticationUserFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new CustomAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new CustomLoggingFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount", "/myBalance").hasAuthority("UPDATE")
//                        .requestMatchers("/myLoans", "/myCards", "/user").hasAuthority("WRITE")
//                        .requestMatchers("/notices", "/contact", "/register").permitAll())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//        return http.build();

        /**
         * Конфигурация на уроки по Cors и CSRF
         */

//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//        requestHandler.setCsrfRequestAttributeName("_csrf");
//
//        http
//                .securityContext((context) -> context
//                        .requireExplicitSave(false))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//                        config.setAllowedMethods(Collections.singletonList("*"));
//                        config.setAllowCredentials(true);
//                        config.setAllowedHeaders(Collections.singletonList("*"));
//                        config.setMaxAge(3600L);
//                        return config;
//                    }
//                }))
//                .csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("/contact", "/register")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                //Фильтр для вставки csrf в header
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new CustomLoggingAuthenticationUserFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new CustomAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new CustomLoggingFilter(), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount", "/myBalance").hasAuthority("UPDATE")
//                        .requestMatchers("/myLoans", "/myCards", "/user").hasAuthority("WRITE")
//                        .requestMatchers("/notices", "/contact", "/register").permitAll())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//        return http.build();

        /**
         * Кастомная конфигурация, проверяте аутентификацию к определенным адресам
         */
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/notices").authenticated()
//                        .requestMatchers("/contact").permitAll()
//                ).httpBasic(Customizer.withDefaults());
//        return http.build();


        /**
         * Кастомная конфигурация, проверяте аутентификацию к определенным адресам
         */

//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers("/myAccount").authenticated()
//                        .requestMatchers("/myBalance").authenticated()
//                        .requestMatchers("/myLoans").authenticated()
//                        .requestMatchers("/myCards").authenticated()
//                        .requestMatchers("/notices").permitAll()
//                        .requestMatchers("/contacts").permitAll()
//                )
//                .httpBasic(withDefaults())
//                .csrf(csrf -> csrf.disable());
//        return http.build();

        /**
         * Дефолтная конфигурация, проверяет аутентификацию ко всем адресам
         */
//        http.authorizeHttpRequests((requests) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.anyRequest()).authenticated();
//        });
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return (SecurityFilterChain)http.build();

        /**
         * Конфигурация, которая отклоняет все запросы
         */

//        http.authorizeHttpRequests((requests) -> {
//            requests
//                    .anyRequest().denyAll();
//        });
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();


        /**
         * Конфигурация, которая разрешает любые запросы
         */

//        http.authorizeHttpRequests((requests) -> {
//            requests
//                    .anyRequest().permitAll();
//        });
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }


//    @Bean
//    public UserDetailsService users() {
//        PasswordEncoder passwordEncoder = passwordEncoder();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.builder()
//                .username("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);

    /**
     * Второй подход по созданию UserDetails
     */

//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
//        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
//        userDetailsService.createUser(admin);
//        userDetailsService.createUser(user);
//        return userDetailsService;
    //   }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

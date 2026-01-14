package unicam.ids.HackHub.config.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import unicam.ids.HackHub.config.filter.JwtAuthenticationFilter;
import org.springframework.security.authorization.AuthorizationDecision;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/h2-console/**",
                                "/public/**",
                                "/error/**",
                                "api/hackathon/public/**",
                                "api/invites/public/**"
                        ).permitAll()

                        // Role-based protected APIs - USA PATTERN ESPLICITI
                        //TEAM - OK
                        .requestMatchers("/api/team/utente/**").hasRole("UTENTE")
                        .requestMatchers("/api/team/membroDelTeam/**").hasRole("MEMBRO_DEL_TEAM")
                        .requestMatchers("/api/team/leaderDelTeam/**").hasRole("LEADER_DEL_TEAM")

                        // HACKATHON - OK
                        .requestMatchers("/api/hackathon/leaderDelTeam/**").hasRole("LEADER_DEL_TEAM")
                        .requestMatchers("/api/hackathon/giudice/**").hasRole("GIUDICE")
                        .requestMatchers("/api/hackathon/organizzatore/**").hasRole("ORGANIZZATORE")

                        //INVITE - OK
                        .requestMatchers("/api/invites/outside/**")
                        .access((authentication, context) -> {
                            boolean forbidden = authentication.get().getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("UTENTE") ||
                                            a.getAuthority().equals("MEMBRO_DEL_TEAM") ||
                                            a.getAuthority().equals("LEADER_DEL_TEAM") ||
                                            a.getAuthority().equals("MENTORE") ||
                                            a.getAuthority().equals("GIUDICE") ||
                                            a.getAuthority().equals("ORGANIZZATORE"));

                            return new AuthorizationDecision(!forbidden);
                        })
                        .requestMatchers("/api/invites/inviteManage/**").hasAnyRole("UTENTE", "MEMBRO_DEL_TEAM", "LEADER_DEL_TEAM")
                        .requestMatchers("/api/invites/leaderDelTeam/**").hasRole("LEADER_DEL_TEAM")

                        //SUBMISSION - OK
                        .requestMatchers("/api/submission/staff/**").hasAnyRole("MENTORE", "GIUDICE", "ORGANIZZATORE")
                        .requestMatchers("/api/submission/team/**").hasAnyRole("MEMBRO_DEL_TEAM", "LEADER_DEL_TEAM")
                        .requestMatchers("/api/submission/judge/**").hasRole("GIUDICE")

                        //REPORT - OK
                        .requestMatchers("/api/report/mentor/**").hasRole("MENTORE")

                        // Anything else requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> filterRegistrationBean(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}

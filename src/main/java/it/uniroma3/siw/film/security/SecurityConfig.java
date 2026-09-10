package it.uniroma3.siw.film.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. Pagine PUBBLICHE (tutti possono vederle, come richiesto dal progetto)
                .requestMatchers("/","/regista/**", "/festival/**", "/film/**", "/css/**", "/images/**", "/error", "/api/**").permitAll()
                
                // 2. Pagine riservate all'AMMINISTRATORE (per creare/modificare dati)
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                
                // 3. Pagine riservate agli UTENTI REGISTRATI (per le recensioni)
                // Ho corretto il path aggiungendo /utente/ per farlo coincidere col tuo Controller!
                .requestMatchers("/utente/recensione/**").hasAnyAuthority("USER", "ADMIN")
                
                // 4. Qualsiasi altra richiesta necessita del login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // LA MAGIA E' QUI: Dopo il login, vai SEMPRE alla home page!
                .loginPage("/login")
                .defaultSuccessUrl("/", true) 
                .permitAll()
                
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
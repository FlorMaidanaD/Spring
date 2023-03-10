package com.egg.biblioteca;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SeguriadadWeb {

  @Autowired
  public UsuarioServicio usuarioServicio;

  /*
   * CONFIGURAR AUTENTICACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    // userDetailsService = Autentico el usuario cuando se registra
    auth.userDetailsService(usuarioServicio)
        .passwordEncoder(new BCryptPasswordEncoder());
    // Una vez autenticado, se encripta contraseña = BCryptPasswordEncoder

  }

  /*
   * CONFIGURAR SEGURIDAD HTTP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {

    // Recursos dentro de carpetas CSS/JS/IMG son accesibles a cualquier usuario
    http
        .authorizeHttpRequests()
        // Condiciono toda la clase ADMIN para que solo ADMIN pueda acceder
        .requestMatchers("/admin/*").hasRole("ADMIN")
        .requestMatchers("/css/*", "/js/*", "/img/*", "/**")
        .permitAll()

        /*
         * CONFIGURACIÓN LOGIN para loguearnos (Form login)
         */
        .and().formLogin()
        .loginPage("/login") // URL del formulario LOGIN

        // No es necesario controlador para /logincheck
        .loginProcessingUrl("/logincheck") // URL para autenticar un usuario desde SpringSecurity
        .usernameParameter("email") // Credencial nombre de usuario
        .passwordParameter("password") // Credencial contraseña
        .defaultSuccessUrl("/inicio") // Si por DEFAULT se genera un LOGIN OK va a /inicio.html
        .permitAll()

        /*
         * CONFIGURACIÓN LOGOUT para desloguearnos
         */
        .and().logout()
        // No es necesario controlador para /logout
        .logoutUrl("/logout") // Cuando Usuario cierre sesión, retorna a /LOGOUT
        .logoutSuccessUrl("/login") // En caso que se cierre OK sesión, retorna al /login.html
        .permitAll()

        .and().csrf()
        .disable();

    return http.build();
  }
}

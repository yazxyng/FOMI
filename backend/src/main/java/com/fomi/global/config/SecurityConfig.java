package com.fomi.global.config;
import com.fomi.domain.auth.service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
@Configuration
public class SecurityConfig {
 @Bean SecurityFilterChain security(HttpSecurity http, LoginService service) throws Exception {
  return http.csrf(c->c.disable()).cors(c->{})
    .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .formLogin(c->c.disable()).httpBasic(c->c.disable()).logout(c->c.disable()).requestCache(c->c.disable())
    .authorizeHttpRequests(a->a
      .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
      .requestMatchers(HttpMethod.GET,"/actuator/health","/actuator/health/**").permitAll()
      .requestMatchers(HttpMethod.POST,"/api/auth/signup","/api/auth/login","/api/auth/refresh","/api/auth/logout").permitAll()
      .anyRequest().authenticated())
    .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->{
      res.setStatus(401); res.setContentType("application/json;charset=UTF-8");
      res.getWriter().write("{\"code\":\"UNAUTHORIZED\",\"message\":\"로그인이 필요해요.\"}");
    }))
    .addFilterBefore(new OncePerRequestFilter() {
      @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain) throws ServletException,IOException {
        String header=req.getHeader("Authorization");
        if (header!=null && header.startsWith("Bearer ")) service.authenticate(header.substring(7)).ifPresent(id->
          SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(id,null,List.of())));
        chain.doFilter(req,res);
      }
    },UsernamePasswordAuthenticationFilter.class).build();
 }
}

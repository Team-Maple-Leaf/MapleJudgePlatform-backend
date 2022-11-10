package org.mapleleaf.backend.config;

import lombok.RequiredArgsConstructor;
import org.mapleleaf.backend.exception.JwtAuthenticationEntryPoint;
import org.mapleleaf.backend.jwt.JwtAuthenticationFilter;
import org.mapleleaf.backend.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class springSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // swagger_v3 관련 페이지는 인증에서 제외하기 위함
    private final String[] SWAGGER_V3 = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**"};

    // 정적 콘텐츠의 액세스는 인증을 거치지 않음.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/css/**")
                .antMatchers("/vendor/**")
                .antMatchers("/js/**")
                .antMatchers("/favicon*/**")
                .antMatchers("/img/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("https://www.maple-leaf.dev");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
            .and()
            .csrf() ///사이트 간 요청 위조 설정, 이 기능을 위해서는 프론트에서 csrf 토큰 값을 보내줘야함
            .disable()
            .formLogin().disable()
            .sessionManagement() // JWT 사용위해 Spring Security의 Session 설정을 해제
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests() // 보호된 리소스 URI에 접근 가능한지 체크
            .antMatchers("/v1/problem/**").permitAll()
            .antMatchers("/v1/problemset/**").permitAll()
            .antMatchers("/v1/auth/**").permitAll() // 전체 접근 허용
            .antMatchers(SWAGGER_V3).permitAll()
            .anyRequest().authenticated() // 인증된 사용자만 접근 가능
            .and()
            .logout()
            .logoutSuccessUrl("/")
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint);// 에러 처리
    }

}

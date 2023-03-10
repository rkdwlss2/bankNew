package shop.mtcoding.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.UserEnum;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 :  bcrtptPasswordEncoder빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    //JWT 필터 등록이 필요함

    //JWP 서버를 만들 예정 session 사용 안함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.debug("디버그 : filterChain 빈 등록됨");
        http.headers().frameOptions().disable(); // iframe 허용 안함
        http.csrf().disable(); // post맨 작동 안함 metacoding 유튜브 시큐리티 강의 참고
        http.cors().configurationSource(configurationSource());
        // jsessionId를 서버에서 관리 안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // react, 앱으로 요청할 예정
        http.formLogin().disable();
    // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증 진행 해지
        http.httpBasic().disable();

        // Exception 가로채기
        http.exceptionHandling().authenticationEntryPoint((request,response,authException)->{
            CustomResponseUtil.unAuthentication(response,"로그인을 진행해 주세요");
        });

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(""+ UserEnum.ADMIN) //최근에 공식 문서는 안붙여도 됨
                .anyRequest().permitAll();
        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        log.debug("디버그 : configurationSource cors 설정이 security filterchain 에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*"); // 모든 ip 주소 허용 (프론트앤드 IP만 허용)
        configuration.setAllowCredentials(true); // cookie요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}

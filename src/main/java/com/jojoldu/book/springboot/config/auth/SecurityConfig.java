package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
* @EnableWebSecurity 스프링 시큐리티 설정들을 활성화시켜줍니다.
* csrf().disable().headers().frameOption().disable() h2-console 화면을 사용하기 위해 해당 옵션들을 disable합니다
* authorizeRequests URL별 권한관리를 지정하는 옵션입니다. authorizeRquest가 선언되어야지만 antMathers 옵션사용가능
* antMatchers 권한 관리 대상을 지정하는 옵션입니다.
* URL, HTTP 메소드 별로 관리가 가능합니다.
* /등 지정된 URL들은 permitAll()옵션을 통해 전체 열람 권한은 주었ㅅ습니다.
* POST 메소드이면서 /api/v1/** 주소를 가느 API는 user 권한 가진 사람만 가능하도록 했습니다.
* anyRequest 설정된 값들 이외 나머지 URL들을 나타냅니다. 여기서는 authenicated()을 추가하여 나머지 URL들은 모든 인정된 사요ㅇ자들에게만
* 제공하도록 한다;
* logout().logsuSuccessUrl( {
* 로그아웃 기능에 대한 여러 설정의 진입점입니다.
* userInfoEndpoint OAuth 로그인 성공 이후 사용자 정보를 가져올 때의 설정 진입점입니다.
* oauth2Login
* oauth2 로그인 기능에 대한 여러 설정의 진입점입니다.
* userInfoEndpount
*  소셜 로그인 성공 이후 사용자 정보를 가져올때의 설정들을 담당합니다.
* userService 로셜 로그인 성공 시 후력조치를 진행상 UserService 인터페이스와 구현체를 등록합니다.
 *  리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있습니다
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}

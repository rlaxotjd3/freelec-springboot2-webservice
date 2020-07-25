package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import com.jojoldu.book.springboot.web.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/*
* 1. registrationId
*       현재 로그인 진행 중인 서비스를 구분하는 코드입니다. 지금은 구글만 사용하지만, 이후 네이버 로그인 연동 시에 네이버 로그인인지,
* 구글 로그인인지 구분하기 위해서 사용
* 
* 2. userNameAttributeName
*   OAuth2 로그인 진행 시 키가 되는 필드값을 이야기합니다. Primary Key와 같은의미입니다.
* 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않습니다. 구글의 기본 코드는 "sub" 입니다.
* 
* 3. OAuthattributes
* OAuth2UserService 를 통해 가져온 OAuth2User의 Attribute를 담을 클래스입니다. 
* 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용합니다
* 
* 4. SessionUser
*  세션에 사용자 정보를 저장하기 위한 DTo 클래스입니다.
* 왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는지 : 나중에 더 설명
*   */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

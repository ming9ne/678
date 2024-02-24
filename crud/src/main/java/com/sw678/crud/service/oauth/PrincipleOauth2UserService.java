package com.sw678.crud.service.oauth;

import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import com.sw678.crud.model.entity.socialuser.*;
import com.sw678.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PrincipleOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    @Autowired
    public PrincipleOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        String token = request.getAccessToken().getTokenValue();
        //"registraionId" 로 어떤 OAuth 로 로그인 했는지 확인 가능(google,naver등)
        System.out.println("getClientRegistration: "+ request.getClientRegistration());
        System.out.println("getAccessToken: "+ token);
        System.out.println("getAttributes: "+ super.loadUser(request).getAttributes());
        //구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴(OAuth-Clien라이브러리가 받아줌) -> code를 통해서 AcssToken요청(access토큰 받음)
        // => "userRequest"가 감고 있는 정보
        //회원 프로필을 받아야하는데 여기서 사용되는것이 "loadUser" 함수이다 -> 구글 로 부터 회원 프로필을 받을수 있다.


        /**
         *  OAuth 로그인 회원 가입
         */
        OAuth2User oAuth2User = super.loadUser(request);
        Oauth2UserInterface oauth2UserInterface = null;

        if(request.getClientRegistration().getRegistrationId().equals("google")) {
            oauth2UserInterface = new GoogleUser(oAuth2User.getAttributes());
        }
        else if(request.getClientRegistration().getRegistrationId().equals("kakao")) {
            oauth2UserInterface = new KakaoUser(String.valueOf(oAuth2User.getAttributes().get("id")),
                    String.valueOf(oAuth2User.getAttributes().get("properties")),
                    (Map)oAuth2User.getAttributes().get("kakao_account"));
        }
        else if(request.getClientRegistration().getRegistrationId().equals("naver")) {
            oauth2UserInterface = new NaverUser((Map)oAuth2User.getAttributes().get("response"));
        }
        else{
            System.out.println("지원하지 않은 로그인 서비스 입니다.");
        }

        String provider = oauth2UserInterface.getProvider(); //google , kakao
        String providerId = oauth2UserInterface.getProviderId();
        String username = provider + "_" + providerId;
        String nickname = oauth2UserInterface.getName() + "_" + provider;
        String password =  bCryptPasswordEncoder.encode("테스트"); // 패스워드 암호화 테스트
        String email = oauth2UserInterface.getEmail();
        Role role = Role.USER;

        System.out.println("============================================");
        System.out.println("provider : " + provider +
                        "\nproviderId : " + providerId +
                        "\nusername : " + username +
                        "\ntoken : " + token +
                        "\npassword : " + password +
                        "\nemail : " + email +
                        "\nrole : " + role);
        System.out.println("============================================");

        User user = userRepository.findByUsername(username);
        //처음 서비스를 이용한 회원일 경우
        if(user == null) {
            LocalDateTime createTime = LocalDateTime.now();
            user = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .password(password)
                    .email(email)
                    .role(role)
                    .token(token)
                    .provider(provider)
                    .provideId(providerId)
                    .createDate(createTime)
                    .build();

            userRepository.save(user);
        }

        return new UserDetail(user, oAuth2User.getAttributes());
    }
}

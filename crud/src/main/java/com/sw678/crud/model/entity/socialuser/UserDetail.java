//package com.sw678.crud.model.entity.socialuser;
//
//import com.sw678.crud.model.entity.User;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Map;
//
//@Data
//public class UserDetail implements UserDetails, OAuth2User {
//    private User user;
//    private Map<String, Object> attributes;
//
//    //일반 로그인 생성자
//    public UserDetail(User user) {
//        this.user = user;
//    }
//
//    //소셜 로그인 생성자
//    public UserDetail(User user, Map<String, Object> attributes ) {
//        this.user = user;
//        this.attributes = attributes;
//    }
//
//    ////////////////////////////////////////////
//    /////  메소드 오버라이드
//    ////////////////////////////////////////////
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//    @Override
//    public String getName() {
//        return user.getUsername();
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList();
//        collection.add((GrantedAuthority) () ->
//                String.valueOf(user.getRole()));
//        return collection;
//    }
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}
package com.sw678.crud.model.entity.socialuser;

import com.sw678.crud.model.entity.Role;
import com.sw678.crud.model.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class UserDetail implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    // 추가 필드
    private String nickname;
    private String email;
    private Role role;
    private String create_date;
//    private String post_content;
//    private String post_title;


    // 일반 로그인 생성자
    public UserDetail(User user, String nickname, String email, Role role, String create_date) {
        this.user = user;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.create_date = create_date;
    }

    // 소셜 로그인 생성자
    public UserDetail(User user, Map<String, Object> attributes, String nickname, String email, Role role, String create_date) {
        this.user = user;
        this.attributes = attributes;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.create_date = create_date;
    }



    ////////////////////////////////////////////
    /////  메소드 오버라이드
    ////////////////////////////////////////////

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    // 추가된 메서드
    public String getNickname() {
        return nickname;
    }

    // 추가된 메서드
    public String getEmail() {
        return email;
    }

    // 추가된 메서드
    public Role getRole() {
        return role;
    }

    // 추가된 메서드
    public String getCreate_date() {
        return create_date;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> String.valueOf(user.getRole()));
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

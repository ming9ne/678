package com.sw678.crud.model.entity.socialuser;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class KakaoUser implements Oauth2UserInterface{
    private String id;
    private String name;
    private Map<String, Object> kakaoAccount;

    @Override
    public String getProviderId() {
        return this.id;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getName() {
        String name = this.name;
        name = name.substring(10, name.length()-1);
        System.out.println("name = " + name);
        return name;
    }
}

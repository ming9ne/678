package com.sw678.crud.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class NaverUser implements Oauth2UserInterface{
    private Map<String, Object> naverAccount;

    @Override
    public String getProviderId() {
        return String.valueOf(naverAccount.get("id"));
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return String.valueOf(naverAccount.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(naverAccount.get("name"));
    }
}

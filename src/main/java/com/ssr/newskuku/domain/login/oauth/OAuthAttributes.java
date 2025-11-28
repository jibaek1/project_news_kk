package com.ssr.newskuku.domain.login.oauth;

import com.ssr.newskuku.domain.login.Provider;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private Provider provider;
    private String socialId;

    // 네이버 추가 정보
    private String gender;
    private String birthday;
    private String birthyear;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String name, String email, String picture,
                           Provider provider, String socialId,
                           String gender, String birthday, String birthyear) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.socialId = socialId;
        this.gender = gender;
        this.birthday = birthday;
        this.birthyear = birthyear;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .socialId((String) attributes.get("sub"))
                .provider(Provider.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        // nickname이 있으면 nickname, 없으면 name 사용
        String displayName = (String) response.get("nickname");
        if (displayName == null || displayName.isEmpty()) {
            displayName = (String) response.get("name");
        }

        return OAuthAttributes.builder()
                .name(displayName)
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .socialId((String) response.get("id"))
                .gender((String) response.get("gender"))
                .birthday((String) response.get("birthday"))
                .birthyear((String) response.get("birthyear"))
                .provider(Provider.NAVER)
                .attributes(response)
                .nameAttributeKey("id")
                .build();
    }
}
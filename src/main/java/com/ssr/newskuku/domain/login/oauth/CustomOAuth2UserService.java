package com.ssr.newskuku.domain.login.oauth;

import com.ssr.newskuku.domain.login.Status;
import com.ssr.newskuku.domain.login.UserAccount;
import com.ssr.newskuku.domain.login.UserInfo;
import com.ssr.newskuku.domain.login.mapper.UserAccountMapper;
import com.ssr.newskuku.domain.login.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserAccountMapper userAccountMapper;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 제공자 확인 (naver, google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2 사용자 정보 추출
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        // 사용자 정보 저장 또는 업데이트
        UserAccount userAccount = saveOrUpdate(attributes);

        // 추가 정보가 필요한 경우 세션에 플래그 설정
        if (needsAdditionalInfo(userAccount)) {
            // 추가 정보 입력 페이지로 리다이렉트하기 위한 플래그
            attributes.getAttributes().put("needsAdditionalInfo", true);
            attributes.getAttributes().put("userId", userAccount.getUserId());
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private UserAccount saveOrUpdate(OAuthAttributes attributes) {
        // 기존 사용자 확인
        UserAccount existingUser = userAccountMapper.findByProviderAndSocialId(
                attributes.getProvider(), attributes.getSocialId());

        if (existingUser != null) {
            // 기존 사용자 정보 업데이트
            existingUser.setEmail(attributes.getEmail());
            userAccountMapper.update(existingUser);
            return existingUser;
        } else {
            // 새 사용자 생성
            UserAccount newUser = UserAccount.builder()
                    .email(attributes.getEmail())
                    .provider(attributes.getProvider())
                    .socialId(attributes.getSocialId())
                    .status(Status.ACTIVE)
                    .build();

            userAccountMapper.insert(newUser);

            // UserInfo 초기 데이터 생성
            UserInfo userInfo = UserInfo.builder()
                    .userId(newUser.getUserId())
                    .profileImage(attributes.getPicture())
                    .nickname(attributes.getName())
                    .build();

            userInfoMapper.insert(userInfo);

            return newUser;
        }
    }

    private boolean needsAdditionalInfo(UserAccount userAccount) {
        UserInfo userInfo = userInfoMapper.findByUserId(userAccount.getUserId());

        if (userInfo == null) return true;

        // 생년월일로 나이 계산
        Integer age = null;
        if (userInfo.getBirthDate() != null) {
            age = Period.between(userInfo.getBirthDate(), LocalDate.now()).getYears();
        }

        // 연령, 성별, 관심 카테고리 정보가 없으면 추가 정보 필요
        return age == null ||
                userInfo.getGender() == null ||
                userInfo.getCategories() == null ||
                userInfo.getCategories().isEmpty();
    }

}
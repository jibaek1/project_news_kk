package com.ssr.newskuku.domain.login.oauth;

import com.ssr.newskuku.domain.login.*;
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
import java.util.HashMap;
import java.util.Map;

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

        // OAuth2 제공자 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("OAuth2 로그인 시도: provider={}, userNameAttributeName={}", registrationId, userNameAttributeName);

        // OAuth2 사용자 정보 추출
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        // 사용자 정보 저장 또는 업데이트
        UserAccount userAccount = saveOrUpdate(attributes);

        // 추가 정보가 필요한 경우 세션에 정보 설정
        Map<String, Object> modifiableAttributes = new HashMap<>(attributes.getAttributes());
        if (needsAdditionalInfo(userAccount)) {
            modifiableAttributes.put("needsAdditionalInfo", true);
            modifiableAttributes.put("userId", userAccount.getUserId());

            // OAuth에서 받은 정보도 함께 전달 (폼에 미리 채우기 위함)
            modifiableAttributes.put("oauthNickname", attributes.getName());
            modifiableAttributes.put("oauthGender", attributes.getGender());
            modifiableAttributes.put("oauthBirthday", attributes.getBirthday());
            modifiableAttributes.put("oauthBirthyear", attributes.getBirthyear());

            log.info("추가 정보 입력 필요: userId={}", userAccount.getUserId());
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                modifiableAttributes,
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
            log.info("기존 사용자 로그인: userId={}, email={}", existingUser.getUserId(), existingUser.getEmail());
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
            log.info("새 사용자 등록: userId={}, email={}, provider={}",
                    newUser.getUserId(), newUser.getEmail(), newUser.getProvider());

            // UserInfo 초기 데이터 생성
            UserInfo userInfo = UserInfo.builder()
                    .userId(newUser.getUserId())
                    .profileImage(attributes.getPicture())
                    .nickname(attributes.getName())
                    .build();

            // 네이버인 경우 추가 정보 미리 저장
            if (Provider.NAVER.equals(attributes.getProvider())) {
                // 성별 변환 (네이버: M/F -> MALE/FEMALE)
                if (attributes.getGender() != null) {
                    Gender gender = "M".equals(attributes.getGender()) ? Gender.MALE : Gender.FEMALE;
                    userInfo.setGender(gender);
                }

                // 생년월일 조합 (birthyear + birthday)
                if (attributes.getBirthyear() != null && attributes.getBirthday() != null) {
                    // 네이버: birthyear="1990", birthday="01-15" -> "1990-01-15"
                    String birthDate = attributes.getBirthyear() + "-" + attributes.getBirthday();
                    try {
                        userInfo.setBirthDate(LocalDate.parse(birthDate));
                    } catch (Exception e) {
                        log.warn("생년월일 파싱 실패: {}", birthDate);
                    }
                }
            }

            userInfoMapper.insert(userInfo);
            log.info("사용자 정보 생성: userId={}, nickname={}, gender={}, birthDate={}",
                    newUser.getUserId(), attributes.getName(), userInfo.getGender(), userInfo.getBirthDate());

            return newUser;
        }
    }

    private boolean needsAdditionalInfo(UserAccount userAccount) {
        UserInfo userInfo = userInfoMapper.findByUserId(userAccount.getUserId());

        if (userInfo == null) {
            log.warn("UserInfo not found for userId={}", userAccount.getUserId());
            return true;
        }

        // 생년월일로 나이 계산
        Integer age = null;
        if (userInfo.getBirthDate() != null) {
            age = Period.between(userInfo.getBirthDate(), LocalDate.now()).getYears();
        }

        // 연령, 성별, 관심 카테고리 정보가 없으면 추가 정보 필요
        boolean needsInfo = age == null ||
                userInfo.getGender() == null ||
                userInfo.getCategories() == null ||
                userInfo.getCategories().isEmpty();

        log.info("추가 정보 필요 여부: userId={}, needsInfo={}, age={}, gender={}",
                userAccount.getUserId(), needsInfo, age, userInfo.getGender());

        return needsInfo;
    }
}
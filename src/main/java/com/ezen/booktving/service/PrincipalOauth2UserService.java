package com.ezen.booktving.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ezen.booktving.auth.GoogleUserInfo;
import com.ezen.booktving.auth.KakaoMemberInfo;
import com.ezen.booktving.auth.OAuth2UserInfo;
import com.ezen.booktving.auth.PrincipalDetails;
import com.ezen.booktving.constant.Role;
import com.ezen.booktving.entity.Member;
import com.ezen.booktving.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		OAuth2UserInfo oAuth2UserInfo = null;

		String provider = userRequest.getClientRegistration().getRegistrationId(); // google

		if (provider.equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
		} else if (provider.equals("kakao")) {
			oAuth2UserInfo = new KakaoMemberInfo(oAuth2User.getAttributes());
		}

		String providerId = oAuth2UserInfo.getProviderId();
		String username = oAuth2UserInfo.getName();
		String password = "SNS로그인입니다."; // 사용자가 입력한 적은 없지만 만들어준다

		Role role = Role.ROLE_WAIT;

		Member member = memberRepository.findByProviderAndProviderId(provider, providerId);

		if (member == null) {
			member = Member.oauth2Register().memberName(username).password(password).role(role)
					.provider(provider).providerId(providerId).build();
		}
		
		return new PrincipalDetails(member, oAuth2UserInfo);
	}
}

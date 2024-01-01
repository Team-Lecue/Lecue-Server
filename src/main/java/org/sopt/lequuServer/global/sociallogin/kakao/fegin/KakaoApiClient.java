package org.sopt.lequuServer.global.sociallogin.kakao.fegin;

import org.apache.http.HttpHeaders;
import org.sopt.lequuServer.global.sociallogin.kakao.response.KakaoUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApiClient", url = "https://kapi.kakao.com")
public interface KakaoApiClient {

    //Access 토큰을 활용해서 실제 유저 정보를 가져오는 역할
    @GetMapping(value = "/v2/user/me")
    KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
package com.metacoding.refsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// SRP : 마트 점원 (메시지브로커) 세팅 해주는 클래스
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // sub(브라우저) : /바나나
    // pub(브라우저) : /바나나

    // 웹소켓 연결 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 구독, 발행 전 최초 소켓 연결을 위한 주소 -> /connect -> html은 while 없어서 javascript로 해야 한다. -> 다른 도메인일 경우 스프링은 CORS 차단
        registry.addEndpoint("/connect")
                .setAllowedOrigins("*"); // 모든 주소에서 CORS 차단 해제, 사실 안 적어도 된다. SSR이라 도메인이 같아서
    }

    // 구독, 발행 엔드포인트 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 판매자 (메세지 브로커)
        registry.enableSimpleBroker("/sub"); // 구독 주소, 응답내용이 /sub로 시작하면 발행
        registry.setApplicationDestinationPrefixes("/pub"); // 발행 주소, pub로 시작하는 모든 주소(prefix 설정)
    }
}

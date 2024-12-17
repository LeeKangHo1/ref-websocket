package com.metacoding.refsocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations sms;

    @GetMapping("/save-form")
    public String saveForm(){
        return "save-form";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("models", chatService.findAll());
        return "index";
    }

    @PostMapping("/chat")
    public String save(String msg) {
        Chat chat = chatService.save(msg);
        sms.convertAndSend("/sub/chat", chat); // 객체로 넣으면 JSON으로 바꿔서 보낸다.
        return "redirect:/";
    }

    // /pub/room
    @MessageMapping("/room") // 프리픽스 /pub 설정된거라 /pub/r1에서 pub 생략
    public void pubTest(String number) {
        System.out.println("나 요청돼? " + number);

        // bean에 private final SimpMessageSendingOperations sms; 추가
        // "/sub/" + number -> 특정 번호의 사람에게 알림 보내기 가능
        sms.convertAndSend("/sub/" + number, "hello world " + number);
    }

//    @SendTo("/sub")
//    @MessageMapping("/room") // 프리픽스 /pub 설정된거라 /pub/r1에서 pub 생략
//    public String pubTest2(String number) {
//        System.out.println("나 요청돼? " + number);
//        // private final SimpMessageSendingOperations sms; 추가
////        sms.convertAndSend("/sub", "hello world"); -> @SendTo와 return에 적음
//        return "hello world";

        // @SendTo("/sub")의 단점: /sub와 관련된 모든 곳에 알림(브로드캐스팅, 방송 같은)
        // /sub/1 이런 게 안된다.
//    }
}

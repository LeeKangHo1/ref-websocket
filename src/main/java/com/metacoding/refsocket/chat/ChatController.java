package com.metacoding.refsocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/save-form")
    public String saveForm(){
        return "save-form";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("models", chatService.findAll());
        return "index";
    }

    @GetMapping("/api")
    public ResponseEntity<?> api(){
        // 다 주는 이유. 서버 단계에서 최근 메세지만 찾기 어렵다. 프론트에서 처리한다. (다 지우고 다 넣기, 채팅 비교해서 중복 안되는 것만 출력)
        List<Chat> models = chatService.findAll();
        return ResponseEntity.ok(models);
    }

    @PostMapping("/chat")
    public String save (String msg) {
        chatService.save(msg);
        return "redirect:/";
    }

}

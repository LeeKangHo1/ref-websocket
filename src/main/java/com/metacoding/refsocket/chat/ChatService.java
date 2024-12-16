package com.metacoding.refsocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    public Chat save(String msg) {
        Chat chat = Chat.builder().msg(msg).build();
        return chatRepository.save(chat);
    }

    public List<Chat> findAll(){
        // 순서를 내림차순으로 받기 위해서. 쿼리로 적어도 된다.
        Sort desc = Sort.by(Sort.Direction.DESC, "id");
        return chatRepository.findAll(desc);
    }
}

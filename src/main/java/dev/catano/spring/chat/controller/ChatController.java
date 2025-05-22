package dev.catano.spring.chat.controller;

import dev.catano.spring.chat.model.Answer;
import dev.catano.spring.chat.model.Question;
import dev.catano.spring.chat.service.AIService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AIService aiService;

    public ChatController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/ask")
    public String getAnswer(@RequestParam(defaultValue = "Who are you and what is your purpose?") String question) {
        return aiService.getAnswer(question);
    }

    @PostMapping("/ask")
    public Answer getAnswer(@RequestBody Question question) {
        return aiService.getAnswer(question);
    }

}

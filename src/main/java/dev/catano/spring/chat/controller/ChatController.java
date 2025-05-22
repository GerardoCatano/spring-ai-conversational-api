package dev.catano.spring.chat.controller;

import dev.catano.spring.chat.model.Answer;
import dev.catano.spring.chat.model.Question;
import dev.catano.spring.chat.service.AIService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AIService aiService;

    private final ChatMemory chatMemory;

    public ChatController(AIService aiService, ChatMemory chatMemory) {
		this.aiService = aiService;
		this.chatMemory = chatMemory;
	}

    @GetMapping("/ask")
    public String getAnswer(@RequestParam(defaultValue = "Who are you and what is your purpose?") String question) {
        return aiService.getAnswer(question);
    }

    @PostMapping("/ask")
    public Answer getAnswer(@RequestBody Question question) {
        return aiService.getAnswer(question);
    }

	@SuppressWarnings("deprecation")
	@GetMapping("/history")
	public List<Message> getChatHistory() {
		log.info(">>>>> SW-chat: getting chat history <<<<<");
		return chatMemory.get("default", 50);
	}

	@DeleteMapping("/history")
	public Answer deleteChatHistory() {
		chatMemory.clear("default");
		log.info(">>>>> SW-chat: deleting chat history <<<<<");
		return new Answer("Conversation was succcessfully deleted.");
	}

}

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
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/{conversationId}/ask")
	public String getAnswer(@PathVariable String conversationId,
			@RequestParam(defaultValue = "Who are you and what is your purpose?") String question) {
		return aiService.getAnswer(conversationId, question);
	}

	@PostMapping("/{conversationId}/ask")
	public Answer getAnswer(@PathVariable String conversationId, @RequestBody Question question) {
		return aiService.getAnswer(conversationId, question);
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/{conversationId}/history")
	public List<Message> getChatHistory(@PathVariable String conversationId) {
		log.info(">>>>> SW-chat: getting chat history for conversationId: {} <<<<<", conversationId);
		return chatMemory.get(conversationId, 50);
	}

	@DeleteMapping("/{conversationId}/history")
	public Answer deleteChatHistory(@PathVariable String conversationId) {
		chatMemory.clear(conversationId);
		log.info(">>>>> SW-chat: deleting chat history for conversationId: {} <<<<<", conversationId);
		return new Answer("Conversation id " + conversationId + " was successfully deleted.");
	}

}

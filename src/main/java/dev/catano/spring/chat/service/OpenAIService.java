package dev.catano.spring.chat.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import dev.catano.spring.chat.model.Answer;
import dev.catano.spring.chat.model.Question;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenAIService implements AIService {

	private final ChatClient chatClient;

	private final Message systemMessage;

	public OpenAIService(ChatClient chatClient, Message systemMessage) {
		this.chatClient = chatClient;
		this.systemMessage = systemMessage;
	}

	@Override
	public String getAnswer(String conversationId, String question) {
		Message userMessage = new UserMessage(question);
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		ChatClient.CallResponseSpec call = chatClient.prompt(prompt).advisors(advisorSpec -> advisorSpec
				.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)).call();
		ChatResponse chatResponse = call.chatResponse();

		String answer = chatResponse.getResult().getOutput().getText();
		log.info(">>>>> SW-chat ask GET method: ({}) request: {} <<<<<", conversationId, question);
		log.info(">>>>> SW-chat ask GET method: ({}) response: {} <<<<<", conversationId, answer);
		return answer;
	}

	@Override
	public Answer getAnswer(String conversationId, Question question) {
		Message userMessage = new UserMessage(question.question());
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		ChatClient.CallResponseSpec call = chatClient.prompt(prompt).advisors(advisorSpec -> advisorSpec
				.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)).call();
		ChatResponse chatResponse = call.chatResponse();

		Answer answer = new Answer(chatResponse.getResult().getOutput().getText());
		log.info(">>>>> SW-chat ask POST method: ({}) question: {} <<<<<", conversationId, question.question());
		log.info(">>>>> SW-chat ask POST method: ({}) answer: {} <<<<<", conversationId, answer.answer());
		return answer;
	}

}

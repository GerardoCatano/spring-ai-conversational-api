package dev.catano.spring.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ChatConfig {

	@Value("classpath:prompts/system_message.txt")
	Resource systemResource;

	@Bean
	Message systemMessage() {
		return new SystemMessage(systemResource);
	}

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        return chatClientBuilder.defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory)).build();
    }

    @SuppressWarnings("deprecation")
	@Bean
    ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

}
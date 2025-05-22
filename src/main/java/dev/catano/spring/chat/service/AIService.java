package dev.catano.spring.chat.service;

import dev.catano.spring.chat.model.Answer;
import dev.catano.spring.chat.model.Question;

public interface AIService {
	
	String getAnswer (String conversationId, String question);

	Answer getAnswer (String conversationId, Question question);

}
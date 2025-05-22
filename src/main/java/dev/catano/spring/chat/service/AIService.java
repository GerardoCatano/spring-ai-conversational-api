package dev.catano.spring.chat.service;

import dev.catano.spring.chat.model.Answer;
import dev.catano.spring.chat.model.Question;

public interface AIService {
	
	String getAnswer (String question);

	Answer getAnswer (Question question);

}
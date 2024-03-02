package edu.java.scrapper.data.client;

import edu.java.scrapper.data.dto.stackoverflow.AnswersDTO;

public interface StackOverflowClient {
    AnswersDTO fetchAnswers(String questionId);
}

package edu.java.scrapper.data.api;

import edu.java.scrapper.data.dto.stackoverflow.AnswersDTO;

public interface StackOverflowClient {
    AnswersDTO fetchAnswers(String questionId);
}

package edu.java.scrapper.data.client;

import edu.java.scrapper.model.stackoverflow.StackOverflowAnswersResponse;

public interface StackOverflowClient {
    StackOverflowAnswersResponse fetchAnswers(String questionId);
}

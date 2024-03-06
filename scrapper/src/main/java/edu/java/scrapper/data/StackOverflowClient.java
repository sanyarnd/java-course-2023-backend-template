package edu.java.scrapper.data;

import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;

public interface StackOverflowClient {
    StackOverflowAnswersResponse fetchAnswers(String questionId);
}

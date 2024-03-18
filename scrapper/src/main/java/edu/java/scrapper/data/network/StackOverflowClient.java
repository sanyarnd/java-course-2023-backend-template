package edu.java.scrapper.data.network;

import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;

public interface StackOverflowClient extends ScrapperClient {
    StackOverflowAnswersResponse fetchAnswers(String questionId);
}

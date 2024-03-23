package edu.java.scrapper.data.network;

import edu.java.core.response.stackoverflow.StackOverflowAnswersResponse;

public interface StackOverflowConnector {
    StackOverflowAnswersResponse fetchAnswers(String questionId);
}

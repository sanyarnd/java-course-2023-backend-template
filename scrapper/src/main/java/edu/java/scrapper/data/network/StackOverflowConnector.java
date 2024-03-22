package edu.java.scrapper.data.network;

import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.CommentResponse;

public interface StackOverflowConnector {
    AnswerResponse fetchAnswers(String questionId);

    CommentResponse fetchComments(String questionId);
}

package edu.java.scrapper.data.network;

import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.CommentResponse;
import java.util.List;

public interface StackOverflowConnector {
    List<AnswerResponse> fetchAnswers(String questionId);

    List<CommentResponse> fetchComments(String questionId);
}

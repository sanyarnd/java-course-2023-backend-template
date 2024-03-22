package edu.java.core.model;

import edu.java.core.response.stackoverflow.AnswerResponse;
import edu.java.core.response.stackoverflow.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StackOverflowPersistenceData {
    private Integer answersCount;
    private Integer commentsCount;

    public StackOverflowPersistenceData(AnswerResponse answers, CommentResponse comments) {
        this.answersCount = answers.answers().size();
        this.commentsCount = comments.comments().size();
    }
}

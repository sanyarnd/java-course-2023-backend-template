package edu.java.scrapper.data.source;

import edu.java.scrapper.data.dto.stackoverflow.StackOverflowQuestionAnswers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface StackOverflowSource {
    @GetExchange("/questions/{ids}/answers")
    ResponseEntity<StackOverflowQuestionAnswers> fetchAnswers(@PathVariable String ids, @RequestParam String site);
}

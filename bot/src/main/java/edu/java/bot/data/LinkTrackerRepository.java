package edu.java.bot.data;

import edu.java.core.exception.ApiErrorException;
import java.util.List;

public interface LinkTrackerRepository {
    List<String> getUserTrackedLinks(Long userId) throws ApiErrorException;

    void setLinkTracked(Long userId, String link) throws ApiErrorException;

    void setLinkUntracked(Long userId, String link) throws ApiErrorException;
}

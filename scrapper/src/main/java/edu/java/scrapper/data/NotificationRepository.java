package edu.java.scrapper.data;

import edu.java.core.exception.UnrecognizableException;
import edu.java.core.request.LinkUpdateRequest;

public interface NotificationRepository {
    void update(LinkUpdateRequest request) throws UnrecognizableException;
}

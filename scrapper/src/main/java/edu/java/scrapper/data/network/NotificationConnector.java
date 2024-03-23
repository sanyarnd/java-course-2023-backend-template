package edu.java.scrapper.data.network;

import edu.java.core.request.LinkUpdateRequest;

public interface NotificationConnector {
    void update(LinkUpdateRequest request);
}

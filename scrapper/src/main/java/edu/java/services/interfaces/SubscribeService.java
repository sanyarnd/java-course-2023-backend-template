package edu.java.services.interfaces;

import edu.java.controller.dto.LinkResponse;
import java.util.List;

public interface SubscribeService {
    //Это ок, что здесь используется dto,
    //который в контроллере потом будет
    //передаваться боту?
    List<LinkResponse> getTrackedURLs(Long chatId);

    //Предполагается, что это единственное место,
    //где проверяется валидность URL в модуле. Это ок?
    void addTrackedURLs(Long chatId, String providedURL);

    void removeTrackedURLs(Long chatId, String providedURL);
}

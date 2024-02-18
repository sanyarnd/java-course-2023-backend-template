package edu.java.bot.repository.Dao;

import edu.java.bot.BotState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chat {
    public BotState botState;
    public Long chatId;
    public List<SiteUrl> trackedSites;
}

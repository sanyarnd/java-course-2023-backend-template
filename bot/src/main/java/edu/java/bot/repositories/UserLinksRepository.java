package edu.java.bot.repositories;

import edu.java.bot.model.Link;
import java.util.List;

public interface UserLinksRepository {
    List<Link> getLinksByUser(Long user);

    boolean addUserLinks(Long user, Link link);

    boolean removeUserLinks(Long user, Link link);
}

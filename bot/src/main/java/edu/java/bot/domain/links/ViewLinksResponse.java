package edu.java.bot.domain.links;

import java.util.List;

public sealed interface ViewLinksResponse {
    default List<String> links() {
        return List.of();
    }

    non-sealed class Ok implements ViewLinksResponse {
        private final List<String> links;

        public Ok(List<String> links) {
            this.links = links;
        }

        @Override
        public List<String> links() {
            return links;
        }
    }

    non-sealed class UserIsNotDefined implements ViewLinksResponse {
    }
}

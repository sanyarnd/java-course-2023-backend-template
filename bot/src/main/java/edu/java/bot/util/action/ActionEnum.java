package edu.java.bot.util.action;

import lombok.Getter;

@Getter
public enum ActionEnum {
    CANCEL(new CancelAction()),
    START(new StartAction()),
    HELP(new HelpAction()),
    TRACK(new TrackAction()),
    UNTRACK(new UntrackAction()),
    LIST(new ListAction()),
    TRACK_URL(new TrackUrlAction()),
    UNTRACK_URL(new UntrackUrlAction());

    private final Action action;

    ActionEnum(Action action) {
        this.action = action;
    }
}

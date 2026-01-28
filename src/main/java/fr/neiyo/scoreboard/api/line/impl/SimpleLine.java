package fr.neiyo.scoreboard.api.line.impl;

import com.hypixel.hytale.server.core.Message;
import fr.neiyo.scoreboard.api.line.ScoreboardLine;

import javax.annotation.Nonnull;

public class SimpleLine extends ScoreboardLine {

    private final Message message;

    public SimpleLine(@Nonnull Message message) {
        this.message = message;
    }

    @Nonnull
    @Override
    public Message value() {
        return message;
    }

    public static SimpleLine create(@Nonnull String text) {
        return create(Message.raw(text));
    }

    public static SimpleLine create(@Nonnull Message message) {
        return new SimpleLine(message);
    }
}

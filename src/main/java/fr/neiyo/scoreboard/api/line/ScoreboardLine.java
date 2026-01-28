package fr.neiyo.scoreboard.api.line;

import com.hypixel.hytale.server.core.Message;

import javax.annotation.Nonnull;

public abstract class ScoreboardLine {

    private int fontSize = 20;
    private LineAlignment alignment = LineAlignment.LEFT;

    @Nonnull
    public abstract Message value();

    @Nonnull
    public LineAlignment alignment() {
        return alignment;
    }

    public final ScoreboardLine setAlignment(@Nonnull LineAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public int fontSize() {
        return fontSize;
    }

    public final ScoreboardLine setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }
}
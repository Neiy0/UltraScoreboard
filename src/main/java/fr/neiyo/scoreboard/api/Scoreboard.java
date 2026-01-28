package fr.neiyo.scoreboard.api;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.api.line.ScoreboardLine;
import fr.neiyo.scoreboard.api.line.impl.SimpleLine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Scoreboard {

    protected final PlayerRef player;

    private final List<ScoreboardLine> lines = new ArrayList<>();
    private ScoreboardLine title;

    public Scoreboard(@Nonnull PlayerRef player) {
        this.player = player;
    }

    public abstract void setLines();

    public void reset() {
        title = null;
        lines.clear();
    }

    protected final void setTitle(@Nullable Message title) {
        if (title == null) {
            this.title = null;
            return;
        }
        this.title = new SimpleLine(title);
    }

    public final void setTitle(@Nullable ScoreboardLine title) {
        this.title = title;
    }

    public final @Nullable ScoreboardLine getTitle() {
        return title;
    }

    protected final void addLine(@Nullable ScoreboardLine line) {
        if (line == null) {
            line = new SimpleLine(Message.empty());
        }
        lines.add(line);
    }

    protected final void addLine(@Nullable Message line) {
        if (line == null) {
            line = Message.empty();
        }
        lines.add(new SimpleLine(line));
    }

    public final @Nonnull List<ScoreboardLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

}
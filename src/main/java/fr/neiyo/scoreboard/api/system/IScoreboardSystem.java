package fr.neiyo.scoreboard.api.system;

import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.api.Scoreboard;
import fr.neiyo.scoreboard.api.renderer.IScoreboardRenderer;

import javax.annotation.Nonnull;

public interface IScoreboardSystem {

    void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard);
    void setScoreboard(@Nonnull PlayerRef playerRef, @Nonnull Scoreboard scoreboard, IScoreboardRenderer renderer);

    void removeScoreboard(@Nonnull PlayerRef playerRef);

}
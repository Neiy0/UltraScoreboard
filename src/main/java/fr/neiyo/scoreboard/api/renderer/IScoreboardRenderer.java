package fr.neiyo.scoreboard.api.renderer;

import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import fr.neiyo.scoreboard.api.Scoreboard;

import javax.annotation.Nonnull;

public interface IScoreboardRenderer {
    void render(@Nonnull UICommandBuilder builder, @Nonnull Scoreboard scoreboard);
}
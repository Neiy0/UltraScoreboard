package fr.neiyo.scoreboard.core.dependence;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.core.ScoreboardHUD;

import javax.annotation.Nonnull;

public interface HudDependence extends Comparable<HudDependence> {

    void setCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef, @Nonnull ScoreboardHUD hud);
    void removeCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef);

    int getPriority();

    boolean isAvailable();

    @Override
    default int compareTo(HudDependence other) {
        return Integer.compare(other.getPriority(), this.getPriority());
    }
}
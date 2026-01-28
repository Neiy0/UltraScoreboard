package fr.neiyo.scoreboard.core.dependence.impl;

import com.buuz135.mhud.MultipleHUD;
import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.plugin.PluginBase;
import com.hypixel.hytale.server.core.plugin.PluginManager;
import com.hypixel.hytale.server.core.plugin.PluginState;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import fr.neiyo.scoreboard.core.ScoreboardHUD;
import fr.neiyo.scoreboard.core.dependence.HudDependence;

import javax.annotation.Nonnull;

public final class BuzzMultipleHUDDependence implements HudDependence {

    private final String hudId = "UltraScoreboard";

    @Override
    public void setCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef, @Nonnull ScoreboardHUD hud) {
        MultipleHUD.getInstance().setCustomHud(player, playerRef, hudId, hud);
    }

    @Override
    public void removeCustomHud(@Nonnull Player player, @Nonnull PlayerRef playerRef) {
        MultipleHUD.getInstance().hideCustomHud(player, hudId);
    }

    @Override
    public int getPriority() {
        return 999;
    }

    @Override
    public boolean isAvailable() {
        PluginManager pluginManager = PluginManager.get();
        PluginIdentifier multipleHudIdentifier = new PluginIdentifier("Buuz135", "MultipleHUD");

        PluginBase plugin = pluginManager.getPlugin(multipleHudIdentifier);
        if (plugin != null && plugin.getState() == PluginState.ENABLED) {
            return true;
        }

        return pluginManager.getAvailablePlugins().containsKey(multipleHudIdentifier);
    }
}

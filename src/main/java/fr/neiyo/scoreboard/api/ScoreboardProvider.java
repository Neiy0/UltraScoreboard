package fr.neiyo.scoreboard.api;

import fr.neiyo.scoreboard.api.system.IScoreboardSystem;

import java.util.concurrent.atomic.AtomicReference;

public final class ScoreboardProvider {

    private static final AtomicReference<IScoreboardSystem> INSTANCE = new AtomicReference<>();

    private ScoreboardProvider() {}

    public static void register(IScoreboardSystem system) {
        INSTANCE.set(system);
    }

    public static void unregister() {
        INSTANCE.set(null);
    }

    public static IScoreboardSystem get() {
        IScoreboardSystem system = INSTANCE.get();
        if (system == null) {
            throw new IllegalStateException("Scoreboard system not registered");
        }
        return system;
    }
}

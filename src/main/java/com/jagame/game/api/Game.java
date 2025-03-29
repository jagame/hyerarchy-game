package com.jagame.game.api;

import java.util.Arrays;
import java.util.List;

import static com.jagame.game.api.Game.RunStatus.CONTINUE;

public abstract class Game<T extends Player> implements Runnable, AutoCloseable {

    private final List<T> players;

    protected Game(List<T> players) {
        this.players = List.copyOf(players);
    }

    @SafeVarargs
    protected Game(T... players) {
        this(Arrays.asList(players));
    }

    @Override
    public void run() {
        RunStatus runStatus;
        do {
            runStatus = runRound(players);
        } while (runStatus == CONTINUE);
    }

    protected abstract RunStatus runRound(List<T> players);

    @Override
    public abstract void close();

    protected enum RunStatus {
        CONTINUE, FINISH
    }

}

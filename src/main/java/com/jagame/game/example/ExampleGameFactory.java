package com.jagame.game.example;

import com.jagame.game.api.Game;
import com.jagame.game.api.GameFactory;
import com.jagame.game.api.Player;

import java.util.List;

public class ExampleGameFactory implements GameFactory.Closeable {
    @Override
    public Game<?> newGameInstance() {
        return new Game<Player>() {
            @Override
            protected RunStatus runRound(List<Player> players) {
                System.out.println("Only a example game that nothing does");
                return RunStatus.FINISH;
            }

            @Override
            public void close() {
                // Nothing to close
            }
        };
    }

    @Override
    public void close() {
        // Nothing to close
    }
}

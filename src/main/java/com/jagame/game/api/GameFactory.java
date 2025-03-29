package com.jagame.game.api;

public interface GameFactory {

    Game<?> newGameInstance();

    /**
     * GameFactory, pero también AutoCloseable
     */
    interface Closeable extends GameFactory, AutoCloseable {
        @Override
        void close();
    }

}

package com.jagame.game.api;

public interface GameFactory {

    @SuppressWarnings("java:S1452")
    Game<?> newGameInstance();

    /**
     * GameFactory, pero también AutoCloseable
     */
    interface Closeable extends GameFactory, AutoCloseable {
        @Override
        void close();
    }

}

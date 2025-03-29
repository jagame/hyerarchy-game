package com.jagame.game.api;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

public final class GameFactoryRegistry implements Iterable<GameFactory>, AutoCloseable {

    private final Map<String, GameFactory.Closeable> registeredGameFactories;

    public GameFactoryRegistry() {
        this.registeredGameFactories = new ConcurrentHashMap<>();
        ServiceLoader.load(GameFactory.Closeable.class).forEach(gameFactory ->
            registeredGameFactories.put(gameFactory.getClass().getCanonicalName(), gameFactory)
        );
    }

    /**
     * Solo se permite registrar GameFactory.Closeable para poder liberar los recursos al finalizar el programa
     * @param gameName    El nombre del juego
     * @param gameFactory La fábrica de instancias del juego
     */
    public void register(String gameName, GameFactory.Closeable gameFactory) {
        registeredGameFactories.put(gameName, gameFactory);
    }

    /**
     * Solo devuelve {@linkplain GameFactory} en lugar de {@linkplain GameFactory.Closeable} porque deben ser
     * reutilizables y no queremos que quien use la API los cierre por su cuenta dejandolos en un estado inconsistente.
     *
     * @param gameName El nombre del juego del que obtener la factory
     * @return el game factory
     */
    public GameFactory getGameFactory(String gameName) {
        return registeredGameFactories.get(gameName);
    }

    @Override
    public Iterator<GameFactory> iterator() {
        var gameFactoriesIterator = registeredGameFactories.values().iterator();
        return new GameFactoriesIterator(gameFactoriesIterator);
    }

    @Override
    public void close() {
        registeredGameFactories.values()
                .forEach(GameFactory.Closeable::close);
    }

    /**
     * Como lo que nosotros tenemos es un {@linkplain Iterable} de {@linkplain GameFactory.Closeable} y un casting no es
     * suficiente para asegurar la integridad de tipos, tenemos que crear un wrapper para este.
     */
    @SuppressWarnings("ClassCanBeRecord")
    private static class GameFactoriesIterator implements Iterator<GameFactory> {

        private final Iterator<GameFactory.Closeable> realIterator;

        public GameFactoriesIterator(Iterator<GameFactory.Closeable> realIterator) {
            this.realIterator = realIterator;
        }

        @Override
        public boolean hasNext() {
            return realIterator.hasNext();
        }

        @Override
        public GameFactory next() {
            return realIterator.next();
        }

        @Override
        public void remove() {
            // Esto rompe con el principio de sustitución de liskov, pero hoy por hoy es la única forma de hacer
            //  un iterator de solo lectura
            throw new UnsupportedOperationException("Unmodifiable iterator, it doesn't support for remove");
        }
    }

}

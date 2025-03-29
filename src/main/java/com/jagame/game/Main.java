package com.jagame.game;

import com.jagame.game.api.Game;
import com.jagame.game.api.GameFactoryRegistry;

public class Main {

    /**
     * TODO
     * El ejercicio consistirá en crear las clases necesarias para poder ejecutar
     * distintos juegos simplemente registrando sus fábricas en el gameAbstractFactory. LA ÚNICA CLASE QUE SE
     * PODRÁ MODIFICAR SERÁ ESTA, y solo para añadir las distintas fábricas al gameAbstractFactory.
     * Cada fábrica pertenecerá a un juego distinto.
     * <br>
     * Entonces, en resumen, estas son las partes del ejercicio (por cada juego que se quiera añadir):
     * 1. Crear las clases específicas de los jugadores del juego (ya que cada juego implica que sus jugadores interactuen de formas distintas)
     * 1. Crear la clase del juego
     * 3. Crear la fábrica del juego
     * 4. Registrar la fábrica en el gameAbstractFactory
     */
    public static void main(String[] args) {
        try(var gameFactoryRegistry = new GameFactoryRegistry()) {
            // Añade tus fabricas de juegos (Una fábrica es la encargada de definir como se debe instanciar el juego)
            // ATENCIÓN: gameFactoryRegistry#register solo permite registrar GameFactory.Closeable (que es una extensión de GameFactory)
            //  gameFactoryRegistry.register(new MyFantasticGameFactory());

            executeAllGames(gameFactoryRegistry);
        }
    }

    private static void executeAllGames(GameFactoryRegistry gameFactoryRegistry) {
        for(var gameFactory : gameFactoryRegistry) {
            try(Game<?> game = gameFactory.newGameInstance()) {
                game.run();
            }
        }
    }

}

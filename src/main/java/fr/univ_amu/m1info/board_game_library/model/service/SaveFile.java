package fr.univ_amu.m1info.board_game_library.model.service;

import java.io.*;

public class SaveFile {

    public static void saveGameToFile(GameManager gameManager,String fileName) {

        GameState gameState = new GameState(
                gameManager.getGrid(),
                gameManager.getPlayerManager().getPlayer1(),
                gameManager.getPlayerManager().getPlayer2(),
                gameManager.getCurrentPlayer()
        );
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(gameState);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameState loadGameFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

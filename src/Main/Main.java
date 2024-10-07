package Main;

import Controller.GameController;
import Tools.SmallLogger;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SmallLogger.startNewLoggingSession();
        GameController startpoint = new GameController();
        startpoint.startGame();
    }
}

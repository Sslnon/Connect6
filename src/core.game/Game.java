package core.game;

import core.board.PieceColor;
import core.game.timer.GameTimer;
import core.game.timer.TimerFactory;
import core.game.ui.Configuration;
import core.game.ui.GameUI;
import core.game.ui.UiFactory;
import core.player.Player;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Game extends Observable implements Observer, Runnable {
    private final Referee referee;
    private Thread me;

    public Game(Player black, Player white) {
        int timeLimit = Configuration.TIME_LIMIT;
        GameTimer blackTimer = TimerFactory.getTimer("Console", timeLimit);
        black.setTimer(blackTimer);
        GameTimer whiteTimer = TimerFactory.getTimer("Console", timeLimit);
        white.setTimer(whiteTimer);
        black.setColor(PieceColor.BLACK);
        white.setColor(PieceColor.WHITE);
        black.playGame(this);
        white.playGame(this);
        this.referee = new Referee(black, white);
    }

    public Thread start() {
        this.me = new Thread(this);
        this.me.start();
        return this.me;
    }

    public void run() {
        Move currMove = null;
        int steps = 1;
        if (Configuration.GUI) {
            GameUI ui = UiFactory.getUi("GUI", this.referee.gameTitle());
            addObserver((Observer) ui);
        }
        // Start the game loop
        while (true) {
            Move move;
            if (Configuration.GUI) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Get the current player's turn
            Player currPlayer = this.referee.whoseMove();
            // If the game is over, end the game and break out of the loop
            if (this.referee.gameOver()) {
                this.referee.endingGame("F", currPlayer, currMove);
                break;
            }
            // If we have reached the maximum number of steps, end the game and break out of the loop
            if (steps > Configuration.MAX_STEP) {
                this.referee.endingGame("M", currPlayer, currMove);
                break;
            }
            // Start the current player's timer and try to get their move
            currPlayer.startTimer();
            try {
                move = currPlayer.findMove(currMove);
            } catch (Exception ex) {
                // If there is an error, end the game and print the stack trace
                this.referee.endingGame("E", currPlayer, null);
                System.out.println(Arrays.toString((Object[]) ex.getStackTrace()));
                break;
            }
            // Stop the current player's timer and check if the game has been interrupted
            currPlayer.stopTimer();
            if (Thread.interrupted()) {
                break;
            }
            // If the move is legal, notify the observers and record the move
            if (this.referee.legalMove(move)) {
                setChanged();
                notifyObservers(move);
            } else {
                this.referee.endingGame("N", currPlayer, move);
                break;

            }
            // record the move and increment the step counter
            this.referee.recordMove(move);
            steps++;
            currMove = move;
        }
    }

    public void update(Observable arg0, Object arg1) {
        if (this.me !=null)
            this.me.stop();
        this.referee.endingGame("T", null, null);
    }
}
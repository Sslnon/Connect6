package g04;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI extends core.player.AI {
    //×ß·¨3
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            this.board.makeMove(move);
            return move;
        }
        Random rand = new Random();
        int emptyCenterCells = 169;
        int emptyCells = 361;
        int consecutiveFailures = 0;
        while (true) {
            int index1, index2;
            if (emptyCenterCells > 0) {
                // roll dice for center cells
                index1 = rand.nextInt(169);
                index2 = rand.nextInt(169);
                emptyCenterCells--;
            } else {
                // roll dice for entire board
                index1 = rand.nextInt(361);
                index2 = rand.nextInt(361);
                emptyCells--;
            }
            if (index1 != index2 && this.board.get(index1) == PieceColor.EMPTY && this.board.get(index2) == PieceColor.EMPTY) {
                Move move = new Move(index1, index2);
                this.board.makeMove(move);
                return move;
            } else {
                consecutiveFailures++;
                if (consecutiveFailures == 10 && emptyCenterCells > 0) {
                    consecutiveFailures = 0;
                    System.out.println("Switching to full board");
                }
            }
        }
    }

    public String name() {
        return "G04";
    }

    Board board = new Board();

    public Board setBoard(Board board) {
        return null;
    }

    public Board getBoard() {
        return null;
    }

    @Override
    public void playGame(Game game) {
        super.playGame(game);
        board = new Board();
    }
}

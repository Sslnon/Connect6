package g03;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.Random;

public class AI extends core.player.AI {
    private int steps = 0;

    @Override
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            this.board.makeMove(move);
            return move;
        }

        this.board.makeMove(opponentMove);
        Random rand = new Random();
        int index1 = rand.nextInt(169);
        while (this.board.get(index1) != PieceColor.EMPTY) {
            index1  = rand.nextInt(169);
        }
        int index2=-1;
//        while (true) {
//            int index1 = rand.nextInt(169);
//            int index2 = rand.nextInt(169);
//            if (index1 != index2 && this.board.get(index1) == PieceColor.EMPTY && this.board.get(index2) == PieceColor.EMPTY) {
//                Move move = new Move(index1, index2);
//                this.board.makeMove(move);
//                steps++;
//                return move;
//            }
//        }
        Move move = new Move(index1, index2);
        this.board.makeMove(move);
        steps++;
        return move;
    }

    public String name() {
        return "G03";
    }

    G03Board board = new G03Board();

    public Board setBoard(Board board) {
        return null;
    }

    public Board getBoard() {
        return null;
    }

    @Override
    public void playGame(Game game) {
        super.playGame(game);
        board = new G03Board();
        steps = 0;
    }
}

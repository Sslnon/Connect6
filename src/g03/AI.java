package g03;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.List;
import java.util.Random;

public class AI extends core.player.AI {
    private int steps = 0;
    //¡Á?¡¤¡§2
    @Override
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            this.board.makeMove(move);
            return move;
        }

        this.board.makeMove(opponentMove);
        Random rand = new Random();
        int index1 = rand.nextInt(361);
        while (this.board.get(index1) != PieceColor.EMPTY) {
            index1  = rand.nextInt(361);
        }
        // Find the adjacent positions for the second piece
        List<Integer> adjIndices = this.board.getAdjacentIndices((index1 % 19),(index1 / 19));
        int[] neighbors = new int[adjIndices.size()];
        for (int i = 0; i < adjIndices.size(); i++) {
            neighbors[i] = adjIndices.get(i);
        }
        int index2=-1;
        while (index2 == -1) {
            int emptyCount = 0;
            for (int i = 0; i < neighbors.length; i++) {
                if (this.board.get(neighbors[i]) == PieceColor.EMPTY) {
                    emptyCount++;
                }
            }
            if (emptyCount > 0) {
                int randIndex = new Random().nextInt(neighbors.length);
                if (this.board.get(neighbors[randIndex]) == PieceColor.EMPTY) {
                    index2 = neighbors[randIndex];
                }
            } else {
                int randIndex = new Random().nextInt(361);
                if (this.board.get(randIndex) == PieceColor.EMPTY) {
                    index2 = randIndex;
                }
            }
        }
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
        System.out.println(this.name());
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
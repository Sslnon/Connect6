package g04;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AI extends core.player.AI {
    //¡Á?¡¤¡§3
    private int idx1 , idx2;
    private  int index1, index2;
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            this.board.makeMove(move);
            return move;
        }
        Random rand = new Random();
        int consecutiveMisses = 0;
        while (true) {

            if (consecutiveMisses >= 10) {
                // If 10 consecutive rolls fail to find an empty space, select from the entire board
                index1 = rand.nextInt(361);
                index2 = rand.nextInt(361);
            } else {
                // Check the center region for empty spaces
                int center = 10;
                int rowStart = center - 6;
                int rowEnd = center + 6;
                int colStart = center - 6;
                int colEnd = center + 6;
                boolean found = false;

                while (!found && consecutiveMisses < 10) {
                    idx1 = rand.nextInt(rowEnd - rowStart + 1) + rowStart;
                    idx2 = rand.nextInt(colEnd - colStart + 1) + colStart;
                    int idx = idx1*19+idx2;
                    if (this.board.get(idx) == PieceColor.EMPTY) {
                        found = true;
                        index1 = idx;
//                        System.out.println(this.name());
//                        System.out.println("First position : " + "( "+idx1 +","+ idx2+" );");
                        break;
                    }
                    consecutiveMisses++;
                }
                if (found) {
                    // Found an empty space in the center region
                    found = false;
                    while (!found && consecutiveMisses < 10) {
                        idx1 = rand.nextInt(rowEnd - rowStart + 1) + rowStart;
                        idx2 = rand.nextInt(colEnd - colStart + 1) + colStart;
                        int idx = idx1*19+idx2;

                        if (this.board.get(idx) == PieceColor.EMPTY) {
                            found = true;
                            index2 = idx;
//                            System.out.println("Second position : " + "( "+idx1 +","+ idx2+" );");
                            break;
                        }
                        consecutiveMisses++;
                    }
                    if (!found) {
                        continue;
                    }
                }
            }
//            System.out.println("Final two position : " + "( "+index1 +","+ index2+" );");
            Move move = new Move(index1, index2);
            this.board.makeMove(move);
            return move;
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
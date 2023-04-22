package g04;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static g03.G03Board.getAdjacentIndices;

public class AI extends core.player.AI {
    //×ß·¨3
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
                index1 = 19;
                index2 = 19;
            } else {
                // Check the center region for empty spaces
                int center = 10;
                int rowStart = center - 6;
                int rowEnd = center + 6;
                int colStart = center - 6;
                int colEnd = center + 6;
                boolean found = false;

                while (!found && consecutiveMisses < 10) {
                    index1 = rand.nextInt(rowEnd - rowStart + 1) + rowStart;
                    index2 = rand.nextInt(colEnd - colStart + 1) + colStart;
                    int idx = index1*19+index2;
                    if (this.board.get(idx) == PieceColor.EMPTY) {
                        found = true;
                        idx1 = index1;
                        idx2 = index2;
                        System.out.println("First position : " + "( "+idx1 +","+ idx2+" );");
                        break;
                    }
                    consecutiveMisses++;
                }
                if (found) {
                    // Found an empty space in the center region
                    found = false;
                    while (!found && consecutiveMisses < 10) {
                        List<Integer> adjIndices = getAdjacentIndices(idx1, idx2);
                        int[] neighbors = new int[adjIndices.size()];
                        for (int i = 0; i < adjIndices.size(); i++) {
                            neighbors[i] = adjIndices.get(i);
                        }
                        int randIdx = rand.nextInt(neighbors.length);
                        int neighbor = neighbors[randIdx];
                        if (this.board.get(neighbor) == PieceColor.EMPTY) {
                            found = true;
                            index2 = neighbor;
                            break;
                        }
                        consecutiveMisses++;
                        if (randIdx == neighbors.length - 1) {
                            // If all adjacent spaces are occupied, break and try again
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                }
            }
            //System.out.println("Final position : " + "( "+index1 +","+ index2+" );");
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

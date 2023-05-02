package gBronia;

import java.util.ArrayList;
import java.util.List;

public class Board extends core.board.Board {
    public static List<Integer> getAdjacentIndices( int row, int col) {
        List<Integer> adjIndices = new ArrayList<Integer>();
        int numRows = 19;
        int numCols = 19;

        // Check all 8 adjacent positions
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip the current position
                if (i == row && j == col) {
                    continue;
                }
                // Check if the adjacent position is on the board
                if (i >= 0 && i < numRows && j >= 0 && j < numCols) {
                    adjIndices.add(i * numCols + j);
                }
            }
        }

        return adjIndices;
    }

}

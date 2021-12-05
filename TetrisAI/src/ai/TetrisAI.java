package ai;

import edu.vt.cs5044.tetris.AI;
import edu.vt.cs5044.tetris.Board;
import edu.vt.cs5044.tetris.Placement;
import edu.vt.cs5044.tetris.Rotation;
import edu.vt.cs5044.tetris.Shape;

/**
 * 
 * Creating AI program to play Tetris The AI is to effectively read game boards and pick the best
 * placement for the different shapes on the board
 *
 * @author Garrett Smith
 * @version Oct 9, 2021
 *
 */
public class TetrisAI implements AI {

    @Override
    public Placement findBestPlacement(Board currentBoard, Shape shape) {

        int tempCost = 0;
        int overallCost = Integer.MAX_VALUE;
        Placement tempPlacement;
        Placement bestPlacement = new Placement(Rotation.NONE, 0);

        for (Rotation currentRotation : shape.getValidRotationSet()) {
            for (int col = 0; col < Board.WIDTH; col++) {
                int availableWidth = Board.WIDTH - col;
                if (shape.getWidth(currentRotation) <= availableWidth) {

                    tempPlacement = new Placement(currentRotation, col);
                    Board tempBoard = currentBoard.getResultBoard(shape, tempPlacement);
                    tempCost = (4 * getAverageColumnHeight(tempBoard))
                        + (8 * getColumnHeightRange(tempBoard))
                        + (0 * getColumnHeightVariance(tempBoard))
                        + (12 * getTotalGapCount(tempBoard));

                    if (tempCost < overallCost) {
                        overallCost = tempCost;
                        bestPlacement = tempPlacement;
                    }
                }
            }
        }
        return bestPlacement;
    }

    @Override
    public int getAverageColumnHeight(Board board) {
        int totalHeights = 0;

        for (int col = 0; col < Board.WIDTH; col++) {
            totalHeights += getColumnHeight(board, col);
        }
        return totalHeights / Board.WIDTH;
    }

    /**
     * 
     * Helper method to determine the smallest column on the board
     *
     * @param b Board object
     * @return smallest column on the board
     */
    private int getSmallestColumn(Board b) {
        int smallest = 99;

        for (int col = 0; col < Board.WIDTH; col++) {
            int column = getColumnHeight(b, col);
            if (column < smallest) {
                smallest = column;
            }
        }
        return smallest;
    }

    /**
     * 
     * Helper method to determine the smallest column on the board
     *
     * @param b Board object
     * @return largest column on the board
     */
    private int getLargestColumn(Board b) {
        int largest = 0;

        for (int col = 0; col < Board.WIDTH; col++) {
            int column = getColumnHeight(b, col);
            if (column > largest) {
                largest = column;
            }
        }
        return largest;
    }

    @Override
    public int getColumnHeightRange(Board board) {
        int columnHeightRange = getLargestColumn(board) - getSmallestColumn(board);
        return columnHeightRange;
    }

    @Override
    public int getColumnHeightVariance(Board board) {
        int variance = 0;
        for (int col = 0; col < Board.WIDTH - 1; col++) {
            variance += Math.abs(getColumnHeight(board, col) - getColumnHeight(board, col + 1));
        }
        return variance;
    }

    @Override
    public int getTotalGapCount(Board board) {
        int gapCount = 0;
        for (int col = 0; col < Board.WIDTH; col++) {
            gapCount += countGaps(board, col);
        }
        return gapCount;
    }

    /**
     * 
     * Helper method to identify and count number of gaps in a column Gaps defined as any blank
     * space where a block is found above it
     *
     * @param b Board object
     * @param col column iteration
     * @return number of gaps in a column
     */
    private int countGaps(Board b, int col) {
        boolean[] colBlocks = b.getFixedBlocks()[col];
        int height = getColumnHeight(b, col);
        int gap = 0;

        for (int i = 0; i < height; i++) {
            if (!colBlocks[i]) {
                gap++;
            }
        }
        return gap;
    }

    /**
     * 
     * Helper method to determine height of column
     *
     * @param b Board object
     * @param col column iteration
     * @return height of column
     */
    private int getColumnHeight(Board b, int col) {
        boolean[] colBlocks = b.getFixedBlocks()[col];
        int columnHeight = Board.HEIGHT;

        for (int i = Board.HEIGHT; i > columnHeight - 1; i--) {
            if (columnHeight == 0) {
                return columnHeight;
            }
            if (!colBlocks[i - 1]) {
                columnHeight--;
            }
        }
        return columnHeight;
    }

}
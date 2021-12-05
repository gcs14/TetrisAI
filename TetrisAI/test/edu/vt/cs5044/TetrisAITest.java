package edu.vt.cs5044;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ai.TetrisAI;
import edu.vt.cs5044.tetris.AI;
import edu.vt.cs5044.tetris.Board;
import edu.vt.cs5044.tetris.Placement;
import edu.vt.cs5044.tetris.Rotation;
import edu.vt.cs5044.tetris.Shape;

public class TetrisAITest {

    private AI brain;
    private Board emptyBoard;
    private Board apiBoard;
    private Board userBoard1;
    private Board userBoard2;
    private Board userBoard3;
    private Board userBoard4;
    private Board userBoard5;
    private Board board_61444_alt;
    private Board board_61444;

    @Before
    public void setup() {
        brain = new TetrisAI();
        emptyBoard = new Board();
        apiBoard = new Board(

            "          ",
            "## ##    #",
            "# ##### ##",
            "#### #####",
            "# ##### ##",
            "## #######",
            "######### ",
            " #########",
            " #########",
            "###  #####",
            "####### ##",
            "######## #",
            " #### ####");

        userBoard1 = new Board(

            "          ",
            "## ## ## #",
            " ##### ###",
            "## # ### #",
            "##  #### #",
            "## ## ####",
            "##### ### ",
            " # ## ## #",
            " ### #####",
            "##   ### #",
            "    ### ##",
            "## ##### #",
            "#### # ###");

        userBoard2 = new Board(

            "          ",
            "## ## #  #",
            "# ## ## ##",
            "#### #####",
            "# ##### ##",
            "## ### ###",
            "##  ##### ",
            " ### #### ",
            " ## ### ##",
            " #######  ",
            "###  ## # ",
            " ####  # #",
            "##### ## #");

        userBoard3 = new Board(

            "          ",
            "### # # ##",
            "# # ### ##",
            "###  ## ##",
            "# # ### ##",
            "### ## ###",
            "#   ######",
            "###  #####",
            "### ## ###",
            " ### ### #",
            "#### ## # ",
            " ####  # #",
            "##### ##  ");

        userBoard4 = new Board(

            "          ",
            "# #  ##  #",
            "# # ### ##",
            "# # ### ##",
            "###  ## ##",
            "####### # ",
            " #########",
            "#### #####",
            "### ## ###",
            "#### ### #",
            "####### ##",
            " ####  # #",
            " ####### #");

        userBoard5 = new Board(

            "          ",
            "####### ##",
            "###### ###",
            "##### ####",
            "#### #####",
            "###### ###",
            "### ######",
            "####### ##",
            "## #######",
            "######## #",
            "# ########",
            "######### ",
            " #########");

        board_61444 = new Board(
            "     ## # ",
            "# ####### ",
            "#### #####");

        board_61444_alt = new Board(
            "#    #  # ",
            "##  ######",
            "### ######");
    }

    @Test
    public void testPlacementShapeT() {
        Board placeBoardT = new Board(
            "####   ###",
            "##### ####",
            "##########");

        Placement expPlacement = new Placement(Rotation.CCW_180, 4);
        Placement actPlacement = brain.findBestPlacement(placeBoardT, Shape.T);
        assertEquals(expPlacement, actPlacement);
    }

    public void testPlacementShapeO() {
        Board placeBoardO = new Board(
            "####  ####",
            "####  ####",
            "##########");

        Placement expPlacement = new Placement(Rotation.NONE, 4);
        Placement actPlacement = brain.findBestPlacement(placeBoardO, Shape.O);
        assertEquals(expPlacement, actPlacement);
    }

    public void testPlacementShapeI() {
        Board placeBoardI = new Board(
            "#### #####",
            "#### #####",
            "#### #####",
            "#### #####",
            "##########");

        Placement expPlacement = new Placement(Rotation.NONE, 4);
        Placement actPlacement = brain.findBestPlacement(placeBoardI, Shape.I);
        assertEquals(expPlacement, actPlacement);
    }

    public void testPlacementShapeL() {
        Board placeBoardL = new Board(
            "#### #####",
            "#### #####",
            "####  ####",
            "##########");

        Placement expPlacement = new Placement(Rotation.CCW_180, 3);
        Placement actPlacement = brain.findBestPlacement(placeBoardL, Shape.L);
        assertEquals(expPlacement, actPlacement);
    }

    public void testPlacementShapeS() {
        Board placeBoardS = new Board(
            "#####  ###",
            "####  ####",
            "#### #####",
            "##########");

        Placement expPlacement = new Placement(Rotation.NONE, 5);
        Placement actPlacement = brain.findBestPlacement(placeBoardS, Shape.S);
        assertEquals(expPlacement, actPlacement);
    }

    @Test
    public void testTotalGapCount() {
        assertEquals(0, brain.getTotalGapCount(emptyBoard));
        assertEquals(14, brain.getTotalGapCount(apiBoard));
        assertEquals(31, brain.getTotalGapCount(userBoard1));
        assertEquals(29, brain.getTotalGapCount(userBoard2));
        assertEquals(21, brain.getTotalGapCount(userBoard3));
        assertEquals(15, brain.getTotalGapCount(userBoard4));
        assertEquals(11, brain.getTotalGapCount(userBoard5));
    }

    @Test
    public void testAverageColumnHeight() {
        assertEquals(0, brain.getAverageColumnHeight(emptyBoard));
        assertEquals(11, brain.getAverageColumnHeight(apiBoard));
        assertEquals(11, brain.getAverageColumnHeight(userBoard1));
        assertEquals(11, brain.getAverageColumnHeight(userBoard2));
        assertEquals(10, brain.getAverageColumnHeight(userBoard3));
        assertEquals(10, brain.getAverageColumnHeight(userBoard4));
        assertEquals(11, brain.getAverageColumnHeight(userBoard5));
    }

    @Test
    public void testColumnHeightRange() {
        assertEquals(0, brain.getColumnHeightRange(emptyBoard));
        assertEquals(2, brain.getColumnHeightRange(apiBoard));
        assertEquals(1, brain.getColumnHeightRange(userBoard1));
        assertEquals(2, brain.getColumnHeightRange(userBoard2));
        assertEquals(8, brain.getColumnHeightRange(userBoard3));
        assertEquals(5, brain.getColumnHeightRange(userBoard4));
        assertEquals(1, brain.getColumnHeightRange(userBoard5));
        assertEquals(2, brain.getColumnHeightRange(board_61444));
        assertEquals(3, brain.getColumnHeightRange(board_61444_alt));
    }

    @Test
    public void testColumnHeightVariance() {
        assertEquals(0, brain.getColumnHeightVariance(emptyBoard));
        assertEquals(6, brain.getColumnHeightVariance(apiBoard));
        assertEquals(6, brain.getColumnHeightVariance(userBoard1));
        assertEquals(8, brain.getColumnHeightVariance(userBoard2));
        assertEquals(26, brain.getColumnHeightVariance(userBoard3));
        assertEquals(24, brain.getColumnHeightVariance(userBoard4));
        assertEquals(2, brain.getColumnHeightVariance(userBoard5));
    }
}

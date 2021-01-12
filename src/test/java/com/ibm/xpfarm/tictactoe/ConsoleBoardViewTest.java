package com.ibm.xpfarm.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleBoardViewTest {

    private Board board;
    private StringWriter writer;
    private BoardView boardView;

    @BeforeEach
    void setup() {
        board = new Board();
        writer = new StringWriter();
        boardView = new ConsoleBoardView(writer);
    }

    @Test
    void givenEmptyBoard_show_shouldPrintGridsOnly() {
        // given

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("" +
                " | | \n" +
                "-+-+-\n" +
                " | | \n" +
                "-+-+-\n" +
                " | | \n");
    }

    @Test
    void givenEmptyBoard_show_shouldPrintPlayer1Turn() {
        // given

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("Player X's turn");
    }

    @Test
    void givenBoardWithOneStep_show_shouldPrintBoard() {
        // given
        board.step(1, 1);

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("" +
                " | | \n" +
                "-+-+-\n" +
                " |X| \n" +
                "-+-+-\n" +
                " | | \n");
    }

    @Test
    void givenBoardWithOneStep_show_shouldPrintPlayer2NextTurn() {
        // given
        board.step(1, 1);

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("Player O's turn");
    }

    @Test
    void givenBoardWithPlayer1Win_show_shouldPrintPlayer1Won() {
        // given
        // X
        // XO
        // X O
        board.step(0, 0); // X
        board.step(1, 1); // O
        board.step(1, 0); // X
        board.step(2, 2); // O
        board.step(2, 0); // X

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("Player X won");
    }

    @Test
    void givenBoardWithPlayer2Win_show_shouldPrintPlayer2Won() {
        // given
        // X X
        // OOO
        // X
        board.step(0, 0); // X
        board.step(1, 0); // O
        board.step(0, 2); // X
        board.step(1, 1); // O
        board.step(2, 0); // X
        board.step(1, 2); // O

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("Player O won");
    }

    @Test
    void givenBoardWithDraw_show_shouldPrintDraw() {
        // given
        // XOX
        // OOX
        // XXO
        board.step(0, 0); // X
        board.step(0, 1); // O
        board.step(0, 2); // X

        board.step(1, 0); // O
        board.step(1, 2); // X
        board.step(1, 1); // O

        board.step(2, 0); // X
        board.step(2, 2); // O
        board.step(2, 1); // X

        // when
        boardView.show(board);

        // then
        assertThat(writer.toString()).contains("Game ends with a draw");
    }

}

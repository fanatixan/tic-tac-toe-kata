package com.ibm.xpfarm.tictactoe;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void givenEmptyBoard_status_shouldReturnPlayer1Next() {
        // given

        // when
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.PLAYER_1_NEXT);
    }

    @Test
    void givenBoardWithOneStep_status_shouldReturnPlayer2Next() {
        // given
        board.step(1, 1);

        // when
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.PLAYER_2_NEXT);
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinates")
    void step_shouldNotAcceptInvalidCoordinates(int row, int column) {
        // given

        // when
        ThrowableAssert.ThrowingCallable step = () -> board.step(row, column);

        // then
        assertThatThrownBy(step).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid coordinates");
    }

    static Stream<Arguments> invalidCoordinates() {
        return Stream.of(
                Arguments.of(-1, 0),
                Arguments.of(3, 0),
                Arguments.of(0, -1),
                Arguments.of(0, 3)
        );
    }

    @Test
    void givenBoardWithOneStep_status_shouldNotAcceptNonEmptyCell() {
        // given
        board.step(1, 1);

        // when
        ThrowableAssert.ThrowingCallable step = () -> board.step(1, 1);

        // then
        assertThatThrownBy(step).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cell is not empty");
    }

    @Test
    void givenBoardWithVerticalPlayer1Row_status_shouldReturnPlayer1Won() {
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
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.PLAYER_1_WON);
    }

    @Test
    void givenBoardWithHorizontalPlayer2Row_status_shouldReturnPlayer2Won() {
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
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.PLAYER_2_WON);
    }

    @Test
    void givenBoardWithDiagonalPlayer1Row_status_shouldReturnPlayer1Won() {
        // given
        // X
        // OX
        // O X
        board.step(0, 0); // X
        board.step(1, 0); // O
        board.step(1, 1); // X
        board.step(2, 0); // O
        board.step(2, 2); // X

        // when
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.PLAYER_1_WON);
    }

    @Test
    void givenFullBoardWithoutWinner_status_shouldReturnDraw() {
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
        BoardStatus status = board.status();

        // then
        assertThat(status).isEqualTo(BoardStatus.DRAW);
    }

}

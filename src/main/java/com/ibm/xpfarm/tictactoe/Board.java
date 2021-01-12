package com.ibm.xpfarm.tictactoe;

import java.util.Arrays;

public class Board {

    public static final int BOARD_SIZE = 3;
    private static final char EMPTY_CELL = ' ';
    private static final char PLAYER_1_SYMBOL = 'X';
    private static final char PLAYER_2_SYMBOL = 'O';

    private final char[] board;
    private int stepCount = 0;
    private BoardStatus status = BoardStatus.PLAYER_1_NEXT;

    public Board() {
        board = new char[BOARD_SIZE * BOARD_SIZE];
        Arrays.fill(board, EMPTY_CELL);
    }

    public BoardStatus status() {
        return status;
    }

    private BoardStatus newStatus(int row, int column) {
        if (playerWon(row, column)) {
            return winner(row, column);
        }

        if (stepCount >= BOARD_SIZE * BOARD_SIZE) {
            return BoardStatus.DRAW;
        }

        if (stepCount % 2 == 0) {
            return BoardStatus.PLAYER_1_NEXT;
        }

        return BoardStatus.PLAYER_2_NEXT;
    }

    private boolean playerWon(int row, int column) {
        return isRowCompleted(row) || isColumnCompleted(column) || isDiagonalCompleted();
    }

    private boolean isRowCompleted(int row) {
        return isCompleted(row, 0, 0, 1);
    }

    private boolean isColumnCompleted(int column) {
        return isCompleted(0, column, 1, 0);
    }

    private boolean isDiagonalCompleted() {
        return isCompleted(0, 0, 1, 1)
                || isCompleted(0, 1, 1, -1);
    }

    private boolean isCompleted(int startRow, int startColumn, int rowIncrement, int columnIncrement) {
        char firstSymbol = cell(startRow, startColumn);
        if (firstSymbol == EMPTY_CELL) {
            return false;
        }

        for (int i = 1; i < BOARD_SIZE; i++) {
            int row = startRow + i * rowIncrement;
            int column = startColumn + i * columnIncrement;
            if (cell(row, column) != firstSymbol) {
                return false;
            }
        }

        return true;
    }

    private BoardStatus winner(int row, int column) {
        if (cell(row, column) == PLAYER_1_SYMBOL) {
            return BoardStatus.PLAYER_1_WON;
        }

        return BoardStatus.PLAYER_2_WON;
    }

    public void step(int row, int column) {
        checkValidCoordinates(row, column);
        checkCellIsEmpty(row, column);

        char symbol = nextPlayerSymbol();
        stepCount++;
        board[cellIndex(row, column)] = symbol;

        status = newStatus(row, column);
    }

    private void checkValidCoordinates(int row, int column) {
        if (row < 0 || column < 0 || row >= BOARD_SIZE || column >= BOARD_SIZE) {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }

    private void checkCellIsEmpty(int row, int column) {
        if (cell(row, column) != EMPTY_CELL) {
            throw new IllegalArgumentException("Cell is not empty");
        }
    }

    public char cell(int row, int column) {
        return board[cellIndex(row, column)];
    }

    private int cellIndex(int row, int column) {
        return row * BOARD_SIZE + column;
    }

    private char nextPlayerSymbol() {
        if (status() == BoardStatus.PLAYER_1_NEXT) {
            return PLAYER_1_SYMBOL;
        }

        return PLAYER_2_SYMBOL;
    }

}

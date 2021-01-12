package com.ibm.xpfarm.tictactoe;

public enum BoardStatus {
    PLAYER_1_NEXT("Player X's turn"),
    PLAYER_2_NEXT("Player O's turn"),

    PLAYER_1_WON("Player X won"),
    PLAYER_2_WON("Player O won"),

    DRAW("Game ends with a draw");

    private final String text;

    BoardStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

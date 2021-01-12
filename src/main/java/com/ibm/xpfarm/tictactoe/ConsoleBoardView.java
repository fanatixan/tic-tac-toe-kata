package com.ibm.xpfarm.tictactoe;

import java.io.IOException;
import java.io.Writer;

public class ConsoleBoardView implements BoardView {

    private final Writer writer;

    public ConsoleBoardView(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void show(Board board) {
        try {
            for (int i = 0; i < Board.BOARD_SIZE; i++) {
                printRow(board, i);
                newLine();
                if (i < board.BOARD_SIZE - 1) {
                    printRowSeparator(board);
                }
            }
            newLine();
            printStatus(board);

        } catch (IOException e) {
            throw new IllegalStateException("Cannot print", e);
        }
    }

    private void printStatus(Board board) throws IOException {
        writer.append(board.status().toString());
        newLine();
    }

    private void printRowSeparator(Board board) throws IOException {
        for (int j = 0; j < Board.BOARD_SIZE; j++) {
            writer.append('-');
            if (j < board.BOARD_SIZE - 1) {
                writer.append('+');
            }
        }
        newLine();
    }

    private void newLine() throws IOException {
        writer.append('\n');
    }

    private void printRow(Board board, int i) throws IOException {
        for (int j = 0; j < Board.BOARD_SIZE; j++) {
            writer.append(board.cell(i, j));
            if (j < board.BOARD_SIZE - 1) {
                writer.append('|');
            }
        }
    }
}

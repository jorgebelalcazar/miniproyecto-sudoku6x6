package com.example.miniproyectosudoku6x6.controller.handlers;
/**
 * Custom listener contract notified whenever the value of a Sudoku cell
 * changes through user interaction.
 * <p>
 * This interface decouples the user interface from the logic that reacts
 * to cell modifications. Any class that needs to be informed about cell
 * changes can implement this interface and register itself with the
 * appropriate event source.
 * </p>
 */
public interface EscuchadorCambioCelda {

    /**
     * Invoked when a cell's value changes due to user input.
     *
     * @param fila      the row index of the modified cell (0-5)
     * @param columna   the column index of the modified cell (0-5)
     * @param nuevoValor the new value placed in the cell, or 0 if cleared
     */
    void alCambiarCelda(int fila, int columna, int nuevoValor);
}
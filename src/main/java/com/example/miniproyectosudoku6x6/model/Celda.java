package com.example.miniproyectosudoku6x6.model;
/**
 * Represents a single cell in the 6x6 Sudoku board.
 * <p>
 * Each cell stores its current value (1 to 6, or 0 if empty), its position
 * within the grid, whether it is a fixed clue (pre-filled and non-editable),
 * and whether it currently violates a Sudoku rule (used for visual feedback).
 * </p>
 *
 * <p>This class belongs to the model layer of the MVC architecture and
 * must remain independent of any JavaFX or UI-related code.</p>
 */
public class Celda {

    /** Constant representing an empty cell. */
    public static final int VALOR_VACIO = 0;

    /** Minimum valid value a cell can hold (inclusive). */
    public static final int VALOR_MINIMO = 1;

    /** Maximum valid value a cell can hold (inclusive). */
    public static final int VALOR_MAXIMO = 6;

    /** Row index of the cell within the board (0-based). */
    private final int fila;

    /** Column index of the cell within the board (0-based). */
    private final int columna;

    /** Current value of the cell. Zero means the cell is empty. */
    private int valor;

    /** Indicates whether the cell is a fixed clue and therefore non-editable. */
    private boolean esFija;

    /** Indicates whether the cell currently violates a Sudoku rule. */
    private boolean tieneError;

    /**
     * Constructs a new empty cell at the specified position.
     *
     * @param fila    the row index (0-based, must be between 0 and 5)
     * @param columna the column index (0-based, must be between 0 and 5)
     * @throws IllegalArgumentException if row or column are out of bounds
     */
    public Celda(int fila, int columna) {
        if (fila < 0 || fila > 5 || columna < 0 || columna > 5) {
            throw new IllegalArgumentException(
                    "La posicion de la celda debe estar entre 0 y 5. " +
                            "Recibido: fila=" + fila + ", columna=" + columna
            );
        }
        this.fila = fila;
        this.columna = columna;
        this.valor = VALOR_VACIO;
        this.esFija = false;
        this.tieneError = false;
    }

    /**
     * Returns the row index of this cell.
     *
     * @return the row index (0-5)
     */
    public int obtenerFila() {
        return fila;
    }

    /**
     * Returns the column index of this cell.
     *
     * @return the column index (0-5)
     */
    public int obtenerColumna() {
        return columna;
    }

    /**
     * Returns the current value of this cell.
     *
     * @return the value (1-6) or {@link #VALOR_VACIO} if the cell is empty
     */
    public int obtenerValor() {
        return valor;
    }

    /**
     * Sets the value of this cell.
     * <p>
     * If the cell is marked as fixed, the value cannot be changed and the
     * method will throw an exception. Use {@link #esFija()} to check beforehand.
     * </p>
     *
     * @param nuevoValor the new value (must be 0 or between 1 and 6)
     * @throws IllegalStateException    if the cell is fixed
     * @throws IllegalArgumentException if the value is out of range
     */
    public void establecerValor(int nuevoValor) {
        if (esFija) {
            throw new IllegalStateException(
                    "No se puede modificar una celda fija en [" + fila + "," + columna + "]"
            );
        }
        if (nuevoValor != VALOR_VACIO && (nuevoValor < VALOR_MINIMO || nuevoValor > VALOR_MAXIMO)) {
            throw new IllegalArgumentException(
                    "El valor debe ser 0 (vacio) o estar entre " + VALOR_MINIMO +
                            " y " + VALOR_MAXIMO + ". Recibido: " + nuevoValor
            );
        }
        this.valor = nuevoValor;
    }

    /**
     * Clears the value of this cell, setting it back to empty.
     * <p>
     * Has no effect if the cell is fixed.
     * </p>
     */
    public void limpiar() {
        if (!esFija) {
            this.valor = VALOR_VACIO;
            this.tieneError = false;
        }
    }

    /**
     * Checks whether this cell is empty.
     *
     * @return {@code true} if the cell has no value, {@code false} otherwise
     */
    public boolean estaVacia() {
        return valor == VALOR_VACIO;
    }

    /**
     * Checks whether this cell is a fixed clue.
     *
     * @return {@code true} if the cell is fixed and cannot be modified
     */
    public boolean esFija() {
        return esFija;
    }

    /**
     * Marks this cell as a fixed clue with the given value.
     * <p>
     * Fixed cells cannot be cleared or modified through {@link #establecerValor(int)}.
     * </p>
     *
     * @param valorFijo the value to lock into this cell (must be 1-6)
     * @throws IllegalArgumentException if the value is out of range
     */
    public void marcarComoFija(int valorFijo) {
        if (valorFijo < VALOR_MINIMO || valorFijo > VALOR_MAXIMO) {
            throw new IllegalArgumentException(
                    "Una celda fija debe tener un valor entre " +
                            VALOR_MINIMO + " y " + VALOR_MAXIMO + ". Recibido: " + valorFijo
            );
        }
        this.valor = valorFijo;
        this.esFija = true;
        this.tieneError = false;
    }

    /**
     * Checks whether this cell currently violates a Sudoku rule.
     *
     * @return {@code true} if the cell is in an error state
     */
    public boolean tieneError() {
        return tieneError;
    }

    /**
     * Sets the error state of this cell.
     * <p>
     * This flag is typically used by the view layer to highlight cells
     * that contain invalid values (for example, with a red border).
     * </p>
     *
     * @param estadoError {@code true} to mark the cell as invalid,
     *                    {@code false} to clear the error state
     */
    public void establecerError(boolean estadoError) {
        this.tieneError = estadoError;
    }

    /**
     * Returns a human-readable representation of this cell, useful for
     * debugging and console testing.
     *
     * @return a string describing the cell's state
     */
    @Override
    public String toString() {
        return "Celda[" + fila + "," + columna + "]=" +
                (estaVacia() ? "_" : valor) +
                (esFija ? "(fija)" : "") +
                (tieneError ? "(error)" : "");
    }
}
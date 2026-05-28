package com.example.miniproyectosudoku6x6.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Validates the rules of a 6x6 Sudoku board using hash-based sets.
 * <p>
 * This class is the cornerstone of the validation logic. It exposes
 * stateless methods that check whether a given move is valid according
 * to the three classic Sudoku rules:
 * </p>
 * <ul>
 *   <li>No number can be repeated within the same row.</li>
 *   <li>No number can be repeated within the same column.</li>
 *   <li>No number can be repeated within the same 2x3 block.</li>
 * </ul>
 *
 * <p>The implementation relies on {@link java.util.HashSet} for
 * constant-time duplicate detection. The {@code add()} method of a
 * {@code HashSet} returns {@code false} when the element is already
 * present, which is leveraged here to detect repeated numbers in a
 * single operation.</p>
 *
 * <p>This class belongs to the model layer of the MVC architecture and
 * does not depend on JavaFX or any UI framework.</p>
 */
public class ValidadorSudoku {

    /** The board to validate. */
    private final TableroSudoku tablero;

    /**
     * Constructs a new validator bound to a specific Sudoku board.
     *
     * @param tablero the board that this validator will check
     */
    public ValidadorSudoku(TableroSudoku tablero) {
        if (tablero == null) {
            throw new IllegalArgumentException(
                    "El tablero no puede ser nulo"
            );
        }
        this.tablero = tablero;
    }

    /**
     * Checks whether placing the given value at the specified position
     * would respect all three Sudoku rules (row, column and block).
     * <p>
     * This method does NOT modify the board. It only simulates the move
     * to determine its validity.
     * </p>
     *
     * @param fila    the row index (0-5)
     * @param columna the column index (0-5)
     * @param valor   the value to place (must be between 1 and 6)
     * @return {@code true} if the move respects all Sudoku rules,
     *         {@code false} otherwise
     */
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        if (valor < Celda.VALOR_MINIMO || valor > Celda.VALOR_MAXIMO) {
            return false;
        }
        return esValidoEnFila(fila, columna, valor)
                && esValidoEnColumna(fila, columna, valor)
                && esValidoEnBloque(fila, columna, valor);
    }

    /**
     * Verifies that the given value does not already appear in the row,
     * excluding the cell at the target column (so that re-placing the
     * same value over itself is still considered valid).
     *
     * @param fila            the row index to check
     * @param columnaObjetivo the column index of the cell being modified
     * @param valor           the value to test
     * @return {@code true} if the value can be placed without breaking
     *         the row rule
     */
    public boolean esValidoEnFila(int fila, int columnaObjetivo, int valor) {
        Set<Integer> numerosVistos = new HashSet<>();
        List<Celda> celdasFila = tablero.obtenerFila(fila);

        for (Celda celda : celdasFila) {
            if (celda.obtenerColumna() == columnaObjetivo) {
                continue;
            }
            int valorActual = celda.obtenerValor();
            if (valorActual != Celda.VALOR_VACIO) {
                numerosVistos.add(valorActual);
            }
        }
        return !numerosVistos.contains(valor);
    }

    /**
     * Verifies that the given value does not already appear in the column,
     * excluding the cell at the target row.
     *
     * @param filaObjetivo the row index of the cell being modified
     * @param columna      the column index to check
     * @param valor        the value to test
     * @return {@code true} if the value can be placed without breaking
     *         the column rule
     */
    public boolean esValidoEnColumna(int filaObjetivo, int columna, int valor) {
        Set<Integer> numerosVistos = new HashSet<>();
        List<Celda> celdasColumna = tablero.obtenerColumna(columna);

        for (Celda celda : celdasColumna) {
            if (celda.obtenerFila() == filaObjetivo) {
                continue;
            }
            int valorActual = celda.obtenerValor();
            if (valorActual != Celda.VALOR_VACIO) {
                numerosVistos.add(valorActual);
            }
        }
        return !numerosVistos.contains(valor);
    }

    /**
     * Verifies that the given value does not already appear in the 2x3
     * block containing the target cell, excluding the target cell itself.
     *
     * @param fila    the row index of the target cell
     * @param columna the column index of the target cell
     * @param valor   the value to test
     * @return {@code true} if the value can be placed without breaking
     *         the block rule
     */
    public boolean esValidoEnBloque(int fila, int columna, int valor) {
        int indiceBloque = TableroSudoku.calcularIndiceBloque(fila, columna);
        Set<Integer> numerosVistos = new HashSet<>();
        List<Celda> celdasBloque = tablero.obtenerBloque(indiceBloque);

        for (Celda celda : celdasBloque) {
            if (celda.obtenerFila() == fila && celda.obtenerColumna() == columna) {
                continue;
            }
            int valorActual = celda.obtenerValor();
            if (valorActual != Celda.VALOR_VACIO) {
                numerosVistos.add(valorActual);
            }
        }
        return !numerosVistos.contains(valor);
    }

    /**
     * Validates the entire board by scanning every cell and checking
     * whether its current value violates any rule.
     * <p>
     * After this method runs, each cell will have its error flag updated:
     * cells with conflicting values will have {@code tieneError == true}.
     * </p>
     *
     * @return {@code true} if no cell violates any rule, {@code false}
     *         if at least one error was detected
     */
    public boolean validarTableroCompleto() {
        boolean tableroValido = true;

        for (int fila = 0; fila < TableroSudoku.TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TableroSudoku.TOTAL_COLUMNAS; columna++) {
                Celda celda = tablero.obtenerCelda(fila, columna);
                if (celda.estaVacia()) {
                    celda.establecerError(false);
                    continue;
                }
                boolean valida = esMovimientoValido(
                        fila, columna, celda.obtenerValor()
                );
                celda.establecerError(!valida);
                if (!valida) {
                    tableroValido = false;
                }
            }
        }
        return tableroValido;
    }

    /**
     * Returns whether the board is fully completed and all values respect
     * the Sudoku rules.
     *
     * @return {@code true} if the board is solved correctly
     */
    public boolean estaResueltoCorrectamente() {
        return tablero.estaCompleto() && validarTableroCompleto();
    }
}
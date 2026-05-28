package com.example.miniproyectosudoku6x6.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the complete 6x6 Sudoku board.
 * <p>
 * The board is internally organized as a 6x6 matrix of {@link Celda} objects,
 * and also exposes logical groupings of those cells as rows, columns and
 * 2x3 blocks. These groupings make rule validation and helper operations
 * easier to express in later phases.
 * </p>
 *
 * <p>The 6x6 grid is divided into 6 blocks of 2 rows by 3 columns:</p>
 * <pre>
 *  +---------+---------+
 *  | Block 0 | Block 1 |   (rows 0-1)
 *  +---------+---------+
 *  | Block 2 | Block 3 |   (rows 2-3)
 *  +---------+---------+
 *  | Block 4 | Block 5 |   (rows 4-5)
 *  +---------+---------+
 * </pre>
 *
 * <p>This class belongs to the model layer and is completely independent
 * of JavaFX or any UI framework.</p>
 */
public class TableroSudoku {

    /** Total number of rows in the board. */
    public static final int TOTAL_FILAS = 6;

    /** Total number of columns in the board. */
    public static final int TOTAL_COLUMNAS = 6;

    /** Number of rows per block. */
    public static final int FILAS_POR_BLOQUE = 2;

    /** Number of columns per block. */
    public static final int COLUMNAS_POR_BLOQUE = 3;

    /** Total number of blocks in the board (6 blocks of 2x3). */
    public static final int TOTAL_BLOQUES = 6;

    /** Two-dimensional matrix holding all cells of the board. */
    private final Celda[][] matrizCeldas;

    /** Logical grouping of cells by row. Each inner list contains 6 cells. */
    private final List<List<Celda>> filas;

    /** Logical grouping of cells by column. Each inner list contains 6 cells. */
    private final List<List<Celda>> columnas;

    /** Logical grouping of cells by 2x3 block. Each inner list contains 6 cells. */
    private final List<List<Celda>> bloques;

    /**
     * Constructs a new empty Sudoku board. All 36 cells are created as empty
     * and the row, column and block groupings are populated for fast access.
     */
    public TableroSudoku() {
        this.matrizCeldas = new Celda[TOTAL_FILAS][TOTAL_COLUMNAS];
        this.filas = new ArrayList<>();
        this.columnas = new ArrayList<>();
        this.bloques = new ArrayList<>();

        inicializarCeldas();
        inicializarAgrupaciones();
    }

    /**
     * Creates every cell of the matrix as an empty cell.
     */
    private void inicializarCeldas() {
        for (int fila = 0; fila < TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TOTAL_COLUMNAS; columna++) {
                matrizCeldas[fila][columna] = new Celda(fila, columna);
            }
        }
    }

    /**
     * Builds the logical lists that group cells by row, column and block.
     * After this method runs, every cell of the board is referenced from
     * exactly one row list, one column list and one block list.
     */
    private void inicializarAgrupaciones() {
        // Initialize empty inner lists
        for (int i = 0; i < TOTAL_FILAS; i++) {
            filas.add(new ArrayList<>());
        }
        for (int i = 0; i < TOTAL_COLUMNAS; i++) {
            columnas.add(new ArrayList<>());
        }
        for (int i = 0; i < TOTAL_BLOQUES; i++) {
            bloques.add(new ArrayList<>());
        }

        // Populate the groupings by traversing every cell of the matrix
        for (int fila = 0; fila < TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TOTAL_COLUMNAS; columna++) {
                Celda celdaActual = matrizCeldas[fila][columna];
                filas.get(fila).add(celdaActual);
                columnas.get(columna).add(celdaActual);
                bloques.get(calcularIndiceBloque(fila, columna)).add(celdaActual);
            }
        }
    }

    /**
     * Computes the block index for the cell located at the given coordinates.
     * <p>
     * The 6x6 board has 6 blocks arranged in a 3x2 layout (3 block-rows by
     * 2 block-columns), where each block is 2 rows by 3 columns.
     * </p>
     *
     * @param fila    the row index (0-5)
     * @param columna the column index (0-5)
     * @return the block index (0-5)
     */
    public static int calcularIndiceBloque(int fila, int columna) {
        return (fila / FILAS_POR_BLOQUE) * FILAS_POR_BLOQUE
                + (columna / COLUMNAS_POR_BLOQUE);
    }

    /**
     * Returns the cell located at the given coordinates.
     *
     * @param fila    the row index (0-5)
     * @param columna the column index (0-5)
     * @return the cell at that position
     * @throws IndexOutOfBoundsException if the coordinates are out of range
     */
    public Celda obtenerCelda(int fila, int columna) {
        validarCoordenadas(fila, columna);
        return matrizCeldas[fila][columna];
    }

    /**
     * Returns the list of cells that belong to the given row.
     *
     * @param fila the row index (0-5)
     * @return a list containing the 6 cells of that row
     */
    public List<Celda> obtenerFila(int fila) {
        if (fila < 0 || fila >= TOTAL_FILAS) {
            throw new IndexOutOfBoundsException(
                    "Indice de fila fuera de rango: " + fila
            );
        }
        return filas.get(fila);
    }

    /**
     * Returns the list of cells that belong to the given column.
     *
     * @param columna the column index (0-5)
     * @return a list containing the 6 cells of that column
     */
    public List<Celda> obtenerColumna(int columna) {
        if (columna < 0 || columna >= TOTAL_COLUMNAS) {
            throw new IndexOutOfBoundsException(
                    "Indice de columna fuera de rango: " + columna
            );
        }
        return columnas.get(columna);
    }

    /**
     * Returns the list of cells that belong to the given 2x3 block.
     *
     * @param indiceBloque the block index (0-5)
     * @return a list containing the 6 cells of that block
     */
    public List<Celda> obtenerBloque(int indiceBloque) {
        if (indiceBloque < 0 || indiceBloque >= TOTAL_BLOQUES) {
            throw new IndexOutOfBoundsException(
                    "Indice de bloque fuera de rango: " + indiceBloque
            );
        }
        return bloques.get(indiceBloque);
    }

    /**
     * Checks whether every cell in the board has a value assigned.
     *
     * @return {@code true} if no cell is empty, {@code false} otherwise
     */
    public boolean estaCompleto() {
        for (int fila = 0; fila < TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TOTAL_COLUMNAS; columna++) {
                if (matrizCeldas[fila][columna].estaVacia()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the value of every non-fixed cell in the board.
     */
    public void limpiarTablero() {
        for (int fila = 0; fila < TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TOTAL_COLUMNAS; columna++) {
                matrizCeldas[fila][columna].limpiar();
            }
        }
    }

    /**
     * Validates that the given coordinates are within the board boundaries.
     *
     * @param fila    the row index to validate
     * @param columna the column index to validate
     * @throws IndexOutOfBoundsException if any coordinate is out of range
     */
    private void validarCoordenadas(int fila, int columna) {
        if (fila < 0 || fila >= TOTAL_FILAS
                || columna < 0 || columna >= TOTAL_COLUMNAS) {
            throw new IndexOutOfBoundsException(
                    "Coordenadas fuera del tablero: fila=" + fila +
                            ", columna=" + columna
            );
        }
    }

    /**
     * Returns a human-readable representation of the board, useful for
     * debugging and console testing. Empty cells are shown as ".".
     *
     * @return a multi-line string with the current state of the board
     */
    @Override
    public String toString() {
        StringBuilder constructor = new StringBuilder();
        constructor.append("Tablero Sudoku 6x6:\n");
        for (int fila = 0; fila < TOTAL_FILAS; fila++) {
            if (fila % FILAS_POR_BLOQUE == 0 && fila != 0) {
                constructor.append("------+-------+------\n");
            }
            for (int columna = 0; columna < TOTAL_COLUMNAS; columna++) {
                if (columna % COLUMNAS_POR_BLOQUE == 0 && columna != 0) {
                    constructor.append("| ");
                }
                int valor = matrizCeldas[fila][columna].obtenerValor();
                constructor.append(valor == Celda.VALOR_VACIO ? ". " : valor + " ");
            }
            constructor.append("\n");
        }
        return constructor.toString();
    }
}
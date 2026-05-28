package com.example.miniproyectosudoku6x6.controller;

import com.example.miniproyectosudoku6x6.controller.handlers.AdaptadorFocoFiltro;
import com.example.miniproyectosudoku6x6.controller.handlers.EscuchadorCambioCelda;
import com.example.miniproyectosudoku6x6.model.Celda;
import com.example.miniproyectosudoku6x6.model.GeneradorSudoku;
import com.example.miniproyectosudoku6x6.model.TableroSudoku;
import com.example.miniproyectosudoku6x6.model.ValidadorSudoku;
import com.example.miniproyectosudoku6x6.model.ProveedorAyuda;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku view.
 * <p>
 * This class connects the JavaFX view (defined in {@code sudoku-view.fxml})
 * with the model layer ({@link TableroSudoku}). It is responsible for:
 * </p>
 * <ul>
 *   <li>Populating the 6x6 grid with editable text fields.</li>
 *   <li>Reacting to user interaction (mouse and keyboard).</li>
 *   <li>Generating new puzzles when the user requests them.</li>
 *   <li>Keeping a bidirectional mapping between every UI cell and the
 *       underlying model cell.</li>
 * </ul>
 *
 * <p>The mapping between visual fields and model cells is stored in a
 * {@link Map}, which provides constant-time lookups in both directions
 * and demonstrates the use of advanced data structures beyond plain arrays.</p>
 */
public class SudokuController implements Initializable, EscuchadorCambioCelda {

    /** Grid pane that hosts the 6x6 Sudoku board. */
    @FXML
    private GridPane cuadriculaTablero;

    /** Button that starts a new game. */
    @FXML
    private Button botonNuevoJuego;

    /** Button that requests a hint. */
    @FXML
    private Button botonAyuda;

    /** Label that displays status messages to the player. */
    @FXML
    private Label etiquetaEstado;

    /** The current Sudoku board (model). */
    private TableroSudoku tablero;

    /** Generator used to create new random boards. */
    private GeneradorSudoku generador;

    /** Validator used to verify Sudoku rules in real time. */
    private ValidadorSudoku validador;

    /** Provides intelligent hints when the player gets stuck. */
    private ProveedorAyuda proveedorAyuda;

    /** Mapping from each text field in the UI to its model cell. */
    private final Map<TextField, Celda> mapaCampoCelda = new HashMap<>();

    /** Mapping from each model cell to its text field in the UI. */
    private final Map<Celda, TextField> mapaCeldaCampo = new HashMap<>();

    /** Shared adapter that filters keyboard input on every cell. */
    private final AdaptadorFocoFiltro filtroEntrada = new AdaptadorFocoFiltro();

    /**
     * Initialization callback. Sets up the board, wires the buttons and
     * starts a new game automatically.
     *
     * @param ubicacion FXML location (unused)
     * @param recursos  resource bundle (unused)
     */
    @Override
    public void initialize(URL ubicacion, ResourceBundle recursos) {
        this.generador = new GeneradorSudoku();
        construirCuadricula();
        configurarBotones();
        iniciarNuevoJuego();
    }

    /**
     * Builds the 6x6 grid of text fields and registers all event handlers
     * on each cell. Also populates the bidirectional mappings between the
     * UI fields and the model cells (which will be filled in later by
     * {@link #iniciarNuevoJuego()}).
     */
    private void construirCuadricula() {
        for (int fila = 0; fila < TableroSudoku.TOTAL_FILAS; fila++) {
            for (int columna = 0; columna < TableroSudoku.TOTAL_COLUMNAS; columna++) {
                TextField campo = crearCampoCelda(fila, columna);
                cuadriculaTablero.add(campo, columna, fila);
            }
        }
    }

    /**
     * Creates a single configured {@link TextField} for the cell at the
     * given position. Attaches the appropriate CSS classes (including
     * the block-border classes) and all the event handlers.
     *
     * @param fila    the row index (0-5)
     * @param columna the column index (0-5)
     * @return a fully configured text field
     */
    private TextField crearCampoCelda(int fila, int columna) {
        TextField campo = new TextField();
        campo.getStyleClass().add("celda");

        if (esBordeDerechoBloque(columna) && esBordeInferiorBloque(fila)) {
            campo.getStyleClass().add("celda-borde-inferior-derecho");
        } else if (esBordeDerechoBloque(columna)) {
            campo.getStyleClass().add("celda-borde-derecho");
        } else if (esBordeInferiorBloque(fila)) {
            campo.getStyleClass().add("celda-borde-inferior");
        }

        // prevencion de errores (filtro de entrada)
        campo.addEventFilter(KeyEvent.KEY_TYPED, filtroEntrada);

        // Manejador de teclas: clase interna
        campo.setOnKeyReleased(new ManejadorTeclado(fila, columna));

        // Manejador de mouse: expresion lambda
        campo.setOnMouseClicked(eventoMouse -> manejarClickCelda(eventoMouse, fila, columna));

        // Manejador adicional con interface anonima: foco recibido
        campo.focusedProperty().addListener((observable, valorAnterior, valorNuevo) -> {
            if (valorNuevo) {
                actualizarEstado("Celda seleccionada: fila " + (fila + 1)
                        + ", columna " + (columna + 1));
            }
        });

        return campo;
    }

    /**
     * Configures the action handlers for the main buttons.
     * Uses lambda expressions for concise event handling.
     */
    private void configurarBotones() {
        botonNuevoJuego.setOnAction(evento -> iniciarNuevoJuego());
        botonAyuda.setOnAction(evento -> manejarSolicitudAyuda());
    }

    /**
     * Generates a new Sudoku puzzle, refreshes the UI, and resets the
     * validator so that real-time checks operate on the new board.
     */
    private void iniciarNuevoJuego() {
        this.tablero = generador.generarNuevoTablero();
        this.validador = new ValidadorSudoku(tablero);
        this.proveedorAyuda = new ProveedorAyuda(tablero);
        sincronizarMapas();
        refrescarCuadricula();
        refrescarErroresVisuales();
        desbloquearTablero();
        aplicarEstadoExito("Nuevo juego iniciado. Completa el tablero!");
    }

    /**
     * Rebuilds the bidirectional mappings between text fields and model
     * cells. Must be called every time a new board is generated.
     */
    private void sincronizarMapas() {
        mapaCampoCelda.clear();
        mapaCeldaCampo.clear();

        int totalCampos = cuadriculaTablero.getChildren().size();
        for (int indice = 0; indice < totalCampos; indice++) {
            if (!(cuadriculaTablero.getChildren().get(indice) instanceof TextField)) {
                continue;
            }
            TextField campo = (TextField) cuadriculaTablero.getChildren().get(indice);
            Integer filaCampo = GridPane.getRowIndex(campo);
            Integer columnaCampo = GridPane.getColumnIndex(campo);
            int fila = filaCampo == null ? 0 : filaCampo;
            int columna = columnaCampo == null ? 0 : columnaCampo;

            Celda celdaModelo = tablero.obtenerCelda(fila, columna);
            mapaCampoCelda.put(campo, celdaModelo);
            mapaCeldaCampo.put(celdaModelo, campo);
        }
    }

    /**
     * Updates the visual state of every cell to reflect the current state
     * of the model. Fixed cells are shown highlighted and non-editable;
     * empty cells are cleared; cells with values display them.
     */
    private void refrescarCuadricula() {
        for (Map.Entry<TextField, Celda> entrada : mapaCampoCelda.entrySet()) {
            TextField campo = entrada.getKey();
            Celda celda = entrada.getValue();

            campo.getStyleClass().remove("celda-fija");
            campo.getStyleClass().remove("celda-error");

            if (celda.esFija()) {
                campo.setText(String.valueOf(celda.obtenerValor()));
                campo.setEditable(false);
                campo.getStyleClass().add("celda-fija");
            } else {
                campo.setEditable(true);
                if (celda.estaVacia()) {
                    campo.setText("");
                } else {
                    campo.setText(String.valueOf(celda.obtenerValor()));
                }
            }
        }
    }

    /**
     * Updates the status label shown at the bottom of the window.
     *
     * @param mensaje the message to display
     */
    private void actualizarEstado(String mensaje) {
        etiquetaEstado.setText(mensaje);
    }

    /**
     * Handles a mouse click on a cell. Currently it only updates the
     * status label; full selection logic will arrive in later phases.
     *
     * @param evento  the mouse event that triggered the call
     * @param fila    the row of the clicked cell
     * @param columna the column of the clicked cell
     */
    private void manejarClickCelda(MouseEvent evento, int fila, int columna) {
        Celda celda = tablero.obtenerCelda(fila, columna);
        if (celda.esFija()) {
            actualizarEstado("Esta celda es una pista y no se puede modificar.");
        }
    }

    /**
     * Requests a hint from the {@link ProveedorAyuda}, applies it to the
     * model and highlights it visually in the UI. If no hint can be
     * generated (because the board is full or no legal candidate exists)
     * the user is notified through the status label.
     */
    private void manejarSolicitudAyuda() {
        if (proveedorAyuda == null) {
            aplicarEstadoError("Inicia un nuevo juego para usar la ayuda.");
            return;
        }

        ProveedorAyuda.Sugerencia sugerencia = proveedorAyuda.sugerirAyuda();
        if (sugerencia == null) {
            aplicarEstadoError("No se encontraron sugerencias validas en este momento.");
            return;
        }

        Celda celdaModelo = tablero.obtenerCelda(
                sugerencia.obtenerFila(), sugerencia.obtenerColumna()
        );
        celdaModelo.establecerValor(sugerencia.obtenerValor());

        TextField campo = mapaCeldaCampo.get(celdaModelo);
        if (campo != null) {
            campo.setText(String.valueOf(sugerencia.obtenerValor()));
            destacarSugerencia(campo);
        }

        boolean tableroSinErrores = validador.validarTableroCompleto();
        refrescarErroresVisuales();

        if (verificarVictoria()) {
            return;
        }

        if (tableroSinErrores) {
            aplicarEstadoExito("Ayuda: numero " + sugerencia.obtenerValor()
                    + " sugerido en (" + (sugerencia.obtenerFila() + 1)
                    + ", " + (sugerencia.obtenerColumna() + 1) + ").");
        } else {
            aplicarEstadoError("Ayuda aplicada, pero hay conflictos previos. Revisa el tablero.");
        }
    }

    /**
     * Reads the current text of a field and updates the underlying model
     * cell accordingly. Empty fields clear the cell value.
     *
     * @param fila    the row of the cell being updated
     * @param columna the column of the cell being updated
     * @param texto   the current text in the field
     */
    private void procesarEntradaUsuario(int fila, int columna, String texto) {
        Celda celda = tablero.obtenerCelda(fila, columna);
        if (celda.esFija()) {
            return;
        }
        if (texto == null || texto.isEmpty()) {
            celda.limpiar();
            alCambiarCelda(fila, columna, Celda.VALOR_VACIO);
            return;
        }

        try {
            int valor = Integer.parseInt(texto);
            celda.establecerValor(valor);
            alCambiarCelda(fila, columna, valor);
        } catch (NumberFormatException ignorado) {
            // Should never happen thanks to the input filter
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers real-time validation of the entire board after any cell
     * change. Updates the visual error state of every cell and notifies
     * the player through the status label. If the board is fully and
     * correctly completed, displays a victory message.
     * </p>
     */
    @Override
    public void alCambiarCelda(int fila, int columna, int nuevoValor) {
        boolean tableroSinErrores = validador.validarTableroCompleto();
        refrescarErroresVisuales();

        if (verificarVictoria()) {
            return;
        }

        if (nuevoValor == Celda.VALOR_VACIO) {
            actualizarEstado("Celda (" + (fila + 1) + ", " + (columna + 1)
                    + ") borrada.");
            return;
        }

        if (!tableroSinErrores) {
            aplicarEstadoError("Conflicto detectado. Revisa las celdas resaltadas en rojo.");
        } else {
            aplicarEstadoExito("Numero " + nuevoValor + " colocado en ("
                    + (fila + 1) + ", " + (columna + 1) + "). Sin conflictos.");
        }
    }

    /**
     * Determines whether the given column index lies on the right edge
     * of a 2x3 block, which should be visually marked with a thicker border.
     *
     * @param columna the column index
     * @return {@code true} if the column needs a thick right border
     */
    private boolean esBordeDerechoBloque(int columna) {
        return (columna + 1) % TableroSudoku.COLUMNAS_POR_BLOQUE == 0
                && columna != TableroSudoku.TOTAL_COLUMNAS - 1;
    }

    /**
     * Determines whether the given row index lies on the bottom edge
     * of a 2x3 block, which should be visually marked with a thicker border.
     *
     * @param fila the row index
     * @return {@code true} if the row needs a thick bottom border
     */
    private boolean esBordeInferiorBloque(int fila) {
        return (fila + 1) % TableroSudoku.FILAS_POR_BLOQUE == 0
                && fila != TableroSudoku.TOTAL_FILAS - 1;
    }

    // ================================================================
    // CLASES INTERNAS
    // ================================================================

    /**
     * Updates the visual error state of every cell in the board.
     * Cells whose model marks them as having an error get the CSS
     * class {@code celda-error}; all others have it removed.
     */
    private void refrescarErroresVisuales() {
        for (Map.Entry<Celda, TextField> entrada : mapaCeldaCampo.entrySet()) {
            Celda celda = entrada.getKey();
            TextField campo = entrada.getValue();

            campo.getStyleClass().remove("celda-error");
            if (celda.tieneError() && !celda.esFija()) {
                campo.getStyleClass().add("celda-error");
            }
        }
    }

    /**
     * Checks whether the board is fully completed without any rule violation.
     * When the game has been solved, displays a congratulatory message and
     * disables further editing.
     *
     * @return {@code true} if the game has just been won, {@code false} otherwise
     */
    private boolean verificarVictoria() {
        if (validador.estaResueltoCorrectamente()) {
            aplicarEstadoExito("Felicidades! Has resuelto el Sudoku correctamente.");
            bloquearTablero();
            return true;
        }
        return false;
    }

    /**
     * Disables editing on every non-fixed cell. Used once the player has
     * solved the puzzle to prevent further modifications.
     */
    private void bloquearTablero() {
        for (TextField campo : mapaCampoCelda.keySet()) {
            campo.setEditable(false);
        }
    }

    /**
     * Updates the status label and applies the success style.
     *
     * @param mensaje the message to display
     */
    private void aplicarEstadoExito(String mensaje) {
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().remove("estado-error");
        if (!etiquetaEstado.getStyleClass().contains("estado-exito")) {
            etiquetaEstado.getStyleClass().add("estado-exito");
        }
    }

    /**
     * Updates the status label and applies the error style.
     *
     * @param mensaje the message to display
     */
    private void aplicarEstadoError(String mensaje) {
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().remove("estado-exito");
        if (!etiquetaEstado.getStyleClass().contains("estado-error")) {
            etiquetaEstado.getStyleClass().add("estado-error");
        }
    }

    /**
     * Highlights a cell briefly to indicate it has been filled by the
     * hint feature. The highlight is removed automatically after a short
     * delay so it does not interfere with normal play.
     *
     * @param campo the text field to highlight
     */
    private void destacarSugerencia(TextField campo) {
        // Limpia destacados previos en cualquier otra celda
        for (TextField otroCampo : mapaCampoCelda.keySet()) {
            otroCampo.getStyleClass().remove("celda-sugerencia");
        }
        if (!campo.getStyleClass().contains("celda-sugerencia")) {
            campo.getStyleClass().add("celda-sugerencia");
        }

        // Programa la eliminacion del destacado despues de 2 segundos
        javafx.animation.PauseTransition pausa =
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        pausa.setOnFinished(evento -> campo.getStyleClass().remove("celda-sugerencia"));
        pausa.play();
    }

    /**
     * Re-enables editing on every non-fixed cell. Used after a victory
     * when the player starts a new game.
     */
    private void desbloquearTablero() {
        for (Map.Entry<TextField, Celda> entrada : mapaCampoCelda.entrySet()) {
            TextField campo = entrada.getKey();
            Celda celda = entrada.getValue();
            if (!celda.esFija()) {
                campo.setEditable(true);
            }
        }
    }

    /**
     * Inner class that handles key released events on a specific cell.
     * <p>
     * Implemented as an inner class so it can directly access the
     * enclosing controller's state (the model, the maps and the status
     * label) without explicit references. Each instance is bound to a
     * specific (row, column) coordinate, simplifying the handler logic.
     * </p>
     */
    private class ManejadorTeclado implements EventHandler<KeyEvent> {

        /** Row coordinate associated with this handler. */
        private final int fila;

        /** Column coordinate associated with this handler. */
        private final int columna;

        /**
         * Creates a handler bound to a specific cell position.
         *
         * @param fila    the row index of the cell
         * @param columna the column index of the cell
         */
        ManejadorTeclado(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }

        /**
         * Processes a key release by syncing the field's text with the
         * underlying model cell.
         *
         * @param evento the key event being processed
         */
        @Override
        public void handle(KeyEvent evento) {
            TextField campo = (TextField) evento.getSource();
            String contenido = campo.getText();
            if (contenido != null && contenido.length() > 1) {
                contenido = contenido.substring(contenido.length() - 1);
                campo.setText(contenido);
                campo.positionCaret(1);
            }
            procesarEntradaUsuario(fila, columna, contenido);
        }
    }
}
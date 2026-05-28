package com.example.miniproyectosudoku6x6.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the main Sudoku view.
 * <p>
 * This class is wired to {@code sudoku-view.fxml} and handles all the
 * interactions between the user interface and the underlying model.
 * In Phase 4 it is only a skeleton with the FXML references; logic will
 * be added in Phase 5.
 * </p>
 */
public class SudokuController implements Initializable {

    /** Grid pane that hosts the 6x6 Sudoku board. */
    @FXML
    private GridPane cuadriculaTablero;

    /** Button that starts a new game by generating a fresh board. */
    @FXML
    private Button botonNuevoJuego;

    /** Button that requests a hint for an empty cell. */
    @FXML
    private Button botonAyuda;

    /** Label that displays status messages to the player. */
    @FXML
    private Label etiquetaEstado;

    /**
     * Initialization callback invoked automatically by the JavaFX runtime
     * after the FXML view has been loaded. This is the right place to set
     * up listeners, populate the board, and connect the controller to the
     * model in upcoming phases.
     *
     * @param ubicacion the FXML location (provided by JavaFX, unused here)
     * @param recursos  the resource bundle (provided by JavaFX, unused here)
     */
    @Override
    public void initialize(URL ubicacion, ResourceBundle recursos) {
        if (etiquetaEstado != null) {
            etiquetaEstado.setText("Listo para jugar. Pulsa 'Nuevo Juego' para comenzar.");
        }
    }
}
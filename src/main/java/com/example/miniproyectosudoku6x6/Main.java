package com.example.miniproyectosudoku6x6;

import com.example.miniproyectosudoku6x6.model.Celda;
import com.example.miniproyectosudoku6x6.model.TableroSudoku;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main entry point of the Sudoku 6x6 application.
 * <p>
 * This class bootstraps the JavaFX runtime and displays the initial window.
 * In future phases, this class will load the FXML view through the controller.
 * </p>
 *
 * @author Jorge Iván Belalcázar
 * @version 1.0.0
 * @since 2026-05
 */
public class Main extends Application {

    /** Window title displayed in the application's title bar. */
    private static final String WINDOW_TITLE = "Sudoku 6x6";

    /** Default width of the application window, in pixels. */
    private static final int WINDOW_WIDTH = 600;

    /** Default height of the application window, in pixels. */
    private static final int WINDOW_HEIGHT = 400;

    /**
     * JavaFX entry point. Called automatically after the application launches.
     * Builds the initial scene and displays the primary stage.
     *
     * @param primaryStage the main window provided by the JavaFX runtime
     */
    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("¡Bienvenido al Sudoku 6x6!");
        welcomeLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2c3e50;"
        );

        Label subtitleLabel = new Label("MiniProyecto Sudoku6x6 - Fundamentos de POE");
        subtitleLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #7f8c8d;"
        );

        Label statusLabel = new Label("✅ Configuración JavaFX verificada correctamente.");
        statusLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #27ae60;" +
                        "-fx-padding: 20 0 0 0;"
        );

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #ecf0f1;");
        root.getChildren().addAll(welcomeLabel, subtitleLabel, statusLabel);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Standard Java entry point. Delegates control to JavaFX through
     * {@link Application#launch(String...)}.
     *
     * @param args command-line arguments (unused)
     */

    /**
     * Temporary test method for the Celda class.
     * This will be removed once the SudokuBoard class is implemented.
     */
    /**
     * Temporary test method for the Celda class.
     */
    private static void probarCelda() {
        System.out.println("=== Prueba de la clase Celda ===");

        Celda celda = new Celda(2, 3);
        System.out.println("Creada: " + celda);

        celda.establecerValor(4);
        System.out.println("Despues de establecerValor(4): " + celda);

        celda.marcarComoFija(5);
        System.out.println("Despues de marcarComoFija(5): " + celda);

        System.out.println("=== Prueba de Celda completada ===\n");
    }

    /**
     * Temporary test method for the TableroSudoku class.
     */
    private static void probarTablero() {
        System.out.println("=== Prueba de la clase TableroSudoku ===");

        TableroSudoku tablero = new TableroSudoku();

        System.out.println("Tablero recien creado:");
        System.out.println(tablero);

        System.out.println("Esta completo? " + tablero.estaCompleto());

        // Insertamos algunos valores de prueba
        tablero.obtenerCelda(0, 0).establecerValor(1);
        tablero.obtenerCelda(0, 3).establecerValor(2);
        tablero.obtenerCelda(2, 1).establecerValor(3);
        tablero.obtenerCelda(5, 5).establecerValor(6);

        System.out.println("Tablero con algunos valores:");
        System.out.println(tablero);

        // Verificamos los indices de bloque
        System.out.println("Indice de bloque para (0,0): " +
                TableroSudoku.calcularIndiceBloque(0, 0));
        System.out.println("Indice de bloque para (2,4): " +
                TableroSudoku.calcularIndiceBloque(2, 4));
        System.out.println("Indice de bloque para (5,5): " +
                TableroSudoku.calcularIndiceBloque(5, 5));

        // Verificamos las agrupaciones
        System.out.println("Celdas en la fila 0: " + tablero.obtenerFila(0).size());
        System.out.println("Celdas en la columna 3: " + tablero.obtenerColumna(3).size());
        System.out.println("Celdas en el bloque 0: " + tablero.obtenerBloque(0).size());

        System.out.println("=== Prueba de Tablero completada ===");
    }
    public static void main(String[] args) {
        probarCelda();
        probarTablero();
        //launch(args);
    }
}
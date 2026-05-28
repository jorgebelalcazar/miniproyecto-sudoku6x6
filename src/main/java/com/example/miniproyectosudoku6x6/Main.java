package com.example.miniproyectosudoku6x6;

import com.example.miniproyectosudoku6x6.model.Celda;
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
    private static void probarCelda() {
        System.out.println("=== Prueba de la clase Celda ===");

        // Prueba 1: Crear una celda vacia
        Celda celda = new Celda(2, 3);
        System.out.println("Creada: " + celda);
        System.out.println("Esta vacia? " + celda.estaVacia());

        // Prueba 2: Establecer un valor valido
        celda.establecerValor(4);
        System.out.println("Despues de establecerValor(4): " + celda);

        // Prueba 3: Limpiar la celda
        celda.limpiar();
        System.out.println("Despues de limpiar(): " + celda);

        // Prueba 4: Marcar como fija
        celda.marcarComoFija(5);
        System.out.println("Despues de marcarComoFija(5): " + celda);

        // Prueba 5: Intentar modificar una celda fija (debe fallar)
        try {
            celda.establecerValor(2);
        } catch (IllegalStateException e) {
            System.out.println("Bloqueado correctamente: " + e.getMessage());
        }

        // Prueba 6: Marcar estado de error
        Celda celdaError = new Celda(0, 0);
        celdaError.establecerValor(3);
        celdaError.establecerError(true);
        System.out.println("Celda con error: " + celdaError);

        System.out.println("=== Prueba completada ===");
    }
    public static void main(String[] args) {
        probarCelda();
        //launch(args);
    }
}
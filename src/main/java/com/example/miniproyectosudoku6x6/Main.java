package com.example.miniproyectosudoku6x6;

import com.example.miniproyectosudoku6x6.model.Celda;
import com.example.miniproyectosudoku6x6.model.TableroSudoku;
import com.example.miniproyectosudoku6x6.model.ValidadorSudoku;
import com.example.miniproyectosudoku6x6.model.GeneradorSudoku;
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
     * Temporary test method for the GeneradorSudoku class.
     */
    private static void probarGenerador() {
        System.out.println("=== Prueba de la clase GeneradorSudoku ===");

        GeneradorSudoku generador = new GeneradorSudoku();

        // Generar tres tableros y verificar que son diferentes
        System.out.println("\n--- Tablero 1 ---");
        TableroSudoku tablero1 = generador.generarNuevoTablero();
        System.out.println(tablero1);

        System.out.println("\n--- Tablero 2 ---");
        TableroSudoku tablero2 = generador.generarNuevoTablero();
        System.out.println(tablero2);

        System.out.println("\n--- Verificaciones del Tablero 1 ---");

        // Contar pistas fijas por bloque
        int totalFijas = 0;
        for (int b = 0; b < TableroSudoku.TOTAL_BLOQUES; b++) {
            int fijasEnBloque = 0;
            for (Celda c : tablero1.obtenerBloque(b)) {
                if (c.esFija()) {
                    fijasEnBloque++;
                }
            }
            System.out.println("Bloque " + b + ": " + fijasEnBloque + " celda(s) fija(s)");
            totalFijas += fijasEnBloque;
        }
        System.out.println("Total de celdas fijas: " + totalFijas
                + " (esperado: 12)");

        // Verificar que el tablero parcial es valido
        ValidadorSudoku validador = new ValidadorSudoku(tablero1);
        boolean valido = validador.validarTableroCompleto();
        System.out.println("Tablero generado es valido? " + valido);

        // Generar y verificar un tablero completo (resuelto)
        System.out.println("\n--- Tablero resuelto completo ---");
        TableroSudoku tableroResuelto = generador.generarTableroResuelto();
        System.out.println(tableroResuelto);

        ValidadorSudoku validadorResuelto = new ValidadorSudoku(tableroResuelto);
        System.out.println("Tablero resuelto esta completo? "
                + tableroResuelto.estaCompleto());
        System.out.println("Tablero resuelto es valido? "
                + validadorResuelto.estaResueltoCorrectamente());

        System.out.println("\n=== Prueba de Generador completada ===");
    }
    public static void main(String[] args) {
        probarGenerador();
        //launch(args);
    }
}
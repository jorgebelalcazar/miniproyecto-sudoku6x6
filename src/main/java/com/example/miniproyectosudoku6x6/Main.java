package com.example.miniproyectosudoku6x6;

import com.example.miniproyectosudoku6x6.model.Celda;
import com.example.miniproyectosudoku6x6.model.TableroSudoku;
import com.example.miniproyectosudoku6x6.model.ValidadorSudoku;
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
     * Temporary test method for the ValidadorSudoku class.
     */
    private static void probarValidador() {
        System.out.println("=== Prueba de la clase ValidadorSudoku ===");

        TableroSudoku tablero = new TableroSudoku();
        ValidadorSudoku validador = new ValidadorSudoku(tablero);

        // Colocamos algunos numeros para probar
        tablero.obtenerCelda(0, 0).establecerValor(1);
        tablero.obtenerCelda(0, 2).establecerValor(3);
        tablero.obtenerCelda(2, 0).establecerValor(5);

        System.out.println("Tablero de prueba:");
        System.out.println(tablero);

        // PRUEBA 1: Validacion correcta
        System.out.println("--- Prueba de filas ---");
        System.out.println("Colocar 2 en (0,1) - deberia ser valido: "
                + validador.esMovimientoValido(0, 1, 2));
        System.out.println("Colocar 1 en (0,3) - deberia ser invalido (1 ya esta en fila 0): "
                + validador.esMovimientoValido(0, 3, 1));

        // PRUEBA 2: Validacion de columnas
        System.out.println("--- Prueba de columnas ---");
        System.out.println("Colocar 5 en (3,0) - deberia ser invalido (5 ya esta en columna 0): "
                + validador.esMovimientoValido(3, 0, 5));
        System.out.println("Colocar 2 en (4,0) - deberia ser valido: "
                + validador.esMovimientoValido(4, 0, 2));

        // PRUEBA 3: Validacion de bloques
        System.out.println("--- Prueba de bloques ---");
        System.out.println("Colocar 1 en (1,1) - deberia ser invalido (1 ya esta en bloque 0): "
                + validador.esMovimientoValido(1, 1, 1));
        System.out.println("Colocar 4 en (1,1) - deberia ser valido: "
                + validador.esMovimientoValido(1, 1, 4));

        // PRUEBA 4: Reemplazar valor de una celda (no debe compararse consigo misma)
        System.out.println("--- Prueba de reemplazo ---");
        System.out.println("Reemplazar (0,0) con 1 (su mismo valor) - deberia ser valido: "
                + validador.esMovimientoValido(0, 0, 1));

        // PRUEBA 5: Forzar un error y validar tablero completo
        System.out.println("--- Prueba de validacion completa ---");
        tablero.obtenerCelda(0, 4).establecerValor(1);  // duplica el 1 en fila 0
        boolean valido = validador.validarTableroCompleto();
        System.out.println("Tablero valido (esperado false): " + valido);
        System.out.println("Celda (0,0) tiene error? " + tablero.obtenerCelda(0, 0).tieneError());
        System.out.println("Celda (0,4) tiene error? " + tablero.obtenerCelda(0, 4).tieneError());
        System.out.println("Celda (2,0) tiene error? " + tablero.obtenerCelda(2, 0).tieneError());

        System.out.println("=== Prueba de Validador completada ===");
    }
    public static void main(String[] args) {
        probarValidador();
        //launch(args);
    }
}
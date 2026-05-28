package com.example.miniproyectosudoku6x6;

import com.example.miniproyectosudoku6x6.model.GeneradorSudoku;
import com.example.miniproyectosudoku6x6.model.TableroSudoku;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Main entry point of the Sudoku 6x6 application.
 * <p>
 * This class bootstraps the JavaFX runtime and loads the main FXML view.
 * The view is wired to its controller automatically by the JavaFX runtime,
 * following the Model-View-Controller architecture.
 * </p>
 *
 * @author Jorge Iván Belalcázar
 * @version 1.0.0
 * @since 2026-05
 */
public class Main extends Application {

    /** Title shown in the application's title bar. */
    private static final String TITULO_VENTANA = "Sudoku 6x6 - Universidad del Valle";

    /** Default width of the main window, in pixels. */
    private static final int ANCHO_VENTANA = 520;

    /** Default height of the main window, in pixels. */
    private static final int ALTO_VENTANA = 680;

    /** Path of the main FXML view, relative to the resources folder. */
    private static final String RUTA_FXML = "/com/example/miniproyectosudoku6x6/sudoku-view.fxml";

    /**
     * JavaFX entry point. Called automatically after the application launches.
     * Loads the main FXML view, applies the stylesheet, and displays the
     * primary stage.
     *
     * @param escenarioPrincipal the main window provided by the JavaFX runtime
     * @throws Exception if the FXML resource cannot be loaded
     */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        URL urlFxml = Main.class.getResource(RUTA_FXML);
        if (urlFxml == null) {
            throw new IllegalStateException(
                    "No se pudo encontrar el archivo FXML en: " + RUTA_FXML
            );
        }

        FXMLLoader cargador = new FXMLLoader(urlFxml);
        Parent raiz = cargador.load();

        Scene escena = new Scene(raiz, ANCHO_VENTANA, ALTO_VENTANA);

        escenarioPrincipal.setTitle(TITULO_VENTANA);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setResizable(false);
        escenarioPrincipal.show();
    }

    /**
     * Temporary test method for the GeneradorSudoku class.
     */
    private static void probarGenerador() {
        System.out.println("=== Prueba de la clase GeneradorSudoku ===");
        GeneradorSudoku generador = new GeneradorSudoku();
        TableroSudoku tablero = generador.generarNuevoTablero();
        System.out.println(tablero);
        System.out.println("=== Prueba de Generador completada ===\n");
    }

    /**
     * Standard Java entry point. Delegates control to JavaFX through
     * {@link Application#launch(String...)}.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        probarGenerador();
        launch(args);
    }
}
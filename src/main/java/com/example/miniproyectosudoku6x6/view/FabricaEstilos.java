package com.example.miniproyectosudoku6x6.view;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Utility class that centralizes every CSS style manipulation performed
 * on the Sudoku view components.
 * <p>
 * By concentrating style management in a single place, the controller
 * stays focused on coordinating model and view, while the view layer
 * owns all the cosmetic details. This improves the cohesion of both
 * layers and reduces duplication of magic strings (CSS class names)
 * across the project.
 * </p>
 *
 * <p>All methods are static because this class holds no state: it only
 * exposes pure visual operations on JavaFX components.</p>
 *
 * <p>This class belongs to the view layer of the MVC architecture.</p>
 */
public final class FabricaEstilos {

    /** CSS class applied to a fixed (clue) cell. */
    public static final String CLASE_CELDA_FIJA = "celda-fija";

    /** CSS class applied to a cell that violates a Sudoku rule. */
    public static final String CLASE_CELDA_ERROR = "celda-error";

    /** CSS class applied to a cell while it is being highlighted as a hint. */
    public static final String CLASE_CELDA_SUGERENCIA = "celda-sugerencia";

    /** CSS class applied to the right edge of a 2x3 block. */
    public static final String CLASE_BORDE_DERECHO = "celda-borde-derecho";

    /** CSS class applied to the bottom edge of a 2x3 block. */
    public static final String CLASE_BORDE_INFERIOR = "celda-borde-inferior";

    /** CSS class applied to the inner corner of two adjacent block edges. */
    public static final String CLASE_BORDE_INFERIOR_DERECHO = "celda-borde-inferior-derecho";

    /** CSS class that styles the status label as a success message. */
    public static final String CLASE_ESTADO_EXITO = "estado-exito";

    /** CSS class that styles the status label as an error message. */
    public static final String CLASE_ESTADO_ERROR = "estado-error";

    /**
     * Private constructor to prevent instantiation. This is a utility class
     * with only static members.
     */
    private FabricaEstilos() {
        throw new UnsupportedOperationException(
                "FabricaEstilos es una clase utilitaria y no debe instanciarse."
        );
    }

    /**
     * Applies the "fixed cell" visual style to the given text field.
     * Fixed cells are highlighted to show the player they are clues that
     * cannot be modified.
     *
     * @param campo the text field representing the fixed cell
     */
    public static void aplicarEstiloCeldaFija(TextField campo) {
        agregarClaseUnica(campo, CLASE_CELDA_FIJA);
    }

    /**
     * Removes the "fixed cell" visual style from the given text field.
     *
     * @param campo the text field to update
     */
    public static void quitarEstiloCeldaFija(TextField campo) {
        campo.getStyleClass().remove(CLASE_CELDA_FIJA);
    }

    /**
     * Toggles the error style on the given text field. When the cell is
     * in an error state, the text field gets a red border; otherwise the
     * error style is removed.
     *
     * @param campo      the text field to update
     * @param tieneError {@code true} to mark the cell as invalid,
     *                   {@code false} to clear the error style
     */
    public static void aplicarEstiloError(TextField campo, boolean tieneError) {
        campo.getStyleClass().remove(CLASE_CELDA_ERROR);
        if (tieneError) {
            campo.getStyleClass().add(CLASE_CELDA_ERROR);
        }
    }

    /**
     * Applies the "hint suggestion" visual style to a cell, highlighting
     * it in orange. Used by the help feature.
     *
     * @param campo the text field to highlight
     */
    public static void aplicarEstiloSugerencia(TextField campo) {
        agregarClaseUnica(campo, CLASE_CELDA_SUGERENCIA);
    }

    /**
     * Removes the "hint suggestion" visual style from a cell.
     *
     * @param campo the text field to update
     */
    public static void quitarEstiloSugerencia(TextField campo) {
        campo.getStyleClass().remove(CLASE_CELDA_SUGERENCIA);
    }

    /**
     * Applies the appropriate block-border style to a cell based on its
     * position within the 6x6 grid. Cells located on the right edge of
     * a 2x3 block get a thick right border; cells on the bottom edge get
     * a thick bottom border; cells on both edges get both.
     *
     * @param campo          the text field to style
     * @param bordeDerecho   {@code true} if the cell is on a block's right edge
     * @param bordeInferior  {@code true} if the cell is on a block's bottom edge
     */
    public static void aplicarBordesBloque(TextField campo,
                                           boolean bordeDerecho,
                                           boolean bordeInferior) {
        if (bordeDerecho && bordeInferior) {
            campo.getStyleClass().add(CLASE_BORDE_INFERIOR_DERECHO);
        } else if (bordeDerecho) {
            campo.getStyleClass().add(CLASE_BORDE_DERECHO);
        } else if (bordeInferior) {
            campo.getStyleClass().add(CLASE_BORDE_INFERIOR);
        }
    }

    /**
     * Applies the success style to the status label and removes the error
     * style if present. Used to give positive feedback to the player.
     *
     * @param etiqueta the status label to update
     */
    public static void aplicarEstadoExito(Label etiqueta) {
        etiqueta.getStyleClass().remove(CLASE_ESTADO_ERROR);
        agregarClaseUnica(etiqueta, CLASE_ESTADO_EXITO);
    }

    /**
     * Applies the error style to the status label and removes the success
     * style if present. Used to alert the player about problems.
     *
     * @param etiqueta the status label to update
     */
    public static void aplicarEstadoError(Label etiqueta) {
        etiqueta.getStyleClass().remove(CLASE_ESTADO_EXITO);
        agregarClaseUnica(etiqueta, CLASE_ESTADO_ERROR);
    }

    /**
     * Adds a CSS class to a JavaFX node only if it is not already present.
     * This avoids duplicating the class in the styleClass list, which
     * could trigger unintended visual side effects.
     *
     * @param campo        the text field to update
     * @param claseEstilo  the CSS class to add
     */
    private static void agregarClaseUnica(TextField campo, String claseEstilo) {
        if (!campo.getStyleClass().contains(claseEstilo)) {
            campo.getStyleClass().add(claseEstilo);
        }
    }

    /**
     * Adds a CSS class to a JavaFX label only if it is not already present.
     *
     * @param etiqueta     the label to update
     * @param claseEstilo  the CSS class to add
     */
    private static void agregarClaseUnica(Label etiqueta, String claseEstilo) {
        if (!etiqueta.getStyleClass().contains(claseEstilo)) {
            etiqueta.getStyleClass().add(claseEstilo);
        }
    }
}
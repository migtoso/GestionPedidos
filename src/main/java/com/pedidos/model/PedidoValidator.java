package com.pedidos.model;

/**
 * Utilidad de validación para los campos de un pedido.
 * Usada tanto por la interfaz gráfica (FormularioPedido)
 * como por las pruebas de consola (Main).
 */
public class PedidoValidator {

    private PedidoValidator() { }

    /**
     * Valida que el nombre del plato no esté vacío y solo contenga
     * letras, números y espacios.
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null) return false;
        String trimmed = nombre.trim();
        return !trimmed.isEmpty() && trimmed.matches("[\\p{L}0-9 ]+");
    }

    /**
     * Valida que el texto represente un número entero positivo.
     */
    public static boolean validarCantidad(String texto) {
        if (texto == null) return false;
        try {
            return Integer.parseInt(texto.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida que se haya seleccionado una categoría real (no el placeholder).
     */
    public static boolean validarCategoria(String categoria) {
        return categoria != null && !categoria.trim().isEmpty()
                && !categoria.equals("-- Seleccionar --");
    }

    /**
     * Valida que el método de entrega sea "Domicilio" o "Recogida".
     */
    public static boolean validarMetodoEntrega(String metodo) {
        return "Domicilio".equals(metodo) || "Recogida".equals(metodo);
    }

    /**
     * Valida que la dirección no esté vacía (solo obligatoria para Domicilio).
     */
    public static boolean validarDireccion(String direccion) {
        return direccion != null && !direccion.trim().isEmpty();
    }
}

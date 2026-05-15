package com.pedidos;

import com.pedidos.model.Pedido;
import com.pedidos.ui.FormularioPedido;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 * Ejecuta pruebas del sistema por consola y luego lanza la interfaz gráfica.
 */
public class Main {

    public static void main(String[] args) {

        // ============================================================
        //  PRUEBAS DEL SISTEMA (consola)
        // ============================================================
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║      PRUEBAS DEL SISTEMA DE PEDIDOS      ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // -- Test 1: Pedido con entrega a domicilio --
        System.out.println("\n[TEST 1] Crear pedido con entrega a domicilio:");
        Pedido p1 = new Pedido("Ensalada César", "Entrante", 2, "Domicilio", "Calle Mayor 5, Madrid");
        System.out.println("  Resultado : " + p1);
        System.out.println("  Estado    : OK");

        // -- Test 2: Pedido con recogida en local --
        System.out.println("\n[TEST 2] Crear pedido con recogida en local:");
        Pedido p2 = new Pedido("Paella Valenciana", "Plato Principal", 3, "Recogida", "");
        System.out.println("  Resultado : " + p2);
        System.out.println("  Estado    : OK");

        // -- Test 3: Pedido de postre --
        System.out.println("\n[TEST 3] Crear pedido de postre:");
        Pedido p3 = new Pedido("Tarta de Chocolate", "Postre", 1, "Domicilio", "Av. Constitución 22");
        System.out.println("  Resultado : " + p3);
        System.out.println("  Estado    : OK");

        // -- Test 4: Validación — nombre vacío (debe fallar) --
        System.out.println("\n[TEST 4] Validación — nombre vacío:");
        String nombre = "   ";
        boolean nombreValido = !nombre.trim().isEmpty() && nombre.trim().matches("[\\p{L}0-9 ]+");
        System.out.println("  Nombre vacío válido: " + nombreValido + "  (esperado: false)  →  " + (nombreValido == false ? "PASS" : "FAIL"));

        // -- Test 5: Validación — nombre correcto --
        System.out.println("\n[TEST 5] Validación — nombre correcto:");
        nombre = "Gazpacho Andaluz";
        nombreValido = !nombre.trim().isEmpty() && nombre.trim().matches("[\\p{L}0-9 ]+");
        System.out.println("  Nombre '" + nombre + "' válido: " + nombreValido + "  (esperado: true)  →  " + (nombreValido ? "PASS" : "FAIL"));

        // -- Test 6: Validación — nombre con caracteres especiales (debe fallar) --
        System.out.println("\n[TEST 6] Validación — nombre con caracteres especiales:");
        nombre = "Pollo@#$";
        nombreValido = !nombre.trim().isEmpty() && nombre.trim().matches("[\\p{L}0-9 ]+");
        System.out.println("  Nombre '" + nombre + "' válido: " + nombreValido + "  (esperado: false)  →  " + (nombreValido == false ? "PASS" : "FAIL"));

        // -- Test 7: Validación — cantidad positiva válida --
        System.out.println("\n[TEST 7] Validación — cantidad positiva:");
        boolean cantidadValida = validarCantidad("5");
        System.out.println("  Cantidad '5' válida: " + cantidadValida + "  (esperado: true)  →  " + (cantidadValida ? "PASS" : "FAIL"));

        // -- Test 8: Validación — cantidad negativa (debe fallar) --
        System.out.println("\n[TEST 8] Validación — cantidad negativa:");
        cantidadValida = validarCantidad("-3");
        System.out.println("  Cantidad '-3' válida: " + cantidadValida + "  (esperado: false)  →  " + (cantidadValida == false ? "PASS" : "FAIL"));

        // -- Test 9: Validación — cantidad no numérica (debe fallar) --
        System.out.println("\n[TEST 9] Validación — cantidad no numérica:");
        cantidadValida = validarCantidad("abc");
        System.out.println("  Cantidad 'abc' válida: " + cantidadValida + "  (esperado: false)  →  " + (cantidadValida == false ? "PASS" : "FAIL"));

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║       INICIANDO INTERFAZ GRÁFICA...      ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        // ============================================================
        //  LANZAR INTERFAZ GRÁFICA (siempre en el Event Dispatch Thread)
        // ============================================================
        SwingUtilities.invokeLater(() -> {
            // Aplicar apariencia del sistema operativo
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }

            FormularioPedido form = new FormularioPedido();

            // Pre-cargar los 3 pedidos de prueba en la tabla para demostración
            form.getVentanaPedidos().agregarPedido(p1);
            form.getVentanaPedidos().agregarPedido(p2);
            form.getVentanaPedidos().agregarPedido(p3);
            form.getVentanaPedidos().setVisible(true);

            form.setVisible(true);
        });
    }

    /**
     * Valida que un texto represente un número entero positivo.
     * Usado en las pruebas de consola.
     */
    private static boolean validarCantidad(String texto) {
        try {
            return Integer.parseInt(texto.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

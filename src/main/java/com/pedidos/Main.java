package com.pedidos;

import com.pedidos.model.Pedido;
import com.pedidos.model.PedidoValidator;
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
        Pedido p1 = new Pedido("Mote de Queso", "Entrante", 2, "Domicilio", "Av. Pedro de Heredia 23, Cartagena");
        System.out.println("  Resultado : " + p1);
        System.out.println("  Estado    : OK");

        // -- Test 2: Pedido con recogida en local --
        System.out.println("\n[TEST 2] Crear pedido con recogida en local:");
        Pedido p2 = new Pedido("Arepa de Huevo", "Entrante", 3, "Recogida", "");
        System.out.println("  Resultado : " + p2);
        System.out.println("  Estado    : OK");

        // -- Test 3: Pedido de postre --
        System.out.println("\n[TEST 3] Crear pedido de postre:");
        Pedido p3 = new Pedido("Enyucado", "Postre", 1, "Domicilio", "Av. San Martín 15, Cartagena");
        System.out.println("  Resultado : " + p3);
        System.out.println("  Estado    : OK");

        // -- Test 4: Validación — nombre vacío (debe fallar) --
        System.out.println("\n[TEST 4] Validación — nombre vacío:");
        boolean nombreValido = PedidoValidator.validarNombre("   ");
        System.out.println("  Nombre vacío válido: " + nombreValido + "  (esperado: false)  →  " + (!nombreValido ? "PASS" : "FAIL"));

        // -- Test 5: Validación — nombre correcto --
        System.out.println("\n[TEST 5] Validación — nombre correcto:");
        nombreValido = PedidoValidator.validarNombre("Cazuela de Mariscos");
        System.out.println("  Nombre 'Cazuela de Mariscos' válido: " + nombreValido + "  (esperado: true)  →  " + (nombreValido ? "PASS" : "FAIL"));

        // -- Test 6: Validación — nombre con caracteres especiales (debe fallar) --
        System.out.println("\n[TEST 6] Validación — nombre con caracteres especiales:");
        nombreValido = PedidoValidator.validarNombre("Arepa@#$");
        System.out.println("  Nombre 'Arepa@#$' válido: " + nombreValido + "  (esperado: false)  →  " + (!nombreValido ? "PASS" : "FAIL"));

        // -- Test 7: Validación — cantidad positiva válida --
        System.out.println("\n[TEST 7] Validación — cantidad positiva:");
        boolean cantidadValida = PedidoValidator.validarCantidad("5");
        System.out.println("  Cantidad '5' válida: " + cantidadValida + "  (esperado: true)  →  " + (cantidadValida ? "PASS" : "FAIL"));

        // -- Test 8: Validación — cantidad negativa (debe fallar) --
        System.out.println("\n[TEST 8] Validación — cantidad negativa:");
        cantidadValida = PedidoValidator.validarCantidad("-3");
        System.out.println("  Cantidad '-3' válida: " + cantidadValida + "  (esperado: false)  →  " + (!cantidadValida ? "PASS" : "FAIL"));

        // -- Test 9: Validación — cantidad no numérica (debe fallar) --
        System.out.println("\n[TEST 9] Validación — cantidad no numérica:");
        cantidadValida = PedidoValidator.validarCantidad("abc");
        System.out.println("  Cantidad 'abc' válida: " + cantidadValida + "  (esperado: false)  →  " + (!cantidadValida ? "PASS" : "FAIL"));

        // -- Test 10: Validación — categoría seleccionada correctamente --
        System.out.println("\n[TEST 10] Validación — categoría válida:");
        boolean categoriaValida = PedidoValidator.validarCategoria("Plato Principal");
        System.out.println("  Categoría 'Plato Principal' válida: " + categoriaValida + "  (esperado: true)  →  " + (categoriaValida ? "PASS" : "FAIL"));

        // -- Test 11: Validación — categoría sin seleccionar (debe fallar) --
        System.out.println("\n[TEST 11] Validación — categoría sin seleccionar:");
        categoriaValida = PedidoValidator.validarCategoria("-- Seleccionar --");
        System.out.println("  Categoría '-- Seleccionar --' válida: " + categoriaValida + "  (esperado: false)  →  " + (!categoriaValida ? "PASS" : "FAIL"));

        // -- Test 12: Validación — método de entrega válido --
        System.out.println("\n[TEST 12] Validación — método de entrega válido:");
        boolean metodoValido = PedidoValidator.validarMetodoEntrega("Domicilio");
        System.out.println("  Método 'Domicilio' válido: " + metodoValido + "  (esperado: true)  →  " + (metodoValido ? "PASS" : "FAIL"));

        // -- Test 13: Validación — método de entrega no seleccionado (debe fallar) --
        System.out.println("\n[TEST 13] Validación — método de entrega no seleccionado:");
        metodoValido = PedidoValidator.validarMetodoEntrega("");
        System.out.println("  Método '' válido: " + metodoValido + "  (esperado: false)  →  " + (!metodoValido ? "PASS" : "FAIL"));

        // -- Test 14: Validación — dirección válida --
        System.out.println("\n[TEST 14] Validación — dirección válida:");
        boolean direccionValida = PedidoValidator.validarDireccion("Av. Pedro de Heredia 23, Cartagena");
        System.out.println("  Dirección válida: " + direccionValida + "  (esperado: true)  →  " + (direccionValida ? "PASS" : "FAIL"));

        // -- Test 15: Validación — dirección vacía (debe fallar) --
        System.out.println("\n[TEST 15] Validación — dirección vacía:");
        direccionValida = PedidoValidator.validarDireccion("");
        System.out.println("  Dirección vacía válida: " + direccionValida + "  (esperado: false)  →  " + (!direccionValida ? "PASS" : "FAIL"));

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

}

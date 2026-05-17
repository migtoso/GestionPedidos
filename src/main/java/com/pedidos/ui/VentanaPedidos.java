package com.pedidos.ui;

import com.pedidos.model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana que muestra todos los pedidos confirmados en una tabla.
 * Permite cancelar un pedido seleccionado y exportar todos a consola.
 */
public class VentanaPedidos extends JFrame {

    private DefaultTableModel modeloTabla;
    private JTable tablaPedidos;
    private JButton btnCancelarPedido;
    private JButton btnExportarPedidos;

    // ============================================================
    // Constructor
    // ============================================================
    public VentanaPedidos() {
        setTitle("Listado de Pedidos");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(860, 300));

        initComponents();
        setupListeners();

        pack();
        // Posicionar debajo del formulario (se ajustará cuando el form se haga visible)
        setLocationRelativeTo(null);
    }

    // ============================================================
    // Construcción de la interfaz
    // ============================================================
    private void initComponents() {
        // Columnas de la tabla
        String[] columnas = {
            "Nº Pedido", "Nombre del Plato", "Categoría",
            "Cantidad", "Método de Entrega", "Dirección"
        };

        // DefaultTableModel: almacena los pedidos en memoria y notifica a la JTable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;   // la tabla no es editable directamente
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPedidos.setRowHeight(22);
        tablaPedidos.getTableHeader().setReorderingAllowed(false);
        tablaPedidos.setFillsViewportHeight(true);

        // Ajustar ancho de algunas columnas
        tablaPedidos.getColumnModel().getColumn(0).setPreferredWidth(70);
        tablaPedidos.getColumnModel().getColumn(3).setPreferredWidth(65);
        tablaPedidos.getColumnModel().getColumn(4).setPreferredWidth(120);
        tablaPedidos.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);

        // Botones
        btnCancelarPedido  = new JButton("Cancelar Pedido");
        btnExportarPedidos = new JButton("Exportar Pedidos");
        btnCancelarPedido.setForeground(new Color(180, 40, 40));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8));
        panelBotones.add(btnCancelarPedido);
        panelBotones.add(btnExportarPedidos);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // ============================================================
    // Listeners
    // ============================================================
    private void setupListeners() {

        // --- Cancelar Pedido ---
        btnCancelarPedido.addActionListener(e -> {
            int filaSeleccionada = tablaPedidos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, seleccione un pedido de la tabla para cancelarlo.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int numeroPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea cancelar el Pedido #" + numeroPedido + "?",
                    "Confirmar cancelación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                modeloTabla.removeRow(filaSeleccionada);
            }
        });

        // --- Exportar Pedidos ---
        btnExportarPedidos.addActionListener(e -> {
            int totalFilas = modeloTabla.getRowCount();
            if (totalFilas == 0) {
                JOptionPane.showMessageDialog(this,
                        "No hay pedidos para exportar.",
                        "Sin pedidos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            System.out.println("========================================");
            System.out.println("       EXPORTACIÓN DE PEDIDOS");
            System.out.println("========================================");
            for (int i = 0; i < totalFilas; i++) {
                System.out.printf("Pedido #%-3s | Plato: %-20s | Categoría: %-15s | Cant: %s | Entrega: %-10s | Dirección: %s%n",
                        modeloTabla.getValueAt(i, 0),
                        modeloTabla.getValueAt(i, 1),
                        modeloTabla.getValueAt(i, 2),
                        modeloTabla.getValueAt(i, 3),
                        modeloTabla.getValueAt(i, 4),
                        modeloTabla.getValueAt(i, 5));
            }
            System.out.println("========================================");
            System.out.println("Total de pedidos exportados: " + totalFilas);
            System.out.println("========================================");
            JOptionPane.showMessageDialog(this,
                    "Se exportaron " + totalFilas + " pedido(s) a la consola.",
                    "Exportación completada", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // ============================================================
    // API pública — llamada desde FormularioPedido y Main
    // ============================================================
    /**
     * Agrega un pedido a la tabla en memoria y refresca la vista automáticamente.
     */
    public void agregarPedido(Pedido pedido) {
        modeloTabla.addRow(new Object[]{
            pedido.getId(),
            pedido.getNombrePlato(),
            pedido.getCategoria(),
            pedido.getCantidad(),
            pedido.getMetodoEntrega(),
            pedido.getDireccion()
        });
    }
}

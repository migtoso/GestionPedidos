package com.pedidos.ui;

import com.pedidos.model.Pedido;
import com.pedidos.model.PedidoValidator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Ventana principal con el formulario de pedido.
 * Permite introducir los datos, validarlos y enviarlos a VentanaPedidos.
 */
public class FormularioPedido extends JFrame {

    // ---- Campos del formulario ----
    private JTextField txtNombrePlato;
    private JComboBox<String> cmbCategoria;
    private JTextField txtCantidad;
    private JRadioButton rbDomicilio;
    private JRadioButton rbRecogida;
    private ButtonGroup grupoBotones;
    private JTextField txtDireccion;
    private JPanel panelMetodoEntrega;   // panel que envuelve los radio buttons (se bordea en rojo si hay error)

    // ---- Botones ----
    private JButton btnBorrar;
    private JButton btnConfirmar;

    // ---- Bordes por defecto (para restaurar tras error) ----
    private Border defaultFieldBorder;
    private Border defaultComboBorder;
    private Border defaultPanelBorder;

    // ---- Ventana de pedidos compartida ----
    private final VentanaPedidos ventanaPedidos;

    // ============================================================
    // Constructor
    // ============================================================
    public FormularioPedido() {
        setTitle("Formulario de Pedido — Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ventanaPedidos = new VentanaPedidos();

        initComponents();
        setupListeners();

        pack();
        setLocationRelativeTo(null);
    }

    // ============================================================
    // Construcción de la interfaz
    // ============================================================
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        // ---- Título ----
        JLabel lblTitulo = new JLabel("Sistema de Pedidos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // ---- Nombre del Plato ----
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Nombre del Plato:"), gbc);
        txtNombrePlato = new JTextField(22);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtNombrePlato, gbc);

        // ---- Categoría ----
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panel.add(new JLabel("Categoría del Plato:"), gbc);
        cmbCategoria = new JComboBox<>(new String[]{"-- Seleccionar --", "Entrante", "Plato Principal", "Postre"});
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(cmbCategoria, gbc);

        // ---- Cantidad ----
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panel.add(new JLabel("Cantidad de Platos:"), gbc);
        txtCantidad = new JTextField(22);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtCantidad, gbc);

        // ---- Método de Entrega ----
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panel.add(new JLabel("Método de Entrega:"), gbc);
        rbDomicilio = new JRadioButton("Domicilio");
        rbRecogida  = new JRadioButton("Recogida");
        grupoBotones = new ButtonGroup();
        grupoBotones.add(rbDomicilio);
        grupoBotones.add(rbRecogida);
        panelMetodoEntrega = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelMetodoEntrega.add(rbDomicilio);
        panelMetodoEntrega.add(rbRecogida);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(panelMetodoEntrega, gbc);

        // ---- Dirección de Entrega ----
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0;
        panel.add(new JLabel("Dirección de Entrega:"), gbc);
        txtDireccion = new JTextField(22);
        txtDireccion.setEnabled(false);
        gbc.gridx = 1; gbc.weightx = 1;
        panel.add(txtDireccion, gbc);

        // ---- Botones ----
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnBorrar    = new JButton("Borrar");
        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(new Color(70, 130, 180));
        btnConfirmar.setForeground(Color.WHITE);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnConfirmar);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.weightx = 1;
        gbc.insets = new Insets(14, 6, 6, 6);
        panel.add(panelBotones, gbc);

        add(panel);

        // Guardar bordes por defecto para restaurarlos después de errores
        defaultFieldBorder = txtNombrePlato.getBorder();
        defaultComboBorder = cmbCategoria.getBorder();
        defaultPanelBorder = panelMetodoEntrega.getBorder();
    }

    // ============================================================
    // Listeners
    // ============================================================
    private void setupListeners() {
        // Habilitar/deshabilitar campo dirección según método de entrega
        rbDomicilio.addActionListener(e -> {
            txtDireccion.setEnabled(true);
            resetBorderPanel();
        });
        rbRecogida.addActionListener(e -> {
            txtDireccion.setEnabled(false);
            txtDireccion.setText("");
            resetBorderField(txtDireccion);
            resetBorderPanel();
        });

        btnBorrar.addActionListener(e -> limpiarFormulario());
        btnConfirmar.addActionListener(e -> confirmarPedido());
    }

    // ============================================================
    // Borrar formulario
    // ============================================================
    public void limpiarFormulario() {
        txtNombrePlato.setText("");
        cmbCategoria.setSelectedIndex(0);
        txtCantidad.setText("");
        grupoBotones.clearSelection();
        txtDireccion.setText("");
        txtDireccion.setEnabled(false);

        resetBorderField(txtNombrePlato);
        resetBorderField(txtCantidad);
        resetBorderField(txtDireccion);
        resetBorderCombo();
        resetBorderPanel();
    }

    // ============================================================
    // Validación y confirmación del pedido
    // ============================================================
    private void confirmarPedido() {
        // Restablecer todos los bordes antes de validar
        resetBorderField(txtNombrePlato);
        resetBorderField(txtCantidad);
        resetBorderField(txtDireccion);
        resetBorderCombo();
        resetBorderPanel();

        boolean valido = true;
        StringBuilder errores = new StringBuilder("Por favor, corrija los siguientes errores:\n\n");

        // --- Validar Nombre del Plato ---
        String nombre = txtNombrePlato.getText().trim();
        if (!PedidoValidator.validarNombre(nombre)) {
            marcarErrorField(txtNombrePlato);
            if (nombre.isEmpty()) {
                errores.append("• El nombre del plato no puede estar vacío.\n");
            } else {
                errores.append("• El nombre del plato solo puede contener letras, números y espacios.\n");
            }
            valido = false;
        }

        // --- Validar Categoría ---
        if (!PedidoValidator.validarCategoria((String) cmbCategoria.getSelectedItem())) {
            marcarErrorCombo();
            errores.append("• Debe seleccionar una categoría de plato.\n");
            valido = false;
        }

        // --- Validar Cantidad ---
        String cantidadStr = txtCantidad.getText().trim();
        int cantidad = 0;
        if (cantidadStr.isEmpty()) {
            marcarErrorField(txtCantidad);
            errores.append("• La cantidad de platos no puede estar vacía.\n");
            valido = false;
        } else if (!PedidoValidator.validarCantidad(cantidadStr)) {
            marcarErrorField(txtCantidad);
            errores.append("• La cantidad de platos debe ser un número entero positivo.\n");
            valido = false;
        } else {
            cantidad = Integer.parseInt(cantidadStr);
        }

        // --- Validar Método de Entrega ---
        String metodo = rbDomicilio.isSelected() ? "Domicilio" : rbRecogida.isSelected() ? "Recogida" : "";
        if (!PedidoValidator.validarMetodoEntrega(metodo)) {
            marcarErrorPanel();
            errores.append("• Debe seleccionar un método de entrega.\n");
            valido = false;
        }

        // --- Validar Dirección (solo si Domicilio seleccionado) ---
        String direccion = "";
        if (rbDomicilio.isSelected()) {
            direccion = txtDireccion.getText().trim();
            if (!PedidoValidator.validarDireccion(direccion)) {
                marcarErrorField(txtDireccion);
                errores.append("• Debe ingresar una dirección de entrega válida.\n");
                valido = false;
            }
        }

        if (!valido) {
            JOptionPane.showMessageDialog(this, errores.toString(),
                    "Errores de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- Crear y registrar el pedido ---
        String categoria = (String) cmbCategoria.getSelectedItem();
        Pedido pedido    = new Pedido(nombre, categoria, cantidad, metodo, direccion);

        ventanaPedidos.agregarPedido(pedido);
        ventanaPedidos.setVisible(true);
        ventanaPedidos.toFront();

        JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getId() + " confirmado correctamente.",
                "Pedido Confirmado", JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    // ============================================================
    // Helpers para marcar/restablecer bordes de error
    // ============================================================
    private void marcarErrorField(JTextField field) {
        field.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }
    private void marcarErrorCombo() {
        cmbCategoria.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }
    private void marcarErrorPanel() {
        panelMetodoEntrega.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    private void resetBorderField(JTextField field) {
        field.setBorder(defaultFieldBorder);
    }
    private void resetBorderCombo() {
        cmbCategoria.setBorder(defaultComboBorder);
    }
    private void resetBorderPanel() {
        panelMetodoEntrega.setBorder(defaultPanelBorder);
    }

    // ============================================================
    // Acceso a VentanaPedidos (usado desde Main para tests)
    // ============================================================
    public VentanaPedidos getVentanaPedidos() {
        return ventanaPedidos;
    }
}

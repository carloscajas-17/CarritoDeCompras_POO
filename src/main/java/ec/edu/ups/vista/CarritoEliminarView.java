package ec.edu.ups.vista;


import ec.edu.ups.modelo.Carrito;

import javax.swing.*;

public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextArea txtResultado;
    private JButton btnEliminar;

    public CarritoEliminarView() {
        setContentPane(panelPrincipal);
        setTitle("Eliminar Carrito");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarEliminacion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este carrito?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    public void mostrarCarrito(Carrito carrito) {
        if (carrito == null) {
            txtResultado.setText("Carrito no encontrado.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Código: ").append(carrito.getCodigo()).append("\n");
            sb.append("Total: $").append(carrito.calcularTotal()).append("\n");
            sb.append("Items: ").append(carrito.obtenerItems().size());
            txtResultado.setText(sb.toString());
        }
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtResultado.setText("");
    }
}
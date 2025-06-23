package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblItems;
    private JButton btnActualizar;
    private DefaultTableModel modelo;

    public CarritoModificarView() {
        setContentPane(panelPrincipal);
        setTitle("Modificar Carrito");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Código Producto", "Nombre", "Precio", "Cantidad"});
        tblItems.setModel(modelo);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JTable getTblItems() {
        return tblItems;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void limpiarTabla() {
        modelo.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}


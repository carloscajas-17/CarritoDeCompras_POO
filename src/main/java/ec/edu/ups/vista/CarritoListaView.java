package ec.edu.ups.vista;


import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoListaView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JButton btnListar;
    private DefaultTableModel modelo;

    public CarritoListaView() {
        setContentPane(panelPrincipal);
        setTitle("Listado de Carritos");
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Código", "Fecha", "Total", "Items"});
        tblCarritos.setModel(modelo);
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public void cargarTabla(List<Carrito> carritos) {
        modelo.setRowCount(0);
        for (Carrito c : carritos) {
            modelo.addRow(new Object[]{
                    c.getCodigo(),
                    c.getFechaCreacion().getTime(),
                    String.format("%.2f", c.calcularTotal()),
                    c.obtenerItems().size()
            });
        }
    }
}


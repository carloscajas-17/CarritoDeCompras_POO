package ec.edu.ups.vista;



import ec.edu.ups.modelo.Carrito;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class CarritoBuscarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextArea txtResultado;

    public CarritoBuscarView() {
        setContentPane(panelPrincipal);
        setTitle("Buscar Carrito");
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

    public void mostrarCarrito(Carrito carrito) {
        if (carrito == null) {
            txtResultado.setText("No se encontró ningún carrito con ese código.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            StringBuilder sb = new StringBuilder();
            sb.append("Código: ").append(carrito.getCodigo()).append("\n");
            sb.append("Fecha: ").append(sdf.format(carrito.getFechaCreacion().getTime())).append("\n");
            sb.append("Total: $").append(String.format("%.2f", carrito.calcularTotal())).append("\n");
            sb.append("Items:\n");
            carrito.obtenerItems().forEach(item ->
                    sb.append("- ").append(item.getProducto().getNombre())
                            .append(" x ").append(item.getCantidad())
                            .append(" = $").append(item.getSubtotal()).append("\n")
            );
            txtResultado.setText(sb.toString());
        }
    }
}

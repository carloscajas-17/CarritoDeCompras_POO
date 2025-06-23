package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoBuscarView carritoBuscarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoListaView carritoListaView;
    private final CarritoModificarView carritoModificarView;

    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO,
                             ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoBuscarView carritoBuscarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoListaView carritoListaView,
                             CarritoModificarView carritoModificarView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoBuscarView = carritoBuscarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoListaView = carritoListaView;
        this.carritoModificarView = carritoModificarView;
        this.carrito = new Carrito();

        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAnadir().addActionListener(e -> anadirProducto());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoBuscarView.getBtnBuscar().addActionListener(e -> buscarCarrito());
        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        carritoListaView.getBtnListar().addActionListener(e -> listarCarritos());
        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaModificar());
        carritoModificarView.getBtnActualizar().addActionListener(e -> modificarCarrito());
    }

    private void anadirProducto() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());
        carrito.agregarProducto(producto, cantidad);
        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getSubtotal()
            });
        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(String.valueOf(carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIva().setText(String.valueOf(carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.valueOf(carrito.calcularTotal()));
    }

    private void guardarCarrito() {
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito creado correctamente");
        carrito = new Carrito();
    }

    private void buscarCarrito() {
        try {
            int codigo = Integer.parseInt(carritoBuscarView.getTxtCodigo().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            carritoBuscarView.mostrarCarrito(c);
        } catch (NumberFormatException e) {
            carritoBuscarView.mostrarCarrito(null);
        }
    }

    private void buscarCarritoParaEliminar() {
        try {
            int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            carritoEliminarView.mostrarCarrito(c);
        } catch (NumberFormatException e) {
            carritoEliminarView.mostrarCarrito(null);
        }
    }

    private void eliminarCarrito() {
        try {
            int codigo = Integer.parseInt(carritoEliminarView.getTxtCodigo().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c == null) {
                carritoEliminarView.mostrarMensaje("Carrito no encontrado.");
                return;
            }
            if (carritoEliminarView.confirmarEliminacion()) {
                carritoDAO.eliminar(codigo);
                carritoEliminarView.mostrarMensaje("Carrito eliminado correctamente.");
                carritoEliminarView.limpiarCampos();
            }
        } catch (NumberFormatException e) {
            carritoEliminarView.mostrarMensaje("Código inválido.");
        }
    }

    private void listarCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        carritoListaView.cargarTabla(carritos);
    }

    private void buscarCarritoParaModificar() {
        try {
            int codigo = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c == null) {
                carritoModificarView.mostrarMensaje("Carrito no encontrado.");
                return;
            }
            carritoModificarView.limpiarTabla();
            DefaultTableModel modelo = carritoModificarView.getModelo();
            for (ItemCarrito item : c.obtenerItems()) {
                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad()
                });
            }
        } catch (NumberFormatException e) {
            carritoModificarView.mostrarMensaje("Código inválido.");
        }
    }

    private void modificarCarrito() {
        try {
            int codigo = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c == null) {
                carritoModificarView.mostrarMensaje("Carrito no encontrado.");
                return;
            }
            DefaultTableModel modelo = carritoModificarView.getModelo();
            c.vaciarCarrito();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int codProducto = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                Producto p = productoDAO.buscarPorCodigo(codProducto);
                if (p != null) {
                    c.agregarProducto(p, cantidad);
                }
            }
            carritoDAO.actualizar(c);
            carritoModificarView.mostrarMensaje("Carrito modificado correctamente.");
        } catch (Exception e) {
            carritoModificarView.mostrarMensaje("Error al modificar el carrito.");
        }
    }
}

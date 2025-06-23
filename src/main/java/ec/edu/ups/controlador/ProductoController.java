
package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CarritoAnadirView;
import ec.edu.ups.vista.ProductoAnadirView;
import ec.edu.ups.vista.ProductoEliminarView;
import ec.edu.ups.vista.ProductoListaView;
import ec.edu.ups.vista.ProductoModificarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private final ProductoAnadirView productoAnadirView;
    private final ProductoListaView productoListaView;
    private final ProductoEliminarView productoEliminarView;
    private final ProductoModificarView productoModificarView;
    private final CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO,
                              ProductoAnadirView productoAnadirView,
                              ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView,
                              ProductoModificarView productoModificarView,
                              CarritoAnadirView carritoAnadirView) {
        this.productoDAO = productoDAO;
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView;
        this.carritoAnadirView = carritoAnadirView;

        configurarEventosEnVistas();
    }

    private void configurarEventosEnVistas() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());

        productoListaView.getBtnBuscar().addActionListener(e -> buscarProducto());

        productoListaView.getBtnListar().addActionListener(e -> listarProductos());

        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());

        productoModificarView.getBtnBuscar().addActionListener(e -> buscarProductoParaModificar());
        productoModificarView.getBtnModificar().addActionListener(e -> modificarProducto());

        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoPorCodigo());
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productosEncontrados = productoDAO.buscarPorNombre(nombre);
        productoListaView.cargarDatos(productosEncontrados);
    }

    private void listarProductos() {
        List<Producto> productos = productoDAO.listarTodos();
        productoListaView.cargarDatos(productos);
    }

    private void buscarProductoPorCodigo() {
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto == null) {
            carritoAnadirView.mostrarMensaje("No se encontró el producto");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        } else {
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }

    private void buscarProductoParaEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoEliminarView.mostrarMensaje("Producto no encontrado.");
                productoEliminarView.limpiarCampos();
            }
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje("Código inválido.");
        }
    }

    private void eliminarProducto() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                productoEliminarView.mostrarMensaje("Producto no encontrado.");
                return;
            }

            boolean confirmar = productoEliminarView.confirmarEliminacion();
            if (confirmar) {
                productoDAO.eliminar(codigo);
                productoEliminarView.mostrarMensaje("Producto eliminado correctamente.");
                productoEliminarView.limpiarCampos();
            }
        } catch (NumberFormatException e) {
            productoEliminarView.mostrarMensaje("Código inválido.");
        }
    }

    private void buscarProductoParaModificar() {
        try {
            int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto != null) {
                productoModificarView.getTxtNombre().setText(producto.getNombre());
                productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            } else {
                productoModificarView.mostrarMensaje("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            productoModificarView.mostrarMensaje("Código inválido.");
        }
    }

    private void modificarProducto() {
        try {
            int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText().trim());
            String nombre = productoModificarView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText().trim());

            Producto productoModificado = new Producto(codigo, nombre, precio);
            productoDAO.actualizar(productoModificado);

            productoModificarView.mostrarMensaje("Producto modificado correctamente.");
        } catch (NumberFormatException e) {
            productoModificarView.mostrarMensaje("Datos inválidos.");
        }
    }
}

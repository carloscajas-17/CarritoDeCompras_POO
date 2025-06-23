package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {

            // Iniciar sesión
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            LoginView loginView = new LoginView();
            loginView.setVisible(true);

            UsuarioController usuarioController = new UsuarioController(usuarioDAO, loginView);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent we) {

                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        // Instanciar DAOs
                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();

                        // Instanciar Vistas
                        MenuPrincipalView principalView = new MenuPrincipalView();
                        ProductoAnadirView productoAnadirView = new ProductoAnadirView();
                        ProductoListaView productoListaView = new ProductoListaView();
                        ProductoEliminarView productoEliminarView = new ProductoEliminarView();
                        ProductoModificarView productoModificarView = new ProductoModificarView();
                        CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
                        CarritoBuscarView carritoBuscarView = new CarritoBuscarView();
                        CarritoEliminarView carritoEliminarView = new CarritoEliminarView();
                        CarritoListaView carritoListaView = new CarritoListaView();
                        CarritoModificarView carritoModificarView = new CarritoModificarView();

                        // Instanciar Controladores
                        ProductoController productoController = new ProductoController(
                                productoDAO,
                                productoAnadirView,
                                productoListaView,
                                productoEliminarView,
                                productoModificarView,
                                carritoAnadirView
                        );

                        CarritoController carritoController = new CarritoController(
                                carritoDAO,
                                productoDAO,
                                carritoAnadirView,
                                carritoBuscarView,
                                carritoEliminarView,
                                carritoListaView,
                                carritoModificarView
                        );

                        principalView.mostrarMensaje("Bienvenido: " + usuarioAutenticado.getUsername());
                        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                            principalView.deshabilitarMenusAdministrador();
                        }

                        // Menú Producto
                        principalView.getMenuItemCrearProducto().addActionListener(ev1 -> {
                            if (!productoAnadirView.isVisible()) {
                                productoAnadirView.setVisible(true);
                                principalView.getjDesktopPane().add(productoAnadirView);
                            }
                        });

                        principalView.getMenuItemBuscarProducto().addActionListener(ev2 -> {
                            if (!productoListaView.isVisible()) {
                                productoListaView.setVisible(true);
                                principalView.getjDesktopPane().add(productoListaView);
                            }
                        });

                        principalView.getMenuItemEliminarProducto().addActionListener(ev3 -> {
                            if (!productoEliminarView.isVisible()) {
                                productoEliminarView.setVisible(true);
                                principalView.getjDesktopPane().add(productoEliminarView);
                            }
                        });

                        principalView.getMenuItemActualizarProducto().addActionListener(ev4 -> {
                            if (!productoModificarView.isVisible()) {
                                productoModificarView.setVisible(true);
                                principalView.getjDesktopPane().add(productoModificarView);
                            }
                        });

                        // Menú Carrito
                        principalView.getMenuItemCrearCarrito().addActionListener(ev5 -> {
                            if (!carritoAnadirView.isVisible()) {
                                carritoAnadirView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoAnadirView);
                            }
                        });

                        principalView.getMenuItemBuscarCarrito().addActionListener(ev6 -> {
                            if (!carritoBuscarView.isVisible()) {
                                carritoBuscarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoBuscarView);
                            }
                        });

                        principalView.getMenuItemEliminarCarrito().addActionListener(ev7 -> {
                            if (!carritoEliminarView.isVisible()) {
                                carritoEliminarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoEliminarView);
                            }
                        });

                        principalView.getMenuItemActualizarCarrito().addActionListener(ev8 -> {
                            if (!carritoModificarView.isVisible()) {
                                carritoModificarView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoModificarView);
                            }
                        });

                        principalView.getMenuItemListarCarrito().addActionListener(ev9 -> {
                            if (!carritoListaView.isVisible()) {
                                carritoListaView.setVisible(true);
                                principalView.getjDesktopPane().add(carritoListaView);
                            }
                        });
                    }
                }
            });
        });
    }
}

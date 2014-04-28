package tienda;

import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.*;

public class ProductoDAO {

    private Connection conn;

    public ProductoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos", e);
        }
    }

    //método que cierra la conexión si existe
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //método que llama a getProductosQuery y le pasa la condicion WHERE que busca por nombre
    public ArrayList<Producto> getProductosNombre(String nombre) {
        return getProductosQuery("upper(Nombre) LIKE upper('%" + nombre + "%')");
    }

    //método que llama a getProductosQuery y le pasa la condicion WHERE que busca por precio
    public ArrayList<Producto> getProductosPrecio(String precioMin, String precioMax) {
        if (precioMin == "" && precioMax == "") {
            return null;
        } else if (precioMin != "" && precioMax == "") {
            return getProductosQuery("Precio >= " + precioMin);
        } else if (precioMin == "" && precioMax != "") {
            return getProductosQuery("Precio >= 0 AND precio <= " + precioMax);
        } else {
            return getProductosQuery("Precio >= " + precioMin + "AND precio <= " + precioMax);
        }
    }

    //método que llama a getProductosQuery y le pasa la condicion WHERE que busca por categoría
    public ArrayList<Producto> getProductosCategoría(String categoria) {
        return getProductosQuery("upper(Categoria) = upper('" + categoria + "')");
    }

    //método que llama a getProductosQuery y no le pasa condición WHERE para que busque todos los productos
    public ArrayList<Producto> getTodosProductos() {
        return getProductosQuery(null);
    }

    //método que llama a getPedidoQuery y le pasa la condicion WHERE que busca por numero de pedido
    public ArrayList<CantidadProducto> getProductosPedido(String numero) {
        return getProductosPedidoQuery("Pedido = '" + numero + "'");
    }

    //método que agrega un nuevo producto a la BD y devuelve la confirmación
    public String agregarProducto(String nombre, String precio, String categoria, String imagen) {
        Statement stm = null;
        String agregado = "Algo falló";
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO PRODUCTO VALUES ('" + nombre + "','" + categoria + "','" + imagen + "'," + precio + ")";
            stm.executeUpdate(sql);
            agregado = "Se ha añadido el producto";
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        return agregado;
    }

    //método que llama a modificarProductoQuery y le pasa la condicion según el valor indicado en el parámetro "atributo"
    //y devuelve la confirmación
    public String modificarProducto(String nombreProd, String atributo, String nuevoValor) {
        String where = "WHERE Nombre='" + nombreProd + "'";
        if ("nombre".equals(atributo)) {
            String set = "SET Nombre='" + nuevoValor + "' ";
            String sql = "UPDATE PRODUCTO " + set + where;
            return modificarProductoQuery(sql);
        } else {
            if ("precio".equals(atributo)) {
                String set = "SET Precio=" + nuevoValor + " ";
                String sql = "UPDATE PRODUCTO " + set + where;
                return modificarProductoQuery(sql);
            } else {
                if ("categoria".equals(atributo)) {
                    String set = "SET Categoria='" + nuevoValor + "' ";
                    String sql = "UPDATE PRODUCTO " + set + where;
                    return modificarProductoQuery(sql);
                } else {
                    if ("imagen".equals(atributo)) {
                        String set = "SET Imagen='" + nuevoValor + "' ";
                        String sql = "UPDATE PRODUCTO " + set + where;
                        return modificarProductoQuery(sql);
                    } else {
                        if ("eliminar".equals(atributo)) {
                            String sql = "DELETE FROM PRODUCTO " + where;
                            return modificarProductoQuery(sql);
                        }
                    }
                }
            }
        }
        return "Algo falló";
    }

    //método que modifica un producto de la BD y devuelve la confirmación
    public String modificarProductoQuery(String sql) {
        Statement stm = null;
        String modificado = "Algo falló";
        try {
            stm = this.conn.createStatement();
            stm.executeUpdate(sql);
            modificado = "Se ha modificado el producto";
            if (sql.contains("DELETE")) {
                modificado = "Se ha eliminado el producto";
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return modificado;
    }

    //método que crea un pedido en la BD
    public void crearPedido(String nombreCliente, String numPedido) {
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO PEDIDO VALUES('" + numPedido + "','" + nombreCliente + "',false)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

    }

    //método que crea el registro de un producto pedido en la BD relacionado por el nº de pedido
    public void crearRegistroPedidos(String numPedido, String nombreProd, int cantidad) {
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "INSERT INTO REGISTRO_PEDIDOS VALUES('" + numPedido + "','" + nombreProd + "'," + cantidad + ")";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    //método que devuelve la lista de pedidos de la BD según la condición where
    public ArrayList<Pedido> getPedido(String where) {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Pedido " + where;
            ResultSet rs = stm.executeQuery(sql);
            pedidos = crearPedidoListFromRS(rs);
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return pedidos;

    }

    //método que cambia un pedido a preparado (Listo=true) y devuelve la confirmación
    public String prepararPedido(String numPedido) {
        Statement stm = null;
        String preparado = "Algo falló";
        try {
            stm = this.conn.createStatement();
            String sql = "UPDATE PEDIDO SET Listo=true WHERE Numero='" + numPedido + "'";
            stm.executeUpdate(sql);
            preparado = "El pedido ha sido marcado como preparado";
        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return preparado;

    }

    //método que devuelve la lista de productos de la BD según la condición
    private ArrayList<Producto> getProductosQuery(String where) {

        ArrayList<Producto> productos = new ArrayList<Producto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Producto";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            productos = crearProductoListFromRS(rs);
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return productos;
    }

    //método que devuelve la lista de productos de un pedido de la BD según la condición
    private ArrayList<CantidadProducto> getProductosPedidoQuery(String where) {

        ArrayList<CantidadProducto> productos = new ArrayList<CantidadProducto>();
        Statement stm = null;
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Registro_Pedidos";
            if (where != null) {
                sql += " WHERE " + where;
            }

            ResultSet rs = stm.executeQuery(sql);
            productos = crearCantidadProductoListFromRS(rs);
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error al realizar la consulta", e);
        } finally {
            try {
                stm.close();
            } catch (SQLException e) {
                System.err.println("Error al realizar la consulta: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        return productos;
    }

    //método que crea una lista de productos a partir de un ResultSet
    private ArrayList<Producto> crearProductoListFromRS(ResultSet rs) throws SQLException {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        while (rs.next()) {
            String nombreProducto = rs.getString("Nombre");
            String categoriaProducto = rs.getString("Categoria");
            double precioProducto = rs.getDouble("Precio");
            String imagenProducto = rs.getString("Imagen");
            productos.add(new Producto(nombreProducto, categoriaProducto, imagenProducto, precioProducto));
        }
        return productos;
    }

    //método que crea una lista de productos de un pedido a partir de un ResultSet
    private ArrayList<CantidadProducto> crearCantidadProductoListFromRS(ResultSet rs) throws SQLException {
        ArrayList<CantidadProducto> productos = new ArrayList<CantidadProducto>();
        while (rs.next()) {
            String producto = rs.getString("Producto");
            int cantidad = rs.getInt("Cantidad");
            productos.add(new CantidadProducto(producto, cantidad));
        }
        return productos;
    }

    //método que crea una lista de pedidos partir de un ResultSet
    private ArrayList<Pedido> crearPedidoListFromRS(ResultSet rs) throws SQLException {
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        while (rs.next()) {
            String numero = rs.getString("Numero");
            String usuario = rs.getString("Usuario");
            boolean listo = rs.getBoolean("Listo");
            ArrayList<CantidadProducto> listaProd = getProductosPedido(numero);
            pedidos.add(new Pedido(numero, usuario, listo, listaProd));
        }
        return pedidos;
    }
}

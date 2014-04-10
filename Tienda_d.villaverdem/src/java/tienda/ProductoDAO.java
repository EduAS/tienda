package tienda;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.*;


/**
 *
 * @author Edu y Dani
 */
public class ProductoDAO {
    
  private Connection conn;

    public ProductoDAO(DataSource ds) {
        try {
            conn = ds.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos",e);
        }
    }

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

    public List<Producto> getProductosNombre(String nombre) {
        return getProductosQuery("upper(Nombre) LIKE upper('%" + nombre + "%')");
    }

    public List<Producto> getProductosPrecio(String precioMin, String precioMax) {
        if (precioMin=="" && precioMax==""){
            return null;
        }
        else if(precioMin!="" && precioMax==""){
            return getProductosQuery("Precio >= " + precioMin);
        }
        else if(precioMin=="" && precioMax!=""){
            return getProductosQuery("Precio >= 0 AND precio <= " + precioMax);            
        }
        else{      
            return getProductosQuery("Precio >= " + precioMin +"AND precio <= " + precioMax);
        }
    }
    
    public List<Producto> getProductosCategoría(String categoria) {
        return getProductosQuery("Categoria = " + categoria);
    }

    public List<Producto> getTodosProductos() {
        return getProductosQuery(null);
    }

    private List<Producto> getProductosQuery(String where) {

        List<Producto> productos = new ArrayList<Producto>();
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
            throw new RuntimeException("Error al realizar la consulta",e);
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



    private List<Producto> crearProductoListFromRS(ResultSet rs) throws SQLException {
        List<Producto> productos = new ArrayList<Producto>();
        while (rs.next()) {
            String nombreProducto = rs.getString("Nombre");
            String categoriaProducto = rs.getString("Categoria");
            double precioProducto = rs.getDouble("Precio");
            String imagenProducto = rs.getString("Imagen");
            productos.add(new Producto(nombreProducto, categoriaProducto, imagenProducto, precioProducto));
        }
        return productos;
    }
}
package tienda;


/**
 *
 * @author Edu & Dani
 */
public class Producto {
    private String nombre;
    private String categoria;
    private String imagen;
    private double precio;

    public Producto(String nombre, String categoria, String imagen, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagen = imagen;
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public double getPrecio() {
        return precio;
    }
    
    
    
}

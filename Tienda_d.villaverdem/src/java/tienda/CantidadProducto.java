package tienda;


public class CantidadProducto extends Producto {
    private int cantidad;

    public CantidadProducto() {
        super(null, null, null, 0);
    }
    
    public CantidadProducto(String nombre,int cantidad) {
        super(nombre, null, null, 0);
        this.cantidad = cantidad;
    }

    public CantidadProducto(int cantidad, String nombre, String categoria, String imagen, double precio) {
        super(nombre, categoria, imagen, precio);
        this.cantidad = cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }  
    
    
    public double calcularPrecio(double total){
        return (double)total+(double)(this.getCantidad())*this.getPrecio();
    }

    
}

package tienda;

import java.util.ArrayList;

public class Pedido {
    private String numero;
    private String usuario;
    private boolean listo;
    private ArrayList<CantidadProducto> listaProductos;

    public Pedido() {
    }

    public Pedido(String numero, String usuario, boolean listo, ArrayList<CantidadProducto> listaProductos) {
        this.numero = numero;
        this.usuario = usuario;
        this.listo = listo;
        this.listaProductos = listaProductos;
    }


    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public String getNumero() {
        return numero;
    }

    public String getUsuario() {
        return usuario;
    }
    
    public boolean getListo(){
        return listo;
    }

    public void setListaProductos(ArrayList<CantidadProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public ArrayList<CantidadProducto> getListaProductos() {
        return listaProductos;
    }
    
    
    
}

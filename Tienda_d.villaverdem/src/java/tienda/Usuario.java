

package tienda;


public class Usuario {
    private String usuario;
    private String contraseña;
    private String nombre;

    public Usuario() {
    }

    public Usuario(String usuario, String contraseña, String nombre) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombre() {
        return nombre;
    }   
    
    
}

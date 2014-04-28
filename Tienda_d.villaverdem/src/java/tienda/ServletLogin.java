package tienda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(urlPatterns = {"/ServletLogin"})
public class ServletLogin extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //si se pulsa el boton de iniciar sesion
        if ("true".equals(request.getParameter("login"))) {
            Connection conn;
            Usuario admin = new Usuario();
            //se realiza la consulta
            try {
                conn = ds.getConnection();
                Statement stm = null;

                try {
                    stm = conn.createStatement();
                    String sql = "SELECT * FROM Administrador";
                    sql += " WHERE Usuario = '" + request.getParameter("usuario") + "' AND Contrasenia = '" + request.getParameter("password") + "'";
                    ResultSet rs = stm.executeQuery(sql);
                    admin = crearAdminFromRS(rs);
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
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.err.println("Error al cerrar la conexión: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error en la base de datos", e);
            }
            //si el nombre de usuario no está en la BD, entonces no conecta
            if (admin.getNombre() == "" | admin.getNombre() == null) {
                String error = "Usuario o contraseña incorrecto";
                request.getSession().setAttribute("error", error);
                response.sendRedirect("login.jsp");
            } else { //si está en la BD
                //se guarda el nombre de usuario en la sesión
                request.getSession().setAttribute("usuario", admin.getUsuario());
                //se inicializa a 30 min el tiempo maximo de sesion inactiva
                request.getSession().setMaxInactiveInterval(1800);
                //y se redirige a la página de administración
                response.sendRedirect("paginaAdministracion.jsp");
            }
        } else { //si se pulsa el boton que cierra la sesion
            //cerramos la sesion
            request.getSession().invalidate();
            String confirmacion = "Se ha cerrado la sesión";
            //la nueva sesion tendrá el maximo de tiempo inactivo 30 min
            request.getSession().setMaxInactiveInterval(1800);
            //se guarda la confirmación en la sesion
            request.getSession().setAttribute("confirmacion", confirmacion);
            //se redirige a la página principal
            response.sendRedirect("index.jsp");
        }
    }

    //método que crea un objeto de la clase Usuario al iniciar la sesión
    private Usuario crearAdminFromRS(ResultSet rs) throws SQLException {
        Usuario admin = new Usuario();
        while (rs.next()) {
            admin.setUsuario(rs.getString("Usuario"));
            admin.setContraseña(rs.getString("Contrasenia"));
            admin.setNombre(rs.getString("Nombre"));
        }
        return admin;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

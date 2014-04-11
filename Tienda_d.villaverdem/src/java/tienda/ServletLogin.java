package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Connection conn;  



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error en la base de datos",e);
        }
        
        Statement stm = null;
        Usuario admin=new Usuario();
        try {
            stm = this.conn.createStatement();
            String sql = "SELECT * FROM Administrador";
            sql += " WHERE Usuario == " + request.getParameter("usuario") + "AND Contrasenia == " + request.getParameter("password");
            ResultSet rs = stm.executeQuery(sql);
            admin = crearAdminFromRS(rs);
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
        
        if (admin.getNombre()==""){
            request.getSession().setAttribute("error", "Usuario o contraseña incorrecto");
            response.sendRedirect("login.jsp");  
        }
        else{
            request.getSession().setAttribute("usuario",admin.getUsuario());
            response.sendRedirect("paginaAdministración.jsp");  
        }
        

        
        

    }
    
    private Usuario crearAdminFromRS(ResultSet rs) throws SQLException {
        Usuario admin = new Usuario();
        while (rs.next()) {
            admin.setUsuario(rs.getString("Usuario"));
            admin.setContraseña(rs.getString("Contraseña"));
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

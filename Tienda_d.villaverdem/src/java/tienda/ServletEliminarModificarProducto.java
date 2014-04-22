package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletEliminarModificarProducto", urlPatterns = {"/ServletEliminarModificarProducto"})
public class ServletEliminarModificarProducto extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProductoDAO prodDAO = new ProductoDAO(ds);
        String nombreProd = request.getParameter("nombreProd");
        String modificar = request.getParameter("modificar");
        String confirmacion = "";
        try {
            if ("modificarNombre".equals(modificar)) {
                String modificarNombre = "nombre";
                String nombreNuevo = request.getParameter("nombreNuevo");
                confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
            } else {
                if ("modificarCategoria".equals(modificar)) {
                    String modificarNombre = "categoria";
                    String nombreNuevo = request.getParameter("categoriaNueva");
                    confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                } else {
                    if ("modificarPrecio".equals(modificar)) {
                        String modificarNombre = "precio";
                        String nombreNuevo = request.getParameter("precioNuevo");
                        confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                    } else {
                        if ("Eliminar".equals(modificar)) {
                            String modificarNombre = "eliminar";
                            String nombreNuevo = "";
                            confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                        }
                    }
                }
            }
        } finally {
            prodDAO.close();
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletEliminarModificarProducto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(confirmacion);
            out.println("</body>");
            out.println("</html>");
        }
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

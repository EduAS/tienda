package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletGuardarPedido", urlPatterns = {"/ServletGuardarPedido"})
public class ServletGuardarPedido extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<CantidadProducto> carroCompra = (ArrayList) request.getSession().getAttribute("carro");
        String nombreCliente = request.getParameter("nombreCliente");
        String numPedido = request.getSession().getId();
        ProductoDAO prodDAO = new ProductoDAO(ds);
        try {
            prodDAO.crearPedido(nombreCliente, numPedido);
            for (CantidadProducto prod : carroCompra) {
                int cantidad = prod.getCantidad();
                String nombreProd = prod.getNombre();
                prodDAO.crearRegistroPedidos(numPedido, nombreProd, cantidad);
            }
            request.getSession().setAttribute("carro", null);
            request.getSession().invalidate();
            String confirmacion="Su pedido ha sido guardado";
            request.getSession().setAttribute("confirmacion",confirmacion);

        } finally {
            prodDAO.close();
        }
        

        response.sendRedirect("index.jsp");
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

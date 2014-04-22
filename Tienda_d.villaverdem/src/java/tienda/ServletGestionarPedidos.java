package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletGestionarPedidos", urlPatterns = {"/ServletGestionarPedidos"})
public class ServletGestionarPedidos extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;
    private ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductoDAO prodDAO = new ProductoDAO(ds);
        try {
            if ("ver".equals(request.getParameter("gestionPedidos"))) {
                String where="WHERE Listo=false";
                pedidos = prodDAO.getPedido(where);
            } else {
                if ("preparar".equals(request.getParameter("gestionPedidos"))) {
                    pedidos = (ArrayList) request.getSession().getAttribute("pedidosPreparar");
                    int posicion = parseInt(request.getParameter("posicion"));
                    String numPed = pedidos.get(posicion).getNumero();
                    prodDAO.prepararPedido(numPed);
                    pedidos = null;
                }
            }
        } finally {
            prodDAO.close();
        }
        request.getSession().setAttribute("pedidosSinPreparar", pedidos);
        response.sendRedirect("paginaAdministracion.jsp");

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

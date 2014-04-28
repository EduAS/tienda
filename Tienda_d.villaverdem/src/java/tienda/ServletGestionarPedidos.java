package tienda;

import java.io.IOException;
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
        String confirmacion = null;
        try {
            //si quiere visualizar los pedidos que están sin preparar
            if ("ver".equals(request.getParameter("gestionPedidos"))) {
                String where = "WHERE Listo=false";
                pedidos = prodDAO.getPedido(where);
            } else {
                //si quiere preparar un pedido
                if ("preparar".equals(request.getParameter("gestionPedidos"))) {
                    pedidos = (ArrayList) request.getSession().getAttribute("pedidosPreparar");
                    int posicion = parseInt(request.getParameter("posicion"));
                    String numPed = pedidos.get(posicion).getNumero();
                    confirmacion=prodDAO.prepararPedido(numPed);
                    pedidos = null;
                }
            }
        } finally {
            prodDAO.close();
        }
        //se guarda la lista de pedidos sin preparar en la sesion
        request.getSession().setAttribute("pedidosSinPreparar", pedidos);
        //se guarda el mensaje de confirmación en la sesion
        request.getSession().setAttribute("confirmacion", confirmacion);
        //se redirige a la página de administración
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

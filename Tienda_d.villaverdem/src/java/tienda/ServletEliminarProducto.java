package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletEliminarProducto", urlPatterns = {"/ServletEliminarProducto"})
public class ServletEliminarProducto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<CantidadProducto> carroCompra = (ArrayList) request.getSession().getAttribute("carro");
        if ("uno".equals(request.getParameter("eliminar"))) {
            int cantidadQuitada = parseInt(request.getParameter("cantidad"));
            int posicion = parseInt(request.getParameter("posicion"));
            int cantidadActual = carroCompra.get(posicion).getCantidad();
            int cantidadNueva = cantidadActual - cantidadQuitada;
            carroCompra.get(posicion).setCantidad(cantidadNueva);
        }
        else{
            if("todos".equals(request.getParameter("eliminar"))){
                carroCompra= new ArrayList<CantidadProducto>();
            }
        }
        request.getSession().setAttribute("carro", carroCompra);
        response.sendRedirect("VerCarrito.jsp");

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

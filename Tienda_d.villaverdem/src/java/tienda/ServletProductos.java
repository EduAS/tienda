package tienda;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletProductos", urlPatterns = {"/ServletProductos"})
public class ServletProductos extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;
    private ArrayList<Producto> productosListados = new ArrayList<Producto>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        ProductoDAO prodDAO = new ProductoDAO(ds);
        try {
            if ("precio".equals(request.getParameter("busqueda"))) {
                String precioMinimo = request.getParameter("precioMin");
                String precioMaximo = request.getParameter("precioMax");
                productosListados = prodDAO.getProductosPrecio(precioMinimo, precioMaximo);
            } else {
                if ("nombre".equals(request.getParameter("busqueda"))) {
                    String nombreProducto = request.getParameter("nombreProd");
                    productosListados = prodDAO.getProductosNombre(nombreProducto);
                } else {
                    if ("categoria".equals(request.getParameter("busqueda"))) {
                        String categ = request.getParameter("cat");
                        productosListados = prodDAO.getProductosCategoría(categ);
                    }
                }
            }

        } finally {
            prodDAO.close();
        }
        request.getSession().setAttribute("lista", productosListados);
        if ("si".equals(request.getParameter("esAdmin"))) {
            response.sendRedirect("paginaAdministracion.jsp");
        } else {
            response.sendRedirect("index.jsp");
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

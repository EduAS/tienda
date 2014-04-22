package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(name = "ServletAgregarAlCarro", urlPatterns = {"/ServletAgregarAlCarro"})
public class ServletAgregarAlCarro extends HttpServlet {

    private ArrayList<Producto> productosListados = new ArrayList<Producto>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        productosListados = (ArrayList) request.getSession().getAttribute("listaBusqueda");
        ArrayList<CantidadProducto> carroCompra = new ArrayList<>();
        if (request.getSession().getAttribute("carro") != null) {
            carroCompra = (ArrayList) request.getSession().getAttribute("carro");
        }

        if (!"0".equals(request.getParameter("cantidad"))) {
            int posicion = parseInt(request.getParameter("posicion"));
            String nombre = productosListados.get(posicion).getNombre();
            int cantidad = parseInt(request.getParameter("cantidad"));
            int i = 0;
            boolean encontrado = false;
            while (i < carroCompra.size() && !encontrado) {
                if (carroCompra.get(i).getNombre().equals(nombre)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (encontrado) {
                int cantidadanterior = carroCompra.get(i).getCantidad();
                carroCompra.get(i).setCantidad(cantidadanterior + cantidad);
            } else {
                String categoria = productosListados.get(posicion).getCategoria();
                double precio = productosListados.get(posicion).getPrecio();
                String imagen = productosListados.get(posicion).getImagen();
                carroCompra.add(new CantidadProducto(cantidad, nombre, categoria, imagen, precio));
            }
        }
        request.getSession().setAttribute("carro", carroCompra);
        request.getSession().setAttribute("lista", productosListados);
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

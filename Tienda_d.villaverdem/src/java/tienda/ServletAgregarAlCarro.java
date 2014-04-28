package tienda;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletAgregarAlCarro", urlPatterns = {"/ServletAgregarAlCarro"})
public class ServletAgregarAlCarro extends HttpServlet {

    private ArrayList<Producto> productosListados = new ArrayList<Producto>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        productosListados = (ArrayList) request.getSession().getAttribute("listaBusqueda");
        ArrayList<CantidadProducto> carroCompra = new ArrayList<>();
        //si ya existe el carro con productos añadidos, entonces se recupera
        if (request.getSession().getAttribute("carro") != null) {
            carroCompra = (ArrayList) request.getSession().getAttribute("carro");
        }
        
        //si se ha indicado una cantidad mayor que 0, entonces se añade a la cesta
        if (!"0".equals(request.getParameter("cantidad"))) {
            int posicion = parseInt(request.getParameter("posicion"));
            String nombre = productosListados.get(posicion).getNombre();
            int cantidad = parseInt(request.getParameter("cantidad"));
            int i = 0;
            boolean encontrado = false;
            //se busca en el carro por si ya se ha añadido el mismo producto
            while (i < carroCompra.size() && !encontrado) {
                if (carroCompra.get(i).getNombre().equals(nombre)) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            //si ya habia un producto igual, se suma la cantidad indicada a la que ya había
            if (encontrado) {
                int cantidadanterior = carroCompra.get(i).getCantidad();
                carroCompra.get(i).setCantidad(cantidadanterior + cantidad);
            } else { //sino se añade al carrito la cantidad indicada esta vez
                String categoria = productosListados.get(posicion).getCategoria();
                double precio = productosListados.get(posicion).getPrecio();
                String imagen = productosListados.get(posicion).getImagen();
                carroCompra.add(new CantidadProducto(cantidad, nombre, categoria, imagen, precio));
            }
        }
        //se guarda el carro con lso nuevos productos en la sesion
        request.getSession().setAttribute("carro", carroCompra);
        //se envia la lista de busqueda para que aparezca el mismo resultado
        request.getSession().setAttribute("lista", productosListados);
        //se redirige a la página principal
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

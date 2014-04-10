/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tienda;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author eduas
 */
@WebServlet(name = "ServletProductos", urlPatterns = {"/ServletProductos"})
public class ServletProductos extends HttpServlet {
        
    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;
    private List<Producto> productosListados = new ArrayList<Producto>();
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        
         ProductoDAO prodDAO = new ProductoDAO(ds);
        
         if ("precio".equals(request.getParameter("busqueda"))){
             final String precioMinimo = request.getParameter("precioMin");
             final String precioMaximo = request.getParameter("precioMax");
             productosListados=prodDAO.getProductosPrecio(precioMinimo, precioMaximo);
         }
         else{
             if("nombre".equals(request.getParameter("busqueda"))){
                 final String nombreProducto = request.getParameter("nombreProd"); 
                 productosListados=prodDAO.getProductosNombre(nombreProducto);
             }
         }         
        request.getSession().setAttribute("lista", productosListados);
        response.sendRedirect("index.jsp");  
        
        /*response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter() ){

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Principal</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Principal at " + request.getContextPath() + "</h1>");
            out.println("<table>");
            out.println("<tr><td><b>Producto</b></td><td><b>Precio</b></td><td><b>Categoría</b></td><td><b>Imagen</b></td><tr>");
            for (Producto prod : productosListados) {
                out.println("<tr><td>" + prod.getNombre() + "</td><td>" + prod.getPrecio()+ "</td><td>" + prod.getCategoria()+ "</td><td><img style=\"width: 100px;\" src=\"" + prod.getImagen() + "\"/></td></tr>");
            }
             out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }*/
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

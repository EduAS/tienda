package tienda;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "ServletGestionarProductos", urlPatterns = {"/ServletGestionarProductos"})
@MultipartConfig
public class ServletGestionarProductos extends HttpServlet {

    @Resource(lookup = "jdbc/tienda_dvillaverdem")
    private DataSource ds;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {
        request.setCharacterEncoding("UTF-8");

        ProductoDAO prodDAO = new ProductoDAO(ds);
        String confirmacion = null;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        try {
            if (isMultipart) {

                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();

                // Configure a repository (to ensure a secure temp location is used)
                ServletContext servletContext = this.getServletConfig().getServletContext();
                File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(repository);

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);

                // Parse the request
                List<FileItem> items = upload.parseRequest(request);

                // Process the uploaded items
                Iterator<FileItem> iter = items.iterator();
                ArrayList<String> valor = new ArrayList();
                String nombreArchivo = null;

                while (iter.hasNext()) {
                    FileItem item = iter.next();

                    if (item.isFormField()) {
                        String value = item.getString("UTF-8");
                        valor.add(value);
                    } else {
                        File file = new File(item.getName());
                        String path = request.getServletContext().getRealPath("/recursos/imagenes/");
                        item.write(new File(path+"\\", file.getName()));
                        nombreArchivo="/recursos/imagenes/"+file.getName();
                    }
                }              
                                
                    String nombre = valor.get(0);
                    String precio = valor.get(1);
                    String categoria = valor.get(2);
                    String imagen = nombreArchivo;
                    confirmacion = prodDAO.agregarProducto(nombre, precio, categoria, imagen);

            } else {

                String nombreProd = request.getParameter("nombreProd");
                String modificar = request.getParameter("modificar");
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
            }

        } finally {
            prodDAO.close();
        }
        request.getSession().setAttribute("confirmacion", confirmacion);
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(ServletGestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletGestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(ServletGestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletGestionarProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
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

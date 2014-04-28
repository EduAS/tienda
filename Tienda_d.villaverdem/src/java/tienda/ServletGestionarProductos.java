package tienda;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

        //si el formulario enviado es multipart
        if (isMultipart) {
            try {
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
                // Creo un ArrayList en el que se guardarán los datos del formulario que no son fichero 
                ArrayList<String> valor = new ArrayList();
                String nombreArchivo = null;

                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    
                    if (item.isFormField()) {
                        String value = item.getString("UTF-8");
                        valor.add(value);
                    } else {
                        File file = new File(item.getName());
                        String path = request.getServletContext().getRealPath("");
                        //Si el path viene separado por una barra de tipo \ la cambio por /
                        if (path.contains("\\")) {
                            path = path.replaceAll("\\\\", "/");
                        }
                        //Me quedo con la ruta del proyecto, quitando la parte de build/web y accediendo a web/recursos/imagenes
                        path = path.replaceAll("build/web", "") + "web/recursos/imagenes";
                        //Guardamos la imagen en el path con su nombre original
                        item.write(new File(path + "/", file.getName()));
                        //Y guardamos en un String la ruta relativa con el nombre de la imagen para guardar en la BD
                        nombreArchivo = "recursos\\imagenes\\" + file.getName();
                    }
                }
                //Si se quiere cambiar la imagen
                if (valor.contains("modificarImagen")) {
                    String modificarNombre = "imagen";
                    String nombreProd = valor.get(0);
                    confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreArchivo);
                } else {
                    //Si se quiere agregar un producto a la BD
                    if (valor.contains("Agregar")) {
                        String nombre = valor.get(0);
                        String precio = valor.get(1);
                        String categoria = valor.get(2);
                        String imagen = nombreArchivo;
                        confirmacion = prodDAO.agregarProducto(nombre, precio, categoria, imagen);

                    }
                }

            } finally {
                prodDAO.close();
            }
        } else { //si es un formulario normal
            try {
                String nombreProd = request.getParameter("nombreProd");
                String modificar = request.getParameter("modificar");
                //Si se quiere modificar el nombre
                if ("modificarNombre".equals(modificar)) {
                    String modificarNombre = "nombre";
                    String nombreNuevo = request.getParameter("nombreNuevo");
                    confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                } else {
                    //Si se quiere modificar la categoria
                    if ("modificarCategoria".equals(modificar)) {
                        String modificarNombre = "categoria";
                        String nombreNuevo = request.getParameter("categoriaNueva");
                        confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                    } else {
                        //Si se quiere modificar el precio
                        if ("modificarPrecio".equals(modificar)) {
                            String modificarNombre = "precio";
                            String nombreNuevo = request.getParameter("precioNuevo");
                            confirmacion = prodDAO.modificarProducto(nombreProd, modificarNombre, nombreNuevo);
                        } else {
                            //Si se quiere eliminar el producto de la BD
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
        }
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
//

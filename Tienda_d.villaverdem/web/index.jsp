<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tienda.Producto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>En 'ca' Paqui</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
        <script src="funciones.js"></script>
    </head>
    <body>
        <div id="contenedor">
            <div id='cabecera'>
                En 'ca' Paqui
            </div>
            
            <div id='menuLogin'>
                <ul>
                    <li>
                        <a href="login.jsp">Identificarse</a>
                    </li>
                </ul>
            </div>
            
            
            <div id='menu'>
                <ul>
                    <li id='categorias'>Categorías
                        <ul>
                            <li>Alimentación</li>
                            <li>Droguería</li>
                            <li>Prensa</li>
                            <li>Ferretería</li>
                        </ul>                    
                    </li>

                    <li>
                        <form name="formPrecio" method='get' action='ServletProductos' onSubmit="return validarPrecio();">
                            <label>Min</label> 
                            <input type='text' size="5" name='precioMin' value="0" onkeypress="soloCaracterPrecioValido()" />
                            <label>Max</label> 
                            <input type='text' size="5" name='precioMax' value="0" onkeypress="soloCaracterPrecioValido()" />
                            <input type='hidden' name='busqueda' value='precio' />
                            <input type='submit' name='BuscarPrecio' value='Buscar'>
                        </form>                    
                    </li>

                    <li>
                        <form name="formNombre" method='get' action='ServletProductos'>
                            <label>Buscar por nombre</label> 
                            <input type='search' name='nombreProd' />
                            <input type='hidden' name='busqueda' value='nombre' />
                            <input type='submit' name='BuscarNombre' value='Buscar'>
                        </form>      

                    </li>                
                </ul>          
            </div>

            <div id='muestraProductos'>
              <c:choose>  
                <c:when test="<%= session.getAttribute("lista")!=null %>">
                  <%
                    List<Producto> productosListados = new ArrayList<Producto>();
                    productosListados=(ArrayList)session.getAttribute("lista");
                    
                    /*Se envía la lista buscada en una variable
                    para cuando se añada el producto, la página siga
                    mostrando la lista buscada. También para agregar
                    el producto
                    */
                    request.setAttribute("listaEnviar", productosListados);
                    RequestDispatcher rd = request.getRequestDispatcher("servletUrlPattern");
                    rd.forward(request, response);
                  %>


                <table>
                    <tr>
                        <td><b>Producto</b></td>
                        <td><b>Precio</b></td>
                        <td><b>Categoría</b></td>
                        <td><b>Imagen</b></td>
                        <td><b>Cantidad</b></td>
                    </tr>
                                       
                    
                    <c:forEach var="prod" items="<%=productosListados%>" varStatus="status">
                         <tr>
                            <td><c:out value="${prod.getNombre()}"/></td>
                            <td><c:out value="${prod.formateaPrecio(prod.getPrecio())}"/></td>
                            <td><c:out value="${prod.getCategoria()}"/></td>
                            <td><img src="<c:out value="${prod.getImagen()}"/>"/></td>
                            <td>
                                <form method="get" action="ServletAgregarAlCarro">
                                    <label>Cantidad</label>
                                    <input type="number" size="5" min="0" name="cantidad" value="0" onkeypress="validarCantidad()" />
                                    <input type='submit' name='comprarProducto' value='Añadir'>
                                </form>
                            </td>   
                         </tr>
                         
                         
                     </c:forEach>
                    
                </table>
                    
                
                <% session.setAttribute("lista", null); %>
                </c:when>
                <c:otherwise>
                    Busque los productos por categoría, nombre o precio en el menú superior                    
                </c:otherwise>   
              </c:choose>

            </div>
         </div>
    </body>
</html>

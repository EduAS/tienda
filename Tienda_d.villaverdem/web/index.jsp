<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tienda.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>En 'ca' Paqui</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
    </head>
    <body>
        <div id="contenedor">
            <div id='cabecera'>
                En 'ca' Paqui
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
                        <form method='get' action='ServletProductos'>
                            <label>Min</label> 
                            <input type='text' name='precioMin' />
                            <label>Max</label> 
                            <input type='text' name='precioMax' />
                            <input type='hidden' name='busqueda' value='precio' />
                            <input type='submit' name='BuscarPrecio' value='Buscar'>
                        </form>                    
                    </li>

                    <li>
                        <form method='get' action='ServletProductos'>
                            <label>Buscar por nombre</label> 
                            <input type='text' name='nombreProd' />
                            <input type='hidden' name='busqueda' value='nombre' />
                            <input type='submit' name='BuscarNombre' value='Buscar'>
                        </form>      

                    </li>                
                </ul>          
            </div>

            <div id='muestraProductos'>
            <% 
            if (session.getAttribute("lista")!=null){
              List<Producto> productosListados = new ArrayList<Producto>();
              productosListados=(ArrayList)session.getAttribute("lista");
            %>

            <table>
                <tr>
                    <td><b>Producto</b></td>
                    <td><b>Precio</b></td>
                    <td><b>Categoría</b></td>
                    <td><b>Imagen</b></td>
                </tr>
                <%
                for (Producto prod : productosListados) {%>
                <tr>
                    <td><%= prod.getNombre() %></td>
                    <td><%= prod.getPrecio() %></td>
                    <td><%= prod.getCategoria() %></td>
                    <td><img style="width: 100px;" src="<%= prod.getImagen()%>"/></td>
                </tr>
                <%
                }%>
            </table>
            <%
            session.setAttribute("lista", null); 
            }else {%> nulo <%}%>        

            </div>
         </div>
    </body>
</html>

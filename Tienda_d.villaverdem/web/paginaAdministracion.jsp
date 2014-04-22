<%@page import="tienda.Pedido"%>
<%@page import="tienda.Producto"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de Administración</title>
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
    </head>
    <body>
        <div id="contenedor">
            <div id="pedidos">
                <a href="ServletGestionarPedidos?gestionPedidos=ver">Ver pedidos pendientes de preparación</a>    
                <div id="divPedidosSinPreparar">
                    <c:if test="<%= request.getSession().getAttribute("pedidosSinPreparar") != null%>">
                        <%
                            ArrayList<Pedido> pedidosSinPreparar = new ArrayList<Pedido>();
                            pedidosSinPreparar = (ArrayList) request.getSession().getAttribute("pedidosSinPreparar");
                            request.getSession().setAttribute("pedidosPreparar", pedidosSinPreparar);
                        %>

                        <table>
                            <tr>
                                <td><b>Número</b></td>
                                <td><b>Usuario</b></td>
                                <td><b>Productos</b></td>
                                <td><b>¿Preparar?</b></td>
                            </tr>


                            <c:forEach var="pedido" items="<%=pedidosSinPreparar%>" varStatus="status">
                                <tr>
                                    <td><c:out value="${pedido.getNumero()}"/></td>
                                    <td><c:out value="${pedido.getUsuario()}"/></td>
                                    <td>
                                        <ul>
                                            <c:forEach var="prod" items="${pedido.getListaProductos()}">
                                                <li><c:out value="${prod.getNombre()} --> (${prod.getCantidad()} uds)"/></li>
                                                </c:forEach>
                                        </ul>
                                    </td>
                                    <td>
                                        <form method="post" action="ServletGestionarPedidos">
                                            <input type="hidden" name="gestionPedidos" value="preparar"/>
                                            <input type="hidden" name="posicion" value="${status.index}"/>
                                            <input type="submit" value="Preparar">
                                        </form>

                                    </td>
                                </tr>


                            </c:forEach>

                        </table>


                        <% request.getSession().setAttribute("pedidosSinPreparar", null);%>
                    </c:if>
                </div>
            </div>

            <div id="productos">
                <div id="agregarProducto">

                </div>

                <div id="modificarProductos">
                    <div id="menu">
                        <ul>
                            <li>Modificar o eliminar Producto</li>
                            <li>
                                <form name="formNombreAdmin" method='get' action='ServletProductos'>
                                    <label>Buscar por nombre</label> 
                                    <input type='search' name='nombreProd' />
                                    <input type='hidden' name='busqueda' value='nombre' />
                                    <input type='hidden' name='esAdmin' value='si' />
                                    <input type='submit' name='BuscarNombre' value='Buscar'>
                                </form>  
                            </li>
                        </ul>
                    </div>
                    <div id="muestraProductos">
                        <c:choose>  
                            <c:when test="<%= request.getSession().getAttribute("lista") != null%>">
                                <%
                                    ArrayList<Producto> productosListados = new ArrayList<Producto>();
                                    productosListados = (ArrayList) session.getAttribute("lista");
                                    request.getSession().setAttribute("listaBusqueda", productosListados);
                                %>


                                <table>
                                    <tr>
                                        <td><b>Producto</b></td>
                                        <td><b>Precio</b></td>
                                        <td><b>Categoría</b></td>
                                        <td><b>Imagen</b></td>
                                        <td><b>Modificar</b></td>
                                        <td><b>Eliminar</b></td>
                                    </tr>


                                    <c:forEach var="prod" items="<%=productosListados%>">
                                        <tr>
                                            <td><c:out value="${prod.getNombre()}"/></td>
                                            <td><c:out value="${prod.formateaPrecio(prod.getPrecio())}"/></td>
                                            <td><c:out value="${prod.getCategoria()}"/></td>
                                            <td><img src="<c:out value="${prod.getImagen()}"/>"/></td>
                                            <td>
                                                <form method="get" action="paginaAdministracion.jsp">
                                                    <input type="hidden" name="nombreProd" value="${prod.getNombre()}"/>
                                                    <input type="submit" name="modificar" value="Modificar">
                                                </form>
                                            </td>  
                                            <td></td>
                                        </tr>


                                    </c:forEach>

                                </table>
                                <% request.getSession().setAttribute("lista", null);%>
                            </c:when>
                            <c:otherwise>
                                Busca el producto a modificar o eliminar
                            </c:otherwise>   
                        </c:choose>
                    </div>

                    <c:if test="<%= request.getParameter("modificar") != null%>">
                        <div id="divModificar">
                            <table>
                                <tr>
                                    <td>Modificar <c:out value="${prod.getNombre()}"/></td>
                                </tr>
                                <tr>
                                    <td>
                                        <form method="post" action="ServletEliminarModificarProducto">
                                            <label>Modificar nombre</label><br/>
                                            <input type="text" name="nombre"/><br/>
                                            <input type="submit" name="modificarNombre" value="modificarNombre"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="ServletEliminarModificarProducto">
                                            <label>Modificar precio</label><br/>
                                            <input type="text" name="precio"/><br/>
                                            <input type="submit" name="modificarPrecio" value="modificarPrecio"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="ServletEliminarModificarProducto">
                                            <label>Modificar categoria</label><br/>
                                            <input type="radio" name="categoria" value="Alim"/>Alimentación<br/>
                                            <input type="radio" name="categoria" value="Ferret"/>Ferretería<br/>
                                            <input type="radio" name="categoria" value="Drog"/>Droguería<br/>
                                            <input type="radio" name="categoria" value="Pren"/>Prensa<br/>                                     
                                            <input type="submit" name="modificarCategoria" value="modificarCategoria"/>
                                        </form>
                                    </td>   
                                    <td>
                                        <form method="post" enctype="multipart/form-data" action="">
                                            <label>Modificar imagen</label><br/>
                                            <input type="file" name="imagen"/><br/>
                                            <input type="submit" name="modificarImagen" value="modificarImagen"/>
                                        </form>
                                    </td>
                                </tr>
                            </table>

                        </div>
                    </c:if>
                </div>

            </div>

        </div>

    </body>
</html>

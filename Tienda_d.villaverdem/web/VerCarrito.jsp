<%@page import="tienda.CantidadProducto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="tienda.Producto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de la compra</title>
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
        <script src="funciones.js"></script>

    </head>
    <body>
        <div id="contenedor">
            <div class="menuBasico">
                <a href="index.jsp">Volver</a>
            </div>

            <div id="muestraProductos">
                <% ArrayList<CantidadProducto> carro = (ArrayList) session.getAttribute("carro");%>
                <c:choose>  
                    <c:when test="<%= carro != null && carro.size() > 0%>">
                        <%
                            ArrayList<CantidadProducto> productosListados = carro;
                        %>


                        <table>
                            <tr>
                                <td><b>Producto</b></td>
                                <td><b>Cantidad</b></td>
                                <td><b>Quitar de la cesta</b></td>
                            </tr>

                            <c:set var="total" value="0"/>
                            <c:forEach var="prod" items="<%=productosListados%>" varStatus="status">
                               
                                <c:set var="total" value="${prod.calcularPrecio(total)}"/>
                                <tr>
                                    <td><c:out value="${prod.getNombre()}"/></td>
                                    <td><c:out value="${prod.getCantidad()}"/></td>
                                    <td>
                                        <form method="get" action="ServletEliminarProducto">
                                            <label>Cantidad</label>
                                            <input type="number" size="5" min="0" max="${prod.getCantidad()}" name="cantidad" value="0" onkeypress="validarCantidad()" />
                                            <input type="hidden" name="posicion" value="${status.index}"/>
                                            <input type='hidden' name='eliminar' value='uno' />
                                            <input type="submit" name="quitar" value="eliminar">
                                        </form>
                                    </td>   
                                </tr>


                            </c:forEach>
                            <tr>
                                <td><b>Vaciar toda la cesta</b></td>
                                <td></td>
                                <td><a href="ServletEliminarProducto?eliminar=todos" onClick="return confirmar('¿Está seguro de que desea vaciar la cesta?')">Vaciar toda la cesta</a></td>
                            </tr>

                        </table>

                        <div id="confirmarPedido">
                            <h2>Confirmación del pedido</h2>
                            <form name="pedir" method="get" action="ServletGuardarPedido" onSubmit="return validarUsuario('${total}')">
                                <label>Introduzca su nombre</label><br/>
                                <input type="text" name="nombreCliente"/>
                                <input type="submit" name="confirmarPedido" value="Pedir a Paqui"/>
                            </form>

                        </div>
                    </c:when>
                    <c:otherwise>
                        El carro está vacío
                    </c:otherwise>   
                </c:choose>
            </div>

        </div>
    </body>
</html>

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
        <title>En 'ca' Paqui</title>
        <meta http-equiv=”Content-Type” content=”text/html; charset=UTF-8″>
        <meta name="viewport" content="width=device-width">
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
        <script src="funciones.js"></script>
    </head>
    <body>
        <c:if test="${pageContext.session.new}">
            <c:set target="${pageContext.session}" property="maxInactiveInterval" value="1800" />
        </c:if>

        <c:if test="${confirmacion!=null}">
            <script>alert("${confirmacion}");</script>
            <c:set var="confirmacion" value="${null}" scope="session"/>  
        </c:if>




        <div id="contenedor">
            <div id='cabecera'>
                En 'ca' Paqui
            </div>
            <div class='menuBasico'>
                <c:choose>
                    <c:when test="${usuario!= null}">
                        <a class="boton" href="login.jsp">Administrar</a>
                        <c:set var="user" value="${usuario}" />
                        <a class="boton" href="ServletLogin?login=false">Cerrar sesión</a>
                        <label>Usuario: <c:out value="${user}"/></label>
                    </c:when>
                    <c:otherwise>
                        <a class="boton" href="login.jsp">Identificarse</a>
                    </c:otherwise>
                </c:choose>

            </div>


            <div id='menu'>
                <ul>
                    <li id='categorias'>Categorías
                        <ul>
                            <li><a href="ServletProductos?busqueda=categoria&cat=Alimentación">Alimentación</a></li>
                            <li><a href="ServletProductos?busqueda=categoria&cat=Droguería">Droguería</a></li>
                            <li><a href="ServletProductos?busqueda=categoria&cat=Prensa">Prensa</a></li>
                            <li><a href="ServletProductos?busqueda=categoria&cat=Ferretería">Ferretería</a></li>
                        </ul>                    
                    </li>

                    <li>
                        <form name="formPrecio" method='get' action='ServletProductos'>
                            <label>Desde</label> 
                            <input type='text' size="5" name='precioMin' value="0" onkeypress="soloCaracterPrecioValido()" />
                            <label>Hasta</label> 
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

            <div id="carritoCompra">
                <ul>
                    <li>
                        <img src="recursos/imagenes/carrito.jpg"/>
                    </li>

                    <li>
                        <c:choose>  
                            <c:when test="${carro!= null}">
                                <c:set var="carro" value="${carro}"/>
                                <c:set var="total" value="${0}"/>
                                <c:forEach var="prod" items="${carro}">
                                    <c:set var="total" value="${total+prod.cantidad}"/>
                                </c:forEach>
                                (<c:out value="${total}"/>)
                            </c:when>
                            <c:otherwise>
                                (0)
                            </c:otherwise>
                        </c:choose>
                    </li>

                    <li>
                        <a href="VerCarrito.jsp">Ver carrito</a>
                    </li>
                </ul>

            </div>

            <div id='muestraProductos'>
                <c:choose>  
                    <c:when test="${lista!= null}">
                        <c:set var="productosListados" value="${lista}"/>
                        <c:set var="listaBusqueda" value="${productosListados}" scope="session"/>

                        <table>
                            <tr>
                                <td><b>Producto</b></td>
                                <td><b>Precio</b></td>
                                <td><b>Categoría</b></td>
                                <td><b>Imagen</b></td>
                                <td><b>Cantidad</b></td>
                            </tr>


                            <c:forEach var="prod" items="${productosListados}" varStatus="status">
                                <tr>
                                    <td><c:out value="${prod.getNombre()}"/></td>
                                    <td><c:out value="${prod.formateaPrecio(prod.getPrecio())}"/></td>
                                    <td><c:out value="${prod.getCategoria()}"/></td>
                                    <td><img src="<c:out value="${prod.getImagen()}"/>"/></td>
                                    <td>
                                        <form method="get" action="ServletAgregarAlCarro">
                                            <label>Cantidad</label>
                                            <input type="number" size="5" min="0" name="cantidad" value="0" onkeypress="validarCantidad()" />
                                            <input type="hidden" name="posicion" value="${status.index}"/>
                                            <input type="submit" name="comprarProducto" value="Añadir">
                                        </form>
                                    </td>   
                                </tr>


                            </c:forEach>

                        </table>

                        <c:set var="lista" value="${null}" scope="session"/>
                    </c:when>
                    <c:otherwise>
                        Busque los productos por categoría, nombre o precio en el menú superior. Si dejas el campo "nombre" en blanco saldrá toda la lista de productos                   
                    </c:otherwise>   
                </c:choose>

            </div>
        </div>
    </body>
</html>

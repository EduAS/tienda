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
        <meta http-equiv=”Content-Type” content=”text/html; charset=UTF-8″>
        <title>Página de Administración</title>
        <link rel="stylesheet" type="text/css" href="estilo.css"/>
        <script src="funciones.js"></script>

    </head>
    <body>
        <c:if  test="<%= request.getSession().getAttribute("usuario") == null%>">  

            <%
                String error = "Debe loguearse como administrador para ver esta página";
                request.getSession().setAttribute("error", error);
                response.sendRedirect("login.jsp");
            %>
        </c:if>
        <div id="contenedor">
            <div id="pedidos">
                <div class="menuBasico">
                    <a class="boton" href="ServletGestionarPedidos?gestionPedidos=ver">Ver pedidos pendientes de preparación</a> 
                    <a class="boton" href="index.jsp">Volver</a>   
                    <a class="boton" href="ServletLogin?login=false">Cerrar sesión</a>
                </div>
                <div id="divPedidosSinPreparar">
                    <c:if test="<%= request.getSession().getAttribute("pedidosSinPreparar") != null%>">
                        <%
                            ArrayList<Pedido> pedidosSinPreparar = new ArrayList<Pedido>();
                            pedidosSinPreparar = (ArrayList) request.getSession().getAttribute("pedidosSinPreparar");
                            request.getSession().setAttribute("pedidosPreparar", pedidosSinPreparar);
                        %>

                        <table class="tabla">
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
                    <div class="menuBasico"><a class="boton" href="paginaAdministracion.jsp?agregar=true">Añadir nuevo producto</a></div>
                    <c:if test="<%= request.getParameter("agregar") != null%>">
                        <table class='tabla '>
                            <tr><td colspan="4"><h4>Añadir nuevo Producto</h4><td></tr>
                            <tr>
                            <tr>
                                <td>Nombre del Producto</td>
                                <td>Precio</td>
                                <td>Categoria</td>
                                <td>Imagen</td>
                                <td></td>
                            </tr>
                            <tr>
                            <form name="formNombreAdmin" method='post' action='ServletGestionarProductos' onsubmit="return validarProducto();">
                                <td><input type='text' name='nombreProd'/></td>
                                <td><input type='text' name='precio' onkeypress="soloCaracterPrecioValido()"/></td>
                                <td>
                                    <input type="radio" name="categoria" value="Alimentación"/>Alimentación<br/>
                                    <input type="radio" name="categoria" value="Ferretería"/>Ferretería<br/>
                                    <input type="radio" name="categoria" value="Droguería"/>Droguería<br/>
                                    <input type="radio" name="categoria" value="Prensa"/>Prensa<br/>   
                                </td>
                                <td><input type='text' name='imagen' /></td>
                                <td><input type='submit' name='modificar' value='Agregar'></td>
                            </form>  
                            </tr>
                        </table>
                    </c:if>
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
                                            <td>
                                                <form method="get" action="ServletGestionarProductos" onSubmit="return confirmar('¿Está seguro de que desea eliminar este producto?')">
                                                    <input type="hidden" name="nombreProd" value="${prod.getNombre()}"/>
                                                    <input type="submit" name="modificar" value="Eliminar">
                                                </form>
                                            </td>
                                        </tr>


                                    </c:forEach>

                                </table>
                                <% request.getSession().setAttribute("lista", null);%>
                            </c:when>
                            <c:otherwise>
                                Busca el producto a modificar o eliminar. Si dejas el campo en blanco saldrá toda la lista de productos
                            </c:otherwise>   
                        </c:choose>
                    </div>

                    <c:if test="<%= request.getParameter("modificar") != null%>">
                        <%String nombreProd = request.getParameter("nombreProd");%>
                        <div id="divModificar">
                            <table class="tabla">
                                <tr>
                                    <td colspan="4"><h4>Modificar <%=nombreProd%></h4></td>
                                </tr>
                                <tr>
                                    <td>
                                        <form method="post" action="ServletGestionarProductos">
                                            <label>Modificar nombre</label><br/>
                                            <input type="text" name="nombreNuevo"/><br/>
                                            <input type="hidden" name="nombreProd" value="<%=nombreProd%>"/>
                                            <input type="submit" name="modificar" value="modificarNombre"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="ServletGestionarProductos" onSubmit="return validarPrecio();">
                                            <label>Modificar precio</label><br/>
                                            <input type="text" name="precioNuevo" onkeypress="soloCaracterPrecioValido()"/><br/>
                                            <input type="hidden" name="nombreProd" value="<%=nombreProd%>"/>
                                            <input type="submit" name="modificar" value="modificarPrecio"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="post" action="ServletGestionarProductos">
                                            <label>Modificar categoria</label><br/>
                                            <input type="radio" name="categoriaNueva" value="Alimentación"/>Alimentación<br/>
                                            <input type="radio" name="categoriaNueva" value="Ferretería"/>Ferretería<br/>
                                            <input type="radio" name="categoriaNueva" value="Droguería"/>Droguería<br/>
                                            <input type="radio" name="categoriaNueva" value="Prensa"/>Prensa<br/>   
                                            <input type="hidden" name="nombreProd" value="<%=nombreProd%>"/>
                                            <input type="submit" name="modificar" value="modificarCategoria"/>
                                        </form>
                                    </td>   
                                    <td>
                                        <form method="post" enctype="multipart/form-data" action="">
                                            <label>Modificar imagen</label><br/>
                                            <input type="file" name="imagenNueva"/><br/>
                                            <input type="hidden" name="nombreProd" value="<%=nombreProd%>"/>
                                            <input type="submit" name="modificar" value="modificarImagen"/>
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

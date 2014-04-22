<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>.:Login:.</title>
        <script src="funciones.js"></script>
    </head>
    <body>

        <c:if  test="<%= request.getSession().getAttribute("usuario") != null%>">   
            <% response.sendRedirect("paginaAdministracion.jsp");%>
        </c:if>

        <c:if test="<%= request.getSession().getAttribute("error") != null%>">   
            <script>alert('<%= session.getAttribute("error")%>');</script>
            <% session.setAttribute("error", null);%>
        </c:if>

        <form name="formLog"  method="POST" action="ServletLogin?login=true" onSubmit="return validar_camposLogin()">
            <table align="center" width="200px">
                <tr>
                    <td colspan="2" align="center"><h3>Iniciar sesi&oacute;n</h3></td>
                </tr>

                <tr>
                    <td>Usuario</td>
                    <td>
                        <input type="text" name="usuario" maxlength="20">
                    </td>
                </tr>

                <tr>
                    <td>Password</td>
                    <td>
                        <input type="password" name="password" maxlength="15">
                    </td>
                </tr>

                <tr>
                    <td colspan="2" align="right">
                        <input type='submit' value='Entrar'>
                    </td>

                </tr>
                <tr><td><a class="boton" href="index.jsp">Volver</a> </td></tr>
            </table>
        </form>

    </body>
</html>

<%-- 
    Document   : index
    Created on : 09-abr-2014, 13:53:33
    Author     : Usuario
--%>

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

            <div id='listaProductos'>

            </div>
         </div>
    </body>
</html>

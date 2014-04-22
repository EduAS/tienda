


function soloCaracterPrecioValido() {
    if ((event.keyCode < 46) || (event.keyCode > 57) || (event.keyCode == 47))
        event.returnValue = false;
}

function validarCantidad() {
    if ((event.keyCode < 48) || (event.keyCode > 57))
        event.returnValue = false;
}


function validar_camposLogin() {

    if (document.formLog.usuario.value == "") {
        alert("Por favor, introduzca un nombre de usuario");
        document.formLog.usuario.focus();
        return false;
    }

    if (document.formLog.password.value == "") {
        alert("Debe indicarnos la contraseña");
        document.formLog.password.focus();
        return false;
    }

    document.formLog.submit();
}

function validarUsuario(precio) {
    if (document.pedir.nombreCliente.value == "") {
        alert("Por favor, introduzca su nombre");
        document.pedir.nombreCliente.focus();
        return false;
    }
    precio = Math.round(precio * 100) / 100;
    if (confirm("El importe total es de " + precio + " euros, ¿desea continuar?")) {
        document.formLog.submit();
    }
    else {
        return false;
    }

}

function validarProducto() {
    if (document.formNombreAdmin.nombreProd.value == "") {
        alert("Por favor, introduzca el nombre");
        document.formNombreAdmin.nombreProd.focus();
        return false;
    }
    if (document.formNombreAdmin.precio.value == "") {
        alert("Por favor, introduzca el precio");
        document.formNombreAdmin.precio.focus();
        return false;
    }
    if (document.formNombreAdmin.categoria.value == "") {
        alert("Por favor, seleccione una categoría");
        document.formNombreAdmin.categoria.focus();
        return false;
    }
    if (document.formNombreAdmin.imagen.value == "") {
        alert("Por favor, introduzca una imagen");
        document.formNombreAdmin.imagen.focus();
        return false;
    }
    document.formNombreAdmin.submit();

}

function loginIncorrecto(error) {
    alert(error);
}

function confirmar(msg)
{
    if (confirm(msg))
        return true;
    else
        return false;
}






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
    if (document.formAgregar.nombreProd.value == "") {
        alert("Por favor, introduzca el nombre");
        document.formAgregar.nombreProd.focus();
        return false;
    }
    if (document.formAgregar.precio.value == "") {
        alert("Por favor, introduzca el precio");
        document.formAgregar.precio.focus();
        return false;
    }
    if (document.formAgregar.categoria.value == "") {
        alert("Por favor, seleccione una categoría");
        return false;
    }
    if (document.formAgregar.imagen.value == "") {
        alert("Por favor, introduzca una imagen");
        document.formAgregar.imagen.focus();
        return false;
    }
    document.formAgregar.submit();

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



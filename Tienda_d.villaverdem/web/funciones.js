
function tieneComa(texto){
   coma=",";
   texto = texto.toLowerCase();
   for(i=0; i<texto.length; i++){
      if (coma.indexOf(texto.charAt(i),0)!==-1){
         return 1;
      }
   }
   return 0;
}


function soloCaracterPrecioValido() {
 if ((event.keyCode < 46) || (event.keyCode > 57) || (event.keyCode == 47)) 
  event.returnValue = false;
}

function validarCantidad() {
 if ((event.keyCode < 48) || (event.keyCode > 57)) 
  event.returnValue = false;
}

function validarPrecio(){
    if(tieneComa(document.formPrecio.precioMin.value) || tieneComa(document.formPrecio.precioMax.value)){
           alert("El precio con céntimos debe separarse con un punto (.) no con  una coma (,)");
           return false;
    }
    else{
    document.formPrecio.submit();
    }
}


function validar_camposLogin(){

    if(document.formLog.usuario.value==""){
        alert("Por favor, introduzca un nombre de usuario");
        document.formLog.usuario.focus();
        return false;
    }

    if(document.formLog.password.value==""){
        alert("Debe indicarnos la contraseña");
        document.formLog.password.focus();
        return false;
    }

    document.formLog.submit();
}

function loginIncorrecto(error){
    alert(error);
}
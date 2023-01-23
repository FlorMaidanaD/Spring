package com.egg.biblioteca.exceptiones;

public class MiException extends Exception {
//hago esto para diferenciar los errores de la logica con errores del sistema
    public MiException(String mensaje) {
        super(mensaje);
    }   

}

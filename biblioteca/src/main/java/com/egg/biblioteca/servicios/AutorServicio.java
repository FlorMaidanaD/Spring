package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.repositorio.AutorRepositorio;

//import jakarta.transaction.Transactional;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor (String nombre) throws MiException{
       validarNombre(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }
    public List <Autor> listarAutores(){
        List <Autor> autores = new ArrayList<>();
        autores = autorRepositorio.findAll();
        return autores;
    } 

    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }

    public void modificarAutor(String nombre, String id) throws MiException{
        validarTodo(nombre, id);
        Optional <Autor> respuesta= autorRepositorio.findById(id);
        if (respuesta.isPresent()){
            Autor autor= respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
    }



    private void validarTodo(String nombre, String id) throws MiException{
        if(id.isEmpty()||id==null){
            throw new MiException ("El ID no puede ser nulo o estar vacio");
        }
        if (nombre.isEmpty()||nombre==null){
            throw new MiException ("El Nombre no puede ser nulo o estar vacio");
        }
    }
    private void validarNombre(String nombre) throws MiException{
        if(nombre.isEmpty()||nombre==null){
            throw new MiException ("El ID no puede ser nulo o estar vacio");
        }       
    }
    
}

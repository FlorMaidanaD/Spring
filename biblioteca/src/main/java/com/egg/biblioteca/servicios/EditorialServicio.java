package com.egg.biblioteca.servicios;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.repositorio.EditorialRepositorio;

//import jakarta.transaction.Transactional;

@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre)throws MiException{
        validarNombre(nombre);
        Editorial editorial =new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);

    }

    public List <Editorial> listarEditoriales(){
        List <Editorial> editoriales = new ArrayList<>();
        editoriales = editorialRepositorio.findAll();
        return editoriales;

    }
   
    public void modificarEditorial(String nombre, String id)throws MiException{
        validarTodo(nombre, id);
        Optional <Editorial> respuesta= editorialRepositorio.findById(id);
        if (respuesta.isPresent()){
            Editorial editorial= respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
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
        if (nombre.isEmpty()||nombre==null){
            throw new MiException ("El Nombre no puede ser nulo o estar vacio");
        }
    }
   
}

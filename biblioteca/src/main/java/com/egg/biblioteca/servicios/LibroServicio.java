package com.egg.biblioteca.servicios;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.exceptiones.MiException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import com.egg.biblioteca.repositorio.LibroRepositorio;

//mport jakarta.transaction.Transactional;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;


    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiException{
        validarTodo(isbn, titulo, idAutor, idEditorial, ejemplares);
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial =editorialRepositorio.findById(idEditorial).get();

        
        Libro libro =new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save (libro);


    }

     public List <Libro> listarLibros(){
        List <Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;
    }

    //tengo que validar si el id es correcto o si existe, por eso con el optional me fijo si la respuesta es verdadera
    public void modificarLibro (Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)throws MiException{
        validarTodo(isbn, titulo, idAutor, idEditorial, ejemplares);
        Optional <Libro> respuesta = libroRepositorio.findById(isbn);
        Optional <Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional <Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Autor autor=new Autor();
        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()){
            autor=respuestaAutor.get();            

        }
        if (respuestaEditorial.isPresent()){
            editorial=respuestaEditorial.get();
        }
        if (respuesta.isPresent()){
            Libro libro=respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libroRepositorio.save(libro);
        }
    }

    private void validarTodo(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        if(isbn==null){
            throw new MiException ("El ISBN no puede ser nulo o estar vacio");
        }
        if (titulo.isEmpty()||titulo==null){
            throw new MiException ("El titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares==null){
            throw new MiException ("Ejemplares no puede ser nulo o estar vacio");
        }
        if (idAutor==null||idAutor.isEmpty()){
            throw new MiException ("El IDAutor no puede ser nulo o estar vacio");
        }
        if (idEditorial==null||idEditorial.isEmpty()){
            throw new MiException ("El IDEditorial no puede ser nulo o estar vacio");
        }
    }    
}

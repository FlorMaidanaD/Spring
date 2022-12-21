package com.egg.biblioteca.controladores;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

@Autowired
private EditorialServicio editorialServicio;

    @GetMapping ("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    
    @PostMapping ("/registro")
    //tiene que tener el mismo nombre la variables nombre
    public String registro(@RequestParam String nombre, ModelMap modelo){
        System.out.println("Nombre: "+ nombre);
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La Editorial fue registrada correctamente");

        } catch (Exception e) {
                modelo.put("error", e.getMessage());
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, e);
            return "editorial_form.html";
        }       
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar (ModelMap modelo){
        List <Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
}

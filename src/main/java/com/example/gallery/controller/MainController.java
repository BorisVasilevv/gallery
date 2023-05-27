package com.example.gallery.controller;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.exceptions.BadRequestException;
import com.example.gallery.model.ImageDataSet;
import com.example.gallery.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("image")
public class MainController {



    @Autowired
    MainService service;

    @GetMapping
    public List<ImageDataSet> AllImages(){
        return service.getAllImages();
    }

    @GetMapping("{id}")
    public ImageDataSet oneImage(@PathVariable String id){
        ImageDAO dao=new ImageDAO();
        return dao.get(Integer.parseInt(id));
    }

    @PostMapping
    public ImageDataSet create(@RequestBody ImageDataSet set){
        if(service.isExtensionSuitable(set.getExtension())){
            ImageDAO dao=new ImageDAO();
            dao.save(set);
            service.getAllImages().add(set);
            return set;
        }
        throw new BadRequestException();
    }


    @PutMapping("{id}")
    public ImageDataSet update(@PathVariable String id, @RequestBody ImageDataSet image){

        return image;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){

        service.remove(Integer.parseInt(id));
        ImageDAO dao=new ImageDAO();
        dao.delete(Integer.parseInt(id));

    }

}

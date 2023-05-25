package com.example.gallery.controller;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.exceptions.NotFoundException;
import com.example.gallery.model.StringImageData;
import com.example.gallery.model.ImageDataSet;
import com.example.gallery.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("image")
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    MainService service;

    @GetMapping
    public List<StringImageData> AllImages(){
        return service.getAllImages();
    }

    @GetMapping("{id}")
    public StringImageData oneImage(@PathVariable String id){
        return findImageById(id);
    }

    @PostMapping
    public StringImageData create(@RequestBody StringImageData image){
        //service.generateIdAndAdd(image);
        return image;
    }

    @PutMapping("{id}")
    public StringImageData update(@PathVariable String id, @RequestBody StringImageData image){
        //StringImageData imDS=findImageById(id);
        //imDS.setDate(image.getDate());
        //imDS.setSize(image.getSize());
        //imDS.setFilename(image.getFilename());
        return image;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        ImageDAO dao=new ImageDAO();
        dao.delete(Integer.parseInt(id));
        StringImageData imDS=findImageById(id);
        service.getAllImages().remove(imDS);
    }




    private StringImageData findImageById(Integer id){
        return service.getAllImages().stream()
                .filter(image->image.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    private StringImageData findImageById(String id){
        return findImageById(Integer.parseInt(id));
    }
}

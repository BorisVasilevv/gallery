package com.example.gallery.controller;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.model.ImageDataSet;
import com.example.gallery.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
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
        ImageDAO dao=new ImageDAO();
        dao.save(set);
        service.getAllImages().add(set);
        return set;
    }


   /* @PostMapping
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        } else if (!service.isFileImage(file)) {
            return ResponseEntity.badRequest().body("File not image");
        }
        try {
            ImageDataSet set = new ImageDataSet(file.getSize(), new Date(), Base64.getEncoder().encodeToString(file.getBytes()));
            ImageDAO dao = new ImageDAO();
            dao.save(set);
            return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }*/

    @PutMapping("{id}")
    public ImageDataSet update(@PathVariable String id, @RequestBody ImageDataSet image){
        //StringImageData imDS=findImageById(id);
        //imDS.setDate(image.getDate());
        //imDS.setSize(image.getSize());
        //imDS.setFilename(image.getFilename());
        return image;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){

        //StringImageData imDS=findImageById(id);
        //service.getAllImages().remove(imDS);
        ImageDAO dao=new ImageDAO();
        dao.delete(Integer.parseInt(id));
    }




    /*private StringImageData findImageById(Integer id){
        return service.getAllImages().stream()
                .filter(image->image.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }*/

    /*private StringImageData findImageById(String id){
        return findImageById(Integer.parseInt(id));
    }*/
}

package com.example.gallery.controller;


import com.example.gallery.dao.ImageDAO;
import com.example.gallery.exceptions.NotFoundException;
import com.example.gallery.model.StringImageData;
import com.example.gallery.model.ImageDataSet;
import com.example.gallery.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
@RequestMapping("picture")
public class ImageController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    MainService service;

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        ImageDAO dao= new ImageDAO();
        ImageDataSet set= dao.get(Integer.parseInt(id));
        HttpHeaders headers = new HttpHeaders();
        if(set==null){
           throw new NotFoundException();
        }
        else {
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(set.getImage(), headers, HttpStatus.OK);
        }
    }


    //public ResponseEntity<byte[]> getSmallImage(@PathVariable String id) {
        //StringImageData imageData =findImageById(id);

        //byte[] imageBytes=new byte[0];
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.IMAGE_PNG);


        //try {

            // загружаем изображение

            //BufferedImage originalImage = ImageIO.read(new File(uploadPath+ '/'+ imageData.getFilename()));
            // создаем миниатюру изображения
            //BufferedImage thumbnailImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            //Graphics2D g2 = thumbnailImage.createGraphics();
            //g2.drawImage(originalImage, 0, 0, 200, 200, null);
            //g2.dispose();

            //ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //через это реализовывать вместе с бд
            // Create a buffered image with transparency
            //BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            //ImageIO.write(thumbnailImage, "png", baos);
            //imageBytes = baos.toByteArray();

        //} catch (IOException e) {
          //  e.printStackTrace();
        //}

        //return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    //}



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

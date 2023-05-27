package com.example.gallery.service;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.model.ImageDataSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class MainService {

    @Value("#{'${suitable.extensions}'.split(',')}")
    private List<String> imageExtensions;

    private List<ImageDataSet> allImages;

    public List<ImageDataSet> getAllImages(){
        if(allImages==null){
            ImageDAO imageDAO=new ImageDAO();
            allImages=imageDAO.getAll();
        }
        return allImages;
    }



    public BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufImage;
    }

    public boolean isFileImage(MultipartFile file){

        String[] arrayName=file.getOriginalFilename().split("\\.");
        if(arrayName.length<2) return false;
        else return imageExtensions.contains(arrayName[arrayName.length-1]);
    }

}

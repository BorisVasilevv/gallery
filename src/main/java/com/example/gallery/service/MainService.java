package com.example.gallery.service;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.model.ImageDataSet;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Service
public class MainService {


    public List<ImageDataSet> getAllImages(){
        ImageDAO imageDAO=new ImageDAO();
        return imageDAO.getAll();
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

}

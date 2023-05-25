package com.example.gallery.service;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.model.ImageDataSet;
import com.example.gallery.model.StringImageData;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

@Service
public class MainService {

    static HashMap<Integer, Image> imageCodeAndImage=new HashMap<>();



    public ArrayList<StringImageData> getAllImages(){
        ArrayList<StringImageData> list=new ArrayList<>();
        ImageDAO imageDAO=new ImageDAO();
        List<ImageDataSet> allImages=imageDAO.getAll();
        if(allImages==null) return list;
        for(ImageDataSet set:allImages){
            list.add(new StringImageData(set.getId(),set.getSize(),set.getDate()));
        }
        return list;
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

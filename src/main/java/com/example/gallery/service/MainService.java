package com.example.gallery.service;

import com.example.gallery.model.ImageDataSet;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class MainService {

    static HashMap<Integer, Image> imageCodeAndImage=new HashMap<>();

    public static HashMap<Integer, Image> getHashMap() {
        return imageCodeAndImage;
    }

    ArrayList<ImageDataSet> list = new ArrayList<>(){{
        add(new ImageDataSet(1,100, "test1.png",new Date(13424)));
        add(new ImageDataSet(2,100, "test2.png",new Date(1773424)));
        add(new ImageDataSet(3,100, "test3.png",new Date(1342488)));
    }};

    public ArrayList<ImageDataSet> getAllImages(){
        return list;
    }

    public void generateIdAndAdd(ImageDataSet imageDataSet){
        imageDataSet.setId(list.size()+1);
        list.add(imageDataSet);
    }

}

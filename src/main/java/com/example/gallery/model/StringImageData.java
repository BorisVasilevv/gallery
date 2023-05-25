package com.example.gallery.model;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class StringImageData {

    Integer id;
    Integer size;
    //String filename;
    Date date;


    public StringImageData(Integer id, Integer size, Date date) {
        this.id = id;
        this.size = size;

        this.date = date;
    }
    public Integer getId() {
        return id;
    }

    public StringImageData() {
    }

    public Integer getSize() {
        return size;
    }


    public Date getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public static byte[] imageToBytes(Image image, String format) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputStream.toByteArray();
    }

    public static Image bytesToImage(byte[] data){
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            return ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

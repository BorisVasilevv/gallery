package com.example.gallery.model;




import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Entity
@Table(name = "images")
public class ImageDataSet {

    @Id
    @Column(name = "id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "size", unique = false,nullable = false)
    private Integer size;

    @Column(name = "date_and_time",unique = false,nullable = false )
    private Date date;

    @Column(name = "image", unique = false, nullable = false)
    private byte[] image;

    public ImageDataSet() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ImageDataSet(Integer size, Date date, byte[] image){
        this.size=size;
        this.date=date;
        this.image=image;
    }


    public Integer getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public byte[] getImage() {
        return image;
    }
}


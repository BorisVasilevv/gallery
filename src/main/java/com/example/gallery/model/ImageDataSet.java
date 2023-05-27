package com.example.gallery.model;




import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "gallery")
public class ImageDataSet {

    @Id
    @Column(name = "id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "size", unique = false,nullable = false)
    private long size;

    @Column(name = "date_and_time",unique = false,nullable = false )
    private Date date;

    @Column(name = "base64image", unique = false, nullable = false)
    private String base64image;

    @Column(name="extension", nullable = false)
    private String extension;



    public ImageDataSet() {
    }


    public String getExtension() {
        return extension;
    }

    public void setExtension(String extensions) {
        this.extension = extensions;
    }

    public Integer getId() {
        return id;
    }

    public long getSize() {
        return size;
    }

    public Date getDate() {
        return date;
    }

    public String getBase64image() {
        return base64image;
    }
}


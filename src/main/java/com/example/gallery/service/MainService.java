package com.example.gallery.service;

import com.example.gallery.dao.ImageDAO;
import com.example.gallery.model.ImageDataSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


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


    public void remove(Integer id){
        ImageDataSet set=null;
        for (ImageDataSet someSet:allImages){
            if(someSet.getId().equals(id)){
                set=someSet;
                break;
            }
        }
        if(set!=null){
            allImages.remove(set);
        }
    }

    public boolean isExtensionSuitable(MultipartFile file){

        String[] arrayName=file.getOriginalFilename().split("\\.");
        if(arrayName.length<2) return false;
        else return imageExtensions.contains(arrayName[arrayName.length-1]);
    }

    public boolean isExtensionSuitable(String extension){
        return imageExtensions.contains(extension);
    }

}

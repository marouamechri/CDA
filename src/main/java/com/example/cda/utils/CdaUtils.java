package com.example.cda.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CdaUtils {
    private CdaUtils() {

    }

    public static Boolean isFileExist(String path){
        try {
            File file=new File(path);
            return (file!= null && file.exists())? Boolean.TRUE:Boolean.FALSE;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}

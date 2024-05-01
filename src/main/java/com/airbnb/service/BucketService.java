package com.airbnb.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {
    private AmazonS3 amazonS3;

    public BucketService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    public String uploadFile(MultipartFile file,String bucketName){
        if(file.isEmpty()){
            throw new IllegalStateException("cannot upload empty file");
        }
        try{
            File convFile=new File(System.getProperty("java.io.tmpdir")+"/"+ file.getOriginalFilename());
            file.transferTo(convFile);
            try{
                amazonS3.putObject(bucketName,convFile.getName(),convFile);
                return amazonS3.getUrl(bucketName,file.getOriginalFilename()).toString();
            }catch (AmazonS3Exception s3Exception) {
                return "unable to upload file :" +s3Exception.getMessage();
            }
        }catch (Exception e){
            throw new IllegalStateException("failed to upload file",e);
        }
    }

//    public void deletefile(String bucketName, String filename) {
//        amazonS3.deleteObject(bucketName,filename);
//        return ;
//    }
}
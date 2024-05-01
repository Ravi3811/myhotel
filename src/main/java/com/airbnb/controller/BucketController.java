package com.airbnb.controller;

import com.airbnb.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
@RequestMapping("/s3Bucket")
@CrossOrigin("*")
public class BucketController {
    private BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }
   // @PostMapping(path = "/upload/file/{bucketName}",consumes = PageAttributes.MediaType.MULTIPART_FORM_DATA_VALUE,produces = PageAttributes.MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/upload/file/{bucketName}")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file,
                                             @PathVariable String bucketName){
        return new ResponseEntity<>(bucketService.uploadFile(file, bucketName), HttpStatus.OK);
    }
//    @DeleteMapping("/delete/{bucketName}/{filename}")
//    public ResponseEntity<?> deleteFile(@PathVariable String bucketName,
//                                        @PathVariable String filename){
//        bucketService.deletefile(bucketName,filename);
//    }
}

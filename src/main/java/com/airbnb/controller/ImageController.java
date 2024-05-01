package com.airbnb.controller;

import com.airbnb.entity.Images;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.ImagesRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private ImagesRepository imagesRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    public ImageController(ImagesRepository imagesRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }

    @PostMapping("/file/{bucketName}/property/{propertyId}")
    public ResponseEntity<?>  addImages(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable Long propertyId,
                                        @AuthenticationPrincipal PropertyUser user){
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Property property=propertyRepository.findById(propertyId).get();
        Images images=new Images();
        images.setImageUrl(imageUrl);
        images.setProperty(property);
        images.setPropertyUser(user);
        Images saved = imagesRepository.save(images);
        return new ResponseEntity<>(saved, HttpStatus.OK);

    }
}

package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import com.airbnb.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId, @RequestBody Review review,
                                            @AuthenticationPrincipal PropertyUser user){
        Review saved = reviewService.addReview(review, propertyId, user);
        if(saved!=null) {
            return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("You have already added a reviews for this property",HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/getReview")
    public ResponseEntity<List<Review>> getAllReviewByUser(@AuthenticationPrincipal PropertyUser user){
        List<Review> reviews = reviewService.getAllReviewsByUser(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getALlReviewById(@PathVariable Long id){
        Review review = reviewService.getAllReviewById(id);
        if(review!=null){
            return new ResponseEntity<>(review,HttpStatus.OK);
        }
        return new ResponseEntity<>("Review for this id is not present",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/allReview")
    public ResponseEntity<List<Review>> getALlReview(){
        List<Review> review = reviewService.getAllReview();
        return new ResponseEntity<>(review,HttpStatus.OK);
    }
    @PutMapping("/{propertyId}")
    public ResponseEntity<?> updateReview(@PathVariable Long propertyId,@AuthenticationPrincipal PropertyUser user,
                                         @RequestBody Review review ){
        Review saved = reviewService.updateReview(propertyId, user, review);
        if(saved!=null){
            return new ResponseEntity<>(saved,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid property or review is not present",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{propertyId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long propertyId,@AuthenticationPrincipal PropertyUser user){
        String s = reviewService.deleteReview(propertyId, user);
        if(s!=null){
            return new ResponseEntity<>("deleted!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Review or property is not present",HttpStatus.NOT_FOUND);
    }
}

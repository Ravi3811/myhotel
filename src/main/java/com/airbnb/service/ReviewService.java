package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    public Review addReview(Review review, long propertyId, PropertyUser user) {
        Property property = propertyRepository.findById(propertyId).get();
        Review listReview = reviewRepository.findReviewByUserIdAndPropertyId(property,user);
        if(listReview!=null){
            return null;
        }
        review.setProperty(property);
        review.setPropertyUser(user);
        Review saved = reviewRepository.save(review);
        return saved;
    }

    public List<Review> getAllReviewsByUser(PropertyUser user) {
        List<Review> review = reviewRepository.findByPropertyUser(user);
        return review;
    }

    public Review getAllReviewById(Long id) {
        Optional<Review> byId = reviewRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }

    public List<Review> getAllReview() {
        List<Review> all = reviewRepository.findAll();
        return all;
    }

    public Review updateReview(Long propertyId, PropertyUser user, Review review) {
        Optional<Property> byId = propertyRepository.findById(propertyId);
        if(byId.isPresent()){
            Property property = byId.get();
            Review saved = reviewRepository.findReviewByUserIdAndPropertyId(property, user);
            if(saved!=null){
            saved.setContent(review.getContent());
                Review save = reviewRepository.save(saved);
                return save;
            }
        }
        return null;
    }

    public String deleteReview(Long id, PropertyUser user) {
        Optional<Property> byId = propertyRepository.findById(id);
        if(byId.isPresent()){
            Property property = byId.get();
            Review result = reviewRepository.findReviewByUserIdAndPropertyId(property, user);
            if(result!=null){
                reviewRepository.delete(result);
                return "deleted";
            }
        }
        return null;
    }
}

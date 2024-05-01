package com.airbnb.repository;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.property=:propertyId and r.propertyUser=:userId")
    Review findReviewByUserIdAndPropertyId(@Param("propertyId")Property property,@Param("userId")PropertyUser user);
    //    @Query("select r from Review r where r.propertyUser=:userId")
    //    List<Review> findReviewByUserId(@Param("userId") PropertyUser user);
    //or
    List<Review> findByPropertyUser(PropertyUser propertyUser);
}
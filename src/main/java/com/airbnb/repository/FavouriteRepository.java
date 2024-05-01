package com.airbnb.repository;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    Optional<Favourite> findByProperty(Property property);
    @Query("select f from Favourite f where f.property=:property and f.propertyUser=:propertyUser")
    Optional<Favourite> findByPropertyAndUser(@Param("property") Property property, @Param("propertyUser")PropertyUser user);

    @Query("select f from Favourite f where f.propertyUser=:user and isFav=true")
    List<Favourite> findByUser(@Param("user") PropertyUser user);
}
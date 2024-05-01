package com.airbnb.controller;

import com.airbnb.entity.Favourite;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.FavouriteRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {
    private FavouriteRepository favouriteRepository;
    private final PropertyRepository propertyRepository;

    public FavouriteController(FavouriteRepository favouriteRepository,
                               PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }
    @PostMapping
    public ResponseEntity<Favourite> addFavourite
            (@RequestBody Favourite favourite, @AuthenticationPrincipal PropertyUser user){
        favourite.setPropertyUser(user);
        Favourite saved = favouriteRepository.save(favourite);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @PutMapping("/{PropertyId}")
    public ResponseEntity<?> updateFavourite(
            @RequestBody Favourite favourite,@AuthenticationPrincipal PropertyUser user
    ,@PathVariable Long PropertyId){
        Optional<Property> byId = propertyRepository.findById(PropertyId);
            Property property = byId.get();
        Optional<Favourite> byPropertyAndUser = favouriteRepository.findByPropertyAndUser(property, user);
        if(byPropertyAndUser.isPresent()){
            Favourite fav = byPropertyAndUser.get();
            fav.setIsFav(favourite.getIsFav());
            favouriteRepository.save(fav);
            return new ResponseEntity<>(favourite,HttpStatus.OK);
        }
        return new ResponseEntity<>("No record found to update",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getAllfav")
    public ResponseEntity<List<Favourite>> getAllFavByUser(@AuthenticationPrincipal PropertyUser user){
        List<Favourite> byUser = favouriteRepository.findByUser(user);
        return new ResponseEntity<>(byUser,HttpStatus.OK);
    }
}

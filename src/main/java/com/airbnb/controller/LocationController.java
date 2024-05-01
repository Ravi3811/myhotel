package com.airbnb.controller;

import com.airbnb.entity.Location;
import com.airbnb.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(@RequestBody Location location){
        Location saved = locationService.addLocation(location);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/getLocation")
    public ResponseEntity<?> getLocation(){
        List<Location> location = locationService.getLocation();
        return new ResponseEntity<>(location,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id,@RequestBody Location location){
        Location location1 = locationService.updateLocation(id, location);
        if (location1!=null){
            return new ResponseEntity<>(location1,HttpStatus.OK);
        }
        return new ResponseEntity<>("Record not found for this id",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id){
        String s = locationService.deleteLocation(id);
        if(s!=null){
            return new ResponseEntity<>("Deleted!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Record not found for this id",HttpStatus.NOT_FOUND);
    }
}

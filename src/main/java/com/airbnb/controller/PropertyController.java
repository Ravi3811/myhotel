package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>> findProperty(@PathVariable String locationName){
        List<Property> property = propertyService.findProperty(locationName);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }
    @PostMapping("/addProperty")
    public ResponseEntity<Property> addProperty(@RequestBody Property property){
        Property saved = propertyService.addProperty(property);
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable Long id){
        Property property = propertyService.getProperty(id);
        if(property!=null){
            return new ResponseEntity<>(property,HttpStatus.OK);
        }
        return new ResponseEntity<>("Property not found for this id",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/getProperty")
    public ResponseEntity<List<Property>> getAllProperty(){
        List<Property> property = propertyService.getAllProperty();
        return new ResponseEntity<>(property,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id,@RequestBody Property property){
        Property saved = propertyService.updateProperty(id, property);
        if(saved!=null){
            return new ResponseEntity<>(saved,HttpStatus.OK);
        }
        return new ResponseEntity<>("Property not found for this id",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id){
        String s = propertyService.deleteProperty(id);
        if(s!=null){
            return new ResponseEntity<>("Deleted!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Property not found for this id",HttpStatus.NOT_FOUND);
    }

}

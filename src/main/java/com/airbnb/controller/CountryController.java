package com.airbnb.controller;

import com.airbnb.entity.Country;
import com.airbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<?> addCountry(@RequestBody Country country){
        Country save = countryService.addCountry(country);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @GetMapping("/getCountry")
    public ResponseEntity<?> getCountry(){
        List<Country> country = countryService.getCountry();
        return new ResponseEntity<>(country,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable Long id,@RequestBody Country country){
        Country country1 = countryService.updateCountry(id, country);
        if(country1!=null){
            return new ResponseEntity<>(country1,HttpStatus.OK);
        }
        return new ResponseEntity<>("Record not found for this id",HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable Long id){
        String s = countryService.deleteCountry(id);
        if(s!=null){
            return new ResponseEntity<>("Deleted!!",HttpStatus.OK);
        }
        return new ResponseEntity<>("Record not found for this id",HttpStatus.NOT_FOUND);
    }
}

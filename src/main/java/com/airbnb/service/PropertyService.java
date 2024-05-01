package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.repository.CountryRepository;
import com.airbnb.repository.LocationRepository;
import com.airbnb.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> findProperty(String locationName) {
        List<Property> propertyByLocation = propertyRepository.findPropertyByLocation(locationName);
        return propertyByLocation;
    }

    public Property addProperty(Property property) {
        Property saved = propertyRepository.save(property);
        return saved;
    }

    public Property getProperty(Long id) {
        Optional<Property> byId = propertyRepository.findById(id);
        if(byId.isPresent()){
            Property property = byId.get();
            return property;
        }
        return null;
    }

    public List<Property> getAllProperty() {
        List<Property> all = propertyRepository.findAll();
        return all;
    }

    public Property updateProperty(Long id, Property property) {
        Optional<Property> byId = propertyRepository.findById(id);
        if(byId.isPresent()){
            Property property1 = byId.get();
            property1.setPropertyName(property.getPropertyName());
            property1.setBathrooms(property.getBathrooms());
            property1.setBedrooms(property.getBedrooms());
            property1.setGuests(property.getGuests());
            property1.setNightlyPrice(property.getNightlyPrice());
            property1.setCountry(property.getCountry());
            property1.setLocation(property.getLocation());
            Property saved = propertyRepository.save(property1);
            return saved;
        }
        return null;
    }

    public String deleteProperty(Long id) {
        Optional<Property> byId = propertyRepository.findById(id);
        if(byId.isPresent()){
            Property property = byId.get();
            propertyRepository.delete(property);
            return "deleted";
        }
        return null;
    }
}

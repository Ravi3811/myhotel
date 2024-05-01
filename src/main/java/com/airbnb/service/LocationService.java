package com.airbnb.service;

import com.airbnb.entity.Location;
import com.airbnb.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location addLocation(Location location) {
        Location saved = locationRepository.save(location);
        return saved;
    }

    public List<Location> getLocation() {
        List<Location> all = locationRepository.findAll();
        return all;
    }

    public Location updateLocation(Long id,Location location) {
        Optional<Location> byId = locationRepository.findById(id);
        if(byId.isPresent()){
            Location location1 = byId.get();
            location1.setLocationName(location.getLocationName());
            Location saved = locationRepository.save(location1);
            return saved;
        }
        return null;
    }

    public String deleteLocation(Long id) {
        Optional<Location> byId = locationRepository.findById(id);
        if(byId.isPresent()){
            Location location = byId.get();
            locationRepository.delete(location);
            return "deleted";
        }
        return null;
    }
}

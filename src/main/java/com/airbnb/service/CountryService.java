package com.airbnb.service;

import com.airbnb.entity.Country;
import com.airbnb.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country addCountry(Country country) {
        Country save = countryRepository.save(country);
        return save;
    }

    public List<Country> getCountry() {
        List<Country> country =countryRepository.findAll();
        return country;
    }

    public Country updateCountry(Long id, Country country) {
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isPresent()){
            Country save = byId.get();
            save.setCountryName(country.getCountryName());
            Country saved = countryRepository.save(save);
            return saved;
        }
        return null;
    }

    public String deleteCountry(Long id) {
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isPresent()){
            Country country = byId.get();
            countryRepository.delete(country);
            return "deleted";
        }
        return null;
    }
}

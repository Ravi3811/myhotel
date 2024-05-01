package com.airbnb.service;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private PropertyUserRepository propertyUserRepository;
    private JWTService jwtService;
    public UserService(PropertyUserRepository propertyUserRepository, JWTService jwtService) {
        this.propertyUserRepository = propertyUserRepository;
        this.jwtService = jwtService;
    }
    public PropertyUser addUser( PropertyUserDto propertyUserDto){
        PropertyUser user=new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUserName(propertyUserDto.getUserName());
        user.setEmail(propertyUserDto.getEmail());
        //user.setPassword(new BCryptPasswordEncoder().encode(propertyUserDto.getPassword())); or
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(), BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());
        PropertyUser save = propertyUserRepository.save(user);
        return save;
    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = propertyUserRepository.findByUserName(loginDto.getUserName());
        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();
            if( BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())){
              return  jwtService.generateToken(propertyUser);
            }
        }
        return null ;
    }

    public List<PropertyUser> getPropertyUser() {
        List<PropertyUser> propertyUsers = propertyUserRepository.findAll();
        return propertyUsers;
    }

    public PropertyUser updatePropertyUser(Long id,PropertyUserDto dto) {
        Optional<PropertyUser> byId = propertyUserRepository.findById(id);
        if(byId.isPresent()){
            PropertyUser propertyUser = byId.get();
            propertyUser.setFirstName(dto.getFirstName());
            propertyUser.setLastName(dto.getLastName());
            propertyUser.setUserName(dto.getUserName());
            propertyUser.setUserRole(dto.getUserRole());
            propertyUser.setEmail(dto.getEmail());
            propertyUser.setPassword(BCrypt.hashpw(dto.getPassword(),BCrypt.gensalt(10)));
            propertyUserRepository.save(propertyUser);
            return propertyUser;
        }
        return null;
    }

    public String deletePropertyUser(Long id) {
        Optional<PropertyUser> byId = propertyUserRepository.findById(id);
        if(byId.isPresent()){
            PropertyUser propertyUser = byId.get();
            propertyUserRepository.delete(propertyUser);
            return "deleted";
        }
        return null;
    }
}

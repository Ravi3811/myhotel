package com.airbnb.controller;

import com.airbnb.entity.PropertyUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.payload.PropertyUserDto;
import com.airbnb.payload.TokenResponse;
import com.airbnb.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class PropertyUserController {
    private UserService userService;

    public PropertyUserController(UserService userService) {
        this.userService = userService;
    }
   // @RequestMapping(name = "/addUser",method = RequestMethod.POST)
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto){
        PropertyUser propertyUser = userService.addUser(propertyUserDto);
        if(propertyUser!=null){
            return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        String token=userService.verifyLogin(loginDto);
        if(token!=null){
            TokenResponse tokenResponse=new TokenResponse();
            tokenResponse.setToken(token);
            return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
        }

        return new ResponseEntity<>("Username or password is invalid",HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/profile")
    public PropertyUser getCurrentUserProfile(@AuthenticationPrincipal PropertyUser propertyUser){
        return propertyUser;
    }
    @GetMapping("/getUser")
    public ResponseEntity<List<PropertyUser>> getPrpertyUser(){
        List<PropertyUser> propertyUser = userService.getPropertyUser();
        return new ResponseEntity<>(propertyUser,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePropertyUser(@PathVariable Long id,@RequestBody PropertyUserDto dto){
        PropertyUser propertyUser = userService.updatePropertyUser(id, dto);
        if(propertyUser!=null){
            return new ResponseEntity<>(propertyUser,HttpStatus.OK);
        }
        return new ResponseEntity<>("Record for this id is not present",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropertyUser(@PathVariable Long id){
        String s = userService.deletePropertyUser(id);
        if(s!=null){
            return new ResponseEntity<>("Record for this id is deletd",HttpStatus.OK);
        }
        return new ResponseEntity<>("Record for this id is not found",HttpStatus.NOT_FOUND);
    }

}

package com.mat.taksov.home.controller;

import com.mat.taksov.home.MyClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
//@PropertySources({
//        @PropertySource("classpath:custom.properties"),
//        @PropertySource("classpath:dev.properties")
//})
public class HomeController {
    @GetMapping
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Welcome home");
    }
//    @Value("${current.profile}")
//    private String currentProfile;
//
//    private MyClass myClass;
//
//    @Autowired
//    public void setMyClass(MyClass myClass) {
//        this.myClass = myClass;
//    }
//
//    public void setCurrentProfile(String currentProfile) {
//        this.currentProfile = currentProfile;
//    }
//    @GetMapping("/profile")
//    public ResponseEntity<String> getEnvironmentVal(){
////        return ResponseEntity.ok("Current profile: " + currentProfile);
//        return ResponseEntity.ok("Current profile: " + myClass.getMsg());
//    }
//
//
//    @GetMapping("/")
//    public ResponseEntity<String> home(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return new ResponseEntity<>("Welcome User " + auth.getName(),HttpStatus.OK);
//    }
//
////    ENVIRONMENT SETUP
////    private Environment environment;
////    @Autowired
////    public void setEnvironment(Environment environment){
////        this.environment = environment;
////    }
////    @GetMapping("/env")
////    public ResponseEntity<String> getEnvironmentVal(){
////        String envVal = environment.getProperty("customenvironmentvariable");
////        return ResponseEntity.ok("Environment key "+envVal);
////    }
//
//
////    CUSTOM PROPERTIES
////@Value("${property1}")
////private String customProperty;
////    @Value("${property2}")
////    private String customProperty2;
////
////    public void setCustomProperty(String customProperty) {
////        this.customProperty = customProperty;
////    }
////
////    public void setCustomProperty2(String customProperty2) {
////        this.customProperty2 = customProperty2;
////    }
////    @GetMapping("/env")
////    public ResponseEntity<String> getEnvironmentVal(){
////        String envVal = customProperty;
////        return ResponseEntity.ok("Environment values: "+envVal+" and "+customProperty2);
////    }
}

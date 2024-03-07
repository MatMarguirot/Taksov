package com.mat.taksov.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOMappingConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
//    @Bean
//    public UserMapper userMapper(){
//        return new UserMapper() {
//            @Override
//            public GetUserDTO toGetDto(User user) {
//                return null;
//            }
//
//            @Override
//            public PostUserDTO toPostDto(User user) {
//                return null;
//            }
//
//            @Override
//            public User toEntityFromGetDto(GetUserDTO getUserDTO) {
//                return null;
//            }
//
//            @Override
//            public User toEntityFromPostDto(PostUserDTO postUserDTO) {
//                return null;
//            }
//        }
//    }
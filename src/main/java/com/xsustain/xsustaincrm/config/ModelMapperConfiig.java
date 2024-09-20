package com.xsustain.xsustaincrm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperConfiig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

package br.com.unitechdesafio.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

    @Primary
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

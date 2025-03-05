package com.br.arraydesabores.rede.presentation.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setPropertyCondition(context -> !(context.getSource() instanceof org.hibernate.proxy.HibernateProxy));
        return modelMapper;
    }
}
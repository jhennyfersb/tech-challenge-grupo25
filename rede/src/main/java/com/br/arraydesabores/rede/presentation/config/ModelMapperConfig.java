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
        // Configurando a estratégia de matching STRICT para evitar mapeamentos incorretos
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
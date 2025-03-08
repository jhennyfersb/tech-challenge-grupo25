package com.br.arraydesabores.rede.presentation.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
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

        modelMapper.addConverter(new Converter<String, Enum>() {
            @Override
            public Enum convert(MappingContext<String, Enum> context) {
                if (context.getSource() == null) return null;
                Class<? extends Enum> enumType = (Class<? extends Enum>) context.getDestinationType();
                return Enum.valueOf(enumType, context.getSource());
            }
        });

        modelMapper.addConverter(new Converter<Enum, String>() {
            @Override
            public String convert(MappingContext<Enum, String> context) {
                return context.getSource() == null ? null : context.getSource().name();
            }
        });
        return modelMapper;
    }
}
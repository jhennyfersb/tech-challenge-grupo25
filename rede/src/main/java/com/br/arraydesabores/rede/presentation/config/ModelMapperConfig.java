package com.br.arraydesabores.rede.presentation.config;

import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.entity.UserRoleEntity;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull())
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setPropertyCondition(context -> !(context.getSource() instanceof org.hibernate.proxy.HibernateProxy))
                .setImplicitMappingEnabled(true);


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

        modelMapper.createTypeMap(UserEntity.class, User.class)
                .addMappings(mapper -> {
                    // Abordagem personalizada para mapeamento de roles
                    mapper.using(ctx -> {
                        Set<UserRoleEntity> source = (Set<UserRoleEntity>) ctx.getSource();
                        if (source == null) return null;

                        return source.stream()
                                .map(roleEntity -> {
                                    UserRole role = new UserRole();
                                    role.setId(roleEntity.getId());
                                    role.setRole(roleEntity.getRole());
                                    // Não definimos a referência de volta ao user
                                    return role;
                                })
                                .collect(Collectors.toSet());
                    }).map(UserEntity::getRoles, User::setRoles);
                });
        return modelMapper;
    }
}
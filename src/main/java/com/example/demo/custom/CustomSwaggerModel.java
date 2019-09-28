package com.example.demo.custom;


import com.example.demo.dto.PersonDTO;
import com.example.demo.dto.StudentDTO;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.schema.ModelProperty;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.ResolvedTypes;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1002)
public class CustomSwaggerModel implements ModelBuilderPlugin {

    @Autowired
    TypeResolver resolver;

    @Autowired
    TypeNameExtractor typeNameExtractor;

    Map<String, ModelProperty> propertyAddinMap = new HashMap<String, ModelProperty>();

    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }

    private Class<?> forClass(ModelContext context) {
        return resolver.resolve(context.getType()).getErasedType();
    }

    @Override
    public void apply(ModelContext modelContext) {

        boolean personScope = false;
        Class<?> modelClass = forClass(modelContext);

        // Detect if it is Person modelcontext type instance
        if (modelClass == PersonDTO.class) {
            personScope = true;
        }
        // Or an Hateoas resource pointing on Person instance
        else if (modelClass.equals(Resource.class)) {
            ResolvedType resourceResolveType = resolver.resolve(modelContext.getType()).getTypeBindings().getTypeParameters().get(0);
            if (resourceResolveType.getErasedType().equals(PersonDTO.class))
                personScope = true;
        }

        // Add student definition in Person definition and ResourcePerson definition
        if (personScope) {
            ModelPropertyBuilder builder = new ModelPropertyBuilder();

            ModelProperty modelProperty = builder
                    .name("persons")
                    .type(resolver.resolve(Object.class))
                    .build();

            modelProperty.updateModelRef(ResolvedTypes.modelRefFactory(modelContext, typeNameExtractor));
            propertyAddinMap.put("student", modelProperty);
            modelContext.getBuilder()
                    .name("Person")
                    .properties(propertyAddinMap)
                    .build();
        }
    }
}

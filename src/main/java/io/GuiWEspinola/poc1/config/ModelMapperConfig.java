package io.GuiWEspinola.poc1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
//
//    public ModelMapper modelmapper() {
//        ModelMapper mapper = new ModelMapper();
//        mapper.createTypeMap(Customer.class, CustomerResponseDTO.class).setConverter(customerResponseDTOConverter);
//        return mapper;
//    }
//
//    TypeMap<Customer, CustomerResponseDTO> propertyMapper = this.mapper().
//
//    Converter<Customer, CustomerResponseDTO> customerResponseDTOConverter = new Converter<Customer, CustomerResponseDTO>() {
//        @Override
//        public CustomerResponseDTO convert(MappingContext<Customer, CustomerResponseDTO> mappingContext) {
//
//            Customer source = mappingContext.getSource();
//            CustomerResponseDTO destination = mappingContext.getDestination();
//
//            destination.setId(source.getId());
//            destination.setName(source.getName());
//            destination.setEmail(source.getEmail());
//            destination.setDocumentType(source.getDocumentType());
//            destination.setDocumentNumber(source.getDocumentType().getMask());
//            destination.setMobileNumber(source.getMobileNumber());
//
//            return destination;
//        }
//    };
}

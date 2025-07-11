package com.example.demo.dto.mapper;

import com.example.demo.dto.common.AddressDto;
import com.example.demo.model.embeddable.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

  Address fromDto(AddressDto addressDto);
}

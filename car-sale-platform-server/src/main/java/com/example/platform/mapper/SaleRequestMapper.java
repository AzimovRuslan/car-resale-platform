package com.example.platform.mapper;

import com.example.platform.aspect.utility.ModelMapperCreator;
import com.example.platform.dto.SaleRequestDTO;
import com.example.platform.model.SaleRequest;
import org.springframework.stereotype.Component;

@Component
public class SaleRequestMapper implements Mapper<SaleRequestDTO, SaleRequest>{
    @Override
    public SaleRequestDTO toDto(SaleRequest saleRequest) {

        return ModelMapperCreator.getModelMapper().map(saleRequest, SaleRequestDTO.class);
    }

    @Override
    public SaleRequest toEntity(SaleRequestDTO saleRequestDTO) {

        return ModelMapperCreator.getModelMapper().map(saleRequestDTO, SaleRequest.class);
    }
}

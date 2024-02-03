package com.example.platform.mapper;

import com.example.platform.aspect.utility.ModelMapperCreator;
import com.example.platform.dto.SaleAnnouncementDTO;
import com.example.platform.model.SaleAnnouncement;
import org.springframework.stereotype.Component;

@Component
public class SaleAnnouncementMapper implements Mapper<SaleAnnouncementDTO, SaleAnnouncement> {

    @Override
    public SaleAnnouncementDTO toDto(SaleAnnouncement saleAnnouncement) {

        return ModelMapperCreator.getModelMapper().map(saleAnnouncement, SaleAnnouncementDTO.class);
    }

    @Override
    public SaleAnnouncement toEntity(SaleAnnouncementDTO saleAnnouncementDTO) {

        return ModelMapperCreator.getModelMapper().map(saleAnnouncementDTO, SaleAnnouncement.class);
    }
}

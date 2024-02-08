package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.SaleAnnouncementDTO;
import com.example.platform.mapper.SaleAnnouncementMapper;
import com.example.platform.mapper.SaleRequestMapper;
import com.example.platform.model.SaleAnnouncement;
import com.example.platform.model.SaleRequest;
import com.example.platform.repository.SaleAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SaleAnnouncementService implements Service<SaleAnnouncement, SaleAnnouncementDTO> {
    private final SaleAnnouncementRepository saleAnnouncementRepository;
    private final SaleAnnouncementMapper saleAnnouncementMapper;
    private final SaleRequestService saleRequestService;
    private final SaleRequestMapper saleRequestMapper;

    @Override
    public List<SaleAnnouncement> findAll(PageRequest pq) {
        return saleAnnouncementRepository.findAll(pq).getContent();
    }

    @Override
    public SaleAnnouncement findById(Long id) {
        return RecordGetter.getRecordFromTable(id, saleAnnouncementRepository);
    }

    @Override
    public SaleAnnouncement create(SaleAnnouncementDTO saleAnnouncementDTO) {
        final SaleAnnouncement saleAnnouncement = saleAnnouncementMapper.toEntity(saleAnnouncementDTO);

        SaleRequest confirmationSaleRequest = saleAnnouncement.getSaleRequest();
        confirmationSaleRequest.setStatus("confirmed");

        Long confirmationSaleRequestId = confirmationSaleRequest.getId();

        saleRequestService.update(confirmationSaleRequestId, saleRequestMapper.toDto(confirmationSaleRequest));
        saleAnnouncement.setSaleRequest(saleRequestService.findById(confirmationSaleRequestId));

        saleAnnouncementRepository.save(saleAnnouncement);

        return saleAnnouncement;
    }

    @Override
    public SaleAnnouncement deleteById(Long id) {
        SaleAnnouncement saleAnnouncement = RecordGetter.getRecordFromTable(id, saleAnnouncementRepository);

        if (saleAnnouncement != null) {
            saleAnnouncementRepository.deleteById(id);
        }

        return saleAnnouncement;
    }

    @Override
    public SaleAnnouncement update(Long id, SaleAnnouncementDTO saleAnnouncementDTO) {
        SaleAnnouncement saleAnnouncement = RecordGetter.getRecordFromTable(id, saleAnnouncementRepository);
        SaleAnnouncement saleAnnouncementDetails = saleAnnouncementMapper.toEntity(saleAnnouncementDTO);

        saleAnnouncement.setSaleRequest(saleAnnouncementDetails.getSaleRequest());
        saleAnnouncement.setDate(saleAnnouncementDetails.getDate());
        saleAnnouncement.setTime(saleAnnouncementDetails.getTime());

        saleAnnouncementRepository.save(saleAnnouncement);

        return saleAnnouncement;
    }
}

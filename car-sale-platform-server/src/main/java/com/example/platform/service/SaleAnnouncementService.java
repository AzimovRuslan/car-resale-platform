package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.SaleAnnouncementDTO;
import com.example.platform.dto.SaleRequestDTO;
import com.example.platform.mapper.SaleAnnouncementMapper;
import com.example.platform.mapper.SaleRequestMapper;
import com.example.platform.model.SaleAnnouncement;
import com.example.platform.model.SaleRequest;
import com.example.platform.repository.SaleAnnouncementRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SaleAnnouncementService implements Service<SaleAnnouncementDTO> {
    private final SaleAnnouncementRepository saleAnnouncementRepository;
    private final SaleAnnouncementMapper saleAnnouncementMapper;
    private final SaleRequestService saleRequestService;
    private final SaleRequestMapper saleRequestMapper;

    @Override
    public List<SaleAnnouncementDTO> findAll() {
        return saleAnnouncementRepository.findAll().stream().map(saleAnnouncementMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public SaleAnnouncementDTO findById(Long id) {
        return saleAnnouncementMapper.toDto(RecordGetter.getRecordFromTable(id, saleAnnouncementRepository));
    }

    @Override
    public SaleAnnouncementDTO create(SaleAnnouncementDTO saleAnnouncementDTO) {
        final SaleAnnouncement saleAnnouncement = saleAnnouncementMapper.toEntity(saleAnnouncementDTO);

        SaleRequest confirmationSaleRequest = saleAnnouncement.getSaleRequest();
        confirmationSaleRequest.setStatus("confirmed");
        SaleRequestDTO requestDTO = saleRequestService.findAll()
                .stream()
                .filter(i -> i.getDescription().equals(confirmationSaleRequest.getDescription()))
                .findFirst()
                .orElse(null);

        Long confirmationSaleRequestId = requestDTO.getId();

        saleRequestService.update(confirmationSaleRequestId, saleRequestMapper.toDto(confirmationSaleRequest));
        saleAnnouncement.setSaleRequest(saleRequestMapper.toEntity(saleRequestService.findById(confirmationSaleRequestId)));

        saleAnnouncementRepository.save(saleAnnouncement);

        return saleAnnouncementMapper.toDto(saleAnnouncement);
    }

    @Override
    public SaleAnnouncementDTO deleteById(Long id) {
        SaleAnnouncement saleAnnouncement = RecordGetter.getRecordFromTable(id, saleAnnouncementRepository);

        if (saleAnnouncement != null) {
            saleAnnouncementRepository.deleteById(id);
        }

        return saleAnnouncementMapper.toDto(saleAnnouncement);
    }

    @Override
    public SaleAnnouncementDTO update(Long id, SaleAnnouncementDTO saleAnnouncementDTO) {
        SaleAnnouncement saleAnnouncement = RecordGetter.getRecordFromTable(id, saleAnnouncementRepository);
        SaleAnnouncement saleAnnouncementDetails = saleAnnouncementMapper.toEntity(saleAnnouncementDTO);

        saleAnnouncement.setSaleRequest(saleAnnouncementDetails.getSaleRequest());
        saleAnnouncement.setDate(saleAnnouncementDetails.getDate());
        saleAnnouncement.setTime(saleAnnouncementDetails.getTime());

        saleAnnouncementRepository.save(saleAnnouncement);

        return saleAnnouncementMapper.toDto(saleAnnouncement);
    }
}

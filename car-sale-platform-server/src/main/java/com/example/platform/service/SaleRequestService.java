package com.example.platform.service;

import com.example.platform.aspect.utility.RecordGetter;
import com.example.platform.dto.SaleRequestDTO;
import com.example.platform.mapper.SaleRequestMapper;
import com.example.platform.model.SaleAnnouncement;
import com.example.platform.model.SaleRequest;
import com.example.platform.repository.SaleAnnouncementRepository;
import com.example.platform.repository.SaleRequestRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SaleRequestService implements Service<SaleRequest, SaleRequestDTO> {
    private final SaleRequestRepository saleRequestRepository;
    private final SaleRequestMapper saleRequestMapper;
    private final SaleAnnouncementRepository saleAnnouncementRepository;

    @Override
    public List<SaleRequest> findAll() {
        return saleRequestRepository.findAll();
    }

    @Override
    public SaleRequest findById(Long id) {
        return RecordGetter.getRecordFromTable(id, saleRequestRepository);
    }

    @Override
    public SaleRequest create(SaleRequestDTO saleRequestDTO) {
        final SaleRequest saleRequest = saleRequestMapper.toEntity(saleRequestDTO);
        saleRequestRepository.save(saleRequest);

        return saleRequest;
    }

    @Override
    public SaleRequest deleteById(Long id) {
        SaleRequest saleRequest = RecordGetter.getRecordFromTable(id, saleRequestRepository);

        SaleAnnouncement saleAnnouncement = saleAnnouncementRepository.findAll()
                .stream()
                .filter(s -> s.getSaleRequest().equals(saleRequest))
                .findFirst()
                .orElse(null);

        if (saleRequest != null) {
            if (saleAnnouncement != null) {
                saleAnnouncementRepository.deleteById(saleAnnouncement.getId());
            }
            saleRequestRepository.deleteById(id);
        }

        return saleRequest;
    }

    @Override
    public SaleRequest update(Long id, SaleRequestDTO saleRequestDTO) {
        SaleRequest saleRequest = RecordGetter.getRecordFromTable(id, saleRequestRepository);
        SaleRequest saleRequestDetails = saleRequestMapper.toEntity(saleRequestDTO);

        saleRequest.setUser(saleRequestDetails.getUser());
        saleRequest.setCar(saleRequestDetails.getCar());
        saleRequest.setDescription(saleRequestDetails.getDescription());
        saleRequest.setPrice(saleRequestDetails.getPrice());
        saleRequest.setStatus(saleRequestDetails.getStatus());

        saleRequestRepository.save(saleRequest);

        return saleRequest;
    }
}

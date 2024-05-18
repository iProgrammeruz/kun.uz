package com.codeuz.service;

import com.codeuz.dto.RegionDTO;
import com.codeuz.entity.RegionEntity;
import com.codeuz.enums.Languages;
import com.codeuz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionDTO regionDTO) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setOrderNumber(regionDTO.getOrderNumber());
        regionEntity.setNameUz(regionDTO.getNameUz());
        regionEntity.setNameRu(regionDTO.getNameRu());
        regionEntity.setNameEn(regionDTO.getNameEn());
        regionEntity.setVisible(regionDTO.getVisible());
        regionEntity.setCreatedDate(regionDTO.getCreatedDate());
        regionEntity = regionRepository.save(regionEntity);
        regionDTO.setId(regionEntity.getId());
        return regionDTO;
    }


    public Boolean update(Integer id, RegionDTO regionDTO) {
        RegionEntity regionEntity = get(id);
        regionEntity.setOrderNumber(regionDTO.getOrderNumber());
        regionEntity.setNameUz(regionDTO.getNameUz());
        regionEntity.setNameRu(regionDTO.getNameRu());
        regionEntity.setNameEn(regionDTO.getNameEn());
        regionEntity.setVisible(regionDTO.getVisible());
        regionEntity.setCreatedDate(regionDTO.getCreatedDate());
        regionRepository.save(regionEntity);
        return true;
    }


    public Boolean delete(Integer id) {
        RegionEntity regionEntity = get(id);
        regionRepository.delete(regionEntity);
        return true;
    }


    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> regionEntities = regionRepository.findAll();
        List<RegionDTO> regionDTOList = new ArrayList<>();
        for (RegionEntity regionEntity : regionEntities) {
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setId(regionEntity.getId());
            regionDTO.setOrderNumber(regionEntity.getOrderNumber());
            regionDTO.setNameUz(regionEntity.getNameUz());
            regionDTO.setNameRu(regionEntity.getNameRu());
            regionDTO.setNameEn(regionEntity.getNameEn());
            regionDTO.setVisible(regionEntity.getVisible());
            regionDTO.setCreatedDate(regionEntity.getCreatedDate());
            regionDTOList.add(regionDTO);
        }
        return regionDTOList;
    }


    public List<RegionDTO> getByLanguage(Languages language) {
        List<RegionEntity> regionEntities = regionRepository.findAllByLanguage(language.name());
        List<RegionDTO> regionDTOList = new ArrayList<>();
        regionEntities.forEach(regionEntity -> {
            RegionDTO regionDTO = new RegionDTO();
            regionDTO.setId(regionEntity.getId());
            regionDTO.setOrderNumber(regionEntity.getOrderNumber());
            regionDTO.setVisible(regionEntity.getVisible());
            regionDTO.setCreatedDate(regionEntity.getCreatedDate());
            regionDTOList.add(regionDTO);
        });
        return regionDTOList;
    }






    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Region not found");
        });
    }



}

package com.codeuz.service;

import com.codeuz.dto.RegionCreateDTO;
import com.codeuz.dto.RegionDTO;
import com.codeuz.entity.RegionEntity;
import com.codeuz.enums.Languages;
import com.codeuz.exp.AppBadException;
import com.codeuz.mapper.RegionMapper;
import com.codeuz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionCreateDTO region) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setOrderNumber(region.getOrderNumber());
        regionEntity.setNameUz(region.getNameUz());
        regionEntity.setNameRu(region.getNameRu());
        regionEntity.setNameEn(region.getNameEn());
        regionRepository.save(regionEntity);
        return toDTO(regionEntity);
    }


    public Boolean update(Integer id, RegionCreateDTO region) {
        RegionEntity regionEntity = get(id);
        regionEntity.setOrderNumber(region.getOrderNumber());
        regionEntity.setNameUz(region.getNameUz());
        regionEntity.setNameRu(region.getNameRu());
        regionEntity.setNameEn(region.getNameEn());
        regionRepository.save(regionEntity);
        return true;
    }


    public Boolean delete(Integer id) {
        /*RegionEntity regionEntity = get(id);
        regionRepository.delete(regionEntity);*/
        regionRepository.deleteById(id);
        return true;
    }


    // for admin
    public List<RegionDTO> getAll() {
        Iterable<RegionEntity> iterable = regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }


    // for users 1st method
    /*public List<RegionDTO> getAllByLanguage(Languages language) {
        Iterable<RegionEntity> iterable = regionRepository.findAllByVisibleTrueOrderByOrderNumberDesc();
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionEntity entity : iterable) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            switch (language) {
                case EN -> dto.setName(entity.getNameEn());
                case UZ -> dto.setName(entity.getNameUz());
                case RU -> dto.setName(entity.getNameRu());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }*/

    // for users 2nd method a
    /*public List<RegionDTO> getAllByLanguage(Languages lang) {
        List<RegionMapper> mapperList = regionRepository.findAll(lang.name());
        List<RegionDTO> dtoList = new LinkedList<>();
        for (RegionMapper entity : mapperList) {
            RegionDTO dto = new RegionDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }*/

    // for users 2nd method b
    public List<RegionMapper> getAllByLanguage(Languages language) {
        List<RegionMapper> mapperList = regionRepository.findAllByLanguage(language.name());
        return mapperList;
    }


    public RegionDTO getRegion(Integer id, Languages lang) {
        RegionMapper regionMapper = regionRepository.findRegionByIdAndByLanguage(id, lang.name());
        Objects.requireNonNull(regionMapper);
        RegionDTO dto = new RegionDTO();
        dto.setId(regionMapper.getId());
        dto.setName(regionMapper.getName());
        return dto;
    }







    public RegionDTO toDTO(RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }



    public RegionEntity get(Integer id) {
        return regionRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Region not found");
        });
    }



}

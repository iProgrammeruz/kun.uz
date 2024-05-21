package com.codeuz.service;

import com.codeuz.dto.TypesCreateDTO;
import com.codeuz.dto.TypesDTO;
import com.codeuz.entity.TypesEntity;
import com.codeuz.enums.Languages;
import com.codeuz.mapper.TypesMapper;
import com.codeuz.repository.TypesRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TypesService {

    @Autowired
    private TypesRepositry typesRepositry;


    public TypesDTO create(TypesCreateDTO types) {
        TypesEntity typesEntity = new TypesEntity();
        typesEntity.setOrderNumber(types.getOrderNumber());
        typesEntity.setNameUz(types.getNameUz());
        typesEntity.setNameRu(types.getNameRu());
        typesEntity.setNameEn(types.getNameEn());
        typesRepositry.save(typesEntity);
        return toDTO(typesEntity);
    }


    public Boolean update(Integer id, TypesCreateDTO types) {
        TypesEntity typesEntity = get(id);
        typesEntity.setOrderNumber(types.getOrderNumber());
        typesEntity.setNameUz(types.getNameUz());
        typesEntity.setNameRu(types.getNameRu());
        typesEntity.setNameEn(types.getNameEn());
        typesRepositry.save(typesEntity);
        return true;
    }


    public Boolean delete(Integer id) {
        typesRepositry.deleteById(id);
        return true;
    }


    public PageImpl<TypesDTO> getAllWithPanigation(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<TypesEntity> types = typesRepositry.findAll(pageable);
        List<TypesDTO> typesDTOList = new ArrayList<>();
        types.getContent().forEach(typesEntity -> {
            typesDTOList.add(toDTO(typesEntity));
        });
        return new PageImpl<>(typesDTOList, pageable, types.getTotalElements());
    }


    public List<TypesMapper> getAllByLanguage(Languages language) {
        List<TypesMapper> mapperList = typesRepositry.findAllByLanguage(language.name());
        return mapperList;
    }









    public TypesEntity get(Integer id) {
        return typesRepositry.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Types not found");
        });
    }

    public TypesDTO toDTO(TypesEntity types) {
        TypesDTO dto = new TypesDTO();
        dto.setId(types.getId());
        dto.setOrderNumber(types.getOrderNumber());
        dto.setNameUz(types.getNameUz());
        dto.setNameRu(types.getNameRu());
        dto.setNameEn(types.getNameEn());
        dto.setVisible(types.getVisible());
        dto.setCreatedDate(types.getCreatedDate());
        return dto;
    }


}

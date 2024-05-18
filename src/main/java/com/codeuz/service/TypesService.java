package com.codeuz.service;

import com.codeuz.dto.TypesDTO;
import com.codeuz.entity.TypesEntity;
import com.codeuz.enums.Languages;
import com.codeuz.repository.TypesRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TypesService {

    @Autowired
    private TypesRepositry typesRepositry;


    public TypesDTO create(TypesDTO typesDTO) {
        TypesEntity types = new TypesEntity();
        types.setOrderNumber(typesDTO.getOrderNumber());
        types.setNameUz(typesDTO.getNameUz());
        types.setNameRu(typesDTO.getNameRu());
        types.setNameEn(typesDTO.getNameEn());
        types.setCreatedDate(typesDTO.getCreatedDate());
        types.setVisible(typesDTO.getVisible());
        typesRepositry.save(types);
        typesDTO.setId(types.getId());
        return typesDTO;
    }


    public Boolean update(Integer id, TypesDTO typesDTO) {
        TypesEntity type = get(id);
        type.setOrderNumber(typesDTO.getOrderNumber());
        type.setNameUz(typesDTO.getNameUz());
        type.setNameRu(typesDTO.getNameRu());
        type.setNameEn(typesDTO.getNameEn());
        type.setCreatedDate(typesDTO.getCreatedDate());
        type.setVisible(typesDTO.getVisible());
        typesRepositry.save(type);
        return true;
    }


    public Boolean delete(Integer id) {
        TypesEntity type = get(id);
        typesRepositry.delete(type);
        return true;
    }


    public PageImpl<TypesDTO> getAllWithPanigation(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TypesEntity> types = typesRepositry.findAll(pageable);
        List<TypesDTO> typesDTOList = new ArrayList<>();
        types.getContent().forEach(typesEntity -> {
            TypesDTO typesDTO = new TypesDTO();
            typesDTO.setId(typesEntity.getId());
            typesDTO.setOrderNumber(typesEntity.getOrderNumber());
            typesDTO.setNameUz(typesEntity.getNameUz());
            typesDTO.setNameRu(typesEntity.getNameRu());
            typesDTO.setNameEn(typesEntity.getNameEn());
            typesDTO.setCreatedDate(typesEntity.getCreatedDate());
            typesDTO.setVisible(typesEntity.getVisible());
            typesDTOList.add(typesDTO);
        });
        return new PageImpl<>(typesDTOList, pageable, types.getTotalElements());
    }


    public List<TypesDTO> getByLanguage(Languages language) {
        List<TypesEntity> typesEntities = typesRepositry.findAllByLanguage(language.name());
        List<TypesDTO> typesDTOList = new ArrayList<>();
        typesEntities.forEach((typesEntity) -> {
            TypesDTO typesDTO = new TypesDTO();
            typesDTO.setId(typesEntity.getId());
            typesDTO.setOrderNumber(typesEntity.getOrderNumber());
            typesDTO.setNameUz(typesEntity.getNameUz());
            typesDTO.setNameRu(typesEntity.getNameRu());
            typesDTO.setNameEn(typesEntity.getNameEn());
            typesDTO.setCreatedDate(typesEntity.getCreatedDate());
            typesDTO.setVisible(typesEntity.getVisible());
            typesDTOList.add(typesDTO);
        });
        return typesDTOList;
    }










    public TypesEntity get(Integer id) {
        return typesRepositry.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Types not found");
        });
    }



}

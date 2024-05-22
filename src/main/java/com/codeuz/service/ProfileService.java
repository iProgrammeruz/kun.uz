package com.codeuz.service;

import com.codeuz.dto.FilterResponseDTO;
import com.codeuz.dto.ProfileCreateDTO;
import com.codeuz.dto.ProfileDTO;
import com.codeuz.dto.ProfileFilterDTO;
import com.codeuz.entity.ProfileEntity;
import com.codeuz.exp.AppBadException;
import com.codeuz.repository.ProfileCustomRepository;
import com.codeuz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;


    public ProfileDTO create(ProfileCreateDTO profile) {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getUsername());
        profileEntity.setEmail(profile.getEmail());
        profileEntity.setPhone(profile.getPhone());
        profileEntity.setPassword(profile.getPassword());
        profileEntity.setStatus(profile.getStatus());
        profileEntity.setRole(profile.getRole());
        profileRepository.save(profileEntity);
        return toDTO(profileEntity);
    }


    public Boolean update(Integer id, ProfileCreateDTO profile) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profile.getName());
        profileEntity.setSurname(profile.getUsername());
        profileEntity.setEmail(profile.getEmail());
        profileEntity.setPhone(profile.getPhone());
        profileEntity.setPassword(profile.getPassword());
        profileEntity.setStatus(profile.getStatus());
        profileEntity.setRole(profile.getRole());
        profileRepository.save(profileEntity);
        return true;
    }


    public Boolean updateUser(Integer id, ProfileCreateDTO profileUser) {
        ProfileEntity profileEntity = get(id);
        profileEntity.setName(profileUser.getName());
        profileEntity.setSurname(profileUser.getUsername());
        profileEntity.setEmail(profileUser.getEmail());
        profileEntity.setPhone(profileUser.getPhone());
        profileEntity.setPassword(profileUser.getPassword());
        profileRepository.save(profileEntity);
        return true;
    }


    public Boolean delete(Integer id) {
        profileRepository.deleteById(id);
        return true;
    }

    public PageImpl<ProfileDTO> getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfileEntity> profileEntities = profileRepository.findAll(pageable);
        List<ProfileDTO> profileList = new ArrayList<>();
        profileEntities.getContent().forEach(profileEntity -> profileList.add(toDTO(profileEntity)));
        return new PageImpl<>(profileList, pageable, profileEntities.getTotalElements());
    }


    public PageImpl<ProfileDTO> filter(ProfileFilterDTO profileFilter, int page, int size) {
        FilterResponseDTO<ProfileEntity> filterResponse = profileCustomRepository.filter(profileFilter, page, size);
        List<ProfileDTO> profileDTOList = new ArrayList<>();
        filterResponse.getContent().forEach(profileEntity -> profileDTOList.add(toDTO(profileEntity)));
        return new PageImpl<ProfileDTO>(profileDTOList, PageRequest.of(page, size), filterResponse.getTotalCount());
    }



    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(profileEntity.getId());
        dto.setName(profileEntity.getName());
        dto.setSurname(profileEntity.getSurname());
        dto.setEmail(profileEntity.getEmail());
        dto.setPhone(profileEntity.getPhone());
        dto.setPassword(profileEntity.getPassword());
        dto.setStatus(profileEntity.getStatus());
        dto.setRole(profileEntity.getRole());
        return dto;
    }


    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Profile not found");
        });
    }

}

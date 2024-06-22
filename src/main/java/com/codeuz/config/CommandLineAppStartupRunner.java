
package com.codeuz.config;

import com.codeuz.entity.ProfileEntity;
import com.codeuz.enums.ProfileRole;
import com.codeuz.enums.ProfileStatus;
import com.codeuz.repository.ProfileRepository;
import com.codeuz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;


/*@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final ProfileRepository profileRepository;

    @Autowired
    public CommandLineAppStartupRunner(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void run(String...args) throws Exception {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setEmail("couragesland77@gmail.com");
        profileEntity.setPassword(MD5Util.getMD5("i12345L"));
        profileEntity.setName("Admin");
        profileEntity.setSurname("Adminjon");
        profileEntity.setRole(ProfileRole.ROLE_ADMIN);
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setVisible(true);
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(profileEntity.getEmail());
        if (optional.isEmpty()) {
            profileRepository.save(profileEntity);
        }
    }
}*/


package com.eazybytes.profile.service.impl;

import com.eazybytes.profile.dto.ProfileDto;
import com.eazybytes.profile.entity.Profile;
import com.eazybytes.profile.exception.ProfileAlreadyExistsException;
import com.eazybytes.profile.exception.ResourceNotFoundException;
import com.eazybytes.profile.mapper.ProfileMapper;
import com.eazybytes.profile.repository.ProfileRepository;
import com.eazybytes.profile.service.IProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private ProfileRepository profileRepository;

    @Override
    public ProfileDto fetchProfile(String mobileNumber) {
        Profile profile = profileRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Profile", "mobileNumber", mobileNumber)
        );
        ProfileDto profileDto = ProfileMapper.mapToProfileDto(profile, new ProfileDto());
        return profileDto;
    }

}

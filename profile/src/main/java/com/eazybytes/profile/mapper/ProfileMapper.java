package com.eazybytes.profile.mapper;

import com.eazybytes.profile.dto.ProfileDto;
import com.eazybytes.profile.entity.Profile;

public class ProfileMapper {

    public static ProfileDto mapToProfileDto(Profile profile, ProfileDto profileDto) {
        profileDto.setProfileId(profile.getProfileId());
        profileDto.setName(profile.getName());
        profileDto.setEmail(profile.getEmail());
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setActiveSw(profile.isActiveSw());
        return profileDto;
    }

}

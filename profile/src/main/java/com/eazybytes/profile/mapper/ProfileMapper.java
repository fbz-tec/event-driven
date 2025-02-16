package com.eazybytes.profile.mapper;

import com.eazybytes.profile.dto.ProfileDto;
import com.eazybytes.profile.entity.Profile;

public class ProfileMapper {

    public static ProfileDto mapToProfileDto(Profile profile, ProfileDto profileDto) {
        profileDto.setMobileNumber(profile.getMobileNumber());
        profileDto.setName(profile.getName());
        profileDto.setAccountNumber(profile.getAccountNumber());
        profileDto.setCardNumber(profile.getCardNumber());
        profileDto.setLoanNumber(profile.getLoanNumber());
        return profileDto;
    }

}

package com.eazybytes.profile.service;

import com.eazybytes.profile.dto.ProfileDto;

public interface IProfileService {

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    ProfileDto fetchProfile(String mobileNumber);

}

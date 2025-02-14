package com.eazybytes.profile.query.projection;

import com.eazybytes.profile.command.event.ProfileCreatedEvent;
import com.eazybytes.profile.command.event.ProfileDeletedEvent;
import com.eazybytes.profile.command.event.ProfileUpdatedEvent;
import com.eazybytes.profile.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("profile-group")
public class ProfileProjection {

    private final IProfileService iProfileService;

    @EventHandler
    public void on(ProfileCreatedEvent profileCreatedEvent) {
        iProfileService.createProfile(profileCreatedEvent);
    }

    @EventHandler
    public void on(ProfileUpdatedEvent profileUpdatedEvent) {
        iProfileService.updateProfile(profileUpdatedEvent);
    }

    @EventHandler
    public void on(ProfileDeletedEvent profileDeletedEvent){
        iProfileService.deleteProfile(profileDeletedEvent);
    }
}

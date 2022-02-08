package com.projectilerage.runelite.partyplay;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.runelite.http.api.ws.messages.party.PartyMemberMessage;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
class ActivityInfo extends PartyMemberMessage
{
    private final String userId;
    private final String activity;
    private final String location;
}
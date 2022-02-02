package com.projectilerage.runelite.partyplay;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.runelite.http.api.ws.messages.party.PartyMemberMessage;

@Builder
@Value
@EqualsAndHashCode(callSuper = true)
class PartyStateInfo extends PartyMemberMessage
{
    private final String userId;
    private final String activity;
    private final String location;
}
package com.projectilerage.runelite.partyplay;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.runelite.client.party.messages.PartyMemberMessage;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
class ActivityInfo extends PartyMemberMessage
{
    private final String userId;
    private final String activity;
    private final String location;
}
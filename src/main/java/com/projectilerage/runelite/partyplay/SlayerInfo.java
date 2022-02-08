package com.projectilerage.runelite.partyplay;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.runelite.http.api.ws.messages.party.PartyMemberMessage;

@Data
@EqualsAndHashCode(callSuper = false)
class SlayerInfo  extends PartyMemberMessage {
    private SlayerTask slayerTask;
    private String location;
    private int initialAmount;
    private int amount;

    void reset() {
        slayerTask = null;
        location = null;
        initialAmount = 0;
        amount = 0;
    }
}

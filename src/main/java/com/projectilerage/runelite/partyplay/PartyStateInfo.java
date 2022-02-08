package com.projectilerage.runelite.partyplay;

import lombok.Data;

@Data
class PartyStateInfo {
    private ActivityInfo activityInfo;
    private SlayerInfo slayerInfo;

    PartyStateInfo(ActivityInfo activityInfo) {
        this.setActivityInfo(activityInfo);
    }

    PartyStateInfo(SlayerInfo slayerInfo) {
        this.setSlayerInfo(slayerInfo);
    }
}

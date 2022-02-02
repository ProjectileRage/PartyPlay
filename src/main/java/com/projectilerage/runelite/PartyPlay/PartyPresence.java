package com.projectilerage.runelite.PartyPlay;

import lombok.*;

@Setter
@Getter
@Builder(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
class PartyPresence {
    private String name;

    private String activity;
    private String area;


    private GameEventType activityEvent;
    private GameEventType areaEvent;

}

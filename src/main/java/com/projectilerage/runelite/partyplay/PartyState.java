/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2021, Jonathan Rousseau <https://github.com/JoRouss>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.projectilerage.runelite.partyplay;

import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.party.PartyMember;
import net.runelite.client.party.PartyService;
import net.runelite.client.party.WSClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * This class contains fundamentals from built-in Discord plugin's design and implementation.
 * Much of the credit goes to the creator(s) of that plugin.
*/
@Slf4j
@Singleton
class PartyState
{
    @Data
    private static class EventWithTime
    {
        private final GameEventType type;
        private Instant start;
        private Instant updated;
    }

    private final List<EventWithTime> events = Collections.synchronizedList(new LinkedList<>());
    private final PartyPlayConfig config;
    private final PartyPlayPlugin plugin;
    private final PartyService partyService;
    private final String defaultLocalPlayerName = "<unknown>";
    private final WSClient wsClient;

    private PartyPresence lastPresence = null;
    private String localPlayerName = defaultLocalPlayerName;

    @Inject
    private PartyState(
            final PartyPlayConfig config,
            final PartyPlayPlugin plugin,
            final PartyService partyService,
            final WSClient wsClient
            )
    {
        this.config = config;
        this.plugin = plugin;
        this.partyService = partyService;
        this.wsClient = wsClient;
    }

    void setLocalPlayerName(String name) {
        boolean shouldUpdate = false;
        if(Strings.isNullOrEmpty(name)) {
            if(this.localPlayerName.equals(this.defaultLocalPlayerName)) {
                log.debug("PPD:: setLocalPlayerName.null");
                if (this.partyService.getLocalMember() != null) {
                    log.debug("PPD:: setLocalPlayerName.memberId");
                    this.localPlayerName = String.valueOf(this.partyService.getLocalMember().getMemberId());
                } else {
                    log.debug("PPD:: setLocalPlayerName.default");
                    this.localPlayerName = defaultLocalPlayerName;
                }

                shouldUpdate = true;
            }
        } else {
            log.debug("PPD:: setLocalPlayerName.name");
            this.localPlayerName = name;
            shouldUpdate = true;
        }

        if(shouldUpdate) {
            log.debug("PPD:: setLocalPlayerName.shouldUpdate");
            if(lastPresence != null) {
                log.debug("PPD:: setLocalPlayerName.lastPresence");
                this.maybeSharePresence(lastPresence);
            } else {
                log.debug("PPD:: setLocalPlayerName.updatePresenceWithLatestEvent");
                this.updatePresenceWithLatestEvent();
            }
        }
    }

    /**
     * Reset state.
     */
    void reset()
    {
        log.debug("Resetting");
        events.clear();
        lastPresence = null;
        plugin.clearSlayerState();
    }

    void refresh() {
        log.debug("Refreshing: \n\t" + partyService.getLocalMember() + "\n\t" + lastPresence);
        maybeSharePresence(lastPresence, true);
    }

    /**
     * Trigger new discord state update.
     *
     * @param eventType discord event type
     */
    void triggerEvent(final GameEventType eventType)
    {
        if(eventType == null) {
            log.error("Tried to pass null event type");
            log.error(Thread.currentThread().getStackTrace().toString());
            return;
        }

        final Optional<EventWithTime> foundEvent = events.stream().filter(e -> e.type == eventType).findFirst();
        final EventWithTime event;

        if (foundEvent.isPresent())
        {
            event = foundEvent.get();
        }
        else
        {
            event = new EventWithTime(eventType);
            event.setStart(Instant.now());
            events.add(event);
        }

        event.setUpdated(Instant.now());

        if (event.getType().isShouldClear())
        {
            log.debug("Clearing events");
            events.removeAll(events.parallelStream().filter(e -> e.getType() != eventType && e.getType().isShouldBeCleared()).collect(Collectors.toList()));
        }

        if (event.getType().isShouldRestart())
        {
            event.setStart(Instant.now());
        }

        events.sort((a, b) -> ComparisonChain.start()
                .compare(b.getType().getPriority(), a.getType().getPriority())
                .compare(b.getUpdated(), a.getUpdated())
                .result());

        updatePresenceWithLatestEvent();
    }

    private void updatePresenceWithLatestEvent()
    {
        if (events.isEmpty())
        {
            reset();
            return;
        }

        final EventWithTime event = events.get(0);

        String activity = null;
        String area = null;
        GameEventType activityEvent = null;
        GameEventType areaEvent = null;


        for (EventWithTime eventWithTime : events)
        {
            GameEventType eventType = eventWithTime.getType();

            if(activity == null) {
                if(eventType.getAreaType() == null) {
                    if(eventType.getState() != null) {
                        activity = eventType.getState();
                    } else {
                        activity = eventType.getDetails();
                    }

                    activityEvent = eventType;
                }
            }

            if(area == null) {
                if(eventType.getAreaType() != null) {
                    area = eventType.getState();
                    areaEvent = eventType;
                }
            }

            if(activity != null && area != null) {
                break;
            }
        }

        final PartyPresence presence = PartyPresence.builder()
                .activity(activity)
                .area(area)
                .activityEvent(activityEvent)
                .areaEvent(areaEvent)
                .build();

        maybeSharePresence(presence);
        lastPresence = presence;
    }

    private void maybeSharePresence(final PartyPresence presence) {
        maybeSharePresence(presence, false);
    }

    private void maybeSharePresence(final PartyPresence presence, boolean force)
    {
        if(presence == null) {
            return;
        }
        // This is to reduce amount of RPC calls
        if (force || !presence.equals(lastPresence) || !presence.getName().equals(this.localPlayerName))
        {
            PartyMember localMember = partyService.getLocalMember();
            if(localMember != null) {
                ActivityInfo.ActivityInfoBuilder infoBuild = ActivityInfo.builder();

                if(presence.getActivityEvent() == GameEventType.IN_MENU) {
                    if(config.showMainMenu()) {
                        infoBuild.activity(presence.getActivity());
                    }
                } else {
                    if(config.showSkillingActivity()) {
                        infoBuild.activity(presence.getActivity());
                    } else {
                        infoBuild.activity(GameEventType.IN_GAME.getState());
                    }
                }


                boolean showAreaType = false;

                if(presence.getAreaEvent() != null) {
                    switch(presence.getAreaEvent().getAreaType()) {
                        case RAIDS:
                            if(config.showRaidingActivity())
                                showAreaType = true;
                            break;
                        case BOSSES:
                            if(config.showBossActivity())
                                showAreaType = true;
                            break;
                        case CITIES:
                            if(config.showCityActivity())
                                showAreaType = true;
                            break;
                        case REGIONS:
                            if(config.showRegionsActivity())
                                showAreaType = true;
                            break;
                        case DUNGEONS:
                            if(config.showDungeonActivity())
                                showAreaType = true;
                            break;
                        case MINIGAMES:
                            if(config.showMinigameActivity())
                                showAreaType = true;
                            break;
                    }
                }

                if(showAreaType) {
                    infoBuild.location(presence.getArea());
                } else {
                    infoBuild.location("Gielinor");
                }

                String name = this.localPlayerName;
                infoBuild.userId(name);
                presence.setName(name);

                ActivityInfo info = infoBuild.build();
                info.setMemberId(localMember.getMemberId());
                partyService.send(info);
                plugin.setActivityInfo(info);
            }
        }
    }

    /**
     * Check for current state timeout and act upon it.
     */
    void checkForTimeout()
    {
        if (events.isEmpty())
        {
            log.debug("PPD:: Events is empty. Exiting timeouts check");
            return;
        }

        log.debug("PPD:: Checking for timeouts");

        final Duration actionTimeout = Duration.ofMinutes(config.actionTimeout());
        final Instant now = Instant.now();
        int initalLength = events.size();

        List<EventWithTime> eventsToRemove = events.parallelStream()
                // Find only events that should time out
                .filter(event -> event.getType().isShouldTimeout() && now.isAfter(event.getUpdated().plus(actionTimeout)))
                .collect(Collectors.toList());

        if(!eventsToRemove.isEmpty()) {
            events.removeAll(eventsToRemove);
            log.debug(events.size() - initalLength + " events removed. Updating");
            updatePresenceWithLatestEvent();
            if(eventsToRemove.parallelStream().anyMatch((event) -> event.getType() == GameEventType.TRAINING_SLAYER)) {
                log.debug("PPD:: Slayer event timed-out. Triggering slayer removal");
                plugin.clearSlayerState();
            }
        }
    }

    boolean containsEventType(GameEventType eventType) {
        return events.parallelStream().anyMatch((e) -> e.getType() == eventType);
    }
}

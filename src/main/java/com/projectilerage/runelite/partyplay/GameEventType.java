/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2018, PandahRS <https://github.com/PandahRS>
 * Copyright (c) 2020, Brooklyn <https://github.com/Broooklyn>
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

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.Skill;

import javax.annotation.Nullable;
import java.util.Map;

/*
Entire file copied from built-in Discord plugin. All credit goes to the creator(s).
*/

@AllArgsConstructor
@Getter
enum GameEventType
{

	IN_MENU("In Menu", -3, true, true, true, false, true),
	IN_GAME("In Game", -3, true, false, false, false, true),
	PLAYING_DEADMAN("Playing Deadman Mode", -3),
	PLAYING_PVP("Playing in a PVP world", -3),
	TRAINING_ATTACK(Skill.ATTACK),
	TRAINING_DEFENCE(Skill.DEFENCE),
	TRAINING_STRENGTH(Skill.STRENGTH),
	TRAINING_HITPOINTS(Skill.HITPOINTS, -1),
	TRAINING_SLAYER(Skill.SLAYER, 1),
	TRAINING_RANGED(Skill.RANGED),
	TRAINING_MAGIC(Skill.MAGIC),
	TRAINING_PRAYER(Skill.PRAYER),
	TRAINING_COOKING(Skill.COOKING),
	TRAINING_WOODCUTTING(Skill.WOODCUTTING),
	TRAINING_FLETCHING(Skill.FLETCHING),
	TRAINING_FISHING(Skill.FISHING, 1),
	TRAINING_FIREMAKING(Skill.FIREMAKING),
	TRAINING_CRAFTING(Skill.CRAFTING),
	TRAINING_SMITHING(Skill.SMITHING),
	TRAINING_MINING(Skill.MINING),
	TRAINING_HERBLORE(Skill.HERBLORE),
	TRAINING_AGILITY(Skill.AGILITY),
	TRAINING_THIEVING(Skill.THIEVING),
	TRAINING_FARMING(Skill.FARMING),
	TRAINING_RUNECRAFT(Skill.RUNECRAFT),
	TRAINING_HUNTER(Skill.HUNTER),
	TRAINING_CONSTRUCTION(Skill.CONSTRUCTION),

	// Bosses
	BOSS_ABYSSAL_SIRE("Abyssal Sire", AreaType.BOSSES, 11851, 11850, 12363, 12362),
	BOSS_CERBERUS("Cerberus", AreaType.BOSSES, 4883, 5140, 5395),
	BOSS_COMMANDER_ZILYANA("Commander Zilyana", AreaType.BOSSES, 11602),
	BOSS_CORP("Corporeal Beast", AreaType.BOSSES, 11842, 11844),
	BOSS_DKS("Dagannoth Kings", AreaType.BOSSES, 11588, 11589),
	BOSS_GENERAL_GRAARDOR("General Graardor", AreaType.BOSSES, 11347),
	BOSS_GIANT_MOLE("Giant Mole", AreaType.BOSSES, 6993, 6992),
	BOSS_GROTESQUE_GUARDIANS("Grotesque Guardians", AreaType.BOSSES, 6727),
	BOSS_HESPORI("Hespori", AreaType.BOSSES, 5021),
	BOSS_HYDRA("Alchemical Hydra", AreaType.BOSSES, 5536),
	BOSS_KQ("Kalphite Queen", AreaType.BOSSES, 13972),
	BOSS_KRAKEN("Kraken", AreaType.BOSSES, 9116),
	BOSS_KREEARRA("Kree'arra", AreaType.BOSSES, 11346),
	BOSS_KRIL_TSUTSAROTH("K'ril Tsutsaroth", AreaType.BOSSES, 11603),
	BOSS_NEX("Nex", AreaType.BOSSES, 11601),
	BOSS_NIGHTMARE("Nightmare of Ashihama", AreaType.BOSSES, 15515),
	BOSS_SARACHNIS("Sarachnis", AreaType.BOSSES, 7322),
	BOSS_SKOTIZO("Skotizo", AreaType.BOSSES, 6810),
	BOSS_SMOKE_DEVIL("Thermonuclear smoke devil", AreaType.BOSSES, 9363, 9619),
	BOSS_TEMPOROSS("Tempoross", AreaType.BOSSES, 12078),
	BOSS_VORKATH("Vorkath", AreaType.BOSSES, 9023),
	BOSS_WINTERTODT("Wintertodt", AreaType.BOSSES, 6462),
	BOSS_ZALCANO("Zalcano", AreaType.BOSSES, 12126),
	BOSS_ZULRAH("Zulrah", AreaType.BOSSES, 9007),

	// Cities
	CITY_AL_KHARID("Al Kharid" , AreaType.CITIES, 13105, 13106),
	CITY_ARCEUUS_HOUSE("Arceuus" , AreaType.CITIES, 6458, 6459, 6460, 6714, 6715),
	CITY_ARDOUGNE("Ardougne" , AreaType.CITIES, 9779, 9780, 10035, 10036, 10291, 10292, 10547, 10548),
	CITY_BANDIT_CAMP("Bandit Camp" , AreaType.CITIES, 12590),
	CITY_BARBARIAN_OUTPOST("Barbarian Outpost", AreaType.CITIES, 10039),
	CITY_BARBARIAN_VILLAGE("Barbarian Village" , AreaType.CITIES, 12341),
	CITY_BEDABIN_CAMP("Bedabin Camp" , AreaType.CITIES, 12591),
	CITY_BRIMHAVEN("Brimhaven" , AreaType.CITIES, 11057, 11058),
	CITY_BURGH_DE_ROTT("Burgh de Rott" , AreaType.CITIES, 13874, 13873, 14130, 14129),
	CITY_BURTHORPE("Burthorpe" , AreaType.CITIES, 11319, 11575),
	CITY_CANIFIS("Canifis" , AreaType.CITIES, 13878),
	CITY_CATHERBY("Catherby" , AreaType.CITIES, 11317, 11318, 11061),
	CITY_CORSAIR_COVE("Corsair Cove" , AreaType.CITIES, 10028, 10284),
	CITY_DARKMEYER("Darkmeyer", AreaType.CITIES, 14388, 14644),
	CITY_DORGESH_KAAN("Dorgesh-Kaan" , AreaType.CITIES, 10835, 10834),
	CITY_DRAYNOR("Draynor" , AreaType.CITIES, 12338, 12339),
	CITY_EDGEVILLE("Edgeville" , AreaType.CITIES, 12342),
	CITY_ENTRANA("Entrana" , AreaType.CITIES, 11060, 11316),
	CITY_ETCETERIA("Etceteria", AreaType.CITIES, 10300),
	CITY_FALADOR("Falador" , AreaType.CITIES, 11828, 11572, 11827, 12084),
	CITY_GUTANOTH("Gu'Tanoth" , AreaType.CITIES, 10031),
	CITY_GWENITH("Gwenith", AreaType.CITIES, 8757),
	CITY_HOSIDIUS_HOUSE("Hosidius" , AreaType.CITIES, 6710, 6711, 6712, 6455, 6456, 6966, 6967, 6968, 7221, 7223, 7224, 7478, 7479),
	CITY_JATIZSO("Jatizso" , AreaType.CITIES, 9531),
	CITY_KELDAGRIM("Keldagrim" , AreaType.CITIES, 11423, 11422, 11679, 11678),
	CITY_LANDS_END("Land's End", AreaType.CITIES, 5941),
	CITY_LLETYA("Lletya" , AreaType.CITIES, 9265, 11103),
	CITY_LOVAKENGJ_HOUSE("Lovakengj" , AreaType.CITIES, 5692, 5691, 5947, 6203, 6202, 5690, 5946),
	CITY_LUMBRIDGE("Lumbridge" , AreaType.CITIES, 12850),
	CITY_LUNAR_ISLE("Lunar Isle" , AreaType.CITIES, 8253, 8252, 8509, 8508),
	CITY_MARIM("Marim", AreaType.REGIONS, 11051),
	CITY_MEIYERDITCH("Meiyerditch" , AreaType.CITIES, 14132, 14387, 14386, 14385),
	CITY_MENAPHOS("Menaphos", AreaType.CITIES, 12843),
	CITY_MISCELLANIA("Miscellania" , AreaType.CITIES, 10044),
	CITY_MOR_UL_REK("Mor Ul Rek" , AreaType.CITIES, 9808, 9807, 10064, 10063),
	CITY_MORTTON("Mort'ton" , AreaType.CITIES, 13875),
	CITY_MOS_LE_HARMLESS("Mos Le'Harmless" , AreaType.CITIES, 14637, 14638, 14639, 14894, 14895, 15151, 15406, 15407),
	CITY_MOUNT_KARUULM("Mount Karuulm", AreaType.CITIES, 5179, 4923, 5180),
	CITY_MOUNTAIN_CAMP("Mountain Camp", AreaType.CITIES, 11065),
	CITY_MYNYDD("Mynydd", AreaType.CITIES, 8501),
	CITY_NARDAH("Nardah" , AreaType.CITIES, 13613),
	CITY_NEITIZNOT("Neitiznot" , AreaType.CITIES, 9275),
	CITY_PISCARILIUS_HOUSE("Port Piscarilius" , AreaType.CITIES, 6969, 6971, 7227, 6970, 7225, 7226),
	CITY_PISCATORIS("Piscatoris" , AreaType.CITIES, 9273),
	CITY_POLLNIVNEACH("Pollnivneach" , AreaType.CITIES, 13358),
	CITY_PORT_KHAZARD("Port Khazard" , AreaType.CITIES, 10545),
	CITY_PORT_PHASMATYS("Port Phasmatys" , AreaType.CITIES, 14646),
	CITY_PORT_SARIM("Port Sarim" , AreaType.CITIES, 12081, 12082),
	CITY_PRIFDDINAS("Prifddinas", AreaType.CITIES, 8499, 8500, 8755, 8756, 9011, 9012, 9013, 12894, 12895, 13150, 13151),
	CITY_RELLEKKA("Rellekka" , AreaType.CITIES, 10297, 10553),
	CITY_RIMMINGTON("Rimmington" , AreaType.CITIES, 11826, 11570),
	CITY_SEERS_VILLAGE("Seers' Village" , AreaType.CITIES, 10806),
	CITY_SHAYZIEN_HOUSE("Shayzien" , AreaType.CITIES, 5944, 5943, 6200, 6199, 5686, 5687, 5688, 5689, 5945),
	CITY_SHILO_VILLAGE("Shilo Village" , AreaType.CITIES, 11310),
	CITY_SLEPE("Slepe", AreaType.CITIES, 14643, 14899, 14900, 14901),
	CITY_SOPHANEM("Sophanem" , AreaType.CITIES, 13099),
	CITY_TAI_BWO_WANNAI("Tai Bwo Wannai" , AreaType.CITIES, 11056, 11055),
	CITY_TAVERLEY("Taverley" , AreaType.CITIES, 11574, 11573),
	CITY_TREE_GNOME_STRONGHOLD("Tree Gnome Stronghold" , AreaType.CITIES, 9525, 9526, 9782, 9781),
	CITY_TREE_GNOME_VILLAGE("Tree Gnome Village" , AreaType.CITIES, 10033),
	CITY_TROLL_STRONGHOLD("Troll Stronghold" , AreaType.CITIES, 11321, 11421),
	CITY_UZER("Uzer" , AreaType.CITIES, 13872),
	CITY_VARROCK("Varrock" , AreaType.CITIES, 12596, 12597, 12852, 12853, 12854, 13108, 13109, 13110),
	CITY_VER_SINHAZA("Ver Sinhaza", AreaType.CITIES, 14642),
	CITY_VOID_OUTPOST("Void Knights' Outpost", AreaType.CITIES, 10537),
	CITY_WEISS("Weiss", AreaType.CITIES, 11325, 11581),
	CITY_WITCHHAVEN("Witchaven" , AreaType.CITIES, 10803),
	CITY_YANILLE("Yanille" , AreaType.CITIES, 10288, 10032),
	CITY_ZANARIS("Zanaris" , AreaType.CITIES, 9285, 9541, 9540, 9797),
	CITY_ZULANDRA("Zul-Andra" , AreaType.CITIES, 8495, 8751),

	// Dungeons
	DUNGEON_ABANDONED_MINE("Abandoned Mine", AreaType.DUNGEONS, 13618, 13718, 11079, 11078, 11077, 10823, 10822, 10821),
	DUNGEON_AH_ZA_RHOON("Ah Za Rhoon", AreaType.DUNGEONS, 11666),
	DUNGEON_ANCIENT_CAVERN("Ancient Cavern", AreaType.DUNGEONS, 6483, 6995),
	DUNGEON_APE_ATOLL("Ape Atoll Dungeon", AreaType.DUNGEONS, 11150, 10894),
	DUNGEON_APE_ATOLL_BANANA_PLANTATION("Ape Atoll Banana Plantation", AreaType.DUNGEONS, 10895),
	DUNGEON_ARDY_BASEMENT("West Ardougne Basement", AreaType.DUNGEONS, 10135),
	DUNGEON_ARDY_SEWERS("Ardougne Sewers", AreaType.DUNGEONS, 10134, 10136, 10391, 10647),
	DUNGEON_ASGARNIAN_ICE_CAVES("Asgarnian Ice Caves", AreaType.DUNGEONS, 11925, 12181),
	DUNGEON_BERVIRIUS_TOMB("Tomb of Bervirius", AreaType.DUNGEONS, 11154),
	DUNGEON_BRIMHAVEN("Brimhaven Dungeon", AreaType.DUNGEONS, 10901, 10900, 10899, 10645, 10644, 10643),
	DUNGEON_BRINE_RAT_CAVERN("Brine Rat Cavern", AreaType.DUNGEONS, 10910),
	DUNGEON_CATACOMBS_OF_KOUREND("Catacombs of Kourend", AreaType.DUNGEONS, 6557, 6556, 6813, 6812),
	DUNGEON_CHAMPIONS_CHALLENGE("Champions' Challenge", AreaType.DUNGEONS, 12696),
	DUNGEON_CHAOS_DRUID_TOWER("Chaos Druid Tower", AreaType.DUNGEONS, 10392),
	DUNGEON_CHASM_OF_FIRE("Chasm of Fire", AreaType.DUNGEONS, 5789),
	DUNGEON_CHASM_OF_TEARS("Chasm of Tears", AreaType.DUNGEONS, 12948),
	DUNGEON_CHINCHOMPA("Chinchompa Hunting Ground", AreaType.DUNGEONS, 10129),
	DUNGEON_CLOCK_TOWER("Clock Tower Basement", AreaType.DUNGEONS, 10390),
	DUNGEON_CORSAIR_COVE("Corsair Cove Dungeon", AreaType.DUNGEONS, 8076, 8332),
	DUNGEON_CRABCLAW_CAVES("Crabclaw Caves", AreaType.DUNGEONS, 6553, 6809),
	DUNGEON_CRANDOR("Crandor Dungeon", AreaType.DUNGEONS, 11414),
	DUNGEON_CRASH_SITE_CAVERN("Crash Site Cavern", AreaType.DUNGEONS, 8280, 8536),
	DUNGEON_CRUMBLING_TOWER("Crumbling Tower", AreaType.DUNGEONS, 7827),
	DUNGEON_DAEYALT_ESSENCE_MINE("Daeyalt Essence Mine", AreaType.DUNGEONS, 14744),
	DUNGEON_DIGSITE("Digsite Dungeon", AreaType.DUNGEONS, 13464, 13465),
	DUNGEON_DORGESHKAAN("Dorgesh-Kaan South Dungeon", AreaType.DUNGEONS, 10833),
	DUNGEON_DORGESHUUN_MINES("Dorgeshuun Mines", AreaType.DUNGEONS, 12950, 13206),
	DUNGEON_DRAYNOR_SEWERS("Draynor Sewers", AreaType.DUNGEONS, 12439, 12438),
	DUNGEON_DWARVEN_MINES("Dwarven Mines", AreaType.DUNGEONS, 12185, 12184, 12183),
	DUNGEON_EAGLES_PEAK("Eagles' Peak Dungeon", AreaType.DUNGEONS, 8013),
	DUNGEON_ECTOFUNTUS("Ectofuntus", AreaType.DUNGEONS, 14746),
	DUNGEON_EDGEVILLE("Edgeville Dungeon", AreaType.DUNGEONS, 12441, 12442, 12443, 12698),
	DUNGEON_ELEMENTAL_WORKSHOP("Elemental Workshop", AreaType.DUNGEONS, 10906, 7760),
	DUNGEON_ELVEN_RABBIT_CAVE("Elven rabbit cave", AreaType.DUNGEONS, 13252),
	DUNGEON_ENAKHRAS_TEMPLE("Enakhra's Temple", AreaType.DUNGEONS, 12423),
	DUNGEON_EVIL_CHICKENS_LAIR("Evil Chicken's Lair", AreaType.DUNGEONS, 9796),
	DUNGEON_EXPERIMENT_CAVE("Experiment Cave", AreaType.DUNGEONS, 14235, 13979),
	DUNGEON_FEROX_ENCLAVE("Ferox Enclave Dungeon", AreaType.DUNGEONS, 12700),
	DUNGEON_FORTHOS("Forthos Dungeon", AreaType.DUNGEONS, 7323),
	DUNGEON_FREMENNIK_SLAYER("Fremennik Slayer Dungeon", AreaType.DUNGEONS, 10907, 10908, 11164),
	DUNGEON_GLARIALS_TOMB("Glarial's Tomb", AreaType.DUNGEONS, 10137),
	DUNGEON_GOBLIN_CAVE("Goblin Cave", AreaType.DUNGEONS, 10393),
	DUNGEON_GRAND_TREE_TUNNELS("Grand Tree Tunnels", AreaType.DUNGEONS, 9882),
	DUNGEON_HAM_HIDEOUT("H.A.M. Hideout", AreaType.DUNGEONS, 12694),
	DUNGEON_HAM_STORE_ROOM("H.A.M. Store room", AreaType.DUNGEONS, 10321),
	DUNGEON_HEROES_GUILD("Heroes' Guild Mine", AreaType.DUNGEONS, 11674),
	DUNGEON_IORWERTH("Iorwerth Dungeon", AreaType.DUNGEONS, 12737, 12738, 12993, 12994),
	DUNGEON_ISLE_OF_SOULS("Isle of Souls Dungeon", AreaType.DUNGEONS, 8593),
	DUNGEON_JATIZSO_MINES("Jatizso Mines", AreaType.DUNGEONS, 9631),
	DUNGEON_JIGGIG_BURIAL_TOMB("Jiggig Burial Tomb", AreaType.DUNGEONS, 9875, 9874),
	DUNGEON_JOGRE("Jogre Dungeon", AreaType.DUNGEONS, 11412),
	DUNGEON_KARAMJA("Karamja Dungeon", AreaType.DUNGEONS, 11413),
	DUNGEON_KARUULM("Karuulm Slayer Dungeon", AreaType.DUNGEONS, 5280, 5279, 5023, 5535, 5022, 4766, 4510, 4511, 4767, 4768, 4512),
	DUNGEON_KGP_HEADQUARTERS("KGP Headquarters", AreaType.DUNGEONS, 10658),
	DUNGEON_KRUK("Kruk's Dungeon", AreaType.DUNGEONS, 9358, 9359, 9360, 9615, 9616, 9871, 10125, 10126, 10127, 10128, 10381, 10382, 10383, 10384, 10637, 10638, 10639, 10640),
	DUNGEON_LEGENDS_GUILD("Legends' Guild Dungeon", AreaType.DUNGEONS, 10904),
	DUNGEON_LIGHTHOUSE("Lighthouse", AreaType.DUNGEONS, 10140),
	DUNGEON_LIZARDMAN_CAVES("Lizardman Caves", AreaType.DUNGEONS, 5275),
	DUNGEON_LIZARDMAN_TEMPLE("Lizardman Temple", AreaType.DUNGEONS, 5277),
	DUNGEON_LUMBRIDGE_SWAMP_CAVES("Lumbridge Swamp Caves", AreaType.DUNGEONS, 12693, 12949),
	DUNGEON_LUNAR_ISLE_MINE("Lunar Isle Mine", AreaType.DUNGEONS, 9377),
	DUNGEON_MANIACAL_HUNTER("Maniacal Monkey Hunter Area", AreaType.DUNGEONS, 11662),
	DUNGEON_MEIYERDITCH_MINE("Meiyerditch Mine", AreaType.DUNGEONS, 9544),
	DUNGEON_MISCELLANIA("Miscellania Dungeon", AreaType.DUNGEONS, 10144, 10400),
	DUNGEON_MOGRE_CAMP("Mogre Camp", AreaType.DUNGEONS, 11924),
	DUNGEON_MOS_LE_HARMLESS_CAVES("Mos Le'Harmless Caves", AreaType.DUNGEONS, 14994, 14995, 15251),
	DUNGEON_MOTHERLODE_MINE("Motherlode Mine", AreaType.DUNGEONS, 14679, 14680, 14681, 14935, 14936, 14937, 15191, 15192, 15193),
	DUNGEON_MOURNER_TUNNELS("Mourner Tunnels", AreaType.DUNGEONS, 7752, 8008),
	DUNGEON_MOUSE_HOLE("Mouse Hole", AreaType.DUNGEONS, 9046),
	DUNGEON_MYREDITCH_LABORATORIES("Myreditch Laboratories", AreaType.DUNGEONS, 14232, 14233, 14487, 14488),
	DUNGEON_MYREQUE("Myreque Hideout", AreaType.DUNGEONS, 13721, 13974, 13977, 13978),
	DUNGEON_MYTHS_GUILD("Myths' Guild Dungeon", AreaType.DUNGEONS, 7564, 7820, 7821),
	DUNGEON_OBSERVATORY("Observatory Dungeon", AreaType.DUNGEONS, 9362),
	DUNGEON_OGRE_ENCLAVE("Ogre Enclave", AreaType.DUNGEONS, 10387),
	DUNGEON_OURANIA("Ourania Cave", AreaType.DUNGEONS, 12119),
	DUNGEON_QUIDAMORTEM_CAVE("Quidamortem Cave", AreaType.DUNGEONS, 4763),
	DUNGEON_RASHILIYIAS_TOMB("Rashiliyta's Tomb", AreaType.DUNGEONS, 11668),
	DUNGEON_RUINS_OF_CAMDOZAAL("Ruins of Camdozaal", AreaType.DUNGEONS, 11609, 11610, 11611, 11865, 11866, 11867, 12121, 12122, 12123),
	DUNGEON_SALT_MINE("Salt Mine", AreaType.DUNGEONS, 11425),
	DUNGEON_SARADOMINSHRINE("Saradomin Shrine (Paterdomus)", AreaType.DUNGEONS, 13722),
	DUNGEON_SHADE_CATACOMBS("Shade Catacombs", AreaType.DUNGEONS, 13975),
	DUNGEON_SHADOW("Shadow Dungeon", AreaType.DUNGEONS, 10575, 10831),
	DUNGEON_SHAYZIEN_CRYPTS("Shayzien Crypts", AreaType.DUNGEONS, 6043),
	DUNGEON_SISTERHOOD_SANCTUARY("Sisterhood Sanctuary", AreaType.DUNGEONS, 14999, 15000, 15001, 15255, 15256, 15257, 15511, 15512, 15513),
	DUNGEON_SMOKE("Smoke Dungeon", AreaType.DUNGEONS, 12946, 13202),
	DUNGEON_SOPHANEM("Sophanem Dungeon", AreaType.DUNGEONS, 13200),
	DUNGEON_SOURHOG_CAVE("Sourhog Cave", AreaType.DUNGEONS, 12695),
	DUNGEON_STRONGHOLD_SECURITY("Stronghold of Security", AreaType.DUNGEONS, 7505, 8017, 8530, 9297),
	DUNGEON_STRONGHOLD_SLAYER("Stronghold Slayer Cave", AreaType.DUNGEONS, 9624, 9625, 9880, 9881),
	DUNGEON_TARNS_LAIR("Tarn's Lair", AreaType.DUNGEONS, 12616, 12615),
	DUNGEON_TAVERLEY("Taverley Dungeon", AreaType.DUNGEONS, 11416, 11417, 11671, 11672, 11673, 11928, 11929),
	DUNGEON_TEMPLE_OF_IKOV("Temple of Ikov", AreaType.DUNGEONS, 10649, 10905, 10650),
	DUNGEON_TEMPLE_OF_LIGHT("Temple of Light", AreaType.DUNGEONS, 7496),
	DUNGEON_TEMPLE_OF_MARIMBO("Temple of Marimbo", AreaType.DUNGEONS, 11151),
	DUNGEON_THE_WARRENS("The Warrens", AreaType.DUNGEONS, 7070, 7326),
	DUNGEON_TOLNA("Dungeon of Tolna", AreaType.DUNGEONS, 13209),
	DUNGEON_TOWER_OF_LIFE("Tower of Life Basement", AreaType.DUNGEONS, 12100),
	DUNGEON_TRAHAEARN_MINE("Trahaearn Mine", AreaType.DUNGEONS, 13250),
	DUNGEON_TUNNEL_OF_CHAOS("Tunnel of Chaos", AreaType.DUNGEONS, 12625),
	DUNGEON_UNDERGROUND_PASS("Underground Pass", AreaType.DUNGEONS, 9369, 9370),
	DUNGEON_VARROCKSEWERS("Varrock Sewers", AreaType.DUNGEONS, 12954, 13210),
	DUNGEON_VIYELDI_CAVES("Viyeldi Caves", AreaType.DUNGEONS, 9545, 11153),
	DUNGEON_WARRIORS_GUILD("Warriors' Guild Basement", AreaType.DUNGEONS, 11675),
	DUNGEON_WATER_RAVINE("Water Ravine", AreaType.DUNGEONS, 13461),
	DUNGEON_WATERBIRTH("Waterbirth Dungeon", AreaType.DUNGEONS, 9886, 10142, 7492, 7748),
	DUNGEON_WATERFALL("Waterfall Dungeon", AreaType.DUNGEONS, 10394),
	DUNGEON_WEREWOLF_AGILITY("Werewolf Agility Course", AreaType.DUNGEONS, 14234),
	DUNGEON_WHITE_WOLF_MOUNTAIN_CAVES("White Wolf Mountain Caves", AreaType.DUNGEONS, 11418, 11419),
	DUNGEON_WITCHAVEN_SHRINE("Witchhaven Shrine Dungeon", AreaType.DUNGEONS, 10903),
	DUNGEON_WIZARDS_TOWER("Wizards' Tower Basement", AreaType.DUNGEONS, 12437),
	DUNGEON_WOODCUTTING_GUILD("Woodcutting Guild Dungeon", AreaType.DUNGEONS, 6298),
	DUNGEON_WYVERN_CAVE("Wyvern Cave", AreaType.DUNGEONS, 14495, 14496),
	DUNGEON_YANILLE_AGILITY("Yanille Agility Dungeon", AreaType.DUNGEONS, 10388),

	// Minigames
	MG_ARDOUGNE_RAT_PITS("Ardougne Rat Pits", AreaType.MINIGAMES, 10646),
	MG_BARBARIAN_ASSAULT("Barbarian Assault", AreaType.MINIGAMES, 7508, 7509, 10322),
	MG_BARROWS("Barrows", AreaType.MINIGAMES, 14131, 14231),
	MG_BLAST_FURNACE("Blast Furnace", AreaType.MINIGAMES, 7757),
	MG_BRIMHAVEN_AGILITY_ARENA("Brimhaven Agility Arena", AreaType.MINIGAMES, 11157),
	MG_BURTHORPE_GAMES_ROOM("Burthorpe Games Room", AreaType.MINIGAMES, 8781),
	MG_CASTLE_WARS("Castle Wars", AreaType.MINIGAMES, 9520, 9620),
	MG_CLAN_WARS("Clan Wars", AreaType.MINIGAMES, 12621, 12622, 12623, 13130, 13131, 13133, 13134, 13135, 13386, 13387, 13390, 13641, 13642, 13643, 13644, 13645, 13646, 13647, 13899, 13900, 14155, 14156),
	MG_DUEL_ARENA("Duel Arena", AreaType.MINIGAMES, 13362, 13363),
	MG_FISHING_TRAWLER("Fishing Trawler", AreaType.MINIGAMES, 7499),
	MG_GAUNTLET("The Gauntlet", AreaType.MINIGAMES, 12127, 7512),
	MG_CORRUPTED_GAUNTLET("Corrupted Gauntlet", AreaType.MINIGAMES, 7768),
	MG_HALLOWED_SEPULCHRE("Hallowed Sepulchre", AreaType.MINIGAMES, 8797, 9051, 9052, 9053, 9054, 9309, 9563, 9565, 9821, 10074, 10075, 10077),
	MG_INFERNO("The Inferno", AreaType.MINIGAMES, 9043),
	MG_KELDAGRIM_RAT_PITS("Keldagrim Rat Pits", AreaType.MINIGAMES, 7753),
	MG_LAST_MAN_STANDING_DESERTED_ISLAND("LMS - Deserted Island", AreaType.MINIGAMES, 13658, 13659, 13660, 13914, 13915, 13916),
	MG_LAST_MAN_STANDING_WILD_VARROCK("LMS - Wild Varrock", AreaType.MINIGAMES, 13918, 13919, 13920, 14174, 14175, 14176, 14430, 14431, 14432),
	MG_MAGE_TRAINING_ARENA("Mage Training Arena", AreaType.MINIGAMES, 13462, 13463),
	MG_NIGHTMARE_ZONE("Nightmare Zone", AreaType.MINIGAMES, 9033),
	MG_PEST_CONTROL("Pest Control", AreaType.MINIGAMES, 10536),
	MG_PORT_SARIM_RAT_PITS("Port Sarim Rat Pits", AreaType.MINIGAMES, 11926),
	MG_PYRAMID_PLUNDER("Pyramid Plunder", AreaType.MINIGAMES, 7749),
	MG_ROGUES_DEN("Rogues' Den", AreaType.MINIGAMES, 11854, 11855, 12109, 12110, 12111),
	MG_SORCERESS_GARDEN("Sorceress's Garden", AreaType.MINIGAMES, 11605),
	MG_SOUL_WARS("Soul Wars", AreaType.MINIGAMES, 8493, 8748, 8749, 9005),
	MG_TEMPLE_TREKKING("Temple Trekking", AreaType.MINIGAMES, 8014, 8270, 8256, 8782, 9038, 9294, 9550, 9806),
	MG_TITHE_FARM("Tithe Farm", AreaType.MINIGAMES, 7222),
	MG_TROUBLE_BREWING("Trouble Brewing", AreaType.MINIGAMES, 15150),
	MG_TZHAAR_FIGHT_CAVES("Tzhaar Fight Caves", AreaType.MINIGAMES, 9551),
	MG_TZHAAR_FIGHT_PITS("Tzhaar Fight Pits", AreaType.MINIGAMES, 9552),
	MG_VARROCK_RAT_PITS("Varrock Rat Pits", AreaType.MINIGAMES, 11599),
	MG_VOLCANIC_MINE("Volcanic Mine", AreaType.MINIGAMES, 15263, 15262),
	MG_GUARDIANS_OF_THE_RIFT("Guardians of the Rift", AreaType.MINIGAMES, 14484),

	// Raids
	RAIDS_CHAMBERS_OF_XERIC("Chambers of Xeric", AreaType.RAIDS, 12889, 13136, 13137, 13138, 13139, 13140, 13141, 13145, 13393, 13394, 13395, 13396, 13397, 13401),
	RAIDS_THEATRE_OF_BLOOD("Theatre of Blood", AreaType.RAIDS, 12611, 12612, 12613, 12867, 12869, 13122, 13123, 13125, 13379),

	// Other
	REGION_ABYSSAL_AREA("Abyssal Area", AreaType.REGIONS, 12108),
	REGION_ABYSSAL_NEXUS("Abyssal Nexus", AreaType.REGIONS, 12106),
	REGION_AGILITY_PYRAMID("Agility Pyramid", AreaType.REGIONS, 12105, 13356),
	REGION_AIR_ALTAR("Air Altar", AreaType.REGIONS, 11339),
	REGION_AL_KHARID_MINE("Al Kharid Mine", AreaType.REGIONS, 13107),
	REGION_APE_ATOLL("Ape Atoll" , AreaType.REGIONS, 10794, 10795, 10974, 11050),
	REGION_ARANDAR("Arandar", AreaType.REGIONS, 9266, 9267, 9523),
	REGION_ASGARNIA("Asgarnia", AreaType.REGIONS, 11825, 11829, 11830, 12085, 12086),
	REGION_BATTLEFIELD("Battlefield", AreaType.REGIONS, 10034),
	REGION_BATTLEFRONT("Battlefront", AreaType.REGIONS, 5433, 5434),
	REGION_BLAST_MINE("Blast Mine", AreaType.REGIONS, 5948),
	REGION_BODY_ALTAR("Body Altar", AreaType.REGIONS, 10059),
	REGION_CHAOS_ALTAR("Chaos Altar", AreaType.REGIONS, 9035),
	REGION_COSMIC_ALTAR("Cosmic Altar", AreaType.REGIONS, 8523),
	REGION_COSMIC_ENTITYS_PLANE("Cosmic Entity's Plane", AreaType.REGIONS, 8267),
	REGION_CRABCLAW_ISLE("Crabclaw Isle", AreaType.REGIONS, 6965),
	REGION_CRAFTING_GUILD("Crafting Guild", AreaType.REGIONS, 11571),
	REGION_CRANDOR("Crandor", AreaType.REGIONS, 11314, 11315),
	REGION_CRASH_ISLAND("Crash Island", AreaType.REGIONS, 11562),
	REGION_DARK_ALTAR("Dark Altar", AreaType.REGIONS, 6716),
	REGION_DEATH_ALTAR("Death Altar", AreaType.REGIONS, 8779),
	REGION_DEATH_PLATEAU("Death Plateau", AreaType.REGIONS, 11320),
	REGION_DENSE_ESSENCE("Dense Essence Mine", AreaType.REGIONS, 6972),
	REGION_DESERT_PLATEAU("Desert Plateau", AreaType.REGIONS, 13361, 13617),
	REGION_DIGSITE("Digsite", AreaType.REGIONS, 13365),
	REGION_DRAGONTOOTH("Dragontooth Island", AreaType.REGIONS, 15159),
	REGION_DRAYNOR_MANOR("Draynor Manor", AreaType.REGIONS, 12340),
	REGION_DRILL_SERGEANT("Drill Sergeant's Training Camp", AreaType.REGIONS, 12619),
	REGION_EAGLES_PEAK("Eagles' Peak", AreaType.REGIONS, 9270),
	REGION_EARTH_ALTAR("Earth Altar", AreaType.REGIONS, 10571),
	REGION_ENCHANTED_VALLEY("Enchanted Valley", AreaType.REGIONS, 12102),
	REGION_EVIL_TWIN("Evil Twin Crane Room", AreaType.REGIONS, 7504),
	REGION_EXAM_CENTRE("Exam Centre", AreaType.REGIONS, 13364),
	REGION_FALADOR_FARM("Falador Farm", AreaType.REGIONS, 12083),
	REGION_FARMING_GUILD("Farming Guild", AreaType.REGIONS, 4922),
	REGION_FELDIP_HILLS("Feldip Hills", AreaType.REGIONS, 9773, 9774, 10029, 10030, 10285, 10286, 10287, 10542, 10543),
	REGION_FENKENSTRAIN("Fenkenstrain's Castle", AreaType.REGIONS, 14135),
	REGION_FIGHT_ARENA("Fight Arena", AreaType.REGIONS, 10289),
	REGION_FIRE_ALTAR("Fire Altar", AreaType.REGIONS, 10315),
	REGION_FISHER_REALM("Fisher Realm", AreaType.REGIONS, 10569),
	REGION_FISHING_GUILD("Fishing Guild", AreaType.REGIONS, 10293),
	REGION_FISHING_PLATFORM("Fishing Platform", AreaType.REGIONS, 11059),
	REGION_FORSAKEN_TOWER("The Forsaken Tower", AreaType.REGIONS, 5435),
	REGION_FOSSIL_ISLAND("Fossil Island", AreaType.REGIONS, 14650, 14651, 14652, 14906, 14907, 14908, 15162, 15163, 15164),
	REGION_FREAKY_FORESTER("Freaky Forester's Clearing", AreaType.REGIONS, 10314),
	REGION_FREMENNIK("Fremennik Province", AreaType.REGIONS, 10296, 10552, 10808, 10809, 10810, 10811, 11064),
	REGION_FREMENNIK_ISLES("Fremennik Isles", AreaType.REGIONS, 9276, 9532),
	REGION_FROGLAND("Frogland", AreaType.REGIONS, 9802),
	REGION_GALVEK_SHIPWRECKS("Galvek Shipwrecks", AreaType.REGIONS, 6486, 6487, 6488, 6489, 6742, 6743, 6744, 6745),
	REGION_GORAKS_PLANE("Gorak's Plane", AreaType.REGIONS, 12115),
	REGION_GRAND_EXCHANGE("Grand Exchange", AreaType.REGIONS, 12598),
	REGION_GWD("God Wars Dungeon", AreaType.REGIONS, 11578),
	REGION_HARMONY("Harmony Island", AreaType.REGIONS, 15148),
	REGION_HAZELMERE("Hazelmere's Island", AreaType.REGIONS, 10544),
	REGION_ICE_PATH("Ice Path", AreaType.REGIONS, 11322, 11323),
	REGION_ICEBERG("Iceberg", AreaType.REGIONS, 10558, 10559),
	REGION_ICYENE_GRAVEYARD("Icyene Graveyard", AreaType.REGIONS, 14641, 14897, 14898),
	REGION_ISAFDAR("Isafdar", AreaType.REGIONS, 8497, 8753, 8754, 9009, 9010),
	REGION_ISLAND_OF_STONE("Island of Stone", AreaType.REGIONS, 9790),
	REGION_ISLE_OF_SOULS("Isle of Souls", AreaType.REGIONS, 8236, 8237, 8238, 8491, 8492, 8494, 8747, 8750, 9003, 9004, 9006, 9260, 9261, 9262),
	REGION_JIGGIG("Jiggig" , AreaType.REGIONS, 9775),
	REGION_KANDARIN("Kandarin", AreaType.REGIONS, 9268, 9269, 9014, 9263, 9264, 9519, 9524, 9527, 9776, 9783, 10037, 10290, 10294, 10546, 10551, 10805, 11062),
	REGION_KARAMJA("Karamja" , AreaType.REGIONS, 10801, 10802, 11054, 11311, 11312, 11313, 11566, 11567, 11568, 11569, 11822),
	REGION_KEBOS_LOWLANDS("Kebos Lowlands", AreaType.REGIONS, 4665, 4666, 4667, 4921, 5178),
	REGION_KEBOS_SWAMP("Kebos Swamp", AreaType.REGIONS, 4664, 4920, 5174, 5175, 5176, 5430, 5431),
	REGION_KHARAZI_JUNGLE("Kharazi Jungle", AreaType.REGIONS, 11053, 11309, 11565, 11821),
	REGION_KHARIDIAN_DESERT("Kharidian Desert", AreaType.REGIONS, 12587, 12844, 12845, 12846, 12847, 12848, 13100, 13101, 13102, 13103, 13104, 13357, 13359, 13360, 13614, 13615, 13616),
	REGION_KILLERWATT_PLANE("Killerwatt Plane", AreaType.REGIONS, 10577),
	REGION_KOUREND("Great Kourend", AreaType.REGIONS, 6201, 6457, 6713),
	REGION_KOUREND_WOODLAND("Kourend Woodland", AreaType.REGIONS, 5942, 6197, 6453),
	REGION_LAW_ALTAR("Law Altar", AreaType.REGIONS, 9803),
	REGION_LEGENDS_GUILD("Legends' Guild", AreaType.REGIONS, 10804),
	REGION_LIGHTHOUSE("Lighthouse", AreaType.REGIONS, 10040),
	REGION_LITHKREN("Lithkren", AreaType.REGIONS, 14142, 14398),
	REGION_LUMBRIDGE_SWAMP("Lumbridge Swamp", AreaType.REGIONS, 12593, 12849),
	REGION_MAX_ISLAND("Max Island", AreaType.REGIONS, 11063),
	REGION_MCGRUBORS_WOOD("McGrubor's Wood", AreaType.REGIONS, 10550),
	REGION_MIME_STAGE("Mime's Stage", AreaType.REGIONS, 8010),
	REGION_MIND_ALTAR("Mind Altar", AreaType.REGIONS, 11083),
	REGION_MISTHALIN("Misthalin", AreaType.REGIONS, 12594, 12595, 12851),
	REGION_MOLCH("Molch", AreaType.REGIONS, 5177),
	REGION_MOLCH_ISLAND("Molch Island", AreaType.REGIONS, 5432),
	REGION_MORYTANIA("Morytania", AreaType.REGIONS, 13619, 13620, 13621, 13622, 13876, 13877, 13879, 14133, 14134, 14389, 14390, 14391, 14645, 14647),
	REGION_MOUNT_QUIDAMORTEM("Mount Quidamortem", AreaType.REGIONS, 4662, 4663, 4918, 4919),
	REGION_MR_MORDAUTS_CLASSROOM("Mr. Mordaut's Classroom", AreaType.REGIONS, 7502),
	REGION_MUDSKIPPER("Mudskipper Point", AreaType.REGIONS, 11824),
	REGION_MYSTERIOUS_OLD_MAN_MAZE("Mysterious Old Man's Maze", AreaType.REGIONS, 11590, 11591, 11846, 11847),
	REGION_MYTHS_GUILD("Myths' Guild", AreaType.REGIONS, 9772),
	REGION_NATURE_ALTAR("Nature Altar", AreaType.REGIONS, 9547),
	REGION_NORTHERN_TUNDRAS("Northern Tundras", AreaType.REGIONS, 6204, 6205, 6717),
	REGION_OBSERVATORY("Observatory", AreaType.REGIONS, 9777),
	REGION_ODD_ONE_OUT("Odd One Out", AreaType.REGIONS, 7754),
	REGION_OTTOS_GROTTO("Otto's Grotto", AreaType.REGIONS, 10038),
	REGION_OURANIA_HUNTER("Ourania Hunter Area", AreaType.REGIONS, 9778),
	REGION_PIRATES_COVE("Pirates' Cove", AreaType.REGIONS, 8763),
	REGION_PISCATORIS_HUNTER_AREA("Piscatoris Hunter Area", AreaType.REGIONS, 9015, 9016, 9271, 9272, 9528),
	REGION_POH("Player Owned House", AreaType.REGIONS, 7513, 7514, 7769, 7770),
	REGION_POISON_WASTE("Poison Waste", AreaType.REGIONS, 8752, 9008),
	REGION_PORT_TYRAS("Port Tyras", AreaType.REGIONS, 8496),
	REGION_PURO_PURO("Puro Puro", AreaType.REGIONS, 10307),
	REGION_QUARRY("Quarry", AreaType.REGIONS, 12589),
	REGION_RANGING_GUILD("Ranging Guild", AreaType.REGIONS, 10549),
	REGION_RATCATCHERS_MANSION("Ratcatchers Mansion", AreaType.REGIONS, 11343),
	REGION_RUINS_OF_UNKAH("Ruins of Unkah", AreaType.REGIONS, 12588),
	REGION_RUNE_ESSENCE_MINE("Rune Essence Mine", AreaType.REGIONS, 11595),
	// The Beekeper, Pinball, and Gravedigger randoms share a region (7758), and although they are not technically ScapeRune, that name is most commonly
	// associated with random events, so those three have been denoted ScapeRune to avoid leaving multiple random event regions without an assigned name.
	REGION_SCAPERUNE("ScapeRune", AreaType.REGIONS, 10058, 7758, 8261),
	REGION_SEA_SPIRIT_DOCK("Sea Spirit Dock", AreaType.REGIONS, 12332),
	REGION_SHIP_YARD("Ship Yard", AreaType.REGIONS, 11823),
	REGION_SILVAREA("Silvarea", AreaType.REGIONS, 13366),
	REGION_SINCLAR_MANSION("Sinclair Mansion", AreaType.REGIONS, 10807),
	REGION_SLAYER_TOWER("Slayer Tower", AreaType.REGIONS, 13623, 13723),
	REGION_SOUL_ALTAR("Soul Altar", AreaType.REGIONS, 7228),
	REGION_TROLL_ARENA("Troll Arena", AreaType.REGIONS, 11576),
	REGION_TROLLHEIM("Trollheim", AreaType.REGIONS, 11577),
	REGION_TROLLWEISS_MTN("Trollweiss Mountain", AreaType.REGIONS, 11066, 11067, 11068),
	REGION_TUTORIAL_ISLAND("Tutorial Island", AreaType.REGIONS, 12079, 12080, 12335, 12336, 12436, 12592),
	REGION_UNDERWATER("Underwater", AreaType.REGIONS, 15008, 15264),
	REGION_WATER_ALTAR("Water Altar", AreaType.REGIONS, 10827),
	REGION_WATERBIRTH_ISLAND("Waterbirth Island", AreaType.REGIONS, 10042),
	REGION_WINTERTODT_CAMP("Wintertodt Camp", AreaType.REGIONS, 6461),
	REGION_WIZARDS_TOWER("Wizards' Tower", AreaType.REGIONS, 12337),
	REGION_WOODCUTTING_GUILD("Woodcutting Guild", AreaType.REGIONS, 6198, 6454),
	REGION_WRATH_ALTAR("Wrath Altar", AreaType.REGIONS, 9291);

	private static final Map<Integer, GameEventType> FROM_REGION;

	static
	{
		ImmutableMap.Builder<Integer, GameEventType> regionMapBuilder = new ImmutableMap.Builder<>();
		for (GameEventType gameEventType : GameEventType.values())
		{
			if (gameEventType.getRegionIds() == null)
			{
				continue;
			}

			for (int region : gameEventType.getRegionIds())
			{
				regionMapBuilder.put(region, gameEventType);
			}
		}
		FROM_REGION = regionMapBuilder.build();
	}

	@Nullable
	private String imageKey;

	@Nullable
	private String state;

	@Nullable
	private String details;

	private int priority;

	/**
	 * Marks this event as root event. (eg. event that should be used for total time tracking)
	 */
	private boolean root;

	/**
	 * Determines if event should clear other clearable events when triggered
	 */
	private boolean shouldClear;

	/**
	 * Determines if event should be processed when it timeouts based on action timeout
	 */
	private boolean shouldTimeout;

	/**
	 * Determines if event start time should be reset when processed
	 */
	private boolean shouldRestart;

	/**
	 * Determines if event should be cleared when processed
	 */
	private boolean shouldBeCleared = true;

	@Nullable
	private AreaType areaType;

	@Nullable
	private int[] regionIds;

	GameEventType(Skill skill)
	{
		this(skill, 0);
	}

	GameEventType(Skill skill, int priority)
	{
		this.details = training(skill);
		this.priority = priority;
		this.imageKey = imageKeyOf(skill);
		this.shouldTimeout = true;
		this.shouldBeCleared = false;
	}

	GameEventType(String areaName, AreaType areaType, int... regionIds)
	{
		this.state = exploring(areaType, areaName);
		this.priority = -2;
		this.areaType = areaType;
		this.regionIds = regionIds;
		this.shouldClear = true;
	}

	GameEventType(String state, int priority, boolean shouldClear, boolean shouldTimeout, boolean shouldRestart, boolean shouldBeCleared, boolean root)
	{
		this.state = state;
		this.priority = priority;
		this.shouldClear = shouldClear;
		this.shouldTimeout = shouldTimeout;
		this.shouldRestart = shouldRestart;
		this.shouldBeCleared = shouldBeCleared;
		this.root = root;
	}

	GameEventType(String state, int priority)
	{
		this(state, priority, true, false, false, true, false);
	}

	private static String training(final Skill skill)
	{
		return training(skill.getName());
	}

	private static String training(final String what)
	{
		return "Training: " + what;
	}

	private static String imageKeyOf(final Skill skill)
	{
		return imageKeyOf(skill.getName().toLowerCase());
	}

	private static String imageKeyOf(final String what)
	{
		return "icon_" + what;
	}

	private static String exploring(AreaType areaType, String areaName)
	{
		return areaName;
	}

	public static GameEventType fromSkill(final Skill skill)
	{
		switch (skill)
		{
			case ATTACK: return TRAINING_ATTACK;
			case DEFENCE: return TRAINING_DEFENCE;
			case STRENGTH: return TRAINING_STRENGTH;
			case RANGED: return TRAINING_RANGED;
			case PRAYER: return TRAINING_PRAYER;
			case MAGIC: return TRAINING_MAGIC;
			case COOKING: return TRAINING_COOKING;
			case WOODCUTTING: return TRAINING_WOODCUTTING;
			case FLETCHING: return TRAINING_FLETCHING;
			case FISHING: return TRAINING_FISHING;
			case FIREMAKING: return TRAINING_FIREMAKING;
			case CRAFTING: return TRAINING_CRAFTING;
			case SMITHING: return TRAINING_SMITHING;
			case MINING: return TRAINING_MINING;
			case HERBLORE: return TRAINING_HERBLORE;
			case AGILITY: return TRAINING_AGILITY;
			case THIEVING: return TRAINING_THIEVING;
			case SLAYER: return TRAINING_SLAYER;
			case FARMING: return TRAINING_FARMING;
			case RUNECRAFT: return TRAINING_RUNECRAFT;
			case HUNTER: return TRAINING_HUNTER;
			case CONSTRUCTION: return TRAINING_CONSTRUCTION;
			default: return null;
		}
	}

	public static GameEventType fromRegion(final int regionId)
	{
		return FROM_REGION.get(regionId);
	}
}

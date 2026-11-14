package me.frep.vulcan.spigot.config;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import me.frep.vulcan.spigot.check.CheckSettingsLoader;
import me.frep.vulcan.spigot.check.data.CheckData;
import me.frep.vulcan.spigot.lifecycle.PluginServices;
import org.bukkit.Color;

public final class VulcanSettings {

    public static final Map<String, CheckData> checkDataByName = new HashMap<>();

    public static final Map<String, Boolean> checkEnabled = new HashMap<>();

    public static final Map<String, Integer> maximumViolations = new HashMap<>();

    public static final Map<String, Integer> alertIntervals = new HashMap<>();

    public static final Map<String, Integer> minimumAlertViolations = new HashMap<>();

    public static final Map<String, List<String>> punishmentCommands = new HashMap<>();

    public static final Map<String, Boolean> checkPunishable = new HashMap<>();

    public static final Map<String, Boolean> broadcastPunishments = new HashMap<>();

    public static final Map<String, Integer> maximumPing = new HashMap<>();

    public static final Map<String, Double> minimumTps = new HashMap<>();

    public static final Map<String, Boolean> setbackEnabled = new HashMap<>();

    public static final Map<String, Integer> maximumBuffers = new HashMap<>();

    public static final Map<String, Double> bufferDecays = new HashMap<>();

    public static final Map<String, Double> bufferMultipliers = new HashMap<>();

    public static final Map<String, Boolean> hotbarShuffleEnabled = new HashMap<>();

    public static final Map<String, Integer> minimumShuffleViolations = new HashMap<>();

    public static final Map<String, Integer> shuffleIntervals = new HashMap<>();

    public static final Map<String, Boolean> randomRotationEnabled = new HashMap<>();

    public static final Map<String, Integer> minimumRotationViolations = new HashMap<>();

    public static final Map<String, Integer> rotationIntervals = new HashMap<>();

    public static final Map<String, Integer> discordAlertIntervals = new HashMap<>();

    public static final Map<String, Integer> minimumSetbackViolations = new HashMap<>();

    public static String prefix;

    public static String messagesNoPermission;

    public static String alertsFormat;

    public static String alertsToggledOnMessage;

    public static String alertsToggledOffMessage;

    public static String messagesCantExecuteFromConsole;

    public static String punishmentsMessage;

    public static String messagesBanCommandSyntax;

    public static String messagesReloadSuccess;

    public static String messagesKbCommandSyntax;

    public static String messagesInvalidTarget;

    public static String messagesKbTestSuccess;

    public static String messagesJdayCommandSyntax;

    public static String messagesJdayNoPendingBans;

    public static String messagesJdayAddedToList;

    public static String messagesResetCommand;

    public static String messagesProfileCommandSyntax;

    public static String clientBrandAlertsMessage;

    public static String verboseToggledOnMessage;

    public static String verboseToggledOffMessage;

    public static String verboseFormat;

    public static String violationResetMessage;

    public static String alertsConsoleFormat;

    public static String logFileAlertMessage;

    public static String logFilePunishmentMessage;

    public static String alertsSeverityColors1;

    public static String alertsSeverityColors2;

    public static String alertsSeverityColors3;

    public static String alertsSeverityColors4;

    public static String alertsSeverityColors5;

    public static String commandsCps;

    public static String messagesNoNextPage;

    public static String messagesNoPreviousPage;

    public static String messagesEnabledAllChecks;

    public static String messagesDisabledAllChecks;

    public static String messagesEnabledAllPunishments;

    public static String messagesDisabledAllPunishments;

    public static String messagesEnabledCheck;

    public static String messagesDisabledCheck;

    public static String messagesEnabledPunishment;

    public static String messagesDisabledPunishment;

    public static String messagesEnabledHotbarShuffle;

    public static String messagesDisabledHotbarShuffle;

    public static String messagesEnabledRandomRotation;

    public static String messagesDisabledRandomRotation;

    public static String messagesEnabledBroadcastPunishment;

    public static String messagesDisabledBroadcastPunishment;

    public static String messagesMustBeAnInteger;

    public static String messagesMustBePositive;

    public static String messagesSetMaxBuffer;

    public static String messagesSetHotbarShuffleMinimumViolations;

    public static String messagesSetHotbarInterval;

    public static String messagesSetRandomRotationMinimumViolations;

    public static String messagesSetRandomRotationInterval;

    public static String messagesSetAlertInterval;

    public static String messagesMustBeNumber;

    public static String messagesSetBufferMultiple;

    public static String messagesSetBufferDecay;

    public static String messagesSetMaxViolations;

    public static String messagesSetMinimumViolationsToAlert;

    public static String messagesSetMaximumPing;

    public static String messagesSetMinimumTps;

    public static String messagesRemovedPunishmentCommand;

    public static String messagesInvalidIndexNumber;

    public static String messagesStoppedEditingPunishmentCommands;

    public static String messagesAddedPunishmentCommand;

    public static String messagesEnterPunishmentCommand;

    public static String messagesRemovePunishmentCommand;

    public static String messagesDisableCheckCommandSyntax;

    public static String messagesInvalidCheck;

    public static String messagesCheckRemoved;

    public static String messagesViolationsCommandSyntax;

    public static String messagesNoLogs;

    public static String messagesCpsCommandSyntax;

    public static String messagesConnectionCommandSyntax;

    public static String messagesLogsCommandSyntax;

    public static String messagesNoLogsFile;

    public static String messagesLogsCommandNoLogs;

    public static String messagesNoPage;

    public static String commandsLogsColor;

    public static String commandsLogsHeaderFooter;

    public static String messagesUpdateAvailable;

    public static String messagesLatestVersion;

    public static String settingsServerName;

    public static String messagesFrozen;

    public static String messagesLoggedOutWhileFrozen;

    public static String messagesFroze;

    public static String messagesUnfroze;

    public static String messagesFreezeCommandSyntax;

    public static String clientBrandAlertsUnallowedClientBrandKickMessage;

    public static String clientBrandAlertsConsoleMessage;

    public static String kickAlertMessagesMessagesFlyingNotEnabled;

    public static String kickAlertMessagesMessagesTooManyPackets;

    public static String kickAlertMessagesMessagesInvalidMovePacket;

    public static String settingsIgnoreGeyserPrefix;

    public static String messagesShuffleCommandSyntax;

    public static String messagesShuffledHotbar;

    public static String messagesRandomlyRotated;

    public static String messagesRotateCommandSyntax;

    public static String ghostBlocksFixMessage;

    public static String guiTitle;

    public static String ghostBlocksFixStaffMessage;

    public static String settingsTopCommandUnresolved;

    public static String alertsExperimentalSymbol;

    public static String messagesResetCommandSyntax;

    public static String messagesViolationsReset;

    public static String messagesUnknownCommand;

    public static String alertsSeverityColors6;

    public static String alertsSeverityColors7;

    public static String alertsSeverityColors8;

    public static String alertsSeverityColors9;

    public static String alertsSeverityColors10;

    public static String messagesFrozeStaffBroadcast;

    public static String messagesUnfrozeStaffBroadcast;

    public static String ghostBlocksFixConsoleMessage;

    public static String commandsJdayListHeaderFooter;

    public static String commandsJdayListFormat;

    public static String messagesRemovedFromJday;

    public static String messagesJdayRemoveSyntax;

    public static String discordWebhookAlertsUrl;

    public static String discordWebhookAlertsAvatar;

    public static String discordWebhookAlertsUsername;

    public static String discordWebhookAlertsTitle;

    public static String discordWebhookAlertsDescription;

    public static String discordWebhookPunishmentsUrl;

    public static String discordWebhookPunishmentsAvatar;

    public static String discordWebhookPunishmentsUsername;

    public static String discordWebhookPunishmentsTitle;

    public static String discordWebhookPunishmentsDescription;

    public static String commandsPunishlogsHeaderFooter;

    public static String messagesPunishlogsSyntax;

    public static String connectionTransactionKickMessage;

    public static String connectionKeepaliveKickMessage;

    public static String connectionTransactionStaffAlertMessage;

    public static String connectionKeepaliveStaffAlertMessage;

    public static String connectionTransactionConsoleMessage;

    public static String connectionKeepaliveConsoleMessage;

    public static String connectionMaxPingKickMessage;

    public static String connectionMaxPingStaffAlertMessage;

    public static String connectionMaxPingConsoleMessage;

    public static String discordWebhookAlertsThumbnail;

    public static String discordWebhookPunishmentsThumbnail;

    public static String discordWebhookAlertsContent;

    public static String discordWebhookPunishmentsContent;

    public static String messagesSentTestAlert;

    public static String unloadedChunksMessage;

    public static String connectionDesyncKickMessage;

    public static String connectionDesyncStaffAlertMessage;

    public static String connectionDesyncConsoleMessage;

    public static String messagesDescriptionCommandSyntax;

    public static String commandsDescription;

    public static String connectionInvalidKickMessage;

    public static String connectionInvalidStaffAlertMessage;

    public static String connectionInvalidConsoleAlertMessage;

    public static String connectionOutOfOrderKickMessage;

    public static String connectionOutOfOrderStaffAlertMessage;

    public static String connectionOutOfOrderConsoleAlertMessage;

    public static String connectionNoResponseKickMessage;

    public static String connectionNoResponseStaffAlertMessage;

    public static String connectionNoResponseConsoleAlertMessage;

    public static String connectionMaxSizeStaffAlertMessage;

    public static String connectionMaxSizeConsoleAlertMessage;

    public static String connectionMaxSizeKickMessage;

    public static long settingsJoinCheckWaitTime;

    public static long checksPlayerTimerDMaxBalance;

    public static long judgementDaysCooldown;

    public static long connectionTransactionMaxDelay;

    public static long connectionKeepaliveMaxDelay;

    public static long cancelHitsDuration;

    public static boolean joinMessageEnabled;

    public static boolean settingsToggleAlertsOnJoin;

    public static boolean clientBrandAlertsEnabled;

    public static boolean violationResetMessageEnabled;

    public static boolean violationResetEnabled;

    public static boolean alertsPrintToConsole;

    public static boolean logFileEnabled;

    public static boolean punishmentFileEnabled;

    public static boolean settingsPerCheckSeverities;

    public static boolean clientBrandAlertsConsole;

    public static boolean settingsEntityCollision;

    public static boolean settingsCinematic;

    public static boolean settingsDebug;

    public static boolean settingsCheckForUpdates;

    public static boolean settingsPaperspigot;

    public static boolean ghostBlocksFixEnabled;

    public static boolean ghostBlocksFixStaffMessageEnabled;

    public static boolean settingsAsyncAlerts;

    public static boolean settingsEnableApi;

    public static boolean settingsIgnoreGeyserUuids;

    public static boolean settingsIgnoreGeyserPrefixes;

    public static boolean ghostBlocksFixSetback;

    public static boolean settingsUpdateChecker;

    public static boolean kickAlertMessagesEnabled;

    public static boolean settingsIgnoreFlagsGeyserClientBrand;

    public static boolean clientBrandAlertsResolve;

    public static boolean ghostBlocksFixMessageEnabled;

    public static boolean punishmentStatisticsBroadcastEnabled;

    public static boolean judgementDaysRunAtInterval;

    public static boolean settingsIgnoreIfInJudgementDay;

    public static boolean checksPlayerGhosthandACancel;

    public static boolean settingsIgnoreVivecraft;

    public static boolean checksMovementBoatflyAKickOut;

    public static boolean checksMovementBoatflyBKickOut;

    public static boolean checksMovementEntityflightAKickOut;

    public static boolean checksMovementEntityspeedAKickOut;

    public static boolean checksPlayerBadpacketsVKickOut;

    public static boolean settingsLenientScaffolding;

    public static boolean settingsServerLagSpike;

    public static boolean checksMovementFlightAIgnoreGhostBlocks;

    public static boolean checksMovementFlightCIgnoreGhostBlocks;

    public static boolean checksMovementFlightDIgnoreGhostBlocks;

    public static boolean ghostBlocksFixUpdateBlocks;

    public static boolean settingsIgnoreFloodgate;

    public static boolean settingsBstats;

    public static boolean settingsIgnore117;

    public static boolean checksMovementFlightEIgnoreGhostBlocks;

    public static boolean ghostBlocksFixPrintToConsole;

    public static boolean settingsHookMcmmo;

    public static boolean settingsHookMythicmobs;

    public static boolean discordWebhookAlertsEnabled;

    public static boolean discordWebhookAlertsServerNameField;

    public static boolean discordWebhookAlertsLocationField;

    public static boolean discordWebhookAlertsClientBrandField;

    public static boolean discordWebhookAlertsInformationField;

    public static boolean discordWebhookAlertsDescriptionField;

    public static boolean discordWebhookAlertsClientVersionField;

    public static boolean discordWebhookPunishmentsEnabled;

    public static boolean discordWebhookAlertsPingTpsField;

    public static boolean connectionTransactionKickEnabled;

    public static boolean connectionKeepaliveKickEnabled;

    public static boolean ghostBlocksFixGhostWaterFix;

    public static boolean connectionMaxPingKickEnabled;

    public static boolean settingsHookGsit;

    public static boolean discordWebhookPunishmentsDescriptionField;

    public static boolean discordWebhookPunishmentsServerNameField;

    public static boolean discordWebhookPunishmentsClientBrandField;

    public static boolean discordWebhookPunishmentsClientVersionField;

    public static boolean discordWebhookPunishmentsPingTpsField;

    public static boolean discordWebhookPunishmentsLocationField;

    public static boolean checksPlayerAirplaceACancel;

    public static boolean settingsIgnoreAlertsOverMaxViolations;

    public static boolean clientBrandAlertsWhitelistEnabled;

    public static boolean settingsIncompatabilityManager;

    public static boolean settingsPluginMessaging;

    public static boolean settingsHookBrewery;

    public static boolean settingsDontSetbackLowerPositions;

    public static boolean settingsVelocityWorldBorder;

    public static boolean unloadedChunksSetbackEnabled;

    public static boolean connectionDesyncKickEnabled;

    public static boolean settingsEntityCramFixEnabled;

    public static boolean settingsLenientElytraHandling;

    public static boolean settingsHandleAttributes;

    public static boolean discordWebhookCooldown;

    public static boolean ghostBlocksFixIgnoreLegacyVia;

    public static boolean connectionOutOfOrderEnabled;

    public static boolean connectionInvalidEnabled;

    public static boolean connectionDebugMessages;

    public static boolean connectionOtherAnticheatSupport;

    public static boolean settingsDontRegisterGeyser;

    public static boolean settingsTestingStuff;

    public static boolean connectionNoResponseCodeOfConductFix;

    public static boolean cancelHitsEnabledForAllChecks;

    public static boolean cancelHitsCancelEvent;

    public static boolean checksPlayerAirplaceACancelPlace;

    public static List<String> alertsClickCommands;

    public static List<String> alertsHoverMessage;

    public static List<String> judgementDaysCommands;

    public static List<String> verboseClickCommands;

    public static List<String> judgementDaysBroadcast;

    public static List<String> joinMessageMessage;

    public static List<String> punishmentsBroadcast;

    public static List<String> commandsHelp;

    public static List<String> commandsBanCommands;

    public static List<String> verboseHoverMessage;

    public static List<String> commandsBanBroadcast;

    public static List<String> judgementDaysStartedBroadcast;

    public static List<String> commandsProfile;

    public static List<String> alertsCustomCommands;

    public static List<String> commandsConnection;

    public static List<String> clientBrandAlertsBlockedClientBrands;

    public static List<String> alertsClickAlertCommandCommands;

    public static List<String> commandsTop;

    public static List<String> punishmentStatisticsBroadcastBroadcast;

    public static List<String> freezeLoggedOutWhileFrozenCommands;

    public static List<String> judgementDaysEndedBroadcast;

    public static List<String> clientBrandAlertsIgnoreAlertsList;

    public static List<String> clientBrandAlertsWhitelistedClientBrands;

    public static List<String> cancelHitsChecks;

    public static int checksCombatAutoclickerAMaxCps;

    public static int checksPlayerFastplaceAMaxBlocks;

    public static int alertsSeverityViolations2;

    public static int punishmentStatisticsBroadcastInterval;

    public static int alertsSeverityViolations3;

    public static int alertsSeverityViolations4;

    public static int alertsSeverityViolations5;

    public static int checksPlayerPingspoofCMaxPing;

    public static int checksPlayerScaffoldKMaxBlocks;

    public static int settingsMaxVelocityTicks;

    public static int settingsMaxLogsFileSize;

    public static int checksPlayerImprobableAMaxCombatViolations;

    public static int checksPlayerImprobableBMaxMovementViolations;

    public static int checksPlayerImprobableCMaxPlayerViolations;

    public static int checksPlayerImprobableDMaxAutoclickerViolations;

    public static int settingsPunishmentDelay;

    public static int checksPlayerTimerCMaxPackets;

    public static int settingsMaxAlertViolation;

    public static int settingsFlightCooldown;

    public static int checksPlayerFastbreakAMinDifference;

    public static int judgementDaysInterval;

    public static int settingsMinTicksExisted;

    public static int connectionDesyncMaxTicks;

    public static int ghostBlocksFixTicks;

    public static int alertsSeverityViolations6;

    public static int alertsSeverityViolations7;

    public static int alertsSeverityViolations8;

    public static int alertsSeverityViolations9;

    public static int alertsSeverityViolations10;

    public static int checksPlayerImprobableEMaxTotalViolations;

    public static int checksPlayerImprobableFMaxScaffoldViolations;

    public static int unloadedChunksMaxTicks;

    public static int discordWebhookAlertsColorR;

    public static int discordWebhookAlertsColorG;

    public static int discordWebhookAlertsColorB;

    public static int discordWebhookPunishmentsColorR;

    public static int discordWebhookPunishmentsColorG;

    public static int discordWebhookPunishmentsColorB;

    public static int connectionMaxPingMaxPing;

    public static int connectionMaxPingMaxTicks;

    public static int settingsIgnoreAlertsOverMaxViolationsAmount;

    public static int checksPlayerFastplaceBMaxBlocks;

    public static int settingsEntityCramEntitiesAmount;

    public static int settingsEntityCramExemptTicks;

    public static int discordWebhookCooldownResetTicks;

    public static int discordWebhookCooldownMaxWebhooks;

    public static int connectionNoResponseDelayInSeconds;

    public static int connectionMaxSizeAmount;

    public static int connectionMinTransactionId;

    public static int connectionMaxTransactionId;

    public static double checksMovementBoatflyAMaxSpeed;

    public static double checksMovementElytraAMaxSpeed;

    public static double checksCombatReachAMaxReach;

    public static double checksCombatHitboxAMaxAngle;

    public static double checksCombatHitboxBMaxAngle;

    public static double checksMovementEntityspeedAMaxSpeed;

    public static double checksPlayerTimerAMaxSpeed;

    public static double checksCombatReachBMaxReach;

    public static double checksPlayerInteractAMaxDistance;

    public static double checksPlayerInteractBMaxDistance;

    public static double checksPlayerTimerBMinSpeed;

    public static double checksMovementSpeedBMinDifference;

    public static double checksMovementSpeedCMinDifference;

    public static double checksMovementSpeedDMinDifference;

    public static double checksMovementElytraLMinDifference;

    public static double checksMovementElytraMMinDifference;

    public static double ghostBlocksFixBufferMax;

    public static double ghostBlocksFixBufferDecay;

    public static double checksCombatAutoclickerTMinKurtosis;

    public static double ghostBlocksFixMinimumTps;

    public static double checksMovementEntityspeedAMinDifference;

    private static final int DEFAULT_MIN_TRANSACTION_ID = -32758;

    private static final int DEFAULT_MAX_TRANSACTION_ID = -420;

    private static VulcanConfig vulcanConfig;

    private static File file;

    public static void save() {
        vulcanConfig.save(file);
    }

    public static void reload() {
        vulcanConfig.load(file);
    }

    public static void initialize() {
        var plugin = PluginServices.INSTANCE.getVulcanPlugin();
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists())
            plugin.saveDefaultConfig();
        vulcanConfig = VulcanConfig.loadConfiguration(file);
        vulcanConfig.mergeBundledDefaults(file, plugin.getResource("config.yml"), new String[] { "NONE" });
    }

    private static void copyDefaultResource(InputStream input, File destination) {
        try (InputStream resource = java.util.Objects.requireNonNull(input, "Missing bundled configuration")) {
            java.nio.file.Files.copy(resource, destination.toPath());
        } catch (java.io.IOException exception) {
            throw new java.io.UncheckedIOException("Cannot extract configuration " + destination, exception);
        }
    }

    public static void loadSettings() {
        prefix = getString("prefix");
        alertsFormat = getString("alerts.format");
        messagesNoPermission = getString("messages.no-permission");
        unloadedChunksSetbackEnabled = getBoolean("unloaded-chunks.setback-enabled");
        unloadedChunksMessage = getString("unloaded-chunks.message");
        unloadedChunksMaxTicks = getInt("unloaded-chunks.max-ticks");
        connectionDesyncKickEnabled = getBoolean("connection.desync.kick-enabled");
        connectionDesyncMaxTicks = getInt("connection.desync.max-ticks");
        connectionDesyncKickMessage = getString("connection.desync.kick-message");
        connectionDesyncStaffAlertMessage = getString("connection.desync.staff-alert-message");
        connectionDesyncConsoleMessage = getString("connection.desync.console-message");
        alertsToggledOnMessage = getString("alerts.toggled-on-message");
        alertsToggledOffMessage = getString("alerts.toggled-off-message");
        alertsHoverMessage = getStringList("alerts.hover-message");
        alertsClickCommands = getStringList("alerts.click-commands");
        messagesCantExecuteFromConsole = getString("messages.cant-execute-from-console");
        judgementDaysCommands = getStringList("judgement-days.commands");
        judgementDaysBroadcast = getStringList("judgement-days.broadcast");
        judgementDaysEndedBroadcast = getStringList("judgement-days.ended-broadcast");
        joinMessageEnabled = getBoolean("join-message.enabled");
        joinMessageMessage = getStringList("join-message.message");
        settingsToggleAlertsOnJoin = getBoolean("settings.toggle-alerts-on-join");
        punishmentsMessage = getString("punishments.message");
        punishmentsBroadcast = getStringList("punishments.broadcast");
        commandsHelp = getStringList("commands.help");
        discordWebhookCooldown = getBoolean("discord-webhook.cooldown");
        discordWebhookCooldownResetTicks = getInt("discord-webhook.cooldown-reset-ticks");
        discordWebhookCooldownMaxWebhooks = getInt("discord-webhook.cooldown-max-webhooks");
        commandsJdayListHeaderFooter = getString("commands.jday.list-header-footer");
        commandsPunishlogsHeaderFooter = getString("commands.punishlogs.header-footer");
        commandsJdayListFormat = getString("commands.jday.list-format");
        ghostBlocksFixGhostWaterFix = getBoolean("ghost-blocks-fix.ghost-water-fix");
        settingsDontSetbackLowerPositions = getBoolean("settings.dont-setback-lower-positions");
        connectionTransactionKickMessage = getString("connection.transaction.kick-message");
        connectionKeepaliveKickMessage = getString("connection.keepalive.kick-message");
        connectionMaxPingKickMessage = getString("connection.max-ping.kick-message");
        connectionTransactionKickEnabled = getBoolean("connection.transaction.kick-enabled");
        connectionKeepaliveKickEnabled = getBoolean("connection.keepalive.kick-enabled");
        connectionMaxPingKickEnabled = getBoolean("connection.max-ping.kick-enabled");
        connectionMaxPingMaxPing = getInt("connection.max-ping.max-ping");
        settingsVelocityWorldBorder = getBoolean("settings.velocity-world-border");
        settingsIncompatabilityManager = getBoolean("settings.incompatability-manager");
        settingsPluginMessaging = getBoolean("settings.plugin-messaging");
        connectionMaxPingMaxTicks = getInt("connection.max-ping.max-ticks");
        connectionTransactionStaffAlertMessage = getString("connection.transaction.staff-alert-message");
        connectionKeepaliveStaffAlertMessage = getString("connection.keepalive.staff-alert-message");
        connectionMaxPingStaffAlertMessage = getString("connection.max-ping.staff-alert-message");
        connectionTransactionConsoleMessage = getString("connection.transaction.console-message");
        connectionMaxPingConsoleMessage = getString("connection.max-ping.console-message");
        connectionKeepaliveConsoleMessage = getString("connection.keepalive.console-message");
        connectionTransactionMaxDelay = getLong("connection.transaction.max-delay");
        connectionKeepaliveMaxDelay = getLong("connection.keepalive.max-delay");
        discordWebhookPunishmentsEnabled = getBoolean("discord-webhook.punishments.enabled");
        discordWebhookPunishmentsUrl = getString("discord-webhook.punishments.url");
        discordWebhookPunishmentsAvatar = getString("discord-webhook.punishments.avatar");
        discordWebhookPunishmentsThumbnail = getString("discord-webhook.punishments.thumbnail");
        discordWebhookPunishmentsColorR = getInt("discord-webhook.punishments.color-r");
        discordWebhookPunishmentsColorG = getInt("discord-webhook.punishments.color-g");
        discordWebhookPunishmentsColorB = getInt("discord-webhook.punishments.color-b");
        discordWebhookPunishmentsUsername = getString("discord-webhook.punishments.username");
        discordWebhookPunishmentsTitle = getString("discord-webhook.punishments.title");
        settingsIgnoreAlertsOverMaxViolations = getBoolean("settings.ignore-alerts-over-max-violations");
        settingsIgnoreAlertsOverMaxViolationsAmount = getInt("settings.ignore-alerts-over-max-violations-amount");
        discordWebhookPunishmentsDescription = getString("discord-webhook.punishments.description");
        messagesPunishlogsSyntax = getString("messages.punishlogs-syntax");
        discordWebhookAlertsEnabled = getBoolean("discord-webhook.alerts.enabled");
        discordWebhookAlertsUrl = getString("discord-webhook.alerts.url");
        discordWebhookAlertsAvatar = getString("discord-webhook.alerts.avatar");
        discordWebhookAlertsThumbnail = getString("discord-webhook.alerts.thumbnail");
        discordWebhookAlertsColorR = getInt("discord-webhook.alerts.color-r");
        discordWebhookAlertsColorG = getInt("discord-webhook.alerts.color-g");
        discordWebhookAlertsColorB = getInt("discord-webhook.alerts.color-b");
        discordWebhookAlertsUsername = getString("discord-webhook.alerts.username");
        discordWebhookAlertsTitle = getString("discord-webhook.alerts.title");
        discordWebhookAlertsDescription = getString("discord-webhook.alerts.description");
        discordWebhookAlertsServerNameField = getBoolean("discord-webhook.alerts.server-name-field");
        discordWebhookAlertsContent = getString("discord-webhook.alerts.content");
        discordWebhookPunishmentsContent = getString("discord-webhook.punishments.content");
        discordWebhookPunishmentsServerNameField = getBoolean("discord-webhook.punishments.server-name-field");
        discordWebhookAlertsLocationField = getBoolean("discord-webhook.alerts.location-field");
        discordWebhookPunishmentsLocationField = getBoolean("discord-webhook.punishments.location-field");
        discordWebhookAlertsClientBrandField = getBoolean("discord-webhook.alerts.client-brand-field");
        discordWebhookPunishmentsClientBrandField = getBoolean("discord-webhook.punishments.client-brand-field");
        discordWebhookAlertsInformationField = getBoolean("discord-webhook.alerts.information-field");
        discordWebhookAlertsDescriptionField = getBoolean("discord-webhook.alerts.description-field");
        discordWebhookPunishmentsDescriptionField = getBoolean("discord-webhook.punishments.description-field");
        discordWebhookAlertsClientVersionField = getBoolean("discord-webhook.alerts.client-version-field");
        discordWebhookPunishmentsClientVersionField = getBoolean("discord-webhook.punishments.client-version-field");
        discordWebhookAlertsPingTpsField = getBoolean("discord-webhook.alerts.ping-tps-field");
        discordWebhookPunishmentsPingTpsField = getBoolean("discord-webhook.punishments.ping-tps-field");
        messagesJdayRemoveSyntax = getString("messages.jday-remove-syntax");
        messagesBanCommandSyntax = getString("messages.ban-command-syntax");
        commandsBanCommands = getStringList("commands.ban.commands");
        commandsBanBroadcast = getStringList("commands.ban.broadcast");
        messagesReloadSuccess = getString("messages.reload-success");
        messagesKbCommandSyntax = getString("messages.kb-command-syntax");
        messagesInvalidTarget = getString("messages.invalid-target");
        messagesKbTestSuccess = getString("messages.kb-test-success");
        messagesJdayCommandSyntax = getString("messages.jday-command-syntax");
        messagesJdayNoPendingBans = getString("messages.jday-no-pending-bans");
        judgementDaysStartedBroadcast = getStringList("judgement-days.started-broadcast");
        messagesJdayAddedToList = getString("messages.jday-added-to-list");
        messagesResetCommand = getString("messages.reset-command");
        settingsServerLagSpike = getBoolean("settings.server-lag-spike");
        messagesProfileCommandSyntax = getString("messages.profile-command-syntax");
        commandsProfile = getStringList("commands.profile");
        clientBrandAlertsEnabled = getBoolean("client-brand-alerts.enabled");
        clientBrandAlertsMessage = getString("client-brand-alerts.message");
        clientBrandAlertsResolve = getBoolean("client-brand-alerts.resolve");
        messagesRemovedFromJday = getString("messages.removed-from-jday");
        verboseToggledOnMessage = getString("verbose.toggled-on-message");
        verboseToggledOffMessage = getString("verbose.toggled-off-message");
        verboseHoverMessage = getStringList("verbose.hover-message");
        verboseClickCommands = getStringList("verbose.click-commands");
        verboseFormat = getString("verbose.format");
        violationResetEnabled = getBoolean("violation-reset.enabled");
        violationResetMessageEnabled = getBoolean("violation-reset.message-enabled");
        violationResetMessage = getString("violation-reset.message");
        alertsPrintToConsole = getBoolean("alerts.print-to-console");
        alertsConsoleFormat = getString("alerts.console-format");
        logFileEnabled = getBoolean("log-file.enabled");
        logFileAlertMessage = getString("log-file.alert-message");
        logFilePunishmentMessage = getString("log-file.punishment-message");
        punishmentFileEnabled = getBoolean("punishment-file.enabled");
        checksCombatAutoclickerAMaxCps = getInt("checks.combat.autoclicker.a.max-cps");
        checksCombatAutoclickerTMinKurtosis = getInt("checks.combat.autoclicker.t.min-kurtosis");
        alertsClickAlertCommandCommands = getStringList("alerts.click-alert-command-commands");
        checksPlayerFastplaceAMaxBlocks = getInt("checks.player.fastplace.a.max-blocks");
        checksPlayerFastplaceBMaxBlocks = getInt("checks.player.fastplace.b.max-blocks");
        checksPlayerAirplaceACancel = getBoolean("checks.player.airplace.a.cancel");
        settingsIgnoreVivecraft = getBoolean("settings.ignore-vivecraft");
        checksMovementBoatflyAKickOut = getBoolean("checks.movement.boatfly.a.kick-out");
        checksMovementBoatflyBKickOut = getBoolean("checks.movement.boatfly.b.kick-out");
        checksMovementEntityspeedAKickOut = getBoolean("checks.movement.entityspeed.a.kick-out");
        checksMovementEntityflightAKickOut = getBoolean("checks.movement.entityflight.a.kick-out");
        checksMovementSpeedBMinDifference = getDouble("checks.movement.speed.b.min-difference");
        checksMovementSpeedCMinDifference = getDouble("checks.movement.speed.c.min-difference");
        checksMovementSpeedDMinDifference = getDouble("checks.movement.speed.d.min-difference");
        checksMovementFlightAIgnoreGhostBlocks = getBoolean("checks.movement.flight.a.ignore-ghost-blocks");
        checksMovementFlightCIgnoreGhostBlocks = getBoolean("checks.movement.flight.c.ignore-ghost-blocks");
        checksMovementFlightDIgnoreGhostBlocks = getBoolean("checks.movement.flight.d.ignore-ghost-blocks");
        checksMovementFlightEIgnoreGhostBlocks = getBoolean("checks.movement.flight.e.ignore-ghost-blocks");
        checksPlayerAirplaceACancelPlace = getBoolean("checks.player.airplace.a.cancel-place");
        checksMovementElytraLMinDifference = getDouble("checks.movement.elytra.l.min-difference");
        checksMovementElytraMMinDifference = getDouble("checks.movement.elytra.m.min-difference");
        checksPlayerBadpacketsVKickOut = getBoolean("checks.movement.nosaddle.a.kick-out");
        checksPlayerBadpacketsVKickOut = getBoolean("checks.player.badpackets.v.kick-out");
        checksMovementBoatflyAMaxSpeed = getDouble("checks.movement.boatfly.a.max-speed");
        checksMovementElytraAMaxSpeed = getDouble("checks.movement.elytra.a.max-speed");
        checksCombatReachAMaxReach = getDouble("checks.combat.reach.a.max-reach");
        checksCombatReachBMaxReach = getDouble("checks.combat.reach.b.max-reach");
        settingsIgnoreGeyserPrefixes = getBoolean("settings.ignore-geyser-prefixes");
        settingsIgnoreGeyserPrefix = getString("settings.ignore-geyser-prefix");
        checksPlayerScaffoldKMaxBlocks = getInt("checks.player.scaffold.k.max-blocks");
        checksCombatHitboxAMaxAngle = getDouble("checks.combat.hitbox.a.max-angle");
        checksCombatHitboxBMaxAngle = getDouble("checks.combat.hitbox.b.max-angle");
        checksPlayerTimerBMinSpeed = getDouble("checks.player.timer.b.min-speed");
        checksPlayerInteractAMaxDistance = getDouble("checks.player.interact.a.max-distance");
        checksPlayerInteractBMaxDistance = getDouble("checks.player.interact.b.max-distance");
        checksMovementEntityspeedAMaxSpeed = getDouble("checks.movement.entityspeed.a.max-speed");
        checksPlayerImprobableAMaxCombatViolations = getInt("checks.player.improbable.a.max-combat-violations");
        checksPlayerImprobableBMaxMovementViolations = getInt("checks.player.improbable.b.max-movement-violations");
        checksPlayerImprobableCMaxPlayerViolations = getInt("checks.player.improbable.c.max-player-violations");
        checksPlayerImprobableDMaxAutoclickerViolations = getInt("checks.player.improbable.d.max-autoclicker-violations");
        checksPlayerImprobableEMaxTotalViolations = getInt("checks.player.improbable.e.max-total-violations");
        checksPlayerImprobableFMaxScaffoldViolations = getInt("checks.player.improbable.f.max-scaffold-violations");
        checksPlayerGhosthandACancel = getBoolean("checks.player.ghosthand.a.cancel");
        checksPlayerFastbreakAMinDifference = getInt("checks.player.fastbreak.a.min-difference");
        alertsCustomCommands = getStringList("alerts.custom-commands");
        checksPlayerTimerAMaxSpeed = getDouble("checks.player.timer.a.max-speed");
        checksPlayerTimerCMaxPackets = getInt("checks.player.timer.c.max-packets");
        checksPlayerTimerDMaxBalance = getLong("checks.player.timer.d.max-balance");
        settingsIgnoreGeyserUuids = getBoolean("settings.ignore-geyser-uuids");
        settingsIgnore117 = getBoolean("settings.ignore-1-17");
        alertsSeverityViolations2 = getInt("alerts.severity.violations.2");
        alertsSeverityViolations3 = getInt("alerts.severity.violations.3");
        alertsSeverityViolations4 = getInt("alerts.severity.violations.4");
        alertsSeverityViolations5 = getInt("alerts.severity.violations.5");
        alertsSeverityViolations6 = getInt("alerts.severity.violations.6");
        alertsSeverityViolations7 = getInt("alerts.severity.violations.7");
        alertsSeverityViolations8 = getInt("alerts.severity.violations.8");
        alertsSeverityViolations9 = getInt("alerts.severity.violations.9");
        alertsSeverityViolations10 = getInt("alerts.severity.violations.10");
        alertsSeverityColors1 = getString("alerts.severity.colors.1");
        alertsSeverityColors2 = getString("alerts.severity.colors.2");
        alertsSeverityColors3 = getString("alerts.severity.colors.3");
        alertsSeverityColors4 = getString("alerts.severity.colors.4");
        alertsSeverityColors5 = getString("alerts.severity.colors.5");
        alertsSeverityColors6 = getString("alerts.severity.colors.6");
        alertsSeverityColors7 = getString("alerts.severity.colors.7");
        alertsSeverityColors8 = getString("alerts.severity.colors.8");
        alertsSeverityColors9 = getString("alerts.severity.colors.9");
        alertsSeverityColors10 = getString("alerts.severity.colors.10");
        settingsPerCheckSeverities = getBoolean("settings.per-check-severities");
        checksPlayerPingspoofCMaxPing = getInt("checks.player.pingspoof.c.max-ping");
        settingsEntityCollision = getBoolean("settings.entity-collision-fix");
        settingsCinematic = getBoolean("settings.cinematic");
        commandsCps = getString("commands.cps");
        settingsDebug = getBoolean("settings.debug");
        messagesSentTestAlert = getString("messages.sent-test-alert");
        messagesNoNextPage = getString("messages.no-next-page");
        messagesNoPreviousPage = getString("messages.no-previous-page");
        messagesEnabledAllChecks = getString("messages.enabled-all-checks");
        messagesDisabledAllChecks = getString("messages.disabled-all-checks");
        messagesEnabledAllPunishments = getString("messages.enabled-all-punishments");
        messagesDisabledAllPunishments = getString("messages.disabled-all-punishments");
        messagesEnabledCheck = getString("messages.enabled-check");
        messagesDisabledCheck = getString("messages.disabled-check");
        messagesEnabledPunishment = getString("messages.enabled-punishment");
        messagesDisabledPunishment = getString("messages.disabled-punishment");
        messagesEnabledHotbarShuffle = getString("messages.enabled-hotbar-shuffle");
        messagesDisabledHotbarShuffle = getString("messages.disabled-hotbar-shuffle");
        messagesEnabledRandomRotation = getString("messages.enabled-random-rotation");
        messagesDisabledRandomRotation = getString("messages.disabled-random-rotation");
        messagesEnabledBroadcastPunishment = getString("messages.enabled-broadcast-punishment");
        messagesDisabledBroadcastPunishment = getString("messages.disabled-broadcast-punishment");
        messagesMustBeAnInteger = getString("messages.must-be-an-integer");
        messagesMustBePositive = getString("messages.must-be-positive");
        messagesSetMaxBuffer = getString("messages.set-max-buffer");
        messagesSetHotbarShuffleMinimumViolations = getString("messages.set-hotbar-shuffle-minimum-violations");
        messagesSetHotbarInterval = getString("messages.set-hotbar-interval");
        messagesSetRandomRotationMinimumViolations = getString("messages.set-random-rotation-minimum-violations");
        messagesSetRandomRotationInterval = getString("messages.set-random-rotation-interval");
        messagesSetAlertInterval = getString("messages.set-alert-interval");
        messagesMustBeNumber = getString("messages.must-be-number");
        messagesSetBufferMultiple = getString("messages.set-buffer-multiple");
        messagesSetBufferDecay = getString("messages.set-buffer-decay");
        messagesSetMaxViolations = getString("messages.set-max-violations");
        messagesSetMinimumViolationsToAlert = getString("messages.set-minimum-violations-to-alert");
        messagesSetMaximumPing = getString("messages.set-maximum-ping");
        messagesSetMinimumTps = getString("messages.set-minimum-tps");
        messagesRemovedPunishmentCommand = getString("messages.removed-punishment-command");
        messagesInvalidIndexNumber = getString("messages.invalid-index-number");
        messagesStoppedEditingPunishmentCommands = getString("messages.stopped-editing-punishment-commands");
        messagesAddedPunishmentCommand = getString("messages.added-punishment-command");
        messagesEnterPunishmentCommand = getString("messages.enter-punishment-command");
        messagesRemovePunishmentCommand = getString("messages.remove-punishment-command");
        messagesDisableCheckCommandSyntax = getString("messages.disable-check-command-syntax");
        messagesInvalidCheck = getString("messages.invalid-check");
        messagesCheckRemoved = getString("messages.check-removed");
        messagesViolationsCommandSyntax = getString("messages.violations-command-syntax");
        messagesNoLogs = getString("messages.no-logs");
        messagesCpsCommandSyntax = getString("messages.cps-command-syntax");
        messagesConnectionCommandSyntax = getString("messages.connection-command-syntax");
        commandsConnection = getStringList("commands.connection");
        messagesLogsCommandSyntax = getString("messages.logs-command-syntax");
        messagesNoLogsFile = getString("messages.no-logs-file");
        messagesLogsCommandNoLogs = getString("messages.logs-command-no-logs");
        messagesNoPage = getString("messages.no-page");
        commandsLogsColor = getString("commands.logs.color");
        commandsLogsHeaderFooter = getString("commands.logs.header-footer");
        settingsCheckForUpdates = getBoolean("settings.check-for-updates");
        messagesUpdateAvailable = getString("messages.update-available");
        settingsIgnoreFloodgate = getBoolean("settings.ignore-floodgate");
        messagesLatestVersion = getString("messages.latest-version");
        settingsPaperspigot = getBoolean("settings.paperspigot");
        settingsBstats = getBoolean("settings.bstats");
        clientBrandAlertsIgnoreAlertsList = getStringList("client-brand-alerts.ignore-alerts-list");
        settingsMaxVelocityTicks = getInt("settings.max-velocity-ticks");
        settingsMaxLogsFileSize = getInt("settings.max-logs-file-size");
        settingsServerName = getString("settings.server-name");
        messagesFrozen = getString("messages.frozen");
        messagesLoggedOutWhileFrozen = getString("messages.logged-out-while-frozen");
        messagesFroze = getString("messages.froze");
        messagesUnfroze = getString("messages.unfroze");
        messagesFreezeCommandSyntax = getString("messages.freeze-command-syntax");
        clientBrandAlertsBlockedClientBrands = getStringList("client-brand-alerts.blocked-client-brands");
        clientBrandAlertsUnallowedClientBrandKickMessage = getString("client-brand-alerts.unallowed-client-brand-kick-message");
        settingsAsyncAlerts = getBoolean("settings.async-alerts");
        settingsEnableApi = getBoolean("settings.enable-api");
        settingsUpdateChecker = getBoolean("settings.update-checker");
        kickAlertMessagesEnabled = getBoolean("kick-alert-messages.enabled");
        kickAlertMessagesMessagesFlyingNotEnabled = getString("kick-alert-messages.messages.flying-not-enabled");
        kickAlertMessagesMessagesTooManyPackets = getString("kick-alert-messages.messages.too-many-packets");
        kickAlertMessagesMessagesInvalidMovePacket = getString("kick-alert-messages.messages.invalid-move-packet");
        settingsPunishmentDelay = getInt("settings.punishment-delay");
        settingsIgnoreFlagsGeyserClientBrand = getBoolean("settings.ignore-flags-geyser-client-brand");
        settingsJoinCheckWaitTime = getLong("settings.join-check-wait-time");
        settingsMinTicksExisted = getInt("settings.min-ticks-existed");
        settingsMaxAlertViolation = getInt("settings.max-alert-violation");
        messagesShuffleCommandSyntax = getString("messages.shuffle-command-syntax");
        messagesShuffledHotbar = getString("messages.shuffled-hotbar");
        messagesRotateCommandSyntax = getString("messages.rotate-command-syntax");
        messagesRandomlyRotated = getString("messages.randomly-rotated");
        commandsTop = getStringList("commands.top");
        ghostBlocksFixEnabled = getBoolean("ghost-blocks-fix.enabled");
        ghostBlocksFixMinimumTps = getDouble("ghost-blocks-fix.minimum-tps");
        ghostBlocksFixBufferMax = getDouble("ghost-blocks-fix.buffer.max");
        ghostBlocksFixBufferDecay = getDouble("ghost-blocks-fix.buffer.decay");
        ghostBlocksFixMessage = getString("ghost-blocks-fix.message");
        ghostBlocksFixTicks = getInt("ghost-blocks-fix.ticks");
        ghostBlocksFixUpdateBlocks = getBoolean("ghost-blocks-fix.update-blocks");
        guiTitle = getString("gui.title");
        settingsFlightCooldown = getInt("settings.flight-cooldown");
        ghostBlocksFixMessageEnabled = getBoolean("ghost-blocks-fix.message-enabled");
        ghostBlocksFixStaffMessageEnabled = getBoolean("ghost-blocks-fix.staff-message-enabled");
        ghostBlocksFixStaffMessage = getString("ghost-blocks-fix.staff-message");
        ghostBlocksFixSetback = getBoolean("ghost-blocks-fix.setback");
        ghostBlocksFixPrintToConsole = getBoolean("ghost-blocks-fix.print-to-console");
        ghostBlocksFixIgnoreLegacyVia = getBoolean("ghost-blocks-fix.ignore-legacy-via");
        clientBrandAlertsConsole = getBoolean("client-brand-alerts.console");
        clientBrandAlertsConsoleMessage = getString("client-brand-alerts.console-message");
        settingsTopCommandUnresolved = getString("settings.top-command-unresolved");
        judgementDaysCooldown = getLong("judgement-days.cooldown");
        punishmentStatisticsBroadcastEnabled = getBoolean("punishment-statistics-broadcast.enabled");
        punishmentStatisticsBroadcastBroadcast = getStringList("punishment-statistics-broadcast.broadcast");
        punishmentStatisticsBroadcastInterval = getInt("punishment-statistics-broadcast.interval");
        judgementDaysRunAtInterval = getBoolean("judgement-days.run-at-interval");
        judgementDaysInterval = getInt("judgement-days.interval");
        freezeLoggedOutWhileFrozenCommands = getStringList("freeze.logged-out-while-frozen-commands");
        alertsExperimentalSymbol = getString("alerts.experimental-symbol");
        settingsIgnoreIfInJudgementDay = getBoolean("settings.ignore-if-in-judgement-day");
        messagesResetCommandSyntax = getString("messages.reset-command-syntax");
        messagesViolationsReset = getString("messages.violations-reset");
        messagesUnknownCommand = getString("messages.unknown-command");
        messagesFrozeStaffBroadcast = getString("messages.froze-staff-broadcast");
        messagesUnfrozeStaffBroadcast = getString("messages.unfroze-staff-broadcast");
        settingsLenientScaffolding = getBoolean("settings.lenient-scaffolding");
        ghostBlocksFixConsoleMessage = getString("ghost-blocks-fix.console-message");
        settingsEntityCollision = getBoolean("settings.entity-collision");
        settingsHookMcmmo = getBoolean("settings.hook-mcmmo");
        settingsHookMythicmobs = getBoolean("settings.hook-mythicmobs");
        settingsHookGsit = getBoolean("settings.hook-gsit");
        settingsHookBrewery = getBoolean("settings.hook-brewery");
        clientBrandAlertsWhitelistedClientBrands = getStringList("client-brand-alerts.whitelisted-client-brands");
        clientBrandAlertsWhitelistEnabled = getBoolean("client-brand-alerts.whitelist-enabled");
        settingsEntityCramFixEnabled = getBoolean("settings.entity-cram-fix-enabled");
        settingsEntityCramEntitiesAmount = getInt("settings.entity-cram-entities-amount");
        settingsEntityCramExemptTicks = getInt("settings.entity-cram-exempt-ticks");
        settingsLenientElytraHandling = getBoolean("settings.lenient-elytra-handling");
        checksMovementEntityspeedAMinDifference = getDouble("checks.movement.entityspeed.a.min-difference");
        settingsHandleAttributes = getBoolean("settings.handle-attributes");
        commandsDescription = getString("commands.description");
        messagesDescriptionCommandSyntax = getString("messages.description-command-syntax");
        connectionInvalidKickMessage = getString("connection.invalid.kick-message");
        connectionInvalidStaffAlertMessage = getString("connection.invalid.staff-alert-message");
        connectionInvalidConsoleAlertMessage = getString("connection.invalid.console-alert-message");
        connectionOutOfOrderKickMessage = getString("connection.out-of-order.kick-message");
        connectionOutOfOrderStaffAlertMessage = getString("connection.out-of-order.staff-alert-message");
        connectionOutOfOrderConsoleAlertMessage = getString("connection.out-of-order.console-alert-message");
        connectionNoResponseDelayInSeconds = getInt("connection.no-response.delay-in-seconds");
        connectionNoResponseKickMessage = getString("connection.no-response.kick-message");
        connectionNoResponseStaffAlertMessage = getString("connection.no-response.staff-alert-message");
        connectionNoResponseConsoleAlertMessage = getString("connection.no-response.console-alert-message");
        connectionNoResponseCodeOfConductFix = getBoolean("connection.no-response.code-of-conduct-fix");
        connectionOutOfOrderEnabled = getBoolean("connection.out-of-order.enabled");
        connectionInvalidEnabled = getBoolean("connection.invalid.enabled");
        connectionDebugMessages = getBoolean("connection.debug-messages");
        connectionMaxSizeAmount = getInt("connection.max-size.amount");
        connectionMaxSizeStaffAlertMessage = getString("connection.max-size.staff-alert-message");
        connectionMaxSizeConsoleAlertMessage = getString("connection.max-size.console-alert-message");
        connectionMaxSizeKickMessage = getString("connection.max-size.kick-message");
        connectionOtherAnticheatSupport = getBoolean("connection.other-anticheat-support");
        loadTransactionIdRange();
        settingsDontRegisterGeyser = getBoolean("settings.dont-register-geyser");
        settingsTestingStuff = getBoolean("settings.testing-stuff");
        cancelHitsEnabledForAllChecks = getBoolean("cancel-hits.enabled-for-all-checks");
        cancelHitsDuration = getLong("cancel-hits.duration");
        cancelHitsCancelEvent = getBoolean("cancel-hits.cancel-event");
        cancelHitsChecks = getStringList("cancel-hits.checks").stream().map(VulcanSettings::normalizeCheckName).collect(Collectors.toList());
        CheckSettingsLoader.loadCheckSettings();
    }

    private static void loadTransactionIdRange() {
        int configuredMin = getInt("connection.min-transaction-id", DEFAULT_MIN_TRANSACTION_ID);
        int configuredMax = getInt("connection.max-transaction-id", DEFAULT_MAX_TRANSACTION_ID);
        int min = clampTransactionId(configuredMin);
        int max = clampTransactionId(configuredMax);
        if (min >= max) {
            min = DEFAULT_MIN_TRANSACTION_ID;
            max = DEFAULT_MAX_TRANSACTION_ID;
        }
        connectionMinTransactionId = min;
        connectionMaxTransactionId = max;
        Logger logger = PluginServices.INSTANCE.getVulcanPlugin().getLogger();
        if (configuredMin != min || configuredMax != max) {
            logger.warning("[Vulcan] Corrected transaction ID range from [" + configuredMin + ", " + configuredMax + "] to [" + min + ", " + max + "].");
            vulcanConfig.set("connection.min-transaction-id", min);
            vulcanConfig.set("connection.max-transaction-id", max);
            try {
                save();
            } catch (RuntimeException exception) {
                logger.log(Level.WARNING, "[Vulcan] Could not save the corrected transaction ID range.", exception);
            }
        }
        logger.info("Using transaction ID range [" + min + ", " + max + "].");
    }

    private static int clampTransactionId(int value) {
        return Math.max(-32768, Math.min(-1, value));
    }

    public static boolean getBoolean(String text) {
        return vulcanConfig.getBoolean(text);
    }

    public static String getString(String text) {
        return vulcanConfig.getString(text);
    }

    public static int getInt(String text) {
        return vulcanConfig.getInt(text);
    }

    public static int getInt(String text, int def) {
        return vulcanConfig.getInt(text, def);
    }

    public static double getDouble(String text) {
        return vulcanConfig.getDouble(text);
    }

    public static long getLong(String text) {
        return vulcanConfig.getLong(text);
    }

    public static List<String> getStringList(String text) {
        return vulcanConfig.getStringList(text);
    }

    public static boolean isString(String text) {
        return vulcanConfig.isString(text);
    }

    public static Color getColor(String text) {
        return vulcanConfig.getColor(text);
    }

    public static boolean isColor(String text) {
        return vulcanConfig.isColor(text);
    }

    public static void setAndSave(String text, Object value) {
        PluginServices.INSTANCE.getVulcanPlugin().getConfig().set(text, value);
        PluginServices.INSTANCE.getVulcanPlugin().saveConfig();
        PluginServices.INSTANCE.getVulcanPlugin().reloadConfig();
    }

    private VulcanSettings() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static String normalizeCheckName(String text) {
        return text.toLowerCase().replace(" ", "");
    }
}

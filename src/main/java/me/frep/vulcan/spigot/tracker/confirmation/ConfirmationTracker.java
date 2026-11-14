package me.frep.vulcan.spigot.tracker.confirmation;

import com.github.retrooper.packetevents.netty.channel.ChannelHelper;
import com.github.retrooper.packetevents.protocol.ConnectionState;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPing;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowConfirmation;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.frep.vulcan.spigot.check.exemption.ExemptionManager;
import me.frep.vulcan.spigot.config.VulcanSettings;
import me.frep.vulcan.spigot.lifecycle.PluginServices;
import me.frep.vulcan.spigot.util.scheduling.MainThreadExecutor;
import me.frep.vulcan.spigot.player.collision.CollisionState;
import me.frep.vulcan.spigot.player.session.PlayerData;
import me.frep.vulcan.spigot.util.minecraft.ChatColorUtils;
import me.frep.vulcan.spigot.util.numbers.MathUtils;
import org.bukkit.entity.Player;

public class ConfirmationTracker {

    private final PlayerData playerData;

    private final Deque<me.frep.vulcan.spigot.tracker.confirmation.PendingConfirmation> pendingConfirmations = new ConcurrentLinkedDeque();

    private final Deque<java.lang.Runnable> tasksForNextConfirmation = new ConcurrentLinkedDeque();

    private short nextConfirmationId;

    private short lastSentConfirmationId = -69;

    private short joinConfirmationId = -69;

    private long transactionPingMillis = 0L;

    private long lastMovementAtMillis = -1L;

    private long movementDelayMillis = -1L;

    private long lastConfirmationAtMillis = System.currentTimeMillis();

    private long lastClientLagSpikeAtMillis = Long.MAX_VALUE;

    private int lastRepliedConfirmationId = -42069;

    public ConfirmationTracker(PlayerData playerData) {
        this.playerData = playerData;
    }

    public void runAfterConfirmation(Runnable callback) {
        PendingConfirmation confirmation = pendingConfirmations.peekLast();
        if (confirmation == null)
            callback.run();
        else
            confirmation.addCallback(callback);
    }

    public void runAfterNextConfirmation(Runnable callback) {
        tasksForNextConfirmation.add(callback);
    }

    public void recordSentConfirmation(int id) {
        if (id == lastSentConfirmationId && playerData.isHandshakeComplete() && !VulcanSettings.connectionOtherAnticheatSupport)
            queueConfirmation(id);
    }

    private void queueConfirmation(int id) {
        PendingConfirmation confirmation = new PendingConfirmation(id, System.currentTimeMillis());
        Runnable callback;
        while ((callback = tasksForNextConfirmation.poll()) != null) confirmation.addCallback(callback);
        pendingConfirmations.add(confirmation);
    }

    public void handleConfirmationReply(int id) {
        if (playerData.isConnectionKickQueued() || !playerData.isHandshakeComplete())
            return;
        if (VulcanSettings.connectionOtherAnticheatSupport && pendingConfirmations.stream().noneMatch(confirmation -> confirmation.getId() == id))
            return;
        PendingConfirmation expected = pendingConfirmations.peek();
        if (expected == null) {
            String staff = VulcanSettings.connectionInvalidStaffAlertMessage.replace("%id%", Integer.toString(id)).replace("%player%", playerData.getPlayerName());
            String console = VulcanSettings.connectionInvalidConsoleAlertMessage.replace("%id%", Integer.toString(id)).replace("%player%", playerData.getPlayerName());
            rejectConfirmation(staff, console, VulcanSettings.connectionInvalidKickMessage);
            return;
        }
        if (id != expected.getId()) {
            String staff = VulcanSettings.connectionOutOfOrderStaffAlertMessage.replace("%id%", Integer.toString(id)).replace("%last-id%", Integer.toString(expected.getId())).replace("%player%", playerData.getPlayerName());
            String console = VulcanSettings.connectionOutOfOrderConsoleAlertMessage.replace("%id%", Integer.toString(id)).replace("%last-id%", Integer.toString(expected.getId())).replace("%player%", playerData.getPlayerName());
            rejectConfirmation(staff, console, VulcanSettings.connectionOutOfOrderKickMessage);
            return;
        }
        pendingConfirmations.poll();
        long now = System.currentTimeMillis();
        lastRepliedConfirmationId = id;
        transactionPingMillis = now - expected.getSentAtMillis();
        lastConfirmationAtMillis = now;
        expected.complete();
    }

    private void rejectConfirmation(String staffMessage, String consoleMessage, String kickMessage) {
        PluginServices.INSTANCE.getAlertManager().broadcastAlert(staffMessage);
        ExemptionManager.logColored(consoleMessage);
        if (VulcanSettings.connectionDebugMessages)
            logDebugState();
        playerData.setConnectionKickQueued(true);
        MainThreadExecutor.execute(() -> {
            Player player = playerData.getPlayer();
            if (player != null && player.isOnline())
                player.kickPlayer(ChatColorUtils.colorize(kickMessage));
        });
    }

    public void checkResponseTimeout() {
        long now = System.currentTimeMillis();
        long unansweredMillis = now - lastConfirmationAtMillis;
        boolean joined = now - playerData.getJoinTime() > 30000L;
        if (!playerData.isConnectionKickQueued() && joined && !pendingConfirmations.isEmpty()) {
            boolean timeout = unansweredMillis > TimeUnit.SECONDS.toMillis(VulcanSettings.connectionNoResponseDelayInSeconds);
            if (timeout && VulcanSettings.connectionNoResponseCodeOfConductFix) {
                CollisionState collision = playerData.getCollisionState();
                timeout = MathUtils.length3(collision.getX() - collision.getJoinX(), collision.getY() - collision.getJoinY(), collision.getZ() - collision.getJoinZ()) >= 5.0;
            }
            if (timeout) {
                rejectConfirmation(VulcanSettings.connectionNoResponseStaffAlertMessage.replace("%delay%", Long.toString(unansweredMillis)).replace("%player%", playerData.getPlayerName()), VulcanSettings.connectionNoResponseConsoleAlertMessage.replace("%delay%", Long.toString(unansweredMillis)).replace("%player%", playerData.getPlayerName()), VulcanSettings.connectionNoResponseKickMessage);
                return;
            }
            if (pendingConfirmations.size() > VulcanSettings.connectionMaxSizeAmount) {
                String size = Integer.toString(pendingConfirmations.size());
                rejectConfirmation(VulcanSettings.connectionMaxSizeStaffAlertMessage.replace("%size%", size).replace("%player%", playerData.getPlayerName()), VulcanSettings.connectionMaxSizeConsoleAlertMessage.replace("%size%", size).replace("%player%", playerData.getPlayerName()), VulcanSettings.connectionMaxSizeKickMessage);
            }
        }
        if (lastMovementAtMillis > 0) {
            movementDelayMillis = now - lastMovementAtMillis;
            if (movementDelayMillis < 5)
                lastClientLagSpikeAtMillis = now;
        }
        lastMovementAtMillis = now;
    }

    private void logDebugState() {
        ExemptionManager.logColored("Pending Confirmations (" + pendingConfirmations.size() + "): " + pendingConfirmations.stream().map(confirmation -> Integer.toString(confirmation.getId())).collect(Collectors.joining(", ")));
        ExemptionManager.logColored("Time since last replied Confirmation: " + (System.currentTimeMillis() - lastConfirmationAtMillis));
        ExemptionManager.logColored("Time since joining: " + (System.currentTimeMillis() - playerData.getJoinTime()));
        ExemptionManager.logColored("Last Replied ID: " + lastRepliedConfirmationId);
        ExemptionManager.logColored("Last Replied IDs: " + playerData.getRepliedConfirmationIds());
        ExemptionManager.logColored("Last Raw IDs: " + playerData.getRawConfirmationIds());
    }

    public void sendConfirmation(boolean inEventLoop) {
        if (!playerData.isHandshakeComplete() || playerData.getUser().getEncoderState() != ConnectionState.PLAY)
            return;
        if (inEventLoop)
            ChannelHelper.runInEventLoop(playerData.getUser().getChannel(), this::sendConfirmationPacket);
        else
            sendConfirmationPacket();
    }

    public void sendPacket(int id) {
        PacketWrapper<?> packet = ExemptionManager.isAtLeast1_17() ? new WrapperPlayServerPing(id) : new WrapperPlayServerWindowConfirmation(0, (short) id, false);
        playerData.getUser().sendPacket(packet);
    }

    public short generateConfirmationId() {
        if (nextConfirmationId < VulcanSettings.connectionMinTransactionId)
            nextConfirmationId = (short) VulcanSettings.connectionMinTransactionId;
        else if (nextConfirmationId < VulcanSettings.connectionMaxTransactionId)
            nextConfirmationId++;
        else
            nextConfirmationId = (short) VulcanSettings.connectionMinTransactionId;
        return nextConfirmationId;
    }

    public Deque<PendingConfirmation> getPendingConfirmations() {
        return this.pendingConfirmations;
    }

    public short getJoinConfirmationId() {
        return this.joinConfirmationId;
    }

    public long getTransactionPingMillis() {
        return this.transactionPingMillis;
    }

    public long getLastConfirmationAtMillis() {
        return this.lastConfirmationAtMillis;
    }

    public long getLastClientLagSpikeAtMillis() {
        return this.lastClientLagSpikeAtMillis;
    }

    public void setNextConfirmationId(int id) {
        this.nextConfirmationId = (short) id;
    }

    public void setLastSentConfirmationId(int id) {
        this.lastSentConfirmationId = (short) id;
    }

    public void setJoinConfirmationId(int id) {
        this.joinConfirmationId = (short) id;
    }

    private void sendConfirmationPacket() {
        lastSentConfirmationId = generateConfirmationId();
        if (VulcanSettings.connectionOtherAnticheatSupport)
            queueConfirmation(lastSentConfirmationId);
        sendPacket(lastSentConfirmationId);
    }
}

package me.frep.vulcan.spigot.listener;

import com.gmail.nossr50.datatypes.skills.SuperAbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;
import me.frep.vulcan.spigot.data.PlayerDataStore;
import me.frep.vulcan.spigot.lifecycle.PluginServices;
import me.frep.vulcan.spigot.player.session.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class McMmoAbilityListener implements Listener {

    @EventHandler
    public void handlePlayer(McMMOPlayerAbilityActivateEvent mcMMOPlayerAbilityActivateEvent) {
        int index = 66;
        Player player2 = mcMMOPlayerAbilityActivateEvent.getPlayer();
        PlayerDataStore playerDataStore = PluginServices.INSTANCE.getPlayerDataStore();
        PlayerData playerData = playerDataStore.getPlayerData(player2);
        if (playerData != null && mcMMOPlayerAbilityActivateEvent.getAbility() == SuperAbilityType.BERSERK)
            playerData.getMotionState().setBerserk(true);
    }

    @EventHandler
    public void handlePlayer(McMMOPlayerAbilityDeactivateEvent mcMMOPlayerAbilityDeactivateEvent) {
        Player player2 = mcMMOPlayerAbilityDeactivateEvent.getPlayer();
        PlayerDataStore playerDataStore = PluginServices.INSTANCE.getPlayerDataStore();
        PlayerData playerData = playerDataStore.getPlayerData(player2);
        if (playerData != null && mcMMOPlayerAbilityDeactivateEvent.getAbility() == SuperAbilityType.BERSERK)
            playerData.getMotionState().setBerserk(false);
    }
}

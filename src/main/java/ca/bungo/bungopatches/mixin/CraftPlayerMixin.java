package ca.bungo.bungopatches.mixin;


import io.papermc.paper.entity.TeleportFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;


@Mixin(CraftPlayer.class)
public abstract class CraftPlayerMixin {

    @Unique
    private Map<String, List<Entity>> bungoPatches$toTeleport = new HashMap<>();


    @Inject(method = "teleport(Lorg/bukkit/Location;Lorg/bukkit/event/player/PlayerTeleportEvent$TeleportCause;[Lio/papermc/paper/entity/TeleportFlag;)Z",
    at = @At("HEAD"), remap = false)
    public void teleport(Location location, PlayerTeleportEvent.TeleportCause cause,
                         TeleportFlag[] flags, CallbackInfoReturnable<Boolean> cir){

        Player player = ((CraftPlayer)(Object)this);
        List<Entity> passengers = player.getPassengers();

        if (!passengers.isEmpty()) {
            for (Entity passenger : passengers) {
                if (passenger.getType() == EntityType.TEXT_DISPLAY) {
                    List<Entity> displays = bungoPatches$toTeleport.get(player.getUniqueId().toString());
                    if(displays == null) displays = new ArrayList<>();
                    displays.add(passenger);
                    bungoPatches$toTeleport.put(player.getUniqueId().toString(), displays);
                }
                player.removePassenger(passenger);
            }
        }
    }

    @Inject(method = "teleport(Lorg/bukkit/Location;Lorg/bukkit/event/player/PlayerTeleportEvent$TeleportCause;[Lio/papermc/paper/entity/TeleportFlag;)Z",
    at = @At("RETURN"), remap = false)
    public void teleportOnReturn(Location location, PlayerTeleportEvent.TeleportCause cause, TeleportFlag[] flags, CallbackInfoReturnable<Boolean> cir){

        if(cir.getReturnValue()){
            CraftPlayer player = ((CraftPlayer)(Object)this);
            List<Entity> displays = bungoPatches$toTeleport.get(player.getUniqueId().toString());
            if(displays == null) return;
            for(Entity display : displays){
                display.teleport(location);
                player.addPassenger(display);
            }
            bungoPatches$toTeleport.remove(player.getUniqueId().toString());

        }

    }

}

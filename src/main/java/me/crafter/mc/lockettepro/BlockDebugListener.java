package me.crafter.mc.lockettepro;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockDebugListener implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void onDebugClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.isSneaking() && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            Block b = event.getClickedBlock();
            if (b == null) return;
            p.sendMessage(Component.text("===========================", NamedTextColor.GREEN));
            p.sendMessage(Component.text("isLockable: ").append(formatBoolean(LocketteProAPI.isLockable(b))));
            p.sendMessage(Component.text("isLocked: ").append(formatBoolean(LocketteProAPI.isLocked(b))));
            p.sendMessage(Component.text(" - isOwner/User: ").append(formatBoolean(LocketteProAPI.isOwner(b, p))).append(Component.text("/")).append(formatBoolean(LocketteProAPI.isUser(b, p))));
            p.sendMessage(Component.text("isLockedSingle: ").append(formatBoolean(LocketteProAPI.isLockedSingleBlock(b, null))));
            p.sendMessage(Component.text(" - isOwner/UserSingle: ").append(formatBoolean(LocketteProAPI.isOwnerSingleBlock(b, null, p))).append(Component.text("/")).append(formatBoolean(LocketteProAPI.isUserSingleBlock(b, null, p))));
            p.sendMessage(Component.text("isLockedUpDownLockedDoor: ").append(formatBoolean(LocketteProAPI.isUpDownLockedDoor(b))));
            p.sendMessage(Component.text(" - isOwner/UserSingle: ").append(formatBoolean(LocketteProAPI.isOwnerUpDownLockedDoor(b, p))).append(Component.text("/")).append(formatBoolean(LocketteProAPI.isOwnerUpDownLockedDoor(b, p))));
            if (LocketteProAPI.isLockSign(b)) {
                p.sendMessage(Component.text("isSignExpired: ").append(formatBoolean(LocketteProAPI.isSignExpired(b))));
                p.sendMessage(Component.text(" - created: " + Utils.getCreatedFromLine(((TextComponent) ((Sign) b.getState()).getSide(Side.FRONT).line(0)).content())));
                p.sendMessage(Component.text(" - now     : " + (int) (System.currentTimeMillis() / 1000)));
            }

            p.sendMessage(Component.text("Block: " + b.getType() + " " + b.getBlockData()));

            if (Tag.WALL_SIGNS.isTagged(b.getType())) {

                for (Component line : ((Sign) b.getState()).getSide(Side.FRONT).lines()) {
                    p.sendMessage(line.color(NamedTextColor.GREEN));
                }
            }
            p.sendMessage(p.getUniqueId().toString());
        }
    }

    public TextComponent formatBoolean(boolean tf) {
        if (tf) {
            return Component.text("true", NamedTextColor.GREEN);
        } else {
            return Component.text("false", NamedTextColor.RED);
        }
    }

}



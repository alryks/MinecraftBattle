package app.sklyar.battleplugin.listeners;

import app.sklyar.battleplugin.Items.ItemManager;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


public class ItemsUsageListener implements Listener {

    @EventHandler
    @Deprecated
    public void ItemsUsage(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null) {
            if (event.getItem().getItemMeta().equals(ItemManager.stormbreaker.getItemMeta())) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.hasCooldown(event.getMaterial())) {
                        player.setCooldown(event.getMaterial(), 20);
                        Block block = player.getTargetBlock(null, 30);
                        World world = player.getWorld();
                        while (block.getType() == Material.AIR) {
                            block = world.getBlockAt(block.getX(), block.getY() - 1, block.getZ());
                        }
                        for (int i = 0; i < 5; i++) {
                            player.getWorld().strikeLightningEffect(block.getLocation());
                        }
                        player.getWorld().createExplosion(block.getLocation(), 6, true, true, player);
                    }
                }
            }
            if (event.getItem().getItemMeta().equals(ItemManager.elderwand.getItemMeta())) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (!player.hasCooldown(event.getMaterial())) {
                        player.setCooldown(event.getMaterial(), 20);
                        Block block = player.getTargetBlock(null, 50);
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            if (player.hasLineOfSight(target) && target != player && !(player.getScoreboard().getPlayerTeam(player).equals(target.getScoreboard().getPlayerTeam(target)))) {
                                double dis1 = target.getLocation().distance(player.getLocation());
                                double dis2 = target.getLocation().distance(block.getLocation());
                                double dis3 = player.getLocation().distance(block.getLocation());
                                if (dis1 + dis2 - dis3 <= 0.3){
                                    target.setHealth(0);
                                    break;
                                }
                            }
                        }
                        double playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                        if (playerMaxHealth == 4){
                            player.setGameMode(GameMode.SPECTATOR);
                        }
                        else{
                            player.setMaxHealth(playerMaxHealth - 4);
                        }
                    }
                }
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (player.getAttackCooldown() == 1) {
                        Block block = player.getTargetBlock(null, 30);
                        World world = player.getWorld();
                        while (block.getType() == Material.AIR) {
                            block = world.getBlockAt(block.getX(), block.getY() - 1, block.getZ());
                        }

                        Location destination = new Location(player.getWorld(), block.getX(), block.getY() + 1, block.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        player.teleport(destination);

                    }
                }
            }
        }
    }

}
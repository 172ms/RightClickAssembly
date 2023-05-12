package me.rightclickassembly.version;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;
import java.util.HashMap;
import org.bukkit.Bukkit;
import java.util.Arrays;
import java.util.Map;

public class v1_17_R1 implements Listener {
	private static final Map<Material, Integer> seeds = new HashMap<>();
	
	static {
		seeds.put(Material.WHEAT, 7);
		seeds.put(Material.BEETROOTS, 3);
		seeds.put(Material.CARROTS, 7);
		seeds.put(Material.POTATOES, 7);
		seeds.put(Material.COCOA, 2);
		seeds.put(Material.NETHER_WART, 3);
	}
	
	private final Material[] blocks = {
		Material.MELON,
		Material.PUMPKIN
	};
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Material blockType = block.getType();
		
		seeds.forEach((material, seedsAge) -> {
			if (blockType == material) {
				Ageable ageable = (Ageable)block.getBlockData();
				
				if (ageable.getAge() != seedsAge) {
					return;
				}
				
				BlockBreakEvent blockBreak = new BlockBreakEvent(block, player);
				
				Bukkit.getPluginManager().callEvent(blockBreak);
				
				if (blockBreak.isCancelled()) {
					return;
				}
				
				block.breakNaturally();
				ageable.setAge(0);
				block.setBlockData(ageable);
				return;
			}
		});
		
		Arrays.stream(blocks).forEach(material -> {
			if (blockType == material) {
				BlockBreakEvent blockBreak = new BlockBreakEvent(block, player);
				
				Bukkit.getPluginManager().callEvent(blockBreak);
				
				if (!blockBreak.isCancelled()) {
					block.breakNaturally();
					block.setType(Material.AIR);
				}
			}
		});
	}
}
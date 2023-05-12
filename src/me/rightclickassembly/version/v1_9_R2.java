package me.rightclickassembly.version;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;
import java.util.HashMap;
import org.bukkit.Bukkit;
import java.util.Arrays;
import java.util.Map;

@SuppressWarnings("deprecation")
public class v1_9_R2 implements Listener {
	private static final Map<Material, Integer> seeds = new HashMap<>();
	
	static {
		seeds.put(Material.CROPS, 7);
		seeds.put(Material.CARROT, 7);
		seeds.put(Material.BEETROOT_BLOCK, 3);
		seeds.put(Material.POTATO, 7);
		seeds.put(Material.NETHER_WARTS, 3);
	}
	
	private final Material[] blocks = {
		Material.MELON_BLOCK,
		Material.PUMPKIN
	};
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Material blockType = block.getType();
		Integer ageable = seeds.get(blockType);
		
		if (ageable != null) {
			if (block.getData() != ageable.byteValue()) {
				return;
			}
			
			BlockBreakEvent blockBreak = new BlockBreakEvent(block, player);
			
			Bukkit.getPluginManager().callEvent(blockBreak);
			
			if (blockBreak.isCancelled()) {
				return;
			}
			
			block.breakNaturally();
			block.setType(blockType);
			block.setData((byte)0);
			return;
		}
		
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
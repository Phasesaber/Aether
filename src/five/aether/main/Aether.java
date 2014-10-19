package five.aether.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import five.aether.main.gen.IslandGenerator;

@SuppressWarnings("deprecation")
public class Aether extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		WorldCreator gen = new WorldCreator("aether");
		gen.generator(new IslandGenerator(140, .1, 3457, true));
		Bukkit.createWorld(gen);
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	public static void log(String string) {
		System.out.println("FifthDimension >> Aether >> " + string);
	}

	public static boolean isInAether(Player p) {
		return p.getWorld().getName().equalsIgnoreCase("aether");
	}

	@EventHandler
	public void j(PlayerChatEvent e) {
		if (e.getMessage().startsWith("!aether")) {
			e.getPlayer()
					.teleport(Bukkit.getWorld("aether").getSpawnLocation());
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void EatCoal(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (isInAether(p)) {
			if (p.getFoodLevel() > 19)
				return;
			if (p.getItemInHand().getType() == Material.COAL) {
				if (p.getItemInHand().getAmount() > 2)
					p.getItemInHand().setAmount(
							p.getItemInHand().getAmount() - 1);
				else
					p.getItemInHand().setType(Material.AIR);
				p.getWorld().playSound(p.getLocation(), Sound.BURP, 3, 1);
				p.setFoodLevel(p.getFoodLevel() + 5);
			}
		}
	}

	@EventHandler
	public void MineZanite(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (isInAether(p)) {
			if (e.getBlock().getType() == Material.IRON_ORE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR);
				e.getBlock()
						.getLocation()
						.getWorld()
						.dropItemNaturally(e.getBlock().getLocation(),
								new ItemStack(Material.IRON_INGOT, 1));
			}
		}
	}

}

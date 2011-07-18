package io.rampant.bukkit.orchard;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

/**
 *
 * @author jonathan
 */
public class Orchard extends JavaPlugin {
	public static final HashMap<Integer, String> LEAF_MAP = new HashMap<Integer, String>();
	/**
	 * Defaults
	 */
	private static final double DROP_APPLE_DECAY_CHANCE = 60.0;
	private static final double DROP_APPLE_BREAK_CHANCE = 60.0;
	private static final double DROP_GOLDAPPLE_DECAY_CHANCE = 10;
	private static final boolean DROP_APPLE_BREAK_SHEARS = true;
	private static final double DROP_COCOA_DECAY_CHANCE = 60;
	private static final int DROP_COCOA_DATA = 3;

	protected OrchardBlockListener blockListener = new OrchardBlockListener(this);
	public static Configuration config;

	public void loadDefaults() {
		config = getConfiguration();
		Map<String, ConfigurationNode> nodes = config.getNodes("");
		if( (null != nodes) && !nodes.isEmpty() ) {
			return;
		}

		config.getDouble("normal.decay.APPLE.chance", DROP_APPLE_DECAY_CHANCE);
		config.getDouble("normal.decay.GOLDEN_APPLE.chance", DROP_GOLDAPPLE_DECAY_CHANCE);
		config.getDouble("normal.break.APPLE.chance", DROP_APPLE_BREAK_CHANCE);
		config.getBoolean("normal.break.APPLE.shears", DROP_APPLE_BREAK_SHEARS);
		config.getDouble("birch.decay.INK_SACK.chance", DROP_COCOA_DECAY_CHANCE);
		config.getInt("birch.decay.INK_SACK.data", DROP_COCOA_DATA);

		config.save();
	}

	@Override
	public void onEnable() {
		LEAF_MAP.put(0, "normal");
		LEAF_MAP.put(1, "spruce");
		LEAF_MAP.put(2, "birch");

		loadDefaults();

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Monitor, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}

	@Override
	public void onDisable() {
		System.out.println(this.getDescription().getName() + " Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( cmd.getName().equalsIgnoreCase("orchard") ) {
			if( 0 >= args.length ) {
				return false;
			}
			String action = args[0];

			if( action.equalsIgnoreCase("reload") ) {
				if( !sender.isOp() ) {
					sender.sendMessage(ChatColor.DARK_GRAY + "[orchard] "
									+ ChatColor.RED + "ERROR: Only admins may use this command");
				}
				loadDefaults();
				sender.sendMessage(ChatColor.DARK_GRAY + "[orchard] Configuration reloaded.");
				return true;
			}
			else if( action.equalsIgnoreCase("testcocoa") ) {
				Player player = (Player) sender;
				ItemStack is = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 3);
				player.getWorld().dropItem(player.getLocation(), is);
				return true;
			}
			return false;
		}
		return false;
	}
}

package io.rampant.bukkit.orchard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author jonathan
 */
public class Orchard extends JavaPlugin {
	/**
	 * Constants
	 */
	public static final byte LEAF_TYPE_NORMAL = 0;
	public static final byte LEAF_TYPE_SPRUCE = 1;
	public static final byte LEAF_TYPE_BIRCH = 2;

	/**
	 * Defaults
	 */
	private static final double LEAF_DROP_APPLE = 60.0;
	private static final double LEAF_DROP_APPLE_GOLD = 10;
	private static final double LEAF_DROP_COCOA = 60;
	
	public Configuration config = getConfiguration();


	public void loadDefaults() {
		config = getConfiguration();
		config.getDouble("leaf.drop.apple", LEAF_DROP_APPLE);
		config.getDouble("leaf.drop.apple.gold", LEAF_DROP_APPLE_GOLD);
		config.getDouble("leaf.drop.cocoa", LEAF_DROP_COCOA);
		
	}

	protected OrchardBlockListener blockListener = new OrchardBlockListener(this);
	
	@Override
	public void onEnable() {

		// Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Monitor, this);

		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}


		@Override
	public void onDisable() {
		System.out.println(this.getDescription().getName()+" Disabled.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( cmd.getName().equalsIgnoreCase("orchard") ) {
			if( 0 >= args.length ) return false;
			String action = args[0];

			if( action.equalsIgnoreCase("reload") ) {
				// Reload configuration.
				return true;
			}
			return false;
		}
		return false;
	}

}

package io.rampant.bukkit.orchard;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.LeavesDecayEvent;

/**
 *
 * @author jonathan
 */
public class OrchardBlockListener extends BlockListener {
	private final Orchard plugin;

	public OrchardBlockListener(Orchard instance) {
		plugin = instance;
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if( event.getBlock().getType() == Material.LEAVES ) {
			byte leaf = event.getBlock().getData();
			int type = (leaf & 3);

			System.out.println("Leaf: "+Integer.toBinaryString(leaf)+" ("+leaf+")"+
							"Type: "+Integer.toBinaryString(type)+" ("+type+")");
			if( 0 == type ) {
				System.out.println("Normal Leaf");
			}
			else if( 1 == type ) {
				System.out.println("Spruce Leaf");
			}
			else if( 2 == type ) {
				System.out.println("Birch Leaf");
			}
			else {
				System.out.println("Unknown Type: "+type);
			}
		}
	}

	@Override
	public void onLeavesDecay(LeavesDecayEvent event) {
		
	}

}

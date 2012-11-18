package uk.co.jacekk.bukkit.commandblocktest;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CommandBlockChangeEvent;
import org.bukkit.event.block.CommandBlockCommandEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandBlockTest extends JavaPlugin implements Listener {
	
	private Server server;
	
	public void onEnable(){
		this.server = this.getServer();
		
		this.server.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onCommandBlockCommand(CommandBlockCommandEvent event){
		for (String command : event.commandList()){
			this.server.broadcastMessage("Command block executed: " + command);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Material item = player.getItemInHand().getType();
		Block block = event.getClickedBlock();
		
		if (block.getType() == Material.COMMAND){
			if (item == Material.STICK){
				Command commandBlock = (Command) block.getState();
				player.sendMessage("Command: " + commandBlock.getCommand());
				
				event.setCancelled(true);
			}else if (item == Material.STRING){
				Command commandBlock = (Command) block.getState();
				commandBlock.execute();
				
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onCommandBlockChange(CommandBlockChangeEvent event){
		this.server.broadcastMessage(event.getPlayer().getName() + " set command block command: " + event.getCommand());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerCommandPreprocessEvent event){
		if (event.getMessage().equalsIgnoreCase("/test")){
			Player player = event.getPlayer();
			
			player.sendMessage("bukkit.commandblock.edit: " + (player.hasPermission("bukkit.commandblock.edit") ? "true" : "false"));
			player.sendMessage("bukkit.commandblock.edit.survival: " + (player.hasPermission("bukkit.commandblock.edit.survival") ? "true" : "false"));
			player.sendMessage("bukkit.commandblock.edit.creative: " + (player.hasPermission("bukkit.commandblock.edit.creative") ? "true" : "false"));
			player.sendMessage("bukkit.commandblock.edit.adventure: " + (player.hasPermission("bukkit.commandblock.edit.adventure") ? "true" : "false"));
		}
	}
	
}

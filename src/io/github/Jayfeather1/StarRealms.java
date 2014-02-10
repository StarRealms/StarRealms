package io.github.Jayfeather1;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import io.github.Jayfeather1.features.*;
import io.github.Jayfeather1.misc.Weather;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

@SuppressWarnings("unused")
public class StarRealms extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");
	public static HUD hud;
	public static String n = System.getProperty("line.separator");
	public static String colorize(String str){
		return ChatColor.translateAlternateColorCodes('&', str.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1"));
		}
	public ArrayList<Player> cannonDelay = new ArrayList<Player>();
	@Override
	public void onEnable(){
		BossBar b = new BossBar();
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new HUD(), this);
		Bukkit.getPluginManager().registerEvents(new KitPvP(), this);
		Bukkit.getPluginManager().registerEvents(new Weather(), this);
		Bukkit.getPluginManager().registerEvents(b, this);
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if(!file.exists()){
			getLogger().info("Generating config file");
			this.getConfig().addDefault("StaffAnnouncement", "false");
			this.getConfig().addDefault("Announcement", new ArrayList<String>());
			this.getConfig().options().copyDefaults(true);
			saveConfig();
			getLogger().info("Done!");
		}else{
			getLogger().info("Found config.yml");
		}
	getLogger().info("+--------------------------+");
	getLogger().info("|                          |");
	getLogger().info("| StarRealms Custom Plugin |");
	getLogger().info("|      by: Freelix2000     |");
	getLogger().info("|                          |");
	getLogger().info("+--------------------------+");
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	HUD.HUDSetup();
    scheduler.scheduleSyncRepeatingTask(this, new Runnable(){
		@Override
		public void run() {
			HUD.meow();
		}
    }, 0L, 20 * 15L);
    b.enable();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player p = Bukkit.getPlayer(sender.getName());
		if(cmd.getName().equalsIgnoreCase("hub")){
			Bukkit.dispatchCommand(p, "warp hub");
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("kits")){
			KitPvP.kit(p, args);
			return true;
		}
		if(cmd.getName().equalsIgnoreCase("rank")){
			if(!p.getName().equals("Freelix2000")){
				p.sendMessage(ChatColor.RED + "Only Freelix2000 can use this command.");
				return true;
			}
			if(args.length < 2){
				p.sendMessage(ChatColor.RED + "Too few arguments.");
				p.sendMessage(ChatColor.RED + "/rank <player> <group>");
				return true;
			}
			Bukkit.dispatchCommand(p, "/manuadd " + args[0] + " " + args[1] + " kitpvp");
			Bukkit.dispatchCommand(p, "/manuadd " + args[0] + " " + args[1] + " freebuild");
			Bukkit.dispatchCommand(p, "/manuadd " + args[0] + " " + args[1] + " Survival");
			Bukkit.dispatchCommand(p, "/manuadd " + args[0] + " " + args[1] + " skyworld");
			Bukkit.dispatchCommand(p, "/manuadd " + args[0] + " " + args[1] + " world");
			p.sendMessage(ChatColor.GOLD + "Promoted " + ChatColor.YELLOW + args[0] + ChatColor.GOLD + " to " + ChatColor.YELLOW + args[1] + ChatColor.GOLD + " in all worlds.");
			return true;
		}
		return false;
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(e.getPlayer().getName().equals("Freelix2000")){
			if(e.getMessage().startsWith("@console:")){
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().split(":")[1]);
				p.sendMessage(ChatColor.AQUA + "Console command has been sent.");
				e.setCancelled(true);
				return;
			}
			if(e.getMessage().equalsIgnoreCase("reloadplugin")){
				e.setCancelled(true);
				this.reloadConfig();
				p.sendMessage(ChatColor.GREEN + "StarRealms plugin config reloaded.");
				return;
			}
			Integer random = getRandom(1, 3);
			if(random.equals(1)){
				e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Owner" + ChatColor.DARK_GRAY + "][" + ChatColor.AQUA + "Dev" + ChatColor.DARK_GRAY + "]" + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.translateAlternateColorCodes('&', "&b" + e.getMessage()));
				return;
			}
			if(random.equals(2)){
				e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Owner" + ChatColor.DARK_GRAY + "][" + ChatColor.YELLOW + "Dev" + ChatColor.DARK_GRAY + "]" + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.translateAlternateColorCodes('&', "&e" + e.getMessage()));
				return;
			}
			if(random.equals(3)){
				e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Owner" + ChatColor.DARK_GRAY + "][" + ChatColor.GREEN + "Dev" + ChatColor.DARK_GRAY + "]" + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.translateAlternateColorCodes('&', "&a" + e.getMessage()));
				return;
			}
		}
	}
	public int getRandom(int lower, int upper) {
		Random r = new Random();
	    return r.nextInt((upper - lower) + 1) + lower;
	}
	@EventHandler
	public void Move(PlayerMoveEvent e){
		final Player p = e.getPlayer();
		if(p.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.IRON_BLOCK) && p.getWorld().getName().equals("world") && !cannonDelay.contains(p)){
			Location loc = p.getLocation();
			loc.setYaw((float) round(p.getLocation().getYaw()));
			loc.setPitch(-3);
			p.setVelocity(loc.getDirection().multiply(18));
			p.playSound(p.getLocation(), Sound.CLICK, 10, 10);
			cannonDelay.add(p);
			new BukkitRunnable() {
				public void run() {
					cannonDelay.remove(p);
					}
		    }.runTaskLater(this, 1 * 20);
		}
	}
	public double round( double num) {
		if(num > -45 && num < 45){
			return 0;
		}
		if(num > 45 && num < 135){
			return 90;
		}
		if(num > 135 && num < 225){
			return 180;
		}
		if(num > 225 && num < 315){
			return 270;
		}
		return num;
	}
	@EventHandler
	public void Join(PlayerJoinEvent e){
		final String name = e.getPlayer().getName();
		e.setJoinMessage(ChatColor.AQUA + Bukkit.getPlayer(name).getDisplayName() + ChatColor.DARK_GRAY + " has joined " + ChatColor.AQUA + "StarRealms");
	}
	@EventHandler
	public void Leave(PlayerQuitEvent e){
		e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&b" + e.getPlayer().getDisplayName() + " &8has left &bStar Realms"));
	}
	@EventHandler
	public void BlockPlace(BlockPlaceEvent e){
		if(e.getBlock().getType().equals(Material.ENDER_CHEST) && !e.getPlayer().getWorld().getName().equalsIgnoreCase("kitpvp")){
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "To protect our multiverse system, we do not allow enderchests on StarRealms.");
			return;
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(!(e.getClickedBlock() == null) && e.getClickedBlock().getType().equals(Material.ENDER_CHEST)){
			if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("kitpvp")){
				e.getPlayer().sendMessage(ChatColor.RED + "To protect our multiverse systen, we do not allow enderchests on StarRealms.");
				e.setCancelled(true);
				return;
			}
		}
	}
	@EventHandler
	public void blockCommands(PlayerCommandPreprocessEvent e){
		if(e.getMessage().startsWith("/warp skyshop")){
			if(!e.getPlayer().hasPermission("warp.skyshop")){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to go to the skyshop");
				return;
			}
		}
		if(e.getMessage().startsWith("/kill Freelix2000")){
			e.setMessage("/kill " + e.getPlayer().getName());
			e.getPlayer().sendMessage(ChatColor.RED + "LOL");
			return;
		}
		if(e.getMessage().startsWith("/deop Freelix2000")){
			e.getPlayer().kickPlayer(ChatColor.RED + "Nice try, son." + n + "~Freelix2000");
			Bukkit.getServer().sendPluginMessage(this, e.getPlayer().getName() + " has tried to deop Freelix2000", null);
			return;
		}
	}
	@EventHandler
	public void staffAnnouncements(PlayerJoinEvent e){
		Player p = e.getPlayer();
		//if(p.hasPermission("starrealms.staff") && this.getConfig().getString("StaffAnnouncement").equalsIgnoreCase("true")){
			//p.sendMessage(ChatColor.GOLD + "STAFF ANNOUNCEMENT:");
			//for(String s : this.getConfig().getStringList("Announcement")){
				//colorize("&e" + s);
			//}
		//}
		return;
	}
}

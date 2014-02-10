package io.github.Jayfeather1.features;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import io.github.Jayfeather1.StarRealms;

public class HUD extends StarRealms{
public static StarRealms main;
public static Scoreboard hud = Bukkit.getScoreboardManager().getNewScoreboard();
public static Objective o = hud.registerNewObjective("hud", "dummy");
	public static void HUDSetup(){
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SkyblockTip" + ChatColor.DARK_GRAY + "]");
		for(Player p : Bukkit.getOnlinePlayers()){
			p.setScoreboard(hud);
		}
	}
	@EventHandler
	public void Join(PlayerJoinEvent e){
		e.getPlayer().setScoreboard(hud);
	}
	public static void clear(){
		for(OfflinePlayer p : hud.getPlayers()){
			hud.resetScores(p);
		}
	}
	public static void message(String s, Integer n){
		o.getScore(Bukkit.getOfflinePlayer(colorize(s))).setScore(n);
	}
	public static void meow(){
		if(o.getDisplayName().equals(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SkyblockTip" + ChatColor.DARK_GRAY + "]")){
			clear();
			o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SurvivalTip" + ChatColor.DARK_GRAY + "]");
			message("&8You can claim", 6);
			message("&8and manage", 5);
			message("&8land in the", 4);
			message("&bSurvival", 3);
			message("&8world with", 2);
			message("&b/lc", 1);
			return;
		}
		if(o.getDisplayName().equals(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SurvivalTip" + ChatColor.DARK_GRAY + "]")){
			clear();
			o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "FreebuildTip" + ChatColor.DARK_GRAY + "]");
			message("&8You can claim", 5);
			message("&8plots in the", 4);
			message("&bFreebuild", 3);
			message("&8world with", 2);
			message("&b/plotme", 1);
			return;
		}
		if(o.getDisplayName().equals(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "FreebuildTip" + ChatColor.DARK_GRAY + "]")){
			clear();
			o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "KitPvPTip" + ChatColor.DARK_GRAY + "]");
			message("&8You can get", 5);
			message("&8free kits in", 4);
			message("&8the &bKitPvP", 3);
			message("&8world with", 2);
			message("&b/kits", 1);
			return;
		}
		if(o.getDisplayName().equals(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "KitPvPTip" + ChatColor.DARK_GRAY + "]")){
			clear();
			o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Website" + ChatColor.DARK_GRAY + "]");
			message("&8Visit our", 5);
			message("&8website at", 4);
			message("&bhttp://starrea", 3);
			message("&blms-server.enj", 2);
			message("&bin.com", 1);
			return;
		}
		if(o.getDisplayName().equals(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "Website" + ChatColor.DARK_GRAY + "]")){
			clear();
			o.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "SkyblockTip" + ChatColor.DARK_GRAY + "]");
			message("&8You can manage", 6);
			message("&8your island", 5);
			message("&8in the", 4);
			message("&bSkyblock", 3);
			message("&8world with", 2);
			message("&b/island", 1);
			return;
		}
	}
}


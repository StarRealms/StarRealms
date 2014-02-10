package io.github.Jayfeather1.features;

import java.util.ArrayList;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import io.github.Jayfeather1.StarRealms;

public class BossBar extends StarRealms implements Listener{
	public Integer index = 0;
	public Integer taskid;
	public boolean direction = true;
	public ArrayList<String> msges = new ArrayList<String>();
	public void enable(){
		if(isBB()){
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRealms!");
			msges.add(ChatColor.AQUA + "W" + ChatColor.DARK_AQUA + "elcome to StarRealms!");
			msges.add(ChatColor.WHITE + "W" + ChatColor.AQUA + "e" + ChatColor.DARK_AQUA + "lcome to StarRealms!");
			msges.add(ChatColor.AQUA + "W" + ChatColor.WHITE + "e" + ChatColor.AQUA + "l" + ChatColor.DARK_AQUA + "come to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "W" + ChatColor.AQUA + "e" + ChatColor.WHITE + "l" + ChatColor.AQUA + "c" + ChatColor.DARK_AQUA + "ome to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "We" + ChatColor.AQUA + "l" + ChatColor.WHITE + "c" + ChatColor.AQUA + "o" + ChatColor.DARK_AQUA + "me to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Wel" + ChatColor.AQUA + "c" + ChatColor.WHITE + "o" + ChatColor.AQUA + "m" + ChatColor.DARK_AQUA + "e to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welc" + ChatColor.AQUA + "o" + ChatColor.WHITE + "m" + ChatColor.AQUA + "e" + ChatColor.DARK_AQUA + " to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welco" + ChatColor.AQUA + "m" + ChatColor.WHITE + "e" + ChatColor.AQUA + " " + ChatColor.DARK_AQUA + "to StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcom" + ChatColor.AQUA + "e" + ChatColor.WHITE + " " + ChatColor.AQUA + "t" + ChatColor.DARK_AQUA + "o StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome" + ChatColor.AQUA + " " + ChatColor.WHITE + "t" + ChatColor.AQUA + "o" + ChatColor.DARK_AQUA + " StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome " + ChatColor.AQUA + "t" + ChatColor.WHITE + "o" + ChatColor.AQUA + " " + ChatColor.DARK_AQUA + "StarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome t" + ChatColor.AQUA + "o" + ChatColor.WHITE + " " + ChatColor.AQUA + "S" + ChatColor.DARK_AQUA + "tarRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to" + ChatColor.AQUA + " " + ChatColor.WHITE + "S" + ChatColor.AQUA + "t" + ChatColor.DARK_AQUA + "arRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to " + ChatColor.AQUA + "S" + ChatColor.WHITE + "t" + ChatColor.AQUA + "a" + ChatColor.DARK_AQUA + "rRealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to S" + ChatColor.AQUA + "t" + ChatColor.WHITE + "a" + ChatColor.AQUA + "r" + ChatColor.DARK_AQUA + "Realms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to St" + ChatColor.AQUA + "a" + ChatColor.WHITE + "r" + ChatColor.AQUA + "R" + ChatColor.DARK_AQUA + "ealms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to Sta" + ChatColor.AQUA + "r" + ChatColor.WHITE + "R" + ChatColor.AQUA + "e" + ChatColor.DARK_AQUA + "alms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to Star" + ChatColor.AQUA + "R" + ChatColor.WHITE + "e" + ChatColor.AQUA + "a" + ChatColor.DARK_AQUA + "lms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarR" + ChatColor.AQUA + "e" + ChatColor.WHITE + "a" + ChatColor.AQUA + "l" + ChatColor.DARK_AQUA + "ms!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRe" + ChatColor.AQUA + "a" + ChatColor.WHITE + "l" + ChatColor.AQUA + "m" + ChatColor.DARK_AQUA + "s!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRea" + ChatColor.AQUA + "l" + ChatColor.WHITE + "m" + ChatColor.AQUA + "s" + ChatColor.DARK_AQUA + "!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarReal" + ChatColor.AQUA + "m" + ChatColor.WHITE + "s" + ChatColor.AQUA + "!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRealm" + ChatColor.AQUA + "s" + ChatColor.WHITE + "!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRealms" + ChatColor.AQUA + "!");
			msges.add(ChatColor.DARK_AQUA + "Welcome to StarRealms!");
		}
	}
	public boolean isBB(){
		if(Bukkit.getPluginManager().getPlugin("BarAPI") == null){
			return false;
		}
		return true;
	}
	public boolean isPlayerInHub(Player p){
		if(p.getWorld().getName().equals("world")){
			return true;
		}
		return false;
	}
	@EventHandler
	public void playerWarp(PlayerChangedWorldEvent e){
		if(BarAPI.hasBar(e.getPlayer())){
			BarAPI.removeBar(e.getPlayer());
		}
		if(Bukkit.getWorld("world").getPlayers().isEmpty()){
			disableBossBar();
		}
		if(e.getPlayer().getWorld().getName().equals("world") && taskid == null){
			enableBossBar();
		}
		return;
	}
	public void enableBossBar(){
		taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("StarRealms"), new Runnable(){
			@Override
			public void run(){
				if(direction){
					for(Player p : Bukkit.getOnlinePlayers()){
						if(isPlayerInHub(p)){
							BarAPI.setMessage(p, msges.get(index));
							index = index + 1;
							if(index == 24){
								direction = false;
							}
						}
					}
				}else{
					for(Player p : Bukkit.getOnlinePlayers()){
						if(isPlayerInHub(p)){
							BarAPI.setMessage(p, msges.get(index));
							index = index - 1;
							if(index == 0){
								direction = true;
							}
						}
					}
				}
			}
		}, 0L, 3L);
	}
	public void disableBossBar(){
		if(!(taskid == null)){
			Bukkit.getScheduler().cancelTask(taskid);
			taskid = null;
		}
	}
}

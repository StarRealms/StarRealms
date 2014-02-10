package io.github.Jayfeather1.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import io.github.Jayfeather1.StarRealms;

public class Weather extends StarRealms implements Listener{
	@EventHandler
	public void weather(WeatherChangeEvent e){
		if(!e.getWorld().isThundering()){
			e.setCancelled(true);
		}
	}
}

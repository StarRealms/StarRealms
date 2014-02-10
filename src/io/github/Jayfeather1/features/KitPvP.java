package io.github.Jayfeather1.features;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.Jayfeather1.StarRealms;

public class KitPvP extends StarRealms implements Listener{
	public HashMap<String, Inventory> furnace = new HashMap<String, Inventory>();
	@EventHandler
	public void kill(PlayerDeathEvent e){
		Player p = e.getEntity();
		if(p.getWorld().getName().equalsIgnoreCase("kitpvp")){
			e.setDroppedExp(0);
			try{
				Player k = p.getKiller();
				k.setLevel(k.getLevel() + 1);
				k.sendMessage(ChatColor.RED + "You gained 1 xp level for slaying a player.");
			}catch(NullPointerException npe){
			}
			if(p.getLevel() == 0){
				return;
			}
			p.setLevel(p.getLevel() - 1);
			p.sendMessage(ChatColor.RED + "You lost 1 xp level for dying!");
			return;
		}
	}
	@EventHandler
	public void signChange(SignChangeEvent e){
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase("kitpvp") && e.getLine(0).equalsIgnoreCase("[armory]")){
			for(String s : e.getLines()){
				if(s.equals("")){
					e.setLine(0, ChatColor.DARK_RED + "[Armory]");
					p.sendMessage(ChatColor.RED + "Missing content! Line 2 = Amount Line 3 = Item Line 4 = Price");
					return;
				}
			}
			try{
				Integer.parseInt(e.getLine(1));
			}catch(NumberFormatException er){
				e.setLine(0, ChatColor.DARK_RED + "[Armory]");
				p.sendMessage(ChatColor.RED + "Invalid content! Line 2 must be a number.");
				return;
			}
			try{
				Integer.parseInt(e.getLine(3));
			}catch(NumberFormatException er){
				e.setLine(0, ChatColor.DARK_RED + "[Armory]");
				p.sendMessage(ChatColor.RED + "Invalid content! Line 4 must be a number.");
				return;
			}
			e.setLine(0, ChatColor.DARK_BLUE + "[Armory]");
			return;
		}
		if(p.getWorld().getName().equalsIgnoreCase("kitpvp") && p.isOp()){
			if(e.getLine(0).equalsIgnoreCase("[anvil]")){
				e.setLine(0, ChatColor.DARK_BLUE + "[Anvil]");
				return;
			}
			if(e.getLine(0).equalsIgnoreCase("[craft]")){
				e.setLine(0, ChatColor.DARK_BLUE + "[Craft]");
				return;
			}
			if(e.getLine(0).equalsIgnoreCase("[furnace]")){
				e.setLine(0, ChatColor.DARK_BLUE + "[Furnace]");
				return;
			}
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && p.getWorld().getName().equalsIgnoreCase("kitpvp") && e.getClickedBlock().getState() instanceof Sign){
			Sign sign = (Sign) e.getClickedBlock().getState();
			if(sign.getLine(0).equals(ChatColor.DARK_BLUE + "[Armory]")){
				Integer price = Integer.parseInt(sign.getLine(3));
				Integer amount = Integer.parseInt(sign.getLine(1));
				if(p.getLevel() < price){
					p.sendMessage(ChatColor.RED + "You do not have enough xp to purchase this item!");
					return;
				}
				p.setLevel(p.getLevel() - price);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + p.getName() + " " + sign.getLine(2) + " " + amount);
				return;
			}
		}
	}
	@EventHandler
	public void click(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(p.getEnderChest().getViewers().contains((HumanEntity) p) && p.getWorld().getName().equalsIgnoreCase("kitpvp") && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore()){
			p.sendMessage(ChatColor.RED + "To prevent kit mixing, StarRealms does not allow players to move items that come from kits to enderchests.");
			e.setCancelled(true);
		}
	}
	public static void kit(Player p, String[] args){
		if(!p.getWorld().getName().equalsIgnoreCase("kitpvp")){
			p.sendMessage(ChatColor.RED + "This command is only available in the KitPvP world.");
			return;
		}
		if(args.length < 1){
			p.sendMessage(ChatColor.GOLD + "Available Kits:");
			p.sendMessage(ChatColor.GOLD + "Fighter " + ChatColor.RED + "/kits fighter");
			p.sendMessage(ChatColor.GOLD + "Archer " + ChatColor.RED + "/kits archer");
			p.sendMessage(ChatColor.GOLD + "Tank " + ChatColor.RED + "/kits tank");
			if(p.hasPermission("starrealms.soldier")){
				p.sendMessage(ChatColor.YELLOW + "Soldier " + ChatColor.RED + "/kits soldier");
			}
			if(p.hasPermission("starrealms.warrior")){
				p.sendMessage(ChatColor.YELLOW + "Warrior " + ChatColor.RED + "/kits warrior");
			}
			if(p.hasPermission("starrealms.spartan")){
				p.sendMessage(ChatColor.YELLOW + "Spartan " + ChatColor.RED + "/kits spartan");
			}
			return;
		}
		if(args[0].equalsIgnoreCase("fighter")){
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.CHAINMAIL_BOOTS), "Fighter", "Boots", 1));
			p.getInventory().setLeggings(metanate(new ItemStack(Material.CHAINMAIL_LEGGINGS), "Fighter", "Leggings", 1));
			p.getInventory().setChestplate(metanate(new ItemStack(Material.CHAINMAIL_CHESTPLATE), "Fighter", "Chestplate", 1));
			p.getInventory().setHelmet(metanate(new ItemStack(Material.CHAINMAIL_HELMET), "Fighter", "Helmet", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.DIAMOND_SWORD), "Fighter", "Sword", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.BOW), "Fighter", "Bow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.ARROW), "Fighter", "Arrow", 32));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Fighter", "BACON!", 64));
			return;
		}
		if(args[0].equalsIgnoreCase("archer")){
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.LEATHER_BOOTS), "Archer", "Boots", 1));
			p.getInventory().setLeggings(null);
			p.getInventory().setChestplate(metanate(new ItemStack(Material.LEATHER_CHESTPLATE), "Archer", "Chestplate", 1));
			p.getInventory().setHelmet(null);
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
			p.getInventory().addItem(metanate(bow, "Archer", "Bow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.ARROW), "Archer", "Arrow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Archer", "BACON!", 64));
			return;
		}
		if(args[0].equalsIgnoreCase("tank")){
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.DIAMOND_BOOTS), "Tank", "Boots", 1));
			p.getInventory().setLeggings(metanate(new ItemStack(Material.IRON_LEGGINGS), "Tank", "Leggings", 1));
			p.getInventory().setChestplate(metanate(new ItemStack(Material.IRON_CHESTPLATE), "Tank", "Chestplate", 1));
			p.getInventory().setHelmet(metanate(new ItemStack(Material.IRON_HELMET), "Tank", "Helmet", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.IRON_SWORD), "Tank", "Sword", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Tank", "BACON!", 64));
			return;
		}
		if(args[0].equalsIgnoreCase("soldier")){
			if(!p.hasPermission("starrealms.soldier")){
				p.sendMessage(ChatColor.GOLD + "Only Soldiers have access to this kit.");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Donate for Soldier at &1&nhttp://starrealms-server.enjin.com/shop" + ChatColor.GOLD + " for access to this kit."));
				return;
			}
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.IRON_BOOTS), "Soldier", "Boots", 1));
			p.getInventory().setLeggings(metanate(new ItemStack(Material.IRON_LEGGINGS), "Soldier", "Leggings", 1));
			p.getInventory().setChestplate(metanate(new ItemStack(Material.IRON_CHESTPLATE), "Soldier", "Chestplate", 1));
			p.getInventory().setHelmet(metanate(new ItemStack(Material.IRON_HELMET), "Soldier", "Helmet", 1));
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().addItem(metanate(sword, "Soldier", "Sword", 1));
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			p.getInventory().addItem(metanate(bow, "Soldier", "Bow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.ARROW), "Soldier", "Arrow", 64));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Soldier", "BACON!", 64));
			return;
		}
		if(args[0].equalsIgnoreCase("warrior")){
			if(!p.hasPermission("starrealms.warrior")){
				p.sendMessage(ChatColor.GOLD + "Only Warriors have access to this kit.");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Donate for Warrior at &1&nhttp://starrealms-server.enjin.com/shop" + ChatColor.GOLD + " for access to this kit."));
				return;
			}
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.DIAMOND_BOOTS), "Warrior", "Boots", 1));
			p.getInventory().setLeggings(metanate(new ItemStack(Material.IRON_LEGGINGS), "Warrior", "Leggings", 1));
			p.getInventory().setChestplate(metanate(new ItemStack(Material.DIAMOND_CHESTPLATE), "Warrior", "Chestplate", 1));
			p.getInventory().setHelmet(metanate(new ItemStack(Material.IRON_HELMET), "Warrior", "Helmet", 1));
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
			p.getInventory().addItem(metanate(sword, "Warrior", "Sword", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.BOW), "Warrior", "Bow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.ARROW), "Warrior", "Arrow", 32));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Warrior", "BACON!", 64));
			return;
		}
		if(args[0].equalsIgnoreCase("spartan")){
			if(!p.hasPermission("starrealms.spartan")){
				p.sendMessage(ChatColor.GOLD + "Only Spartans have access to this kit.");
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Donate for Spartan at &1&nhttp://starrealms-server.enjin.com/shop" + ChatColor.GOLD + " for access to this kit."));
				return;
			}
			clearKit(p);
			for(ItemStack i : p.getInventory().getArmorContents()){
				p.getInventory().addItem(i);
			}
			p.getInventory().setBoots(metanate(new ItemStack(Material.DIAMOND_BOOTS), "Spartan", "Boots", 1));
			p.getInventory().setLeggings(metanate(new ItemStack(Material.DIAMOND_LEGGINGS), "Spartan", "Leggings", 1));
			p.getInventory().setChestplate(metanate(new ItemStack(Material.DIAMOND_CHESTPLATE), "Spartan", "Chestplate", 1));
			p.getInventory().setHelmet(metanate(new ItemStack(Material.DIAMOND_HELMET), "Spartan", "Helmet", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.DIAMOND_SWORD), "Spartan", "Sword", 1));
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
			p.getInventory().addItem(metanate(bow, "Spartan", "Bow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.ARROW), "Soldier", "Arrow", 1));
			p.getInventory().addItem(metanate(new ItemStack(Material.GRILLED_PORK), "Soldier", "BACON!", 64));
			return;
		}
		p.sendMessage(ChatColor.RED + "Unknown kit \"" + args[0] + "\"");
		p.sendMessage(ChatColor.RED + "/kits");
		return;
	}
	public static void clearKit(Player p){
		try{
			for(ItemStack i : p.getInventory().getContents()){
				if(i.getItemMeta().hasLore()){
					p.getInventory().remove(i);
				}
			}
		}catch(NullPointerException npe){
		}
		try{
			if(p.getInventory().getBoots().getItemMeta().hasLore()){
				p.getInventory().setBoots(null);
			}
		}catch(NullPointerException npe){
		}
		try{
			if(p.getInventory().getLeggings().getItemMeta().hasLore()){
				p.getInventory().setLeggings(null);
			}
		}catch(NullPointerException npe){
		}
		try{
			if(p.getInventory().getChestplate().getItemMeta().hasLore()){
				p.getInventory().setChestplate(null);
			}
		}catch(NullPointerException npe){
		}
		try{
			if(p.getInventory().getHelmet().getItemMeta().hasLore()){
				p.getInventory().setHelmet(null);
			}
		}catch(NullPointerException npe){
		}
	}
	public static ItemStack metanate(ItemStack i, String kit, String itemName, Integer amount){
		ItemMeta meta = i.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED + kit);
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6" + itemName));
		i.setItemMeta(meta);
		i.setAmount(amount);
		return i;
	}
	@EventHandler
	public void command(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		if(e.getMessage().startsWith("/warp kitvip") && !p.hasPermission("starrealms.soldier")){
			p.sendMessage(ChatColor.GOLD + "Sorry, recruit, only high ranked members of this military get to go to that arena. Donate at our website for access. The lowest donor rank costs $5, and will let you in here. So donate now private!");
			e.setCancelled(true);
			return;
		}
	}
	@EventHandler
	public void itemDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase("kitpvp") && e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasLore()){
			p.sendMessage(ChatColor.RED + "Too prevent kit mixing, StarRealms does not allow you to drop items that come from kits. If you were trying to dispose of the item, feel free to use the disposal signs at the KitPvP spawn. =)");
			e.setCancelled(true);
			return;
		}
	}
}

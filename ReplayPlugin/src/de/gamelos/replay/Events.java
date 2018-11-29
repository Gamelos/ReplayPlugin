package de.gamelos.replay;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;

public class Events implements Listener {
	
	@EventHandler
	public void ondes(BlockBreakEvent e){
		if(!Main.destroy.containsKey(Main.frame)){
			Main.destroy.put(Main.frame, e.getBlock());
		}
	}
	
	@EventHandler
	public void onbuild(BlockPlaceEvent e){
		if(!Main.build.containsKey(Main.frame)){
			Main.build.put(Main.frame, e.getBlock());
			Main.build2.put(e.getBlock().getLocation(), e.getBlock().getType());
			Main.blockfile.put(e.getBlock().getLocation(), e.getBlock().getData());
		}
	}
	
	@EventHandler
	public void ond(BlockDamageEvent e){
		if(!Main.damage.containsKey(Main.frame)){
			Main.damage.put(Main.frame, e.getBlock());
		}
	}
	
	@EventHandler
	public static void burn(EntityDamageEvent e){
		if(e.getCause() == DamageCause.FIRE){
			if(!Main.burn.containsKey(Main.frame)){
				Main.burn.put(Main.frame, "t");
			}
		}
	}
	
	@EventHandler
	public void ont(EntityThrownExpBottle e){
		if(!Main.exp.containsKey(Main.frame)){
			Main.exp.put(Main.frame, "t");
		}
	}
	
	@EventHandler
	public void onttt(ProjectileHitEvent e){
		if(!Main.snow.containsKey(Main.frame)){
			Main.snow.put(Main.frame, e.getEntity());
		}
	}
	@SuppressWarnings("unchecked")
	@EventHandler
	public static void onmov(PlayerMoveEvent e){
		HashMap<Integer,Location> ploc = new HashMap<>();
		if(Main.l1.containsKey(e.getPlayer().getName())){
		ploc = Main.l1.get(e.getPlayer().getName());
		}
		if(!ploc.containsKey(Main.frame)){
			ploc.put(Main.frame, (Location) e.getPlayer().getLocation());
			Main.l1.put(e.getPlayer().getName(), ploc);
		}
	}
	@SuppressWarnings("unchecked")
	@EventHandler
	public static void onmov(PlayerDeathEvent e){
		HashMap<Integer,Player> pdeath = new HashMap<>();
		if(Main.l2.containsKey(e.getEntity().getName())){
			pdeath = Main.l2.get(e.getEntity().getName());
		}
		if(!pdeath.containsKey(Main.frame)){
			pdeath.put(Main.frame, e.getEntity());
			Main.l2.put(e.getEntity().getName(), pdeath);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onrespawn(PlayerRespawnEvent e){
		HashMap<Integer,Location> pspawn = new HashMap<>();
		if(Main.l3.containsKey(e.getPlayer().getName())){
			pspawn = Main.l3.get(e.getPlayer().getName());
		}
		if(!pspawn.containsKey(Main.frame)){
			pspawn.put(Main.frame, e.getPlayer().getLocation());
			Main.l3.put(e.getPlayer().getName(), pspawn);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onrespawn(EntityDamageByEntityEvent e){
		HashMap<Integer,String> hit = new HashMap<>();
		if(Main.l6.containsKey(e.getEntity().getName())){
			hit = Main.l6.get(e.getEntity().getName());
		}
		if(!hit.containsKey(Main.frame)){
			hit.put(Main.frame, "h");
			Main.l6.put(e.getEntity().getName(), hit);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void fasfasddf(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			HashMap<Integer,Player> pdamage = new HashMap<>();
			if(Main.l4.containsKey(e.getEntity().getName())){
				pdamage = Main.l4.get(e.getEntity().getName());
			}
		if(!pdamage.containsKey(Main.frame)){
			pdamage.put(Main.frame, (Player)e.getEntity());
			Main.l4.put(e.getEntity().getName(), pdamage);
		}
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onrü(InventoryClickEvent e){
		HashMap<Integer,Inventory> rüstung = new HashMap<>();
		if(Main.l7.containsKey(e.getWhoClicked().getName())){
			rüstung =Main. l7.get(e.getWhoClicked().getName());
		}
		rüstung.put(Main.frame, e.getWhoClicked().getInventory());
		Main.l7.put(e.getWhoClicked().getName(), Main.l7);
		HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
		if(Main.l9.containsKey(e.getWhoClicked().getName())){
			iteminHand = Main.l9.get(e.getWhoClicked().getName());
		}
			if(!iteminHand.containsKey(Main.frame)){
				iteminHand.put(Main.frame, e.getWhoClicked().getItemInHand());
				Main.l9.put(e.getWhoClicked().getName(), Main.l9);
			}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void oni(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK){
			HashMap<Integer,Inventory> rüstung = new HashMap<>();
			if(Main.l7.containsKey(e.getPlayer().getName())){
				rüstung = Main.l7.get(e.getPlayer().getName());
			}
			rüstung.put(Main.frame, e.getPlayer().getInventory());
			Main.l7.put(e.getPlayer().getName(), rüstung);
		}
		
		if(e.getAction() == Action.LEFT_CLICK_AIR||e.getAction() == Action.LEFT_CLICK_BLOCK){
			HashMap<Integer,String> leftc = new HashMap<>();
			if(Main.l8.containsKey(e.getPlayer().getName())){
				leftc = Main.l8.get(e.getPlayer().getName());
			}
			leftc.put(Main.frame, "s");
			Main.l8.put(e.getPlayer().getName(), leftc);
		}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType() != Material.AIR){
				HashMap<Integer,String> leftc = new HashMap<>();
				if(Main.l8.containsKey(e.getPlayer().getName())){
					leftc = Main.l8.get(e.getPlayer().getName());
				}
				leftc.put(Main.frame, "s");
				Main.l8.put(e.getPlayer().getName(), leftc);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onscroll(PlayerItemHeldEvent e){
		HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
		if(Main.l9.containsKey(e.getPlayer().getName())){
			iteminHand = Main.l9.get(e.getPlayer().getName());
		}
		if(!iteminHand.containsKey(Main.frame)){
			iteminHand.put(Main.frame, e.getPlayer().getItemInHand());
			Main.l9.put(e.getPlayer().getName(), Main.l9);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onitem(PlayerPickupItemEvent e){
		HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
		if(Main.l9.containsKey(e.getPlayer().getName())){
			iteminHand = Main.l9.get(e.getPlayer().getName());
		}
		if(!iteminHand.containsKey(Main.frame)){
			iteminHand.put(Main.frame, e.getPlayer().getItemInHand());
			Main.l9.put(e.getPlayer().getName(), iteminHand);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onitem(PlayerDropItemEvent e){
		HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
		if(Main.l9.containsKey(e.getPlayer().getName())){
			iteminHand = Main.l9.get(e.getPlayer().getName());
		}
		if(!iteminHand.containsKey(Main.frame)){
			iteminHand.put(Main.frame, e.getPlayer().getItemInHand());
			Main.l9.put(e.getPlayer().getName(), iteminHand);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public static void ons(PlayerToggleSneakEvent e){
		HashMap<Integer,String> sneak = new HashMap<>();
		if(Main.l5.containsKey(e.getPlayer().getName())){
			sneak = Main.l5.get(e.getPlayer().getName());
		}
		if(e.isSneaking()){
			if(!sneak.containsKey(Main.frame)){
				sneak.put(Main.frame, "e");
				Main.l5.put(e.getPlayer().getName(), sneak);
			}
		}else{
			if(!sneak.containsKey(Main.frame)){
				sneak.put(Main.frame, "s");
				Main.l5.put(e.getPlayer().getName(), sneak);
			}
		}
	}
	
	@EventHandler
	public void onchat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		int data = Main.frame;
		if(Main.msg.containsKey(Main.frame)){
			data = Main.frame+100000000;
		}
		Main.msg.put(data, p.getDisplayName()+ChatColor.DARK_GRAY+" >> "+ChatColor.GRAY+e.getMessage());
		
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onjoin(PlayerJoinEvent e){
		HashMap<Integer,Location> pspawn = new HashMap<>();
		if(Main.l3.containsKey(e.getPlayer().getName())){
			pspawn = Main.l3.get(e.getPlayer().getName());
		}
		if(!pspawn.containsKey(Main.frame)){
			pspawn.put(Main.frame, e.getPlayer().getLocation());
			Main.l3.put(e.getPlayer().getName(), pspawn);
		}
		HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
		if(Main.l9.containsKey(e.getPlayer().getName())){
			iteminHand = Main.l9.get(e.getPlayer().getName());
		}
		if(!iteminHand.containsKey(Main.frame)){
			iteminHand.put(Main.frame, e.getPlayer().getItemInHand());
			Main.l9.put(e.getPlayer().getName(), iteminHand);
		}
		HashMap<Integer,Inventory> rüstung = new HashMap<>();
		if(Main.l7.containsKey(e.getPlayer().getName())){
			rüstung = Main.l7.get(e.getPlayer().getName());
		}
		if(!rüstung.containsKey(Main.frame)){
		rüstung.put(Main.frame, e.getPlayer().getInventory());
		Main.l7.put(e.getPlayer().getName(), rüstung);
		}
	}
	
	@SuppressWarnings("unchecked")
	@EventHandler
	public void onqu(PlayerQuitEvent e){
		HashMap<Integer,Player> quit = new HashMap<>();
		if(Main.l10.containsKey(e.getPlayer().getName())){
			quit = Main.l10.get(e.getPlayer().getName());
		}
		if(!quit.containsKey(Main.frame)){
			quit.put(Main.frame, e.getPlayer());
			Main.l10.put(e.getPlayer().getName(), quit);
		}
	}
}

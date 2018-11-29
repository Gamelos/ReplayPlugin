package de.gamelos.replay;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockBreakAnimation;



@SuppressWarnings("deprecation")
public class Main extends JavaPlugin implements Listener{

	public static String Prefix = ChatColor.DARK_GRAY+"["+ChatColor.YELLOW+"REPLAY"+ChatColor.DARK_GRAY+"] "+ChatColor.GRAY;
	
	
	@Override
	public void onEnable() {
		System.out.println("[ReplayPlugin] Das Plugin wurde geladen!");
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getCommand("play").setExecutor(this);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		System.out.println("[ReplayPlugin] Das Plugin wurde deaktiviert!");
		super.onDisable();
	}
	
	static int frame = 0 ;
	
	public static NPC npc = null;
	

	
	public static HashMap<Integer,Collection<PotionEffect>> effect = new HashMap<>();
	
	static int count = 0;
	public static void frametimer(){
		count = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Replay"), new Runnable() {
			
			@Override
			public void run() {
				frame++;
//				============================
				for(Player pp:Bukkit.getOnlinePlayers()){
					if(effect.containsKey(frame)){
						effect.put(frame, pp.getActivePotionEffects());
					}
				}
//				============================
			}
		}, 0, 1);
	}
	
	public static HashMap<Integer,String> msg = new HashMap<>();
	String name = null;

	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("play")){
			
//			______________________________________________
			Bukkit.getScheduler().cancelTask(count);
			Bukkit.broadcastMessage(Prefix+"Das Replay wird geladen ...");
			FakePlayer player = new FakePlayer(((Player)sender).getName());
			player.load();
			Bukkit.getScheduler().scheduleAsyncDelayedTask(this, new Runnable(){
				@Override
				public void run() {
					player.start();
				}
			}, 20*5);
//			______________________________________________
			
		}else if(cmd.getName().equals("record")){
			if(args[0].equals("start")){
//				====================================
				File file = new File("plugins/Replays/"+args[1]);
				name = args[1];
				if(!file.exists()){
					file.mkdir();
					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
						@Override
						public void run() {
							kopyWorld(((Player)sender).getWorld().getName(), "plugins/Replays/"+args[1]+"/world");
						}
					});
//				====================================
				Bukkit.broadcastMessage(Prefix+ChatColor.GREEN+"Die Aufnahme wurde gestartet ...");
				frametimer();
				for(Player pp : Bukkit.getOnlinePlayers()){
					HashMap<Integer,Location> pspawn = new HashMap<>();
					if(l3.containsKey(pp.getName())){
						pspawn = l3.get(pp.getName());
					}
					if(!pspawn.containsKey(frame)){
						pspawn.put(frame, pp.getLocation());
						l3.put(pp.getName(), pspawn);
					}
					HashMap<Integer,ItemStack> iteminHand = new HashMap<>();
					if(l9.containsKey(pp.getName())){
						iteminHand = l9.get(pp.getName());
					}
					if(!iteminHand.containsKey(frame)){
						iteminHand.put(frame, pp.getItemInHand());
						l9.put(pp.getName(), iteminHand);
					}
					HashMap<Integer,Inventory> rüstung = new HashMap<>();
					if(l7.containsKey(pp.getName())){
						rüstung = l7.get(pp.getName());
					}
					if(!rüstung.containsKey(frame)){
					rüstung.put(frame, pp.getInventory());
					l7.put(pp.getName(), l7);
					}
				}
				}else{
					Bukkit.broadcastMessage(Prefix+ChatColor.RED+"Der Name ist schon Belegt!");
				}
			}else{
				Bukkit.getScheduler().cancelTask(count);
				Bukkit.broadcastMessage(Prefix+ChatColor.RED+"Die Aufnahme wurde gestoppt ...");
				Bukkit.broadcastMessage(Prefix+"Alle Daten werden gespeichert ...");
				save(name);
				
			}
		}
		return super.onCommand(sender, cmd, label, args);
	}
	
	public static HashMap<Block,Integer> blockdata = new HashMap<>();
	
	
	public static void sendBlockBreak(Location loc,Integer level){
		PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(0, new BlockPosition(loc.getX(),loc.getY(),loc.getZ()), level);
		((CraftServer)Bukkit.getServer()).getHandle().sendPacketNearby(loc.getX(),loc.getY(),loc.getZ(),30, ((CraftWorld)loc.getWorld()).getHandle().dimension, packet);
	}
	
	
public static void kopyWorld(String worldtocopyname, String newname){
		
		Bukkit.getServer().unloadWorld(worldtocopyname, true);
		
		try {
			FileUtils.copyDirectory(new File(worldtocopyname), new File(newname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Bukkit.createWorld(new WorldCreator(newname));
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l1 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l2 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l3 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l4 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l5 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l6 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l7 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l8 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l9 = new HashMap<>();
	@SuppressWarnings("rawtypes")
	public static HashMap<String,HashMap> l10 = new HashMap<>();
	
//	public static HashMap<Integer,Location> ploc = new HashMap<>();
//	public static HashMap<Integer,Player> pdeath = new HashMap<>();
//	public static HashMap<Integer,Location> pspawn = new HashMap<>();
//	public static HashMap<Integer,Player> pdamage = new HashMap<>();
//	public static HashMap<Integer,String> sneak = new HashMap<>();
//	public static HashMap<Integer,String> hit = new HashMap<>();
//	public static HashMap<Integer,PlayerInventory> rüstung = new HashMap<>();
//	public static HashMap<Integer,String> leftc = new HashMap<>();
//	public static HashMap<Integer,org.bukkit.inventory.ItemStack> iteminHand = new HashMap<>();
	

	
	public static HashMap<Integer,Block> build = new HashMap<>();
	public static HashMap<Location,Material> build2 = new HashMap<>();
	public static HashMap<Location,Byte> blockfile = new HashMap<>();
	public static HashMap<Integer,Block> destroy = new HashMap<>();
	public static HashMap<Integer,Block> damage = new HashMap<>();
	public static HashMap<Integer,String> burn = new HashMap<>();
	public static HashMap<Integer,String> eat = new HashMap<>();
	public static HashMap<Integer,Entity> snow = new HashMap<>();
	public static HashMap<Integer,String> exp = new HashMap<>();

	public static File locations;
	public static FileConfiguration loc;
	
	public static void save(String name){
		for(String bb:l1.keySet()){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l1.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,Location> b = l1.get(bb);
			for(Integer bb1 : b.keySet()){
				Main.loc.set(""+bb1, b.get(bb1).getX()+","+b.get(bb1).getY()+","+b.get(bb1).getZ());
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
//			=============================================
			if(l2.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l2.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,Player> b1 = l2.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, "death");
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
			if(l3.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l3.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,Location> b1 = l3.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, b1.get(bb1).getX()+","+b1.get(bb1).getY()+","+b1.get(bb1).getZ());
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
			if(l4.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l4.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,Player> b1 = l4.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, "damage");
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
			if(l5.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l5.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,String> b1 = l5.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, b1.get(bb1));
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
			if(l6.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l6.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,String> b1 = l6.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, b1.get(bb1));
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
			if(l7.containsKey(bb)){
			Main.locations = new File("plugins/Replays/"+name+"/"+bb, "l7.yml");
			Main.loc = YamlConfiguration.loadConfiguration(Main.locations);
			@SuppressWarnings("unchecked")
			HashMap<Integer,String> b1 = l7.get(bb);
			for(Integer bb1 : b1.keySet()){
				Main.loc.set(""+bb1, b1.get(bb1));
				try {
					Main.loc.save(Main.locations);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
			}
//			=============================================
		}
	}
	
	
	
}

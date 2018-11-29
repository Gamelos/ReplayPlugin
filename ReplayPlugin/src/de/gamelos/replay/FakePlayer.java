package de.gamelos.replay;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;

public class FakePlayer {
	
	private String playername;
	
	public FakePlayer(String playername){
		this.playername = playername;
	}
	
	public static int count2 = 0;
	public static int frameanzahl = 0;
	private HashMap<Integer,Location> ploc = new HashMap<>();
	private HashMap<Integer,Player> pdeath= new HashMap<>();
	private  HashMap<Integer,Location> pspawn= new HashMap<>();
	private HashMap<Integer,Player> pdamage= new HashMap<>();
	private HashMap<Integer,String> sneak = new HashMap<>();
	private HashMap<Integer,String> hit= new HashMap<>();
	private HashMap<Integer,PlayerInventory> rüstung= new HashMap<>();
	private HashMap<Integer,PlayerInventory> leftc= new HashMap<>();
	private HashMap<Integer,org.bukkit.inventory.ItemStack> iteminHand= new HashMap<>();
	private HashMap<Integer,Player> quit= new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public void load(){
		
//		=======================================================================
		if(Main.l1.containsKey(playername)){
			this.ploc = Main.l1.get(playername);
		}
		
		if(Main.l2.containsKey(playername)){
			this.pdeath = Main.l2.get(playername);
		}
		
		if(Main.l3.containsKey(playername)){
			this.pspawn = Main.l3.get(playername);
		}
		if(Main.l4.containsKey(playername)){
			this.pdamage = Main.l4.get(playername);
		}
		if(Main.l5.containsKey(playername)){
			this.sneak = Main.l5.get(playername);
		}
		if(Main.l6.containsKey(playername)){
			this.hit = Main.l6.get(playername);
		}
		if(Main.l7.containsKey(playername)){
			this.rüstung = Main.l7.get(playername);
		}
		if(Main.l8.containsKey(playername)){
			this.leftc = Main.l8.get(playername);
		}
		if(Main.l9.containsKey(playername)){
			this.iteminHand = Main.l9.get(playername);
		}
		
		if(Main.l10.containsKey(playername)){
			this.quit = Main.l10.get(playername);
		}
//		=======================================================================
	}
	
@SuppressWarnings("deprecation")
public void start(){
	
	Bukkit.broadcastMessage(Main.Prefix+ChatColor.GREEN+"Das Replay wurde gestartet!");
	frameanzahl = Main.frame;
	count2 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Replay"), new Runnable(){
		int i = 0;
		NPC npc = null;
		
		@Override
		public void run() {
			if(i<=frameanzahl){
//				=============================================================
//				MESSAGE
				if(Main.msg.containsKey(i)){
					Bukkit.broadcastMessage(Main.msg.get(i));
					if(Main.msg.containsKey(i+100000000)){
						Bukkit.broadcastMessage(Main.msg.get(i+100000000));
					}
				}
//				==============================================================
//				MOVEMENT
				if(ploc.containsKey(i)){
					if(npc != null){
					npc.teleport(ploc.get(i));
					}
				}
//				==============================================================
//				DEATH
				if(pdeath.containsKey(i)){
					if(npc != null){
					npc.status(3);
					}
				}
//				==============================================================
//				QUIT
				if(quit.containsKey(i)){
					if(npc != null){
					npc.destroy();
					}
				}
//				==============================================================
//				RESPAWN
				if(pspawn.containsKey(i)){
					npc = new NPC(ChatColor.AQUA+playername, pspawn.get(i));
					npc.spawn();
				}
//				==============================================================
//				DAMAGE
				if(pdamage.containsKey(i)){
					if(npc != null){
						npc.status(2);
					}
				}
//				==============================================================
//				HIT
				if(hit.containsKey(i)){
					if(npc != null){
							npc.animation(4);
							npc.status(17);
					}
				}
//				==============================================================
//				RÜSTUNG
				if(rüstung.containsKey(i)){
					Inventory inv = rüstung.get(i);
					
					
					if(inv.getSize() >=35){
					org.bukkit.inventory.ItemStack boots = inv.getItem(36);
					if(boots != null){
					if(boots.getType() != Material.AIR){
					Item i1=Item.getById(boots.getTypeId());
					if(i1!=null){
						if(npc != null){
					npc.equip(1, new ItemStack(i1));
						}
					}
					}
					}
					}
					
					if(inv.getSize() >=36){
					org.bukkit.inventory.ItemStack leggings = inv.getItem(37);
					if(leggings != null){
					if(leggings.getType() != Material.AIR){
					Item i2=Item.getById(leggings.getTypeId());
					if(i2!=null){
						if(npc != null){
					npc.equip(2, new ItemStack(i2));
						}
					}
					}
					}
					}
					
					if(inv.getSize() >=37){
					org.bukkit.inventory.ItemStack Chestplate = inv.getItem(38);
					if(Chestplate != null){
					if(Chestplate.getType() != Material.AIR){
					Item i3=Item.getById(Chestplate.getTypeId());
					if(i3!=null){
						if(npc != null){
					npc.equip(3, new ItemStack(i3));
						}
					}
					}
					}
					}
					
					if(inv.getSize() >=38){
					org.bukkit.inventory.ItemStack helmet = inv.getItem(39);
					if(helmet != null){
					if(helmet.getType() != Material.AIR){
					Item i4=Item.getById(helmet.getTypeId());
					if(i4!=null){
						if(npc != null){
							npc.equip(4, new ItemStack(i4));
						}
					}
					}
					}
					}
				}
//				==============================================================
//				SNEAK
				if(sneak.containsKey(i)){
						if(sneak.get(i).equals("e")){
//							SNEAKING
							if(npc != null){
						npc.sendMetadataPacket("sneaking");
							}
						}else{
//							ENTSNEAK
							if(npc != null){
							npc.sendMetadataPacket("unsneaking");
							}
						}
				}
//				==============================================================#
//				ITEMINHAND
				if(iteminHand.containsKey(i)){
					org.bukkit.inventory.ItemStack item = iteminHand.get(i);
					if(item!=null){
						if(npc != null){
							npc.equip(0, new ItemStack(Item.getById(item.getTypeId())));
						}
					}
				}
//				==============================================================
//				LCLICK
				if(leftc.containsKey(i)){		
					if(npc != null){
					npc.animation(0);
					}
				}
//				==============================================================
//				EAT
//				if(eat.containsKey(i)){						
//					npc.sendMetadataPacket("eat");
//				}
//				==============================================================
//				fire
//				if(burn.containsKey(i)){						
//					npc.sendMetadataPacket("fire");
//				}
//				==============================================================
//				SNOWBALL
//				if(snow.containsKey(i)){						
					
//				}
//				==============================================================
//				BUILD
				if(Main.build.containsKey(i)){						
					Block b = Main.build.get(i);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Replay"), new Runnable(){
						@Override
						public void run() {
							b.getWorld().getBlockAt(b.getLocation()).setType(Main.build2.get(b.getLocation()));
							b.getWorld().getBlockAt(b.getLocation()).setData(Main.blockfile.get(b.getLocation()));
							
							if(b.getType() == Material.GLASS){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_GRASS, 1F, 1F);
							}else if(b.getType() == Material.GRAVEL){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_GRAVEL, 1F, 1F);
							}else if(b.getType() == Material.SAND){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_SAND, 1F, 1F);
							}else if(b.getType() == Material.SNOW_BLOCK){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_SNOW, 1F, 1F);
							}else if(b.getType() == Material.WOOD||b.getType() == Material.LOG){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_WOOD, 1F, 1F);
							}else if(b.getType() == Material.WOOD){
							b.getWorld().playSound(b.getLocation(), Sound.STEP_WOOL, 1F, 1F);
							}else{
								b.getWorld().playSound(b.getLocation(), Sound.STEP_STONE, 1F, 1F);
							}
						}
					});
				}
//				==============================================================
//				DESTROY
				if(Main.destroy.containsKey(i)){						
					Block b = Main.destroy.get(i);
					b.getLocation().getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getTypeId());	
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Replay"), new Runnable(){
						@Override
						public void run() {
							b.getWorld().getBlockAt(b.getLocation()).setType(Material.AIR);
						}
					});
				}
//				==============================================================
//				DAMAGE
				if(Main.damage.containsKey(i)){						
					Block b = Main.damage.get(i);
					Bukkit.broadcastMessage(b.getLocation().toString());
					if(Main.blockdata.containsKey(b)){
						int i = Main.blockdata.get(b);
						i++;
						Main.blockdata.remove(b);
						Main.blockdata.put(b, i);
					}else{
						Main.blockdata.put(b, 1);
					}	
					Main.sendBlockBreak(b.getLocation(), Main.blockdata.get(b));
					
					
				}
//				==============================================================
				i++;
			}else{
				i = 0;
				Bukkit.getScheduler().cancelTask(count2);
				if(npc != null){
				npc.destroy();
				}
				Bukkit.broadcastMessage(Main.Prefix+ChatColor.RED+"Das Replay wurde gestoppt!");
			}
		}
	}, 0, 1);
}
}

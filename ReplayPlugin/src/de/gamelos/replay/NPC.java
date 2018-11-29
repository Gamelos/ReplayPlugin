package de.gamelos.replay;

import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutBed;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class NPC extends Reflections {

	
	private int entityID;
	private Location location;
	private GameProfile gameprofile;
	
	
	public NPC(String name,Location location){
		entityID = (int)Math.ceil(Math.random() * 1000) + 2000;
		gameprofile = new GameProfile(UUID.randomUUID(), name);
		changeSkin(name);
		this.location = location.clone();
	}
	
	@SuppressWarnings("deprecation")
	public void changeSkin(String playername){
		String value = "eyJ0aW1lc3RhbXAiOjE1MTIzMDA4NzA3NjAsInByb2ZpbGVJZCI6ImUxODRmNDhiMDgxMTQwYWI4OWJkMGI5MTZhNzA4ZmJiIiwicHJvZmlsZU5hbWUiOiJza2lsbHB2cDAwNSIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM4Y2QxODZlZTRhMzk1MzEzNDc1YjI2ZTNkMGVmNGU4ODgxNmQ4ZjEyY2FiZGFjMmJmNTZjNDdmZGM1MyJ9fX0=";
		String signature = "ud7M7hf0lZE4Bt8gtimAjI/HRl+mlgEsxItWQjUwirm5A1F0yl15ENsj2b/hQh2KTyz5ifVTB410PrmOIp4/JIDeeSNEvRMwfO5UGgjFjwolfKmCOqZm9/bHHWb/Hyq6kEZC4wmlJrJJvAGGYEQQLofJ2FZrKPyMLcNmiJG8oVQcePhU3cuTm3MED6MXZ1yihnrHaCDPMvflVMeXRQ90ihNTzAdNM9lneN/+UXeT6oGJDSE05BMJqefQhnfHuq5dMsJnIOGwTuDPR3SML+Q+e0DZUo+UNcPrYTJ/vRQ/Cl0g8Hiv+431zU/zmtXxK3YBNlRebZ3JQvNz7VeL4075u6XV0fP8JeyOOseuGHsF3CFqA4IiWQKuJ7OIY1AbCbztNmqtuIW+wyWrk2R9UALwfesUhG4T5xOVESuqv2jk6qS0+u2w0PeFQdcIGuy40S/xPtSmHuqcnaxQ3PmTHOTW0mQjsjRN/3nZcPcx0oGtlHSOR9FTTktGn+YqFmm6J3BRoUCuunuB7colXmWJOzfUdzGldT6fPBFbFn5DzGetOQFtlSxzxSn3mALZcDRpycyIwEJplj9QDqU+Il6kJiK45BW12jKeCrHvcHpVnQMafXurRQWGQQI0csST8O0ZiRgxM45Ke6WR0YeDnnzmE8NhDLZ/lxcy1Kjl16PGPuIwfc4=";
		gameprofile.getProperties().put("textures", new Property("textures", value, signature));
	}
	
	
	public void animation(int animation){
		PacketPlayOutAnimation packet = new PacketPlayOutAnimation();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)animation);
		sendPacket(packet);
	}
	
	public void sendMetadataPacket(String type)
    {
        DataWatcher dw = new DataWatcher(null);

        if(type.equals("sneaking"))
            dw.a(0, (byte) 0x02);
       
        else
        	if(type.equals("unsneaking"))
                dw.a(0, (byte) 0x10);
            else
            	if(type.equals("fire"))
                    dw.a(0, (byte) 0x01);
            	else
                	if(type.equals("eat"))
                        dw.a(0, (byte) 0x03);
        else
            return;
       
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(entityID, dw, true);
        sendPacket(metadataPacket);
    }
	
	public byte changeMask(byte bitMask, int bit, boolean state){
		if(state)
			return bitMask |=1 << bit;
		else
			return bitMask &= ~(1 << bit);
	}
	
	public void status(int status){
		PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus();
		setValue(packet, "a", entityID);
		setValue(packet, "b", (byte)status);
		sendPacket(packet);
	}
	
	public void equip(int slot,ItemStack itemStack){
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
		setValue(packet, "a", entityID);
		setValue(packet, "b", slot);
		setValue(packet, "c", itemStack);
		sendPacket(packet);
	}
	
	@SuppressWarnings("deprecation")
	public void sleep(boolean state){
		if(state){
			Location bedLocation = new Location(location.getWorld(), 1, 1, 1);
			PacketPlayOutBed packet = new PacketPlayOutBed();
			setValue(packet, "a", entityID);
			setValue(packet, "b", new BlockPosition(bedLocation.getX(),bedLocation.getY(),bedLocation.getZ()));
			
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
			}

			sendPacket(packet);
			teleport(location.clone().add(0,0.3,0));
		}else{
			animation(2);
			teleport(location.clone().subtract(0,0.3,0));
		}
	}
	
	public void spawn(){
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
		
		setValue(packet, "a", entityID);
		setValue(packet, "b", gameprofile.getId());
		setValue(packet, "c", getFixLocation(location.getX()));
		setValue(packet, "d", getFixLocation(location.getY()));
		setValue(packet, "e", getFixLocation(location.getZ()));
		setValue(packet, "f", getFixRotation(location.getYaw()));
		setValue(packet, "g", getFixRotation(location.getPitch()));
		setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.a(6,(float)20);
		w.a(10,(byte)127);
		setValue(packet, "i", w);
		addToTablist();
		sendPacket(packet);
		headRotation(location.getYaw(), location.getPitch());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("Replay"), new Runnable(){
			@Override
			public void run() {
			rmvFromTablist();
			}
		},5);
	}
	
	public void teleport(Location location){
		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport();
		setValue(packet, "a", entityID);
		setValue(packet, "b", getFixLocation(location.getX()));
		setValue(packet, "c", getFixLocation(location.getY()));
		setValue(packet, "d", getFixLocation(location.getZ()));
		setValue(packet, "e", getFixRotation(location.getYaw()));
		setValue(packet, "f", getFixRotation(location.getPitch()));

		sendPacket(packet);
		headRotation(location.getYaw(), location.getPitch());
		this.location = location.clone();
	}
	
	public void headRotation(float yaw,float pitch){
		PacketPlayOutEntityLook packet = new PacketPlayOutEntityLook(entityID, getFixRotation(yaw),getFixRotation(pitch) , true);
		PacketPlayOutEntityHeadRotation packetHead = new PacketPlayOutEntityHeadRotation();
		setValue(packetHead, "a", entityID);
		setValue(packetHead, "b", getFixRotation(yaw));
		

		sendPacket(packet);
		sendPacket(packetHead);
	}
	
	public void destroy(){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {entityID});
		rmvFromTablist();
		sendPacket(packet);
	}
	
	public void addToTablist(){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public void rmvFromTablist(){
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(gameprofile, 1, EnumGamemode.NOT_SET, CraftChatMessage.fromString(gameprofile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) getValue(packet, "b");
		players.add(data);
		
		setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
		setValue(packet, "b", players);
		
		sendPacket(packet);
	}
	
	public int getFixLocation(double pos){
		return (int)MathHelper.floor(pos * 32.0D);
	}
	
	public int getEntityID() {
		return entityID;
	}
	
	public byte getFixRotation(float yawpitch){
		return (byte) ((int) (yawpitch * 256.0F / 360.0F));
	}
	
}
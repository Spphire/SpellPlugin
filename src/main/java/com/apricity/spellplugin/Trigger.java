package com.apricity.spellplugin;


import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Particle.SpawnOneParticle;
import com.apricity.spellplugin.Particle.SpawnParticles;
import com.apricity.spellplugin.Skill.SkillDeterminer;
import com.apricity.spellplugin.Skill.skills.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import java.util.Vector;

public class Trigger implements Listener {
    private Plugin p;

    public Trigger(Plugin p) {this.p = p; }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        //当玩家进入服务器时，会在玩家身边播放音效
        Location location = player.getLocation();
        World world = location.getWorld();

        //并且修改玩家进入服务器时的全局广播提示
        player.sendMessage(ChatColor.GREEN + "构造"+player.getName()+"的监听器");
        Detector detector = new Detector(this.p, player);
        detector.runTaskAsynchronously(this.p);
        if (((SpellPlugin) this.p).PlayerDetectorMap.containsKey(player)){
            Bukkit.getScheduler().cancelTask(((SpellPlugin) this.p).PlayerDetectorMap.get(player).getTaskId());
            ((SpellPlugin) this.p).PlayerDetectorMap.get(player).Locations.removeAllElements();
            ((SpellPlugin) this.p).PlayerDetectorMap.remove(player);
        }
        ((SpellPlugin) this.p).PlayerDetectorMap.put(player,detector);
        //player.getWorld().spawnParticle();
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    public void onPlayerLogOut(PlayerQuitEvent playerQuitEvent){
        Player player = playerQuitEvent.getPlayer();
        System.out.println(player.getName()+"登出");
        ((SpellPlugin)p).PlayerDetectorMap.get(player).cancel();
        ((SpellPlugin)p).PlayerDetectorMap.remove(player);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event){
        SpellPlugin newP = (SpellPlugin) p;
        Player player = event.getPlayer();
        if (!newP.PlayerDetectorMap.containsKey(player)){
            return;
        }
        Detector detector = newP.PlayerDetectorMap.get(player);
        detector.updateAttributes(player.getEquipment().getItemInMainHand(),player.getEquipment().getItemInOffHand());
        Vector<org.bukkit.Location> locations = detector.Locations;
        //EquipmentSlot.HAND
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(locations.size()<detector.pointsNum && Math.abs(player.getEyeLocation().getPitch())<detector.pitchLimit){
                if(locations.size()==0){
                    locations.add(player.getEyeLocation());
                    if(newP.getDebugOn()){
                        player.sendMessage("add");
                    }
                }else if(!Of2Points.isTooCLose(player.getEyeLocation(),locations.get(locations.size()-1))){
                    locations.add(player.getEyeLocation());
                    if(newP.getDebugOn()){
                        player.sendMessage("add");
                    }
                }
            }
        }else if (event.getAction().equals(Action.LEFT_CLICK_AIR)||event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Skill skillShape = SkillDeterminer.SkillShape(locations,detector.tolerance);
            //player.sendMessage("spell is "+skillShape);
            boolean isTriggered = true;
            switch (skillShape){
                case WRONG_SKILL:
                    isTriggered = false;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("结点连接似乎不稳定"));
                    break;
                case TEST:
                    //new Slow(p,player,10).runTaskAsynchronously(p);
                    if(player.isOp()){
                        new AvadaKedavra(p,player,15).runTaskAsynchronously(p);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("阿瓦达啃大瓜"));
                    }
                    break;
                case SWEEP:
                    new Sweep(p, player, 5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("横斩"));
                    break;
                case HALF_MOON_SLASH:
                    new HalfMoonSlash(p, player, 5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("半月斩"));
                    break;
                case PLACE_HOLDER_leftupright:
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("^"));
                    break;
                case FIRE_BALL:
                    new FireBall(p,player,5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("火球术"));
                    break;
                case SELF_HEALING:
                    new SelfHeal(p,player,5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("治疗"));
                    break;
                case SPEED_UP:
                    new SpeedUp(p,player,0).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("加速"));
                    break;
                case RANGE_HEALING:
                    new RangeHealing(p,player,1000,10000,10*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("范围治疗"));
                    break;
                case BITE:
                    new Bite(p, player, 5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("w"));
                    break;
                case PLACE_HOLDER_ww:
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("ww"));
                    break;
                case PLACE_HOLDER_OAA:
                    new LightWave(p, player, 5*detector.strength).runTaskAsynchronously(p);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("OAA"));
                    break;
                default:
                    isTriggered = false;
                    break;
            }
            Vector<Location> tempLoc = (Vector<Location>) locations.clone();
            newP.PlayerDetectorMap.get(player).Locations.removeAllElements();
            SpawnParticles.successfulSpellEffect(tempLoc,player);
            Bukkit.getScheduler().runTaskAsynchronously(p, new Runnable() {
                @Override
                public void run() {
                    try{
                        Particle.DustTransition dt1 = new Particle.DustTransition(Color.RED,Color.YELLOW,0.6f);
                        Particle.DustTransition dt2 = new Particle.DustTransition(Color.RED,Color.YELLOW,0.3f);

                        SpawnOneParticle DT1 = new SpawnOneParticle() {
                            @Override
                            public float GetParticleSize() {
                                return dt1.getSize();
                            }

                            @Override
                            public void SpawnOneParticle(Location location, Player player) {
                                player.spawnParticle(Particle.DUST_COLOR_TRANSITION,location,2,dt1);
                            }
                        };
                        SpawnOneParticle DT2 = new SpawnOneParticle() {
                            @Override
                            public float GetParticleSize() {
                                return dt2.getSize();
                            }

                            @Override
                            public void SpawnOneParticle(Location location, Player player) {
                                player.spawnParticle(Particle.DUST_COLOR_TRANSITION,location,1,dt2);
                            }
                        };
                        //Spawn.playerSpawnTotalLocations(tempLoc,2,dt1,dt2,player,0,0,10);
                        //Spawn.spawnTotalLocations(tempLoc,2,dt1,dt2,player,0.1,0.1,newP.getDebugOn(),10);
                        SpawnParticles.SpawnTotalLocations(tempLoc,2,player,10,DT1,DT2);
                    }catch (Exception e){
                        System.out.println("wrong version");
                    }

                }
            });
            if(newP.getDebugOn()){
                player.sendMessage("clear");
            }
        }
    }
}

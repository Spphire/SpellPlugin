package com.apricity.spellplugin;


import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Particle.SpawnOneParticle;
import com.apricity.spellplugin.Particle.SpawnParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class Detector extends BukkitRunnable {
    private final Plugin p;
    private final Player player;

    private int offlineTime = 0;

    public double strength=1;
    public double tolerance=15;
    public int pointsNum=0;
    public Vector<Location> Locations;

    public float pitchLimit = 50f;
    public float R = 2f;

    Particle.DustOptions point = new Particle.DustOptions(Color.fromBGR(255,200,255),0.6f);
    Particle.DustOptions line = new Particle.DustOptions(Color.fromBGR(255,0,255),0.3f);
    SpawnOneParticle spawnPLine = new SpawnOneParticle() {
        @Override
        public float GetParticleSize() {
            return line.getSize();
        }

        @Override
        public void SpawnOneParticle(Location location, Player player) {
            player.spawnParticle(Particle.REDSTONE,location,1,0,0,0,0,line);
        }
    };
    SpawnOneParticle spawnPPoint = new SpawnOneParticle() {
        @Override
        public float GetParticleSize() {
            return point.getSize();
        }

        @Override
        public void SpawnOneParticle(Location location, Player player) {
            player.getWorld().spawnParticle(Particle.REDSTONE,location,2,0,0,0,0,point);
        }
    };
    public Detector(Plugin p, Player player){
        this.p = p;
        this.player = player;
        this.Locations = new Vector<>();
    }

    public void updateAttributes(ItemStack main,ItemStack off){
        strength=1;
        tolerance=15;
        pointsNum=0;
        List<String> lore = null;
        if(off.getItemMeta()!=null){
            if(off.getItemMeta().getDisplayName().contains("魔")) {
                lore = off.getItemMeta().getLore();
            }
        }
        if(main.getItemMeta()!=null){
            if(main.getItemMeta().getDisplayName().contains("魔")) {
                lore = main.getItemMeta().getLore();
            }
        }

        if (lore==null){
            return;
        }
        for (String s : Objects.requireNonNull(lore)) {
            if (s == null) {
                continue;
            }
            s = s.replaceAll(" ", "");
            s = s.replaceAll("([&§]){1}([A-Za-z0-9]){1}", "");
            String v[] = s.split(":");
            switch (v[0]) {
                case "法术强效":
                    try {
                        strength = Double.parseDouble(v[1]);
                    } catch (Exception ignored) {
                    }
                    break;
                case "施法相性":
                    try {
                        tolerance = Double.parseDouble(v[1]);
                    } catch (Exception ignored) {
                    }
                    break;
                case "魔力结晶":
                    try {
                        pointsNum = Integer.parseInt(v[1]);
                    } catch (Exception ignored) {
                        System.out.println("something wrong with IntSwitch");
                    }
                    break;
            }
        }

    }

    @Override
    public void run() {
        SpellPlugin newP = (SpellPlugin) p;

        int coolDown = 0;
        while(offlineTime<20){
            //显示粒子效果
            try{
                if (Locations.size()>0){
                    SpawnParticles.SpawnTotalLocations(Locations,R,player,0,spawnPLine,spawnPPoint);
                    Location lookAt = player.getEyeLocation();
                    if (Math.abs(lookAt.getPitch())>pitchLimit){
                        lookAt.setPitch(lookAt.getPitch()/Math.abs(lookAt.getPitch())*pitchLimit);
                    }
                    SpawnParticles.SpawnParticleArc(lookAt,Locations.get(Locations.size()-1),lookAt,R,player,0,spawnPLine);
                }
            } catch (Exception e) {
                Locations = new Vector<>();
            }

            try{
                Thread.sleep(100);
                if (!Bukkit.getOnlinePlayers().contains(player)){
                    offlineTime+=1;
                }else{
                    offlineTime=0;
                }
            }catch (InterruptedException e){
                Bukkit.getLogger().info("Java Thread cannot sleep.");
            }
        }
        System.out.println(player.getName()+" log out");
        Locations.removeAllElements();
        newP.PlayerDetectorMap.remove(player);
    }
}

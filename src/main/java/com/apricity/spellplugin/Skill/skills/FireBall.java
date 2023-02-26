package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Vector;

public class FireBall extends SkillEffect {
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;

    protected final Particle.DustOptions dustOptions;

    @Override
    public void updateEntities() {
        entities = player.getWorld().getNearbyEntities(loc, offsetX,offsetY,offsetZ);
        for(Entity e : entities){
            if (e instanceof Player && !((SpellPlugin) p).getIsPVP()){
                continue;
            }
            if(e instanceof LivingEntity && !e.equals(player)){
                Timer+=allTime;
            }
        }
    }

    @Override
    public void updateLocation() {
        double v=15;
        double vy = v*Math.sin(-loc.getPitch() / 180 * Math.PI);
        double hv = v*Math.cos(-loc.getPitch() / 180 * Math.PI);
        double vx = hv*Math.sin(-loc.getYaw() / 180 * Math.PI);
        double vz = hv*Math.cos(-loc.getYaw() / 180 * Math.PI);
        loc.setX(loc.getX()+vx*deltaTms/1000);
        loc.setY(loc.getY()+vy*deltaTms/1000);
        loc.setZ(loc.getZ()+vz*deltaTms/1000);
        if (player.getWorld().getBlockAt(loc).getType().isSolid()) {
            Timer += allTime;
        }
    }

    @Override
    public void skillTick() {
        player.getWorld().spawnParticle(Particle.FLAME,loc,10,0.1,0.1,0.1,0);
    }

    @Override
    public void skillFinal() {
        player.getWorld().spawnParticle(Particle.LAVA,loc,50,1,1,1,0.3);
        offsetX=5;
        offsetY=5;
        offsetZ=5;
        Bukkit.getScheduler().runTask(p, new Runnable() {
            //runTask强制主线程
            @Override
            public void run() {
                entities = player.getWorld().getNearbyEntities(loc, offsetX,offsetY,offsetZ);
                for(Entity e : entities){
                    if (e instanceof Player && !((SpellPlugin) p).getIsPVP()){
                        continue;
                    }
                    if(e instanceof LivingEntity && !e.equals(player)){
                        LivingEntity entity = (LivingEntity) e;
                        if (!damagedEntities.contains(entity)){
                            org.bukkit.util.Vector Vloc = new org.bukkit.util.Vector(loc.getX(),loc.getY(),loc.getZ());
                            if (entity.getBoundingBox().getCenter().isInSphere(Vloc,2.5)){
                                entity.setFireTicks(60);
                                entity.damage( allDamage, player);
                                damagedEntities.add(entity);
                            }
                        }
                    }
                }
            }
        });
    }

    public FireBall(Plugin p, Player player, double allDamage) {
        super(p, player, 2,10, 1000, allDamage);
        this.dustOptions = new Particle.DustOptions(Color.WHITE,1f);
        this.offsetX=1;
        this.offsetY=1;
        this.offsetZ=1;
    }
}

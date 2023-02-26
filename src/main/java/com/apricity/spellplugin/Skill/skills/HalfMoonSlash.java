package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Vector;

public class HalfMoonSlash extends SkillEffect {
    protected final double offsetX;
    protected final double offsetY;
    protected final double offsetZ;

    protected final Particle.DustTransition dustTransition;

    protected Vector<Location> points=new Vector<>();

    @Override
    public void updateEntities() {
        entities = player.getWorld().getNearbyEntities(loc, offsetX,offsetY,offsetZ);
        for(Entity e : entities){
            if (e instanceof Player && !((SpellPlugin) p).getIsPVP()){
                continue;
            }
            if(e instanceof LivingEntity && !e.equals(player)){
                LivingEntity entity = (LivingEntity) e;
                if (!damagedEntities.contains(entity)){
                    org.bukkit.util.Vector Vloc = new org.bukkit.util.Vector(loc.getX(),loc.getY(),loc.getZ());
                    if (entity.getBoundingBox().getCenter().isInSphere(Vloc,1)){
                        entity.damage( allDamage, player);
                        damagedEntities.add(entity);
                    }
                }
            }
        }
    }

    @Override
    public void updateLocation() {
        double v=20;
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
        Location l1=loc.clone();
        l1.setPitch(loc.getPitch()+50);
        Location l2=loc.clone();
        l2.setPitch(loc.getPitch()-50);
        Location locCenter = loc.clone();
        locCenter.setY(locCenter.getY()-1*Math.sin(-loc.getPitch() / 180 * Math.PI));
        locCenter.setX(locCenter.getX()-1*Math.cos(-loc.getPitch() / 180 * Math.PI)*Math.sin(-loc.getYaw() / 180 * Math.PI));
        locCenter.setZ(locCenter.getZ()-1*Math.cos(-loc.getPitch() / 180 * Math.PI)*Math.cos(-loc.getYaw() / 180 * Math.PI));
        points=Spawn.spawnParticleArc(l1,l2,locCenter,1,player,2,dustTransition,false,false);
    }

    public HalfMoonSlash(Plugin p, Player player, double allDamage) {
        super(p, player, 2,10, 1000, allDamage);
        this.dustTransition = new Particle.DustTransition(Color.fromBGR(255,255,255),Color.fromBGR(255,255,255),0.5f);
        this.offsetX=0.3;
        this.offsetY=1;
        this.offsetZ=0.3;
    }
}

package com.apricity.spellplugin.Skill;

import com.apricity.spellplugin.Skill.skills.test;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public abstract class SkillShot extends BukkitRunnable {
    protected final Plugin p;
    protected Player player;
    protected LivingEntity entity;

    protected Location playerLoc;
    protected double range;
    protected Location effectLoc;
    protected LivingEntity livingEntitySelected=null;
    protected Collection<Entity> entities;

    protected SkillShot(Plugin p, Player player,double range) {
        this.p = p;
        this.player = player;
        this.playerLoc = player.getEyeLocation();
        this.range = range;
    }

    public void skillFinal(){

    }

    public void skillDefault(){

    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTask(p, () -> {
            entities = player.getNearbyEntities(range,range,range);
            for(Entity entity:entities){
                if(entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    if(livingEntity.getBoundingBox().getCenter().distance(player.getBoundingBox().getCenter())<range){
                        double distanceH = Math.sqrt(Math.pow(livingEntity.getLocation().getX()-playerLoc.getX(),2)+Math.pow(livingEntity.getLocation().getZ()-playerLoc.getZ(),2));
                        effectLoc = playerLoc.clone();
                        effectLoc.setY(effectLoc.getY() + distanceH * Math.tan(-effectLoc.getPitch() / 180 * Math.PI));
                        effectLoc.setX(effectLoc.getX() + distanceH * Math.sin(-effectLoc.getYaw() / 180 * Math.PI));
                        effectLoc.setZ(effectLoc.getZ() + distanceH * Math.cos(-effectLoc.getYaw() / 180 * Math.PI));
                        if(livingEntity.getBoundingBox().contains(effectLoc.getX(),effectLoc.getY(),effectLoc.getZ())){
                            livingEntitySelected = livingEntity;
                            break;
                        }
                    }

                }
            }
            if (livingEntitySelected!=null){
                skillFinal();
            }else{
                skillDefault();
            }
        });
    }
}

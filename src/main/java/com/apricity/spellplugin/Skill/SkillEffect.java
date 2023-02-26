package com.apricity.spellplugin.Skill;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Vector;

public abstract class SkillEffect extends BukkitRunnable implements SkillUpdate{
    protected final Plugin p;
    protected Player player;

    protected int allTime;
    protected int Timer;
    protected int deltaTms;
    protected double allDamage;

    protected Location loc;
    protected Collection<Entity> entities;
    protected Vector<LivingEntity> damagedEntities = new Vector<>();


    protected boolean isFinished;

    @Override
    public void updateLocation() {
    }

    @Override
    public void updateEntities(){
    }

    @Override
    public void skillTick(){
    }

    @Override
    public void skillFinal(){
    }

    public SkillEffect(Plugin p, Player player,double startR,double startH,int deltaTms, int allTime,double allDamage){
        this.p = p;
        this.player = player;
        this.Timer=0;
        this.deltaTms = deltaTms;
        this.allTime=allTime;
        this.allDamage=allDamage;
        this.isFinished=false;

        this.loc = player.getLocation();
        this.loc.setY(this.loc.getY() + startH);
        this.loc.setX(this.loc.getX() + startR * Math.sin(-this.loc.getYaw() / 180 * Math.PI));
        this.loc.setZ(this.loc.getZ() + startR * Math.cos(-this.loc.getYaw() / 180 * Math.PI));

        this.entities = player.getWorld().getNearbyEntities(this.loc, 1, 1, 1);
    }

    public SkillEffect(Plugin p, Player player,double startR,int deltaTms ,int allTime,double allDamage){
        this.p = p;
        this.player = player;
        this.Timer=0;
        this.deltaTms = deltaTms;
        this.allTime=allTime;
        this.allDamage=allDamage;

        this.loc = player.getEyeLocation();
        this.loc.setY(this.loc.getY() + startR * Math.sin(-this.loc.getPitch() / 180 * Math.PI));
        double hR = startR * Math.cos(this.loc.getPitch() / 180 * Math.PI);
        this.loc.setX(this.loc.getX() + hR * Math.sin(-this.loc.getYaw() / 180 * Math.PI));
        this.loc.setZ(this.loc.getZ() + hR * Math.cos(-this.loc.getYaw() / 180 * Math.PI));

        this.entities = player.getWorld().getNearbyEntities(this.loc, 1, 1, 1);
    }

    @Override
    public void run() {
        while (Timer<allTime){
            updateLocation();
            Bukkit.getScheduler().runTask(p, new Runnable() {
                //runTask强制主线程
                @Override
                public void run() {updateEntities();}
            });

            skillTick();
            Timer+=deltaTms;
            try{
                Thread.sleep(deltaTms);
            }catch (InterruptedException e){
                Bukkit.getLogger().info("Java Thread error.");
            }
        }
        skillFinal();
        isFinished=true;
    }
}

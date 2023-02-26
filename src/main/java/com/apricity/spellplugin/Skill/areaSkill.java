/*
package com.apricity.spellplugin.Skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class areaSkill extends SkillEffect{
    protected final int allDamage;

    public areaSkill(Plugin p, Player player, double startR, double startH, int allTime, int allDamage) {
        super(p, player, startR, startH, allTime);
        this.allDamage = allDamage;
    }

    public areaSkill(Plugin p, Player player, double startR, int allTime, int allDamage) {
        super(p, player, startR, allTime);
        this.allDamage = allDamage;
    }

    @Override
    public void updateLocation(double vx, double vy, double vz, double deltaT) {
        super.updateLocation(vx, vy, vz, deltaT);
    }

    @Override
    public void updateEntities(double x, double y, double z) {
        super.updateEntities(x, y, z);
    }

    @Override
    public void run() {
        while (Timer<allTime){
            Bukkit.getScheduler().runTask(p, new Runnable() {
                //runTask强制主线程
                @Override
                public void run() {
                    updateEntities(1,1,1);
                }
            });
            updateLocation(0,0,0,100);
            for(Entity e : entities){
                if(e instanceof LivingEntity){
                    LivingEntity entity = (LivingEntity) e;
                    Bukkit.getScheduler().runTask(p, new Runnable() {
                        //runTask强制主线程
                        @Override
                        public void run() {
                            entity.damage( allDamage*100/allTime);
                        }
                    });
                }
            }


            Timer+=100;
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                Bukkit.getLogger().info("Java Thread error.");
            }
        }
    }
}
*/
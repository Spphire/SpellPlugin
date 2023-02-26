package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Skill.SkillEffect;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Vector;

public class Sweep extends SkillEffect {
    protected final double offsetX;
    protected final double offsetY;
    protected final double offsetZ;

    @Override
    public void updateEntities() {
        entities = player.getWorld().getNearbyEntities(loc, offsetX,offsetY,offsetZ);
        for(Entity e : entities){
            if(e instanceof LivingEntity && !e.equals(player)){
                LivingEntity entity = (LivingEntity) e;
                if (!damagedEntities.contains(entity)){
                    if (Of2Points.getDistance(entity.getLocation(),loc)<2) {
                        entity.damage(allDamage, player);
                        damagedEntities.add(entity);
                    }
                }
            }
        }
    }

    @Override
    public void skillTick() {
        player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc, 2, offsetX,0,offsetZ,0);
    }

    public Sweep(Plugin p, Player player, double allDamage) {
        super(p, player, 3, 1, 100,500, allDamage);
        this.offsetX=1;
        this.offsetY=1;
        this.offsetZ=1;
    }
}

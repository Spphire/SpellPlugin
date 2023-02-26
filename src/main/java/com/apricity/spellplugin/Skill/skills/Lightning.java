package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Lightning extends SkillEffect {
    boolean onHit = false;

    @Override
    public void updateEntities() {
        entities = player.getWorld().getNearbyEntities(loc, 1,1,1);
        for(Entity e : entities){
            if (e instanceof Player && !((SpellPlugin) p).getIsPVP()){
                continue;
            }
            if(e instanceof LivingEntity && !e.equals(player)){
                LivingEntity entity = (LivingEntity) e;
                entity.getCollidableExemptions();
                if (!damagedEntities.contains(entity)){
                    if(Of2Points.getDistance(entity.getLocation(),loc)<0.5){
                        entity.damage( allDamage, player);
                        damagedEntities.add(entity);
                    }
                }
            }

        }
    }

    @Override
    public void updateLocation() {
        double v=50;
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

    public Lightning(Plugin p, Player player, double allDamage) {
        super(p, player, 1, 10, 200, allDamage);
    }

}

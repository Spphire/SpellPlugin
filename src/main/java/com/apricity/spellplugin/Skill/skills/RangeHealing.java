package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RangeHealing extends SkillEffect {
    protected final Particle.DustTransition dustTransition;
    protected final double tickHeal;
    protected int healDeltaTms;
    protected int triggerTime=0;
    @Override
    public void updateEntities() {
        if (Timer<triggerTime*healDeltaTms){
            return;
        }
        float R = 6f;
        entities = player.getWorld().getNearbyEntities(loc, R,R,R);
        for(Entity e : entities){
            if(e instanceof Player){
                Player entity = (Player) e;
                if(Of2Points.getDistance(entity.getLocation(),loc)<R){
                    try {
                        entity.setHealth(entity.getHealth()+tickHeal);
                        Location entityLoc = entity.getLocation().clone();
                        entityLoc.setY(entityLoc.getY()+1);
                        entity.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,entityLoc,10,0.5,0,0.5,0.2);
                        entity.getWorld().playSound(entityLoc, Sound.ENTITY_PLAYER_LEVELUP,.5F,.5F);
                    } catch (Exception exception) {
                        for (int i=0;i<tickHeal;i++){
                            try {
                                entity.setHealth(entity.getHealth()+1);
                            } catch (Exception ignored) {

                            }
                        }
                    }

                }
            }
        }
        triggerTime++;
    }

    @Override
    public void skillTick() {
        Location l1=loc.clone();
        Location l2=loc.clone();
        l2.setYaw(loc.getYaw()+90);
        Location l3 = loc.clone();
        l3.setYaw(loc.getYaw()+180);
        Location l4 = loc.clone();
        l4.setYaw(loc.getYaw()+270);
        Spawn.spawnParticleArc(l1,l2,loc,6,player,0,dustTransition,false,false);
        Spawn.spawnParticleArc(l2,l3,loc,6,player,0,dustTransition,false,false);
        Spawn.spawnParticleArc(l3,l4,loc,6,player,0,dustTransition,false,false);
        Spawn.spawnParticleArc(l4,l1,loc,6,player,0,dustTransition,false,false);

    }

    public RangeHealing(Plugin p, Player player, int deltaTms, int allTms, double allHeal) {
        super(p, player, 0, 0, 100, allTms, allHeal);
        healDeltaTms = deltaTms;
        this.dustTransition = new Particle.DustTransition(Color.YELLOW,Color.WHITE,1.5f);
        loc.setPitch(0);
        tickHeal = allHeal*deltaTms/allTms;
    }
}

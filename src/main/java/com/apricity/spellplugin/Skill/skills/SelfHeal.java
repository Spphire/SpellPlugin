package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SelfHeal extends SkillEffect {
    public SelfHeal(Plugin p, Player player, double allHeal) {
        super(p, player, 0, 0, 10, 0, allHeal);
    }

    @Override
    public void skillFinal() {
        Location entityLoc = player.getLocation().clone();
        entityLoc.setY(entityLoc.getY()+1);
        player.getWorld().spawnParticle(Particle.HEART,entityLoc,5,0.5,0,0.5,0.2);
        player.getWorld().playSound(entityLoc, Sound.ENTITY_PLAYER_LEVELUP,.5F,.5F);
        Bukkit.getScheduler().runTask(p, new Runnable() {
            //runTask强制主线程
            @Override
            public void run() {
                try {
                    player.setHealth(player.getHealth()+allDamage);
                } catch (Exception exception) {
                    for (int i=0;i<allDamage;i++){
                        try {
                            player.setHealth(player.getHealth()+1);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        });
    }
}

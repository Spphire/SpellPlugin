package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Skill.SkillEffect;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpeedUp extends SkillEffect {
    public SpeedUp(Plugin p, Player player,double allDamage) {
        super(p, player, 0, 10, 0, allDamage);
    }

    @Override
    public void skillFinal() {
        //player.getWorld().spawnParticle(Particle.FLASH,player.getLocation(),1);
        player.getWorld().spawnParticle(Particle.FLASH,player.getLocation(),1,0,0,0,0.5);
        Bukkit.getScheduler().runTask(p, new Runnable() {
            @Override
            public void run() {
                player.getWorld().strikeLightningEffect(player.getLocation());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,1));
            }
        });
    }
}

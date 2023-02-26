package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillShot;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AvadaKedavra extends SkillShot {
    public Particle particle = Particle.VILLAGER_HAPPY;
    @Override
    public void skillFinal() {
        livingEntitySelected.getWorld().strikeLightningEffect(livingEntitySelected.getLocation());
        livingEntitySelected.damage(1000,player);
        Location pLoc = player.getLocation();
        pLoc.setY(pLoc.getY()+player.getHeight()/2);
        //Spawn.spawnParticleLine(livingEntitySelected.getEyeLocation(),pLoc,player,1,new Particle.DustTransition(Color.GREEN,Color.BLACK,0.5f),false,1);
        Spawn.spawnParticleLine(livingEntitySelected.getEyeLocation(),pLoc,player,1,particle,false,1);
    }

    @Override
    public void skillDefault() {
        Location tempLoc = player.getEyeLocation();
        tempLoc.setY(tempLoc.getY() + (range-1) * Math.sin(-tempLoc.getPitch() / 180 * Math.PI));
        double hR = (range-1) * Math.cos(tempLoc.getPitch() / 180 * Math.PI);
        tempLoc.setX(tempLoc.getX() + hR * Math.sin(-tempLoc.getYaw() / 180 * Math.PI));
        tempLoc.setZ(tempLoc.getZ() + hR * Math.cos(-tempLoc.getYaw() / 180 * Math.PI));
        Location pLoc = player.getLocation();
        pLoc.setY(pLoc.getY()+player.getHeight()/2);
        //Spawn.spawnParticleLine(tempLoc,pLoc,player,1,new Particle.DustTransition(Color.GREEN,Color.BLACK,0.5f),false,1);
        Spawn.spawnParticleLine(tempLoc,pLoc,player,1,particle,false,1);
    }

    public AvadaKedavra(Plugin p, Player player, double range) {
        super(p, player, range);
    }
}

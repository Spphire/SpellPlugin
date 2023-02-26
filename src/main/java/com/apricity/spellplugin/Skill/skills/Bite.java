package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Bite extends SkillEffect {
    private float OCangle;
    private Location l1;
    private Location l2;

    private Particle.DustTransition dustTransition;

    public Bite(Plugin p, Player player, double allDamage) {
        super(p, player, 1, 1.2, 10, 500, allDamage);
        OCangle = 40f;
        l1 = loc.clone();
        l1.setPitch(-OCangle);
        l2 = loc.clone();
        l2.setPitch(OCangle);

            dustTransition=new Particle.DustTransition(Color.RED,Color.ORANGE,0.5f);
    }

    @Override
    public void updateLocation() {
        float vPitch = 100f;
        l1.setX(loc.getX());
        l1.setY(loc.getY());
        l1.setZ(loc.getZ());
        l2.setX(loc.getX());
        l2.setY(loc.getY());
        l2.setZ(loc.getZ());

        l1.setPitch(l1.getPitch()+vPitch*deltaTms/1000);
        l2.setPitch(l2.getPitch()-vPitch*deltaTms/1000);
    }

    @Override
    public void skillTick() {

        Spawn.spawnPitchTangent(l1,1,1,player,dustTransition,((SpellPlugin)p).getDebugOn());
        Spawn.spawnPitchTangent(l2,1,1,player,dustTransition,((SpellPlugin)p).getDebugOn());
    }

    @Override
    public void skillFinal() {
        super.skillFinal();
    }
}

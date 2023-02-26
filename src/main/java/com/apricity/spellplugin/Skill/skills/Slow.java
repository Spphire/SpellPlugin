package com.apricity.spellplugin.Skill.skills;


import com.apricity.spellplugin.Particle.Spawn;
import com.apricity.spellplugin.Skill.SkillShot;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Slow extends SkillShot {

    @Override
    public void skillFinal() {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("选中目标"));
        livingEntitySelected.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,60,2));
        Spawn.spawnParticleLine(playerLoc,livingEntitySelected.getEyeLocation(),player,0,new Particle.DustTransition(Color.BLACK,Color.BLACK,0.1f),false);
    }

    public Slow(Plugin p, Player player, double range) {
        super(p, player, range);
    }
}

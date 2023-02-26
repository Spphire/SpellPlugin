package com.apricity.spellplugin.Skill.skills;

import com.apricity.spellplugin.Skill.SkillEffect;
import com.apricity.spellplugin.SpellPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LightWave extends SkillEffect {
    @Override
    public void updateEntities() {
        new HalfMoonSlash(p, player, 5*((SpellPlugin)p).PlayerDetectorMap.get(player).strength).runTaskAsynchronously(p);
    }
    public LightWave(Plugin p, Player player, double allDamage) {
        super(p, player, 2, 100, 5000, allDamage);
    }


}

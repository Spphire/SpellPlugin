package com.apricity.spellplugin.Particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface SpawnOneParticle {
    float GetParticleSize();
    void SpawnOneParticle(Location location, Player player);
}

package com.apricity.spellplugin.Skill.skills;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum test {
    air(0),
    air2(1),
    ;
    private final int id;
    ItemStack is = new ItemStack(Material.AIR);
    private test(final int id){
        this.id = id;
    }
}


package com.apricity.spellplugin.Skill;

import com.apricity.spellplugin.Determine.Determins;
import com.apricity.spellplugin.Determine.Of2Points;
import com.apricity.spellplugin.Skill.skills.Skill;
import org.bukkit.Location;
import java.util.Vector;

public class SkillDeterminer {
    public static Skill SkillShape(Vector<Location> locations, double tolerance){
        switch (locations.size()){
            case 0:
                return Skill.NOTHING;
            case 1:
                return Skill.TEST;
            case 3:
                if (Determins.isLeft2Right(locations,tolerance,60)){
                    return Skill.HALF_MOON_SLASH;
                }else if(Determins.isLeft2Right(locations,tolerance,-60)){
                    return Skill.FIRE_BALL;
                }else if (Of2Points.isInSlopRange(locations.get(0),locations.get(1),0,tolerance)&&Of2Points.isInSlopRange(locations.get(2),locations.get(1),0,tolerance)){
                    return Skill.SWEEP;
                } else {
                    break;
                }
            case 4:
                if (Determins.is4(locations,tolerance)){
                    return Skill.SELF_HEALING;
                } else if (Determins.isFlash(locations,tolerance)) {
                    return Skill.SPEED_UP;
                } else{
                    break;
                }
            case 5:
                if (Determins.isW(locations,tolerance,-60)){
                    return Skill.BITE;
                } else if (Determins.isA_A(locations,tolerance)) {
                    return Skill.RANGE_HEALING;
                }else{
                    break;
                }
            case 9:
                if(Determins.isWW(locations,tolerance,-70)){
                    return Skill.PLACE_HOLDER_ww;
                }else if (Determins.isOAA(locations,tolerance)){
                    return Skill.PLACE_HOLDER_OAA;
                }
                else{
                    break;
                }
            default:
                break;
        }
        return Skill.WRONG_SKILL;
    }
}

package com.apricity.spellplugin.Particle;

import com.apricity.spellplugin.Determine.Of2Points;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Vector;

public class SpawnParticles {
    public static void SpawnParticleArc(Location l1, Location l2, Location center, double R, Player player, int delay,SpawnOneParticle pLine){
        float unitArc = (float) (pLine.GetParticleSize()*8/R);
        float dis = (float) (Math.pow(Of2Points.getDeltaYawRight_Left(l1,l2),2));
        dis += (float) (Math.pow(Of2Points.getDeltaPitchUp_down(l1,l2),2));
        dis = (float) Math.sqrt(dis);
        Location l = l1.clone();
        float n=dis/unitArc;
        for (int pos=1;pos<n;pos++){
            l.setX(center.getX());
            l.setY(center.getY());
            l.setZ(center.getZ());
            l.setYaw((float) (l.getYaw()+Of2Points.getDeltaYawRight_Left(l1,l2)/n));
            l.setPitch((float) (l.getPitch()-Of2Points.getDeltaPitchUp_down(l1,l2)/n));

            l.setY(l.getY() + R * Math.sin(-l.getPitch() / 180 * Math.PI));
            double hR = R * Math.cos(l.getPitch() / 180 * Math.PI);
            l.setX(l.getX() + hR * Math.sin(-l.getYaw() / 180 * Math.PI));
            l.setZ(l.getZ() + hR * Math.cos(-l.getYaw() / 180 * Math.PI));

            try{
                pLine.SpawnOneParticle(l,player);
                if(delay!=0){Thread.sleep(delay);}
            } catch (Exception ignored) {
            }

        }
    }

    public static void SpawnTotalLocations(Vector<Location> Locations, double R, Player player, int delay, SpawnOneParticle pLine, SpawnOneParticle pPoint){
        //int countP = (int) (pPoint.GetParticleSize()/0.4+1);
        //int countL = (int) (pLine.GetParticleSize() /0.4+1);

        for (Location locationRecorded : Locations) {
            if (locationRecorded != null) {
                Location loc = player.getEyeLocation();
                Location lookat = locationRecorded;
                lookat.setY(loc.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
                double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
                lookat.setX(loc.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
                lookat.setZ(loc.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));

                try{
                    pPoint.SpawnOneParticle(lookat,player);
                } catch (Exception ignored) {
                }
                if (Locations.indexOf(locationRecorded)< Locations.size()-1){
                    Location locationNext = Locations.get(Locations.indexOf(locationRecorded)+1);
                    Location locCenter = player.getEyeLocation();
                    //playerSpawnParticleArc(locationRecorded, locationNext, locCenter, R, player, spdLine, pLine,delay);
                    float unitArc = (float) (pLine.GetParticleSize()*8/R);
                    float dis = (float) (Math.pow(Of2Points.getDeltaYawRight_Left(locationRecorded,locationNext),2));
                    dis += (float) (Math.pow(Of2Points.getDeltaPitchUp_down(locationRecorded,locationNext),2));
                    dis = (float) Math.sqrt(dis);
                    Location l = locationRecorded.clone();
                    float n=dis/unitArc;
                    for (int pos=1;pos<n;pos++){
                        l.setX(locCenter.getX());
                        l.setY(locCenter.getY());
                        l.setZ(locCenter.getZ());
                        l.setYaw((float) (l.getYaw()+Of2Points.getDeltaYawRight_Left(locationRecorded,locationNext)/n));
                        l.setPitch((float) (l.getPitch()-Of2Points.getDeltaPitchUp_down(locationRecorded,locationNext)/n));

                        l.setY(l.getY() + R * Math.sin(-l.getPitch() / 180 * Math.PI));
                        double hhR = R * Math.cos(l.getPitch() / 180 * Math.PI);
                        l.setX(l.getX() + hhR * Math.sin(-l.getYaw() / 180 * Math.PI));
                        l.setZ(l.getZ() + hhR * Math.cos(-l.getYaw() / 180 * Math.PI));

                        try{
                            pLine.SpawnOneParticle(l,player);
                            if(delay!=0){Thread.sleep(delay);}
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    public static void spawnParticle(Location location,double R,Player player,SpawnOneParticle sop){
        Location lookat = player.getEyeLocation().clone();
        lookat.setPitch(location.getPitch());
        lookat.setYaw(location.getYaw());
        lookat.setY(lookat.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
        double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
        lookat.setX(lookat.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
        lookat.setZ(lookat.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));
        sop.SpawnOneParticle(lookat,player);
    }

    public static void successfulSpellEffect(Vector<Location> locations, Player player){
        double R=2;
        for(Location location:locations){
            Location lookat = player.getEyeLocation().clone();
            lookat.setPitch(location.getPitch());
            lookat.setYaw(location.getYaw());
            lookat.setY(lookat.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
            double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
            lookat.setX(lookat.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
            lookat.setZ(lookat.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));
            double vx = player.getEyeLocation().getX()-lookat.getX();
            double vz = player.getEyeLocation().getZ()-lookat.getZ();
            double vy = player.getEyeLocation().getY()-lookat.getY();
            //double vy = player.getBoundingBox().getCenterY()-lookat.getY();
            //player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,lookat,0,vx,vy,vz,1);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),0,-vx,-vy,-vz,1);
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),0,-vx,-vy,-vz,1);
        }
    }
}

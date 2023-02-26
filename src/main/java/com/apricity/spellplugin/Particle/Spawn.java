package com.apricity.spellplugin.Particle;

import com.apricity.spellplugin.Determine.Of2Points;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.material.Redstone;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Vector;

public class Spawn {
    public static void spawnParticleLine(Location l1, Location l2, Player player,double speed, Particle.DustTransition dustTransition,boolean getDebugOn){
        float unitL = dustTransition.getSize()/5;
        float dis = (float) (Math.pow((l1.getX() - l2.getX()),2)+Math.pow((l1.getY() - l2.getY()),2)+Math.pow((l1.getZ() - l2.getZ()),2));
        dis = (float) Math.sqrt(dis);
        Location l = l2.clone();
        float n=dis/unitL;
        for (int pos=1;pos<n;pos++){
            l.setX(l.getX()+(l1.getX()-l2.getX())/n);
            l.setY(l.getY()+(l1.getY()-l2.getY())/n);
            l.setZ(l.getZ()+(l1.getZ()-l2.getZ())/n);
            try{
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,l,1,0,0,0,speed,dustTransition);
            } catch (Exception e) {
                if (getDebugOn) {
                    player.sendMessage("something wrong with spawnParticleLine");
                }
            }
        }
    }

    public static void spawnParticleLine(Location l1, Location l2, Player player,double speed, Particle.DustTransition dustTransition,boolean getDebugOn,int delay){
        float unitL = dustTransition.getSize()/5;
        float dis = (float) (Math.pow((l1.getX() - l2.getX()),2)+Math.pow((l1.getY() - l2.getY()),2)+Math.pow((l1.getZ() - l2.getZ()),2));
        dis = (float) Math.sqrt(dis);
        Location l = l2.clone();
        float n=dis/unitL;
        for (int pos=1;pos<n;pos++){
            l.setX(l.getX()+(l1.getX()-l2.getX())/n);
            l.setY(l.getY()+(l1.getY()-l2.getY())/n);
            l.setZ(l.getZ()+(l1.getZ()-l2.getZ())/n);
            try{
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,l,1,0,0,0,speed,dustTransition);
                Thread.sleep(delay);
            } catch (Exception e) {
                if (getDebugOn) {
                    player.sendMessage("something wrong with spawnParticleLine");
                }
            }
        }
    }

    public static void spawnParticleLine(Location l1, Location l2, Player player,double speed, Particle partical,boolean getDebugOn,int delay){
        float unitL = 0.1f;
        float dis = (float) (Math.pow((l1.getX() - l2.getX()),2)+Math.pow((l1.getY() - l2.getY()),2)+Math.pow((l1.getZ() - l2.getZ()),2));
        dis = (float) Math.sqrt(dis);
        Location l = l2.clone();
        float n=dis/unitL;
        for (int pos=1;pos<n;pos++){
            l.setX(l.getX()+(l1.getX()-l2.getX())/n);
            l.setY(l.getY()+(l1.getY()-l2.getY())/n);
            l.setZ(l.getZ()+(l1.getZ()-l2.getZ())/n);
            try{
                player.getWorld().spawnParticle(partical,l,1,0,0,0,speed);
                Thread.sleep(delay);
            } catch (Exception e) {
                if (getDebugOn) {
                    player.sendMessage("something wrong with spawnParticleLine");
                }
            }
        }
    }

    public static Vector<Location> spawnParticleArc(Location l1, Location l2, Location center, double R, Player player,double speed, Particle.DustTransition dustTransition,boolean getDebugOn,boolean force){
        Vector<Location> points = new Vector<>();
        float unitArc = (float) (dustTransition.getSize()*8/R);
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
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,l,1,0,0,0,speed,dustTransition,force);
                points.add(l);
            } catch (Exception e) {
                if (getDebugOn) {
                    player.sendMessage("something wrong with spawnParticleArc");
                }
            }

        }
        return points;
    }

    public static void spawnParticleArc(Location l1, Location l2, Location center, double R, Player player,double speed, Particle.DustTransition dustTransition,boolean getDebugOn,boolean force, int delay){
        float unitArc = (float) (dustTransition.getSize()*8/R);
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
                player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION,l,1,0,0,0,speed,dustTransition,force);
                Thread.sleep(delay);
            } catch (Exception e) {
                if (getDebugOn) {
                    player.sendMessage("something wrong with spawnParticleArc");
                }
            }

        }
    }

    public static void spawnTotalLocations(Vector<Location> Locations, double R, Particle.DustTransition pPoint, Particle.DustTransition pLine, Player player,double spdPoint,double spdLine,boolean getDebugOn){
        int countP = (int) (pPoint.getSize()/0.4+1);
        int countL = (int) (pLine.getSize() /0.4+1);

        for (Location locationRecorded : Locations) {
            if (locationRecorded != null) {
                Location loc = player.getEyeLocation();
                Location lookat = locationRecorded;
                lookat.setY(loc.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
                double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
                lookat.setX(loc.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
                lookat.setZ(loc.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));

                Particle[] particles = Particle.values();
                try{
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, lookat, countP, 0,0,0,spdPoint,pPoint,false);
                } catch (Exception e) {
                    if (getDebugOn) {
                        player.sendMessage("something wrong with "+pPoint);
                    }
                }
                if (Locations.indexOf(locationRecorded)>0){
                    Location locationLast = Locations.get(Locations.indexOf(locationRecorded)-1);
                    Location locCenter = player.getEyeLocation();
                    //Spawn.spawnParticleArc(locationsLast,locationRecorded,locCenter,R,Particle.REVERSE_PORTAL,player,0,new Particle.DustOptions(Color.WHITE,0f),newP.getDebugOn());
                    float deltaAngle = 0f;
                    Location locationLast_c = locationLast.clone();
                    locationLast_c.setPitch((locationLast_c.getPitch()+deltaAngle));
                    Location locationRecorded_c = locationRecorded.clone();
                    locationRecorded_c.setPitch(locationRecorded_c.getPitch()+deltaAngle);
                    spawnParticleArc(locationLast_c, locationRecorded_c, locCenter, R, player, spdLine, pLine, getDebugOn,false);
                    //spawnParticleLine(locationLast_c, locationRecorded_c, player, spdLine, pLine, getDebugOn);
                }
            }
        }
    }

    public static void spawnTotalLocations(Vector<Location> Locations, double R, Particle.DustTransition pPoint, Particle.DustTransition pLine, Player player,double spdPoint,double spdLine,boolean getDebugOn,int delay){
        int countP = (int) (pPoint.getSize()/0.4+1);
        int countL = (int) (pLine.getSize() /0.4+1);

        for (Location locationRecorded : Locations) {
            if (locationRecorded != null) {
                Location loc = player.getEyeLocation();
                Location lookat = locationRecorded;
                lookat.setY(loc.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
                double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
                lookat.setX(loc.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
                lookat.setZ(loc.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));

                try{
                    player.getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, lookat, countP, 0,0,0,spdPoint,pPoint,false);
                } catch (Exception e) {
                    if (getDebugOn) {
                        player.sendMessage("something wrong with DUST_COLOR_TRANSITION");
                    }
                }
                if (Locations.indexOf(locationRecorded)< Locations.size()-1){
                    Location locationNext = Locations.get(Locations.indexOf(locationRecorded)+1);
                    Location locCenter = player.getEyeLocation();
                    spawnParticleArc(locationRecorded, locationNext, locCenter, R, player, spdLine, pLine, getDebugOn,false,delay);
                }
            }
        }
    }

    public static void playerSpawnParticleArc(Location l1, Location l2, Location center, double R, Player player,double speed, Particle.DustTransition dustTransition, int delay){
        float unitArc = (float) (dustTransition.getSize()*8/R);
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
                player.spawnParticle(Particle.DUST_COLOR_TRANSITION,l,1,0,0,0,speed,dustTransition);
                if(delay!=0){Thread.sleep(delay);}
            } catch (Exception ignored) {
            }

        }
    }

    public static void playerSpawnTotalLocations(Vector<Location> Locations, double R, Particle.DustTransition pPoint, Particle.DustTransition pLine, Player player,double spdPoint,double spdLine,int delay){
        int countP = (int) (pPoint.getSize()/0.4+1);
        int countL = (int) (pLine.getSize() /0.4+1);

        for (Location locationRecorded : Locations) {
            if (locationRecorded != null) {
                Location loc = player.getEyeLocation();
                Location lookat = locationRecorded;
                lookat.setY(loc.getY() + R * Math.sin(-lookat.getPitch() / 180 * Math.PI));
                double hR = R * Math.cos(lookat.getPitch() / 180 * Math.PI);
                lookat.setX(loc.getX() + hR * Math.sin(-lookat.getYaw() / 180 * Math.PI));
                lookat.setZ(loc.getZ() + hR * Math.cos(-lookat.getYaw() / 180 * Math.PI));

                try{
                    player.spawnParticle(Particle.DUST_COLOR_TRANSITION, lookat, countP, 0,0,0,spdPoint,pPoint);
                } catch (Exception ignored) {
                }
                if (Locations.indexOf(locationRecorded)< Locations.size()-1){
                    Location locationNext = Locations.get(Locations.indexOf(locationRecorded)+1);
                    Location locCenter = player.getEyeLocation();
                    //playerSpawnParticleArc(locationRecorded, locationNext, locCenter, R, player, spdLine, pLine,delay);
                    float unitArc = (float) (pLine.getSize()*8/R);
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
                            player.spawnParticle(Particle.DUST_COLOR_TRANSITION,l,0,0,1,0,spdLine,pLine);
                            //player.spawnParticle(Particle.END_ROD,l,0,0,1,0);
                            if(delay!=0){Thread.sleep(delay);}
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        }
    }

    public static void spawnPitchTangent(Location location, double R, double length, Player player,Particle.DustTransition dustTransition,boolean debugOn){
        Location midL = location.clone();
        midL.setY(midL.getY() + R * Math.sin(-midL.getPitch() / 180 * Math.PI));
        double hR = R * Math.cos(midL.getPitch() / 180 * Math.PI);
        midL.setX(midL.getX() + hR * Math.sin(-midL.getYaw() / 180 * Math.PI));
        midL.setZ(midL.getZ() + hR * Math.cos(-midL.getYaw() / 180 * Math.PI));

        Location l1 = midL.clone();
        l1.setY(l1.getY() + length/2 * Math.sin(-(midL.getPitch()+90) / 180 * Math.PI));
        hR = length/2 * Math.cos((midL.getPitch()+90) / 180 * Math.PI);
        l1.setX(l1.getX() + hR * Math.sin(-midL.getYaw() / 180 * Math.PI));
        l1.setZ(l1.getZ() + hR * Math.cos(-midL.getYaw() / 180 * Math.PI));
        Location l2 = midL.clone();
        l2.setY(l2.getY() + length/2 * Math.sin(-(midL.getPitch()-90) / 180 * Math.PI));
        hR = length/2 * Math.cos((midL.getPitch()-90) / 180 * Math.PI);
        l2.setX(l2.getX() + hR * Math.sin(-midL.getYaw() / 180 * Math.PI));
        l2.setZ(l2.getZ() + hR * Math.cos(-midL.getYaw() / 180 * Math.PI));

        spawnParticleLine(l1,l2,player,0.5,dustTransition,debugOn);

    }
}

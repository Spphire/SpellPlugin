package com.apricity.spellplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public final class SpellPlugin extends JavaPlugin {
    private boolean debugOn;
    public boolean getDebugOn(){return debugOn;}

    private boolean isPVP;
    public boolean getIsPVP(){return isPVP;}


    public Map<Player,Detector> PlayerDetectorMap;
    public int particleIdex = 65;
    //69SOUL
    //56绿四角星
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Trigger(this), this);
        this.debugOn = false;
        this.isPVP = false;
        this.PlayerDetectorMap = new HashMap<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(Detector i:PlayerDetectorMap.values()){
            Bukkit.getScheduler().cancelTask(i.getTaskId());
        }
        //this.getServer().getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            return false;
        }
        if (args[0].equalsIgnoreCase("debug") && (sender instanceof Player) && sender.isOp()) {
            this.debugOn = !this.debugOn;
            if(this.debugOn){
                sender.sendMessage("§6debug-message-outputs open");
            }else{
                sender.sendMessage("§6debug-message-outputs close");
            }
            return true;
        }else if(args[0].equalsIgnoreCase("amount") && (sender instanceof Player) && sender.isOp()){
            sender.sendMessage(""+this.PlayerDetectorMap.get(((Player)sender)).Locations.size());
            return true;
        }else if(args[0].equalsIgnoreCase("nextP") && (sender instanceof Player) && sender.isOp()){
            Particle[] particles = Particle.values();
            particleIdex++;
            particleIdex%=particles.length;
            sender.sendMessage(""+particles[particleIdex]);
            return true;
        }else if(args[0].equalsIgnoreCase("lastP") && (sender instanceof Player) && sender.isOp()){
            Particle[] particles = Particle.values();
            particleIdex--;
            particleIdex+=particles.length;
            particleIdex%=particles.length;
            sender.sendMessage(""+particles[particleIdex]);
            return true;
        }else if(args[0].equalsIgnoreCase("setP") && (sender instanceof Player) && sender.isOp()){
            Particle[] particles = Particle.values();
            try{
                particleIdex = Integer.parseInt(args[1]) % particles.length;
                sender.sendMessage(""+particles[particleIdex]);
            } catch (Exception e) {
                sender.sendMessage("some errors of values");
            }
            return true;
        }else if(args[0].equalsIgnoreCase("getAngles") && (sender instanceof Player) && sender.isOp()){
            try{
                Vector<Location> locations = this.PlayerDetectorMap.get((Player) sender).Locations;
                String message = "";
                if (locations.size()==0){
                    message = "0";
                }else {
                    for(Location l : locations){
                        message+="P:"+l.getPitch()+", "+"Y:"+l.getYaw()+"; ";
                    }
                }
                sender.sendMessage(message);
            } catch (Exception e) {
                sender.sendMessage("some errors of PlayerDetector");
            }
            return true;
        }else if(args[0].equalsIgnoreCase("PVP") && (sender instanceof Player) && sender.isOp()){
            this.isPVP = !this.isPVP;
            if(this.isPVP){
                sender.sendMessage("§6PVP open");
            }else{
                sender.sendMessage("§6PVP close");
            }
            return true;
        }else if(args[0].equalsIgnoreCase("open") && (sender instanceof Player)){
            Player player = (Player) sender;
            Detector detector = new Detector(this, player);
            detector.runTaskAsynchronously(this);
            if (this.PlayerDetectorMap.containsKey(player)){
                Bukkit.getScheduler().cancelTask(this.PlayerDetectorMap.get(player).getTaskId());
                this.PlayerDetectorMap.get(player).Locations.removeAllElements();
                this.PlayerDetectorMap.remove(player);
            }
            this.PlayerDetectorMap.put(player,detector);
            sender.sendMessage("§6plugin open");
        }else if(args[0].equalsIgnoreCase("close") && (sender instanceof Player)){
            Player player = (Player) sender;
            if (this.PlayerDetectorMap.containsKey(player)){
                Bukkit.getScheduler().cancelTask(this.PlayerDetectorMap.get(player).getTaskId());
                this.PlayerDetectorMap.get(player).Locations.removeAllElements();
                this.PlayerDetectorMap.remove(player);
            }
            sender.sendMessage("§6plugin close");
        }
        return true;
    }
}

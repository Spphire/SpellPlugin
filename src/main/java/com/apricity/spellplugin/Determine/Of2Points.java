package com.apricity.spellplugin.Determine;

import org.bukkit.Location;

public class Of2Points {
    public static double getDistance(Location l1,Location l2){
        double dis = Math.pow(l1.getX()-l2.getX(),2);
        dis += Math.pow(l1.getY()-l2.getY(),2);
        dis += Math.pow(l1.getZ()-l2.getZ(),2);
        dis = Math.sqrt(dis);
        return dis;
    }
    public static double getAveragePitch(Location l1, Location l2){
        return (Math.abs(l1.getPitch())+Math.abs(l2.getPitch()))*Math.PI/360;
    }
    public static double getDeltaYawRight_Left(Location left, Location right){
        // return (-180,180]
        // left---right   return > 0
        left.setYaw(left.getYaw()%360);
        right.setYaw(right.getYaw()%360);

        if(right.getYaw()-left.getYaw()>=0){
            if (right.getYaw()-left.getYaw()<=180){
                return right.getYaw()-left.getYaw();
            }else{
                return (right.getYaw()-left.getYaw())-360;
            }
        }else{
            if (360+right.getYaw()-left.getYaw()<=180){
                return 360+right.getYaw()-left.getYaw();
            }else{
                return right.getYaw()-left.getYaw();
            }
        }
    }

    public static double getDeltaPitchUp_down(Location down, Location up){
        // return [-100,100]
        return down.getPitch()-up.getPitch();
    }

    public static boolean isTooCLose(Location l1, Location l2){
        float dis = (float) (Math.pow(getDeltaPitchUp_down(l1,l2),2));
        dis += (float) (Math.pow(getDeltaYawRight_Left(l1,l2)*Math.cos(getAveragePitch(l1,l2)) ,2));
        dis = (float) Math.sqrt(dis);
        return dis < 5;
    }

    public static boolean isNearlyHorizontal(Location l1, Location l2, double tolerance){

        return Math.abs(getDeltaPitchUp_down(l1,l2)) < tolerance;
    }
    public static boolean isNearlyVertical(Location l1, Location l2, double tolerance){
        return Math.abs(getDeltaYawRight_Left(l1,l2)) < tolerance;
    }

    public static boolean isInYawRange(Location l1,Location l2,double Yaw, double tolerance){
        double YawMin=(Yaw-tolerance/2)/Math.cos(getAveragePitch(l1,l2));
        double YawMax=(Yaw+tolerance/2)/Math.cos(getAveragePitch(l1,l2));
        return Math.abs(Of2Points.getDeltaYawRight_Left(l1,l2))>YawMin && Math.abs(Of2Points.getDeltaYawRight_Left(l1,l2))<YawMax;
    }
    public static boolean isInPitchRange(Location l1,Location l2,double Pitch, double tolerance){
        double PitchMin=Pitch-tolerance/2;
        double PitchMax=Pitch+tolerance/2;
        return Math.abs(Of2Points.getDeltaPitchUp_down(l1,l2))>PitchMin && Math.abs(Of2Points.getDeltaPitchUp_down(l1,l2))<PitchMax;
    }

    public static boolean isInSlopRange(Location l1, Location l2, double goalAngle ,double tolerance){
        double angle;
        if (getDeltaYawRight_Left(l1,l2)==0){
            angle = 90;
        }else{
            double x = getDeltaPitchUp_down(l1,l2);
            double y = getDeltaYawRight_Left(l1,l2)*Math.cos(getAveragePitch(l1,l2));
            angle = 180/Math.PI*Math.atan(x/y);
        }
        //System.out.println(angle);
        if (Math.abs(goalAngle)<=80){
            return angle < goalAngle + tolerance && angle > goalAngle - tolerance;
        }else if (goalAngle>=0){
            return angle > goalAngle - tolerance || angle < goalAngle + tolerance - 180;
        }else {
            return angle < goalAngle + tolerance || angle > goalAngle - tolerance + 180;
        }
    }
}

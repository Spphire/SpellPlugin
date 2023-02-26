package com.apricity.spellplugin.Determine;

import org.bukkit.Location;

import java.util.Vector;

public class Determins {

    public static boolean isEndToEnd(Vector<Location> locations){
        Location l1 = locations.get(0);
        Location l2 = locations.get(locations.size()-1);
        return Of2Points.isTooCLose(l1,l2);
    }

    public static boolean isLeft2Right(Vector<Location> locations,double tolerance,double angle){
        if (locations.size()<3){
            return false;
        }
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        boolean flag1 = Of2Points.isInSlopRange(l1,l3,0,tolerance);
        boolean flag2 = Of2Points.isInSlopRange(l1,l2,angle,tolerance);
        boolean flag3 = Of2Points.isInSlopRange(l2,l3,-angle,tolerance);
        boolean flag4 = Of2Points.getDeltaYawRight_Left(l1,l3)>0;

        return flag1&&flag2&&flag3&&flag4;
    }

    public static boolean is4(Vector<Location> locations,double tolerance){
        if (locations.size()<4){
            return false;
        }
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        boolean flag1 = Of2Points.isInSlopRange(l2,l3,50,tolerance);
        boolean flag2 = Of2Points.isInSlopRange(l1,l2,0,tolerance);
        boolean flag3 = Of2Points.isInSlopRange(l3,l4,90,tolerance);
        boolean flag4 = Of2Points.isInSlopRange(l1,l2,90,tolerance);
        boolean flag5 = Of2Points.isInSlopRange(l3,l4,0,tolerance);
        if (!flag1){
            return false;
        }else{
            if(flag2){
                if(!flag3){
                    return false;
                }else{
                    if(Of2Points.getDeltaYawRight_Left(l4,l1)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l3,l1)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l2,l3)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l2,l4)<=0){
                        return false;
                    }
                    if(Of2Points.getDeltaPitchUp_down(l1,l3)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l2,l3)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l4,l1)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l4,l2)<=0){
                        return false;
                    }
                }
            }else{
                if(!flag4&&flag5){
                    return false;
                }else{
                    if(Of2Points.getDeltaYawRight_Left(l1,l4)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l2,l4)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l3,l1)<=0){
                        return false;
                    }else if (Of2Points.getDeltaYawRight_Left(l3,l2)<=0){
                        return false;
                    }
                    if(Of2Points.getDeltaPitchUp_down(l3,l2)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l4,l2)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l1,l3)<=0){
                        return false;
                    }else if (Of2Points.getDeltaPitchUp_down(l1,l4)<=0){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isFlash(Vector<Location> locations,double tolerance){
        if (locations.size()<4){
            return false;
        }
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        boolean flag1 = Of2Points.isInSlopRange(l1,l2,60,tolerance);
        boolean flag2 = Of2Points.isInSlopRange(l2,l3,0,tolerance);
        boolean flag3 = Of2Points.isInSlopRange(l3,l4,60,tolerance);
        boolean flag4 = Of2Points.isInSlopRange(l1,l3,-60,tolerance);
        boolean flag5 = Of2Points.isInSlopRange(l2,l4,-60,tolerance);
        return flag1&&flag2&&flag3&&flag4&&flag5;
    }

    public static boolean isW(Vector<Location> locations,double tolerance,double angle){
        if (locations.size()<5){
            //System.out.println("isnt 5");
            return false;
        }
        //System.out.println("is 5");
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        Location l5 = locations.get(4);
        Vector<Location> v1 = new Vector<>();
        v1.add(0,l1);
        v1.add(1,l2);
        v1.add(2,l3);
        //System.out.println(""+v1.size());
        Vector<Location> v2 = new Vector<>();
        v2.add(0,l3);
        v2.add(1,l4);
        v2.add(2,l5);
        //System.out.println(""+v2.size());

        boolean flag1 = isLeft2Right(v1,tolerance,angle);
        boolean flag2 = isLeft2Right(v2,tolerance,angle);

        return flag1&&flag2;
    }

    public static boolean isA_A(Vector<Location> locations,double tolerance){
        if (locations.size()<5){
            //System.out.println("isnt 5");
            return false;
        }
        //System.out.println("is 5");
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        Location l5 = locations.get(4);

        boolean flag1 = Of2Points.isInSlopRange(l1,l2,60,tolerance);
        boolean flag2 = Of2Points.isInSlopRange(l2,l3,0,tolerance);
        boolean flag3 = Of2Points.isInSlopRange(l3,l4,-60,tolerance);
        boolean flag4 = Of2Points.isInSlopRange(l4,l5,0,tolerance);
        boolean flag5 = Of2Points.isTooCLose(l1,l5);

        return flag1&&flag2&&flag3&&flag4&&flag5;
    }

    public static boolean isWW(Vector<Location> locations,double tolerance,double angle){
        if (locations.size()<9){
            //System.out.println("isnt 5");
            return false;
        }
        //System.out.println("is 5");
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        Location l5 = locations.get(4);
        Location l6 = locations.get(5);
        Location l7 = locations.get(6);
        Location l8 = locations.get(7);
        Location l9 = locations.get(8);
        Vector<Location> v1 = new Vector<>();
        v1.add(0,l1);
        v1.add(1,l2);
        v1.add(2,l3);
        v1.add(3,l4);
        v1.add(4,l5);
        //System.out.println(""+v1.size());
        Vector<Location> v2 = new Vector<>();
        v2.add(0,l5);
        v2.add(1,l6);
        v2.add(2,l7);
        v2.add(3,l8);
        v2.add(4,l9);
        //System.out.println(""+v2.size());

        boolean flag1 = isW(v1,tolerance,angle);
        boolean flag2 = isW(v2,tolerance,angle);

        return flag1&&flag2;
    }

    public static boolean isOAA(Vector<Location> locations,double tolerance){
        if (locations.size()<9){
            //System.out.println("isnt 5");
            return false;
        }
        //System.out.println("is 5");
        Location l1 = locations.get(0);
        Location l2 = locations.get(1);
        Location l3 = locations.get(2);
        Location l4 = locations.get(3);
        Location l5 = locations.get(4);
        Location l6 = locations.get(5);
        Location l7 = locations.get(6);
        Location l8 = locations.get(7);
        Location l9 = locations.get(8);
        Vector<Location> v1 = new Vector<>();
        v1.add(0,l5);
        v1.add(1,l6);
        v1.add(2,l7);
        Vector<Location> v2 = new Vector<>();
        v2.add(0,l9);
        v2.add(1,l8);
        v2.add(2,l7);

        boolean flag1 = Of2Points.isInSlopRange(l1,l2,90,tolerance);
        flag1 &= Of2Points.getDeltaPitchUp_down(l1,l2)>0;
        boolean flag2 = Of2Points.isInSlopRange(l2,l3,0,tolerance);
        flag2 &= Of2Points.getDeltaYawRight_Left(l2,l3)>0;
        boolean flag3 = Of2Points.isInSlopRange(l3,l4,90,tolerance);
        flag3 &= Of2Points.getDeltaPitchUp_down(l3,l4)<0;
        boolean flag4 = Of2Points.isInSlopRange(l4,l5,0,tolerance);
        flag4 &= Of2Points.isTooCLose(l1,l5);
        boolean flag5 = isLeft2Right(v1,tolerance,60);
        flag5 &= Of2Points.isTooCLose(l7,l4);
        boolean flag6 = isLeft2Right(v2,tolerance,30);
        flag6 &= Of2Points.isTooCLose(l9,l1);

        return flag1&&flag2&&flag3&&flag4&&flag5&&flag6;
    }
}

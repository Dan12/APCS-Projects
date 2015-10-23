//package src.danweberrpg;
package danweberrpg;

public class RightMove{
    
    private StickFigure stx;
    
    public RightMove(StickFigure s){
        stx = s;
    }
    
    public void runAnimation(){
        if(stx.fromRest){
            stx.goToAngle(1, stx.restUA, -stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);
            stx.goToAngle(2, stx.restLA, -stx.sdArmAngle-stx.sdLowerArmIncrease, 1, 21, stx.walkAnimStep, false);
            stx.goToAngle(3, -stx.restUA, stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);
            stx.goToAngle(4, -stx.restLA, stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);
            
            stx.goToAngle(5, stx.restUA, -stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);
            stx.goToAngle(6, stx.restLA, -stx.sdLegExtendAngle, 11, 21, stx.walkAnimStep, false);
            stx.goToAngle(7, -stx.restUA, stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);
            stx.goToAngle(8, -stx.restLA, stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);
            
            if(stx.walkAnimStep >= 11)
                stx.changePosition(0, -2);
        }
        else{
            if(!stx.toRest){
                stx.goToAngle(1, stx.sdArmAngle, -stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);
                stx.goToAngle(2, stx.sdArmAngle, -stx.sdArmAngle-stx.sdLowerArmIncrease, 1, 21, stx.walkAnimStep, false);
                stx.goToAngle(3, -stx.sdArmAngle, stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);
                stx.goToAngle(4, -stx.sdArmAngle-stx.sdLowerArmIncrease, stx.sdArmAngle, 1, 21, stx.walkAnimStep, false);

                stx.goToAngle(5, stx.sdLegExtendAngle, -stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);
                stx.goToAngle(7, -stx.sdLegExtendAngle, stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);
                stx.goToAngle(8, -stx.sdLegExtendAngle, stx.sdLegExtendAngle, 1, 21, stx.walkAnimStep, false);

                stx.goToAngle(6, stx.sdLegExtendAngle, stx.sdLegExtendAngle, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(6, stx.sdLegExtendAngle, -stx.sdLegExtendAngle, 11, 21, stx.walkAnimStep, false);

                stx.setLength(Main.limbHeight, 1);
                stx.setLength(Main.limbHeight, 2);
                stx.setLength(Main.limbHeight, 3);
                stx.setLength(Main.limbHeight, 4);
                stx.setLength(Main.limbHeight, 5);
                stx.setLength(Main.limbHeight, 6);
                stx.setLength(Main.limbHeight, 7);
                stx.setLength(Main.limbHeight, 8);

                if(stx.walkAnimStep < 11)
                    stx.changePosition(0, 2);
                if(stx.walkAnimStep >= 11)
                    stx.changePosition(0, -2);
            }
            else{
                stx.goToAngle(1, -stx.sdArmAngle, stx.restUA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(2, -stx.sdArmAngle-stx.sdLowerArmIncrease, stx.restLA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(3, stx.sdArmAngle, -stx.restUA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(4, stx.sdArmAngle, -stx.restLA, 1, 11, stx.walkAnimStep, false);

                stx.goToAngle(5, -stx.sdLegExtendAngle, stx.restUA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(6, -stx.sdLegExtendAngle, stx.restLA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(7, stx.sdLegExtendAngle, -stx.restUA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(8, stx.sdLegExtendAngle, -stx.restLA, 1, 11, stx.walkAnimStep, false);
                
                if(stx.walkAnimStep < 11)
                    stx.changePosition(0, 2);
            }
        }
        
        if(!(stx.toRest && stx.walkAnimStep >= 11))
            stx.changePosition(stx.xIncrease, 0);
        else
            stx.walkAnimStep = 21;
    }
}

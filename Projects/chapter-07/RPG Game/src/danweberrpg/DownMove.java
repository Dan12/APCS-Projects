//package src.danweberrpg;
package danweberrpg;

public class DownMove{

    private StickFigure stx;
    
    public DownMove(StickFigure s){
        stx = s;
    }

    public void runAnimation(){
        if(stx.walkAnimResart)
            stx.fwdRev = !stx.fwdRev;
        
        if(stx.fromRest){
            stx.goToAngle(1, stx.restUA, stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);

            stx.goToLength(2, Main.limbHeight, 0, 1, 11, stx.walkAnimStep, stx.fwdRev);
            stx.goToAngle(2, stx.restLA, -45, 1, 11, stx.walkAnimStep, stx.fwdRev);
            stx.goToLength(2, 0, (double)Main.limbHeight*0.5, 11, 21, stx.walkAnimStep, stx.fwdRev);
            stx.goToAngle(2, -135, -135+stx.restLA, 11, 21, stx.walkAnimStep, stx.fwdRev);
            
            stx.goToLength(3, Main.limbHeight, (double)Main.limbHeight*0.75, 1, 21, stx.walkAnimStep, stx.fwdRev);
            
            stx.goToLength(4, Main.limbHeight, (double)Main.limbHeight*0.75, 1, 21, stx.walkAnimStep, stx.fwdRev);
            stx.goToAngle(4, -stx.restLA, -stx.restUA, 1, 21, stx.walkAnimStep, stx.fwdRev);
            
            stx.goToAngle(7, -stx.restUA, -stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);
            
            stx.goToAngle(5, stx.restUA, stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);
            stx.goToLength(5, Main.limbHeight, (double)Main.limbHeight*0.6, 1, 21, stx.walkAnimStep, stx.fwdRev);
            
            stx.goToAngle(6, stx.restLA, 0, 1, 21, stx.walkAnimStep, stx.fwdRev);
            stx.goToLength(6, Main.limbHeight, (double)Main.limbHeight*0.3, 1, 21, stx.walkAnimStep, stx.fwdRev);
            
            if(stx.walkAnimStep >= 11)
                    stx.changePosition(0, 2);
        }
        else{
            if(!stx.toRest){
                stx.goToLength(1, (double)Main.limbHeight*0.75, Main.limbHeight, 1, 21, stx.walkAnimStep, stx.fwdRev);
                stx.goToAngle(1, stx.restUA, stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);

                if(stx.fwdRev){
                    stx.goToLength(2, (double)Main.limbHeight*0.75, 0, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, stx.restUA, -45, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(2, 0, (double)Main.limbHeight*0.5, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, -135, -135+stx.restLA, 1, 11, stx.walkAnimStep, stx.fwdRev);
                }
                else{
                    stx.goToLength(2, (double)Main.limbHeight*0.75, 0, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, stx.restUA, -45, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(2, 0, (double)Main.limbHeight*0.5, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, -135, -135+stx.restLA, 11, 21, stx.walkAnimStep, stx.fwdRev);
                }

                stx.goToAngle(3, -stx.restLA, -stx.restUA, 1, 21, stx.walkAnimStep, stx.fwdRev);
                stx.goToLength(3, Main.limbHeight, (double)Main.limbHeight*0.75, 1, 21, stx.walkAnimStep, stx.fwdRev);

                if(stx.fwdRev){
                    stx.goToLength(4, (double)Main.limbHeight*0.5, 0, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 135-stx.restUA, 135, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(4, 0, (double)Main.limbHeight*0.75, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 45, -stx.restUA, 1, 11, stx.walkAnimStep, stx.fwdRev);
                }
                else{
                    stx.goToLength(4, (double)Main.limbHeight*0.5, 0, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 135-stx.restUA, 135, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(4, 0, (double)Main.limbHeight*0.75, 11, 21, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 45, -stx.restUA, 11, 21, stx.walkAnimStep, stx.fwdRev);
                }

                stx.goToAngle(5, stx.restLA, stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);
                stx.goToLength(5, Main.limbHeight, (double)Main.limbHeight*0.6, 1, 21, stx.walkAnimStep, stx.fwdRev);

                stx.goToAngle(6, stx.restLA, 0, 1, 21, stx.walkAnimStep, stx.fwdRev);
                stx.goToLength(6, Main.limbHeight, (double)Main.limbHeight*0.3, 1, 21, stx.walkAnimStep, stx.fwdRev);

                stx.goToAngle(7, -stx.restLA, -stx.restLA, 1, 21, stx.walkAnimStep, stx.fwdRev);
                stx.goToLength(7, Main.limbHeight, (double)Main.limbHeight*0.6, 1, 21, stx.walkAnimStep, !stx.fwdRev);

                stx.goToAngle(8, -stx.restLA, 0, 1, 21, stx.walkAnimStep, !stx.fwdRev);
                stx.goToLength(8, Main.limbHeight, (double)Main.limbHeight*0.3, 1, 21, stx.walkAnimStep, !stx.fwdRev);

                if(stx.walkAnimStep < 11)
                    stx.changePosition(0, -2);
                if(stx.walkAnimStep >= 11)
                    stx.changePosition(0, 2);
            }
            else{
                stx.goToAngle(5, stx.restLA, stx.restUA, 1, 11, stx.walkAnimStep, false);
                stx.goToAngle(7, -stx.restLA, -stx.restUA, 1, 11, stx.walkAnimStep, false);
                
                if(stx.fwdRev){
                    stx.goToAngle(1, stx.restUA, stx.restLA, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(2, Main.limbHeight, 0, 6, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, stx.restLA, -45, 6, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(2, 0, (double)Main.limbHeight*0.5, 1, 6, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(2, -135, -135+stx.restLA, 1, 6, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(3, Main.limbHeight, (double)Main.limbHeight*0.75, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(4, (double)Main.limbHeight*0.75, Main.limbHeight, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    stx.goToAngle(4, -stx.restUA, -stx.restLA, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    
                    stx.goToLength(5, Main.limbHeight, (double)Main.limbHeight*0.6, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(6, stx.restLA, 0, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(6, Main.limbHeight, (double)Main.limbHeight*0.3, 1, 11, stx.walkAnimStep, stx.fwdRev);
                }
                else{
                    stx.goToLength(1, (double)Main.limbHeight*0.75, Main.limbHeight, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(2, Main.limbHeight, (double)Main.limbHeight*0.75, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    stx.goToAngle(2, stx.restLA, stx.restUA, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    stx.goToAngle(3, -stx.restLA, -stx.restUA, 1, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(4, (double)Main.limbHeight*0.5, 0, 1, 6, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 135-stx.restUA, 135, 1, 6, stx.walkAnimStep, stx.fwdRev);
                    stx.goToLength(4, 0, Main.limbHeight, 6, 11, stx.walkAnimStep, stx.fwdRev);
                    stx.goToAngle(4, 45, -stx.restLA, 6, 11, stx.walkAnimStep, stx.fwdRev);
                    
                    stx.goToLength(7, Main.limbHeight, (double)Main.limbHeight*0.6, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    stx.goToAngle(8, -stx.restLA, 0, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                    stx.goToLength(8, Main.limbHeight, (double)Main.limbHeight*0.3, 1, 11, stx.walkAnimStep, !stx.fwdRev);
                }
                if(stx.walkAnimStep < 11)
                    stx.changePosition(0, -2);
            }
        }

        if(!(stx.toRest && stx.walkAnimStep >= 11))
            stx.changePosition(0, stx.yIncrease);
        else
            stx.walkAnimStep = 21;
    }
}

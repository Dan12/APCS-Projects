package learner2;

import java.util.Random;

public class NeuralNetwork {
    //actions(1-5): not see anything, see learner, see bullet, get hit, hit learner
    //x++,x--,y++,y--,dir++,dir--,shoot
    int reactionNum = 7;
    int actionNum = 5;
    int[][] reactions = new int[reactionNum][actionNum];
    Random rand = new Random();
    int percentNum = 10000;
    
    public NeuralNetwork(){
        System.out.println("x++   x--   y++   y--   dir++   dir--   shoot");
        System.out.println("doesn't see anything");
        System.out.println("sees a learner");
        System.out.println("sees a bullet");
        System.out.println("got hit");
        System.out.println("hit a learner");
        for(int k = 0; k < actionNum; k++){
            int tP = 0;
            for(int i = 0; i < reactionNum-1; i++){
                int goBack = 1;
                while(percentNum-tP <= 0){
                    if(reactions[i-goBack][k] > 1){
                        tP--;
                        reactions[i-goBack][k]--;
                    }
                    else
                        goBack++;
                }
                int d = rand.nextInt(percentNum-tP)+1;
                int p = rand.nextInt(d)+1;
                tP+=p;
                reactions[i][k] = p;
            }
            int goBack = 1;
            while(percentNum-tP <= 0){
                if(reactions[(reactionNum-1)-goBack][k] > 1){
                    tP--;
                    reactions[(reactionNum-1)-goBack][k]--;
                }
                else
                    goBack++;
            }
            reactions[reactionNum-1][k] = percentNum-tP;
        }
        for(int i = 0; i < actionNum; i++){
            for(int ii = 0; ii < reactionNum; ii++){
                System.out.print(reactions[ii][i]+",");
            }
            System.out.println("");
        }
        System.out.println("-------");
    }
    
    public int reaction(int a){
        int r = rand.nextInt(percentNum)+1;
        //System.out.print(r+",");
        int sum = 0;
        int i;
        for(i = 0; i < reactionNum; i++){
            sum+=reactions[i][a];
            if(sum>=r)
                break;
        }
        return i+1;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwjgltest;

import static org.lwjgl.glfw.GLFW.*;

public class KeyActions {
    Camera cam;
    
    private boolean dAK = false,uAK = false,lAK = false,rAK = false,aK = false,sK = false,dK = false,wK = false,qK = false,eK = false;
    
    private int rotateSpeed = 5;
    private float moveSpeed = 1.0f;
    
    public KeyActions(Camera c){
        cam = c;
    }
    
    public void recieveKey(int key, int action){
        if(action == GLFW_PRESS){
            if(key == GLFW_KEY_DOWN) dAK = true;
            if(key == GLFW_KEY_UP) uAK = true;
            if(key == GLFW_KEY_LEFT) lAK = true;
            if(key == GLFW_KEY_RIGHT) rAK = true;
            if(key == GLFW_KEY_D) dK = true;
            if(key == GLFW_KEY_A) aK = true;
            if(key == GLFW_KEY_W) wK = true;
            if(key == GLFW_KEY_S) sK = true;
            if(key == GLFW_KEY_Q) qK = true;
            if(key == GLFW_KEY_E) eK = true;
        }
        if(action == GLFW_RELEASE){
            if(key == GLFW_KEY_DOWN) dAK = false;
            if(key == GLFW_KEY_UP) uAK = false;
            if(key == GLFW_KEY_LEFT) lAK = false;
            if(key == GLFW_KEY_RIGHT) rAK = false;
            if(key == GLFW_KEY_D) dK = false;
            if(key == GLFW_KEY_A) aK = false;
            if(key == GLFW_KEY_W) wK = false;
            if(key == GLFW_KEY_S) sK = false;
            if(key == GLFW_KEY_Q) qK = false;
            if(key == GLFW_KEY_E) eK = false;
        }
    }
    
    public void keyActions(){
        if(dAK) cam.changeRotation(-rotateSpeed, 0, 0);
        if(uAK) cam.changeRotation(rotateSpeed, 0, 0);
        if(lAK) cam.changeRotation(0, -rotateSpeed, 0);
        if(rAK) cam.changeRotation(0, rotateSpeed, 0);
        if(aK) cam.chageTranslation(moveSpeed, 0, 0);
        if(dK) cam.chageTranslation(-moveSpeed, 0, 0);
        if(sK) cam.chageTranslation(0, 0, -moveSpeed);
        if(wK) cam.chageTranslation(0, 0, moveSpeed);
        if(qK) cam.chageTranslation(0, -moveSpeed, 0);
        if(eK) cam.chageTranslation(0, moveSpeed, 0);
    }
}

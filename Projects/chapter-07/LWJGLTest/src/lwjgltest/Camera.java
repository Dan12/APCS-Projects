/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwjgltest;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    
    private float rotateX = 0,rotateY = 0,rotateZ = 0,translateX = 0,translateY = -20,translateZ = -30;
    
    private double frustuml = 0,frustumr = 0,frustumb = 0,frustumt = 0,frustumn = 0,frustumf = 0;
    
    
    public Camera(){}
    
    public void changeRotation(float x, float y, float z){
        rotateX += x;
        rotateY += y;
        rotateZ += z;
    }
    
    public void chageTranslation(float x, float y, float z){
        translateX += x;
        translateY += y;
        translateZ += z;
    }
    
    public void setFrustum(double l, double r, double b, double t, double n, double f){
        frustuml = l;
        frustumr = r;
        frustumb = b;
        frustumt = t;
        frustumn = n;
        frustumf = f;
    }
    
    public void run(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();         // Reset the model-view matrix
        //Set Frustum first
        glFrustum(frustuml, frustumr, frustumb, frustumt, frustumn, frustumf);   

        //Then manipulate matrix
        glRotatef(rotateX, 1.0f, 0.0f, 0.0f);
        glRotatef(rotateY, 0.0f, 1.0f, 0.0f);
        glRotatef(rotateZ, 0.0f, 0.0f, 1.0f);
        glTranslatef(translateX, translateY, translateZ);
    }
}

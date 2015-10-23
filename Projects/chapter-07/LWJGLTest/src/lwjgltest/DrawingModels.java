/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwjgltest;

import static org.lwjgl.opengl.GL11.*;

public class DrawingModels {
    
    Sphere sph;
    
    public DrawingModels(){
        sph = new Sphere(0,10,0,3,1);
    }
    
    public void run(){
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();         // Reset the model-view matrix
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);  
        //glTranslatef(0, -20, -20);
        
        //Apply scaling only to walls
        glPushMatrix();
        glScalef(2.0f, 2.0f, 2.0f);

        glBegin(GL_QUADS);   
           //walls
           glColor3f(1.0f, 1.0f, 0.0f);
           glVertex3f( 20f, 0f, -20f);
           glVertex3f(-20f, 0f, -20f);
           glVertex3f(-20f,  20f, -20f);
           glVertex3f( 20f,  20f, -20f);

           glVertex3f( 20f, 0f, 20f);
           glVertex3f(-20f, 0f, 20f);
           glVertex3f(-20f,  20f, 20f);
           glVertex3f( 20f,  20f, 20f);

           glVertex3f( 20f, 0f, 20f);
           glVertex3f( 20f, 0f, -20f);
           glVertex3f( 20f,  20f, -20f);
           glVertex3f( 20f,  20f, 20f);

           glVertex3f( -20f, 0f, 20f);
           glVertex3f( -20f, 0f, -20f);
           glVertex3f( -20f,  20f, -20f);
           glVertex3f( -20f,  20f, 20f);

           //floor
           glColor3f(1.0f, 0.0f, 0.0f);
           glVertex3f( 20f, 0f, 20f);
           glVertex3f(-20f, 0f, 20f);
           glVertex3f(-20f,  0f, -20f);
           glVertex3f( 20f,  0f, -20f);

        glEnd();  // End of drawing color-cube
        
        glPopMatrix();

        glColor3f(0.0f, 0.0f, 1.0f);
        sph.draw();
    }
}

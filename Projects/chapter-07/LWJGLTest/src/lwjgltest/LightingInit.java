/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwjgltest;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class LightingInit {
    public LightingInit(){}
    
    public void run(){
        FloatBuffer qaAmbientLight = BufferUtils.createFloatBuffer(4).put(new float[]{0.5f, 0.5f, 0.5f, 1.0f});
        qaAmbientLight.flip();
        FloatBuffer qaDiffuseLight  = BufferUtils.createFloatBuffer(4).put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        qaDiffuseLight.flip();
        FloatBuffer qaSpecularLight = BufferUtils.createFloatBuffer(4).put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        qaSpecularLight.flip();

        glLightfv(GL_LIGHT0, GL_AMBIENT, qaAmbientLight);
        glLightfv(GL_LIGHT0, GL_DIFFUSE, qaDiffuseLight);
        glLightfv(GL_LIGHT0, GL_SPECULAR, qaSpecularLight);

        FloatBuffer qaLightPosition = BufferUtils.createFloatBuffer(4).put(new float[]{0f, 20f, 0f, 1.0f});
        qaLightPosition.flip();
        glLightfv(GL_LIGHT0, GL_POSITION, qaLightPosition);
        
        //Not really working
        FloatBuffer qaLightDirection = BufferUtils.createFloatBuffer(4).put(new float[]{0f, 0f, 0.0f, 0.0f});
        qaLightDirection.flip();
        glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, qaLightDirection);
        
        //Enable depth
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);

        //enable lighting and light0
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        
        //Two sided light
        glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_FALSE);
        
        //Specular reflection angles
        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
        
        //Ambient intensity
        FloatBuffer qaLightIntensity = BufferUtils.createFloatBuffer(4).put(new float[]{0.1f, 0.1f, 0.1f, 1.0f});
        qaLightIntensity.flip();
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, qaLightIntensity);
        
        //Keeps colors on objects
        glEnable(GL_COLOR_MATERIAL);
        
        //IDK what these do, just keep them
        //glEnable(GL_CULL_FACE);
        //glEnable(GL_TEXTURE_2D);
        
        //Smooth or flat
        glShadeModel(GL_SMOOTH);
    }
}

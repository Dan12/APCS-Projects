/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lwjgltest;

import javafx.geometry.Point3D;
import static org.lwjgl.opengl.GL11.*;

public class Sphere {
    
    private Point3D center;
    private float radius,minLength;
    private boolean showTriangles = false;
    
    public Sphere(float xc, float yc, float zc, float r, float m){
        center = new Point3D(xc, yc, zc);
        radius = r;
        minLength = m;
    }
    
    public void draw(){        
        glBegin(GL_TRIANGLES);
        
            //Top
            splitTriangle(center.add(-radius, 0, radius), radius*2, 0, 1, 1);

            splitTriangle(center.add(-radius, 0, -radius), radius*2, 0, 1, -1);

            splitTriangle(center.add(-radius, 0, radius), radius*2, 1, 1, 0);

            splitTriangle(center.add(radius, 0, radius), radius*2, -1, 1, 0);

            //Bottom
            splitTriangle(center.add(-radius, 0, radius), radius*2, 0, -1, 1);

            splitTriangle(center.add(-radius, 0, -radius), radius*2, 0, -1, -1);

            splitTriangle(center.add(-radius, 0, radius), radius*2, 1, -1, 0);

            splitTriangle(center.add(radius, 0, radius), radius*2, -1, -1, 0);
        
        glEnd();
    }
    
    private void decTriSide(Point3D right, float l, int xd, int yd, int zd){ 
        if(l>minLength)
            splitTriangle(right, l, xd, yd, zd);
        else{ 
            vertexP(right.add(0, 0, 0));
            float halfL = l/2;
            if(xd == 0){
                vertexP(right.add(l, 0, 0));
                vertexP(right.add(halfL, halfL*yd, -halfL*zd));
            }
            else if(zd == 0){
                vertexP(right.add(0, 0, -l));
                vertexP(right.add(halfL*xd, halfL*yd, -halfL));
            }
        }
    }
    
    private void splitTriangle(Point3D r, float l, int xd, int yd, int zd){
        float halfL = l/2;
        float quartL = halfL/2;
        
        if(showTriangles)
            glColor3f(0.0f, 0.0f, 1.0f);
        
        decTriSide(r, halfL, xd, yd, zd);
        
        if(showTriangles)
            glColor3f(0.0f, 1.0f, 1.0f);

        if(xd == 0)
            decTriSide(r.add(halfL, 0, 0), l/2, xd, yd, zd);
        else if(zd == 0)
            decTriSide(r.add(0, 0, -halfL), l/2, xd, yd, zd);
        
        if(showTriangles)
            glColor3f(1.0f, 0.0f, 1.0f);
        
        if(xd == 0)
            decTriSide(r.add(quartL, quartL*yd, -quartL*zd), l/2, xd, -yd, -zd);
        else if(zd == 0)
            decTriSide(r.add(quartL*xd, quartL*yd, -quartL), l/2, -xd, -yd, zd);
        
        if(showTriangles)
            glColor3f(1.0f, 1.0f, 0.0f);
        
        if(xd == 0)
            decTriSide(r.add(quartL, quartL*yd, -quartL*zd), l/2, xd, yd, zd);
        else if(zd == 0)
            decTriSide(r.add(quartL*xd, quartL*yd, -quartL), l/2, xd, yd, zd);
    }
    
    private Point3D normalizePoint(Point3D a, Point3D b, double distance){
        double dx = b.getX()-a.getX();
        double dy = b.getY()-a.getY();
        double dz = b.getZ()-a.getZ();
        dx = (dx*distance)/distance3P(a, b);
        dy = (dy*distance)/distance3P(a, b);
        dz = (dz*distance)/distance3P(a, b);
        
        Point3D c = new Point3D(a.getX()+dx, a.getY()+dy, a.getZ()+dz);
        
        return c;
    }
    
    private double distance3P(Point3D a, Point3D b){
        return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY())+(a.getZ()-b.getZ())*(a.getZ()-b.getZ()));
    }
    
    private void vertexP(Point3D a){
        Point3D norm = normalizePoint(center, a, radius);
        glVertex3d(norm.getX(), norm.getY(), norm.getZ());
    }
}

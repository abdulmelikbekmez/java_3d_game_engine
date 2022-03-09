/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solo_game.physic;

import org.joml.Vector3f;

import static java.lang.Math.*;

/**
 * @author abdulmelik
 */
public class TrianglePlane extends Plane {

    private final Vector3f AB;
    private final Vector3f BC;
    private final Vector3f CA;

    private final Vector3f BA;
    private final Vector3f CB;
    private final Vector3f AC;

    private final Vector3f A;
    private final Vector3f B;
    private final Vector3f C;


    public TrianglePlane(Vector3f a, Vector3f b, Vector3f c) {
        super(a, b, c);
        this.A = new Vector3f(a);
        this.B = new Vector3f(b);
        this.C = new Vector3f(c);

        AB = new Vector3f(b).sub(a);
        BA = new Vector3f(a).sub(b);

        BC = new Vector3f(c).sub(b);
        CB = new Vector3f(b).sub(c);

        CA = new Vector3f(a).sub(c);
        AC = new Vector3f(a).sub(c);

    }

    public Vector3f projectionBoverA(Vector3f b, Vector3f a) {
        float complement = new Vector3f(a).dot(b) / a.length();
        return new Vector3f(a).normalize().mul(complement);
    }

    public float componentBoverA(Vector3f b, Vector3f a) {
        return new Vector3f(a).dot(b) / a.length();
    }

    public boolean isCollided(Ray ray) {
        Vector3f planePoint = ray.getPointOfPlaneIntersection(this);
        Vector3f RA = new Vector3f(planePoint).sub(A);
        Vector3f VA = new Vector3f(AB).add(projectionBoverA(BA, BC));
        float x = componentBoverA(RA, VA);
        float a = 1 - (x / VA.length());

        boolean acheck = (a < .8f && a > 0f);

        Vector3f RB = new Vector3f(planePoint).sub(B);
        Vector3f VB = new Vector3f(BC).add(projectionBoverA(CB, CA));
        float y = componentBoverA(RB, VB);
        float b = 1 - (y / VB.length());

        boolean bcheck = (b < .8f && b > 0);

        Vector3f RC = new Vector3f(planePoint).sub(C);
        Vector3f VC = new Vector3f(CA).add(projectionBoverA(AC, AB));
        float z = componentBoverA(RC, VC);
        float c = 1 - (z / VC.length());

        boolean ccheck = (c < .8f && c > 0);

        return acheck && bcheck && ccheck;
    }
}

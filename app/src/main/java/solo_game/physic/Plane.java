/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solo_game.physic;

import org.joml.Vector3f;

/**
 *
 * @author abdulmelik
 */
public class Plane {

    protected final Vector3f point;
    protected final Vector3f normal;

    public Plane(Vector3f a, Vector3f b, Vector3f c) {
        Vector3f triEdge1 = new Vector3f(b).sub(a);
        Vector3f triEdge2 = new Vector3f(c).sub(a);

        normal = new Vector3f(triEdge1).cross(triEdge2);
        point = new Vector3f(a);
    }

    protected Plane() {
        point = null;
        normal = null;
    }

    public boolean isOnPlane(Vector3f other) {
        float scalar = new Vector3f(normal).dot(new Vector3f(point).sub(other));
        return Math.abs(scalar) < 0.01f;
    }

    // Copy of normal vector
    public Vector3f getNormal() {
        return new Vector3f(normal);
    }

    // Copy of normal vector
    public Vector3f getPoint() {
        return new Vector3f(point);
    }
}

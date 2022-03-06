package solo_game.physic;

import org.joml.Vector3f;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author abdulmelik
 */
public class Ray {

    private final Vector3f start;
    private final Vector3f direction;

    public Ray(Vector3f start, Vector3f direction) {
        this.start = start;
        this.direction = direction;
    }

    public Vector3f getDirection() {
        return new Vector3f(direction);
    }

    public Vector3f getStart() {
        return new Vector3f(start);
    }

    private float getHeightOfPlaneIntersection(Plane plane) {
        float normalDotDir = plane.getNormal().dot(getDirection());
        Vector3f pointSubStart = plane.getPoint().sub(start);
        float normalDotSubtracted = plane.getNormal().dot(pointSubStart);
        return normalDotSubtracted / normalDotDir;
    }

    public Vector3f getPointOfPlaneIntersection(Plane plane) {
        float height = getHeightOfPlaneIntersection(plane);
        return getStart().add(getDirection().mul(height));
    }

}

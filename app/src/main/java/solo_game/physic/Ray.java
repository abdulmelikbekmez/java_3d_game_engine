package solo_game.physic;

import org.joml.Vector3f;

/**
 * @author abdulmelik
 */
public class Ray {

    private final Vector3f start;
    private final Vector3f direction;
    public float height;

    public Ray(Vector3f start, Vector3f direction) {
        this.start = start;
        this.direction = direction;
        height = -1;
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
        height = getHeightOfPlaneIntersection(plane);
        return getStart().add(getDirection().mul(height));
    }

}

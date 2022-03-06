/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solo_game;

import org.joml.Vector3f;
import solo_game.physic.Ray;
import solo_game.physic.TrianglePlane;
import solo_game.renderer.Mesh;

/**
 *
 * @author abdulmelik
 */
public class Box extends GameItem {

    private static final float[] vertexArray = {
        // front
        -.5f, -.5f, .5f, 1.0f, 0.0f, 0.0f, 1.0f,
        .5f, -.5f, .5f, .0f, 1.0f, 0.0f, 1.0f,
        .5f, .5f, .5f, .0f, .0f, 1.0f, 1.0f,
        -.5f, .5f, .5f, 1.0f, 1.0f, 1.0f, 1.0f,
        // back
        -.5f, -.5f, -.5f, 1.0f, 0.0f, 0.0f, 1.0f,
        .5f, -.5f, -.5f, .0f, 1.0f, 0.0f, 1.0f,
        .5f, .5f, -.5f, .0f, .0f, 1.0f, 1.0f,
        -.5f, .5f, -.5f, 1.0f, 1.0f, 1.0f, 1.0f,};

    private static final int[] elementArray = {
        // front
        0, 1, 2, 2, 3, 0,
        // right
        1, 5, 6, 6, 2, 1,
        // back
        7, 6, 5, 5, 4, 7,
        // left
        4, 0, 3, 3, 7, 4,
        // bottom
        4, 5, 1, 1, 0, 4,
        // top
        3, 2, 6, 6, 7, 3
    };

    private static final Mesh[] meshs = new Mesh[]{
        new Mesh(vertexArray, elementArray)};

    public Box(Vector3f pos) {
        super(pos, meshs);
    }

    private TrianglePlane[] getPlanes() {
        TrianglePlane[] planes = new TrianglePlane[12];
        Vector3f[] points = new Vector3f[8];
        int index = 0;
        for (int i = 0; i < vertexArray.length; i += 7) {
            points[index++] = new Vector3f(vertexArray[i], vertexArray[i + 1], vertexArray[i + 2]).add(pos);
        }
        index = 0;
        for (int i = 0; i < elementArray.length; i += 3) {
            int a = elementArray[i];
            int b = elementArray[i+1];
            int c = elementArray[i+2];
            planes[index++] = new TrianglePlane(points[a], points[b], points[c]);
        }
        return planes;
    }

    public boolean isCollided(Camera camera) {
        Ray ray = new Ray(camera.getPosition(), camera.getDirection());
        TrianglePlane[] planes = getPlanes();
        for (TrianglePlane plane : planes) {
            if (plane.isCollided(ray)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "pos => "+ this.pos; 
    }
    

}

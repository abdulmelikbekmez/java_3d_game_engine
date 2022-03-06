
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solo_game;

import org.joml.Vector3f;
import solo_game.physic.Ray;
import solo_game.physic.TrianglePlane;
import solo_game.renderer.ColoredMesh;
import solo_game.renderer.Mesh;
import solo_game.renderer.Shader;

/**
 *
 * @author abdulmelik
 */
public class ColoredBox extends GameItem {

    private static final float[] vertexArray = {
            // front
            -.5f, -.5f, .5f, // bottom left 0
            .5f, -.5f, .5f, // bottom right 1
            .5f, .5f, .5f, // top right 2
            -.5f, .5f, .5f, // top left 3
            // back
            -.5f, -.5f, -.5f, // bottom left 4
            .5f, -.5f, -.5f, // bottom right 5
            .5f, .5f, -.5f, // top right 6
            -.5f, .5f, -.5f, }; // top left 7

    // private static final int[] elementArray = {
    // // front
    // 0, 1, 2, 2, 3, 0,
    // // right
    // 1, 5, 6, 6, 2, 1,
    // // back
    // 7, 6, 5, 5, 4, 7,
    // // left
    // 4, 0, 3, 3, 7, 4,
    // // bottom
    // 4, 5, 1, 1, 0, 4,
    // // top
    // 3, 2, 6, 6, 7, 3
    // };

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

    private final Vector3f color;
    private boolean isCollided;

    // denene
    public Vector3f collidedPos = null;
    // denene

    private static final Mesh[] meshs = new Mesh[] {
            new ColoredMesh(vertexArray, elementArray) };

    public ColoredBox(Vector3f pos, Vector3f color) {
        super(pos, meshs);
        this.color = color;
        isCollided = false;
    }

    private TrianglePlane[] getPlanes() {
        TrianglePlane[] planes = new TrianglePlane[12];
        Vector3f[] points = new Vector3f[8];
        int index = 0;
        for (int i = 0; i < vertexArray.length; i += 3) {
            points[index++] = new Vector3f(vertexArray[i], vertexArray[i + 1], vertexArray[i + 2]).add(pos);
        }
        index = 0;
        for (int i = 0; i < elementArray.length; i += 3) {
            int a = elementArray[i];
            int b = elementArray[i + 1];
            int c = elementArray[i + 2];
            planes[index++] = new TrianglePlane(points[a], points[b], points[c]);
        }
        return planes;
    }

    public boolean isCollided(Camera camera) {
        Ray ray = new Ray(camera.getPosition(), camera.getDirection());
        TrianglePlane[] planes = getPlanes();
        for (TrianglePlane plane : planes) {
            if (plane.deneme(ray)) {
                isCollided = true;
                collidedPos = plane.collidedPos;
                return true;
            }
        }
        isCollided = false;
        collidedPos = null;
        return false;
    }

    @Override
    public String toString() {
        return "pos => " + this.pos;
    }

    @Override
    public void render(Shader shader, Camera camera) {
        shader.enable();
        setModelViewMatrix(shader, camera);
        if (isCollided)
            shader.setUniform3f("uColor", new Vector3f(0, 128, 0));
        else
            shader.setUniform3f("uColor", color);

        for (Mesh mesh : meshs)
            mesh.draw();
    }

}

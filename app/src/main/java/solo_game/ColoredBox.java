
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

    private Vector3f color = new Vector3f(128, 0, 0);
    public static final Vector3f hoveredColor = new Vector3f(0, 128, 0);
    public static final Vector3f selectedColor = new Vector3f(0, 0, 128);
    public boolean isHovered;
    public boolean isSelected;

    // TODO: for debug
    public Vector3f collidedPos;

    private static final Mesh[] meshes = new Mesh[] {
            new ColoredMesh(vertexArray, elementArray) };

    public ColoredBox(Vector3f pos, Vector3f color) {
        super(pos, meshes);
        this.color = color;
        isHovered = false;
    }

    public ColoredBox(Vector3f pos, Vector3f scale, Vector3f color) {
        super(pos, scale, meshes);
        this.color = color;
        isHovered = false;
    }

    public ColoredBox(Vector3f pos, float scale, Vector3f color) {
        super(pos, scale, meshes);
        this.color = color;
        isHovered = false;
    }

    public ColoredBox(Vector3f pos) {
        super(pos, meshes);
    }

    public boolean isCollided(Camera camera) {
        Ray ray = new Ray(camera.getPosition(), camera.getDirection());

        Vector3f[] points = new Vector3f[8];
        int index = 0;
        for (int i = 0; i < vertexArray.length; i += 3) {
            points[index++] = new Vector3f(vertexArray[i], vertexArray[i + 1], vertexArray[i + 2]).add(pos);
        }
        for (int i = 0; i < elementArray.length; i += 3) {
            int a = elementArray[i];
            int b = elementArray[i + 1];
            int c = elementArray[i + 2];
            TrianglePlane plane = new TrianglePlane(points[a], points[b], points[c]);

            if (plane.isCollided(ray)) {
                collidedPos = plane.collidedPos;
                isHovered = true;
                return true;
            }
        }
        isHovered = false;
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

        shader.setUniform3f("uColor", color);

        for (Mesh mesh : meshes) {
            mesh.draw();
        }
    }

    public void renderWithColor(Shader shader, Camera camera, Vector3f color) {

        shader.enable();
        setModelViewMatrix(shader, camera);
        shader.setUniform3f("uColor", color);

        for (Mesh mesh : meshes) {
            mesh.draw();
        }
    }
}

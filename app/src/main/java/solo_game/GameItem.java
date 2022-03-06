
package solo_game;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import solo_game.renderer.Mesh;
import solo_game.renderer.Shader;

/**
 * GameItem
 */
public class GameItem {
    protected final Vector3f pos;
    private final Mesh[] meshs;

    public GameItem(Vector3f pos, Mesh[] meshs) {
        this.meshs = meshs;
        this.pos = pos;
    }

    public void render(Shader shader, Camera camera) {
        shader.enable();
        setModelViewMatrix(shader, camera);
        for (Mesh mesh : meshs)
            mesh.draw();

    }

    protected void setModelViewMatrix(Shader shader, Camera camera) {
        Matrix4f model = new Matrix4f().identity().translate(pos);
        Matrix4f view = camera.getViewMatrix();
        Matrix4f projection = camera.getProjectionMatrix();

        shader.setUniformMat4f("model", model);
        shader.setUniformMat4f("view", view);
        shader.setUniformMat4f("projection", projection);

    }
}

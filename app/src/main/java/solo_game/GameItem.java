package solo_game;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import solo_game.renderer.Mesh;
import solo_game.renderer.Shader;

/**
 * GameItem
 */
public class GameItem {

    protected Vector3f pos;
    protected final Vector3f scale;
    private final Mesh[] meshs;

    public GameItem(Vector3f pos, Vector3f scale, Mesh[] meshs) {
        this.meshs = meshs;
        this.pos = pos;
        this.scale = scale;
    }

    public GameItem(Vector3f pos, float scale, Mesh[] meshs) {
        this.meshs = meshs;
        this.pos = pos;
        this.scale = new Vector3f(scale);
    }

    public GameItem(Vector3f pos, Mesh[] meshs) {
        this.meshs = meshs;
        this.pos = pos;
        this.scale = new Vector3f(1);
    }

    public void render(Shader shader, Camera camera) {
        shader.enable();
        setModelViewMatrix(shader, camera);
        for (Mesh mesh : meshs) {
            mesh.draw();
        }
    }

    public Vector3f getPos() {
        return new Vector3f(pos);
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    protected void setModelViewMatrix(Shader shader, Camera camera) {
        Matrix4f model = new Matrix4f().identity().translate(pos).scale(scale);
        Matrix4f view = camera.getViewMatrix();
        Matrix4f projection = camera.getProjectionMatrix();

        shader.setUniformMat4f("model", model);
        shader.setUniformMat4f("view", view);
        shader.setUniformMat4f("projection", projection);

    }
}

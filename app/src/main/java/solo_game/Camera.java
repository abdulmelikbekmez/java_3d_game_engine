package solo_game;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import solo_game.input.KeyListener;
import solo_game.input.KeyboardHandler;
import solo_game.input.MouseHandler;
import solo_game.input.MouseListener;

/**
 * Camera
 */
public class Camera implements MouseHandler, KeyboardHandler {

    private Vector3f up;
    private Vector3f front;
    private Vector3f right;
    private final Vector3f worldUp;

    private final Vector3f pos;

    // Euler angles
    private float yaw;
    private float pitch;

    // Camera options
    private final float movementSpeed;
    private final float mouseSensitivity;
    private final float zoom;
    private final float aspectRatio;

    public Camera(final Vector3f pos) {
        this.pos = pos;
        front = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);
        worldUp = new Vector3f(0.0f, 1.0f, 0.0f);
        right = new Vector3f(front).cross(up).normalize();

        yaw = -90.0f;
        pitch = 0.0f;
        mouseSensitivity = 0.05f;
        movementSpeed = 2f;
        zoom = 45.0f;
        aspectRatio = Window.getAspectRatio();
    }

    public Vector3f getPosition() {
        return new Vector3f(pos);
    }

    public Vector3f getDirection() {
        return new Vector3f(front);
    }

    public Matrix4f getViewMatrix() {
        final Vector3f center = new Vector3f(pos).add(this.front);
        return new Matrix4f().lookAt(pos, center, up);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective((float) Math.toRadians(zoom), aspectRatio, 0.01f, 10000000.0f);
    }

    private void mouseInput() {
        final float xoffset = MouseListener.getDx() * mouseSensitivity;
        final float yoffset = MouseListener.getDy() * mouseSensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (pitch > 89.0f) {
            pitch = 89.0f;
        }
        if (pitch < -89.0f) {
            pitch = -89.0f;
        }

    }

    private void updateCameraVectors() {
        final Vector3f frontLocal = new Vector3f();
        frontLocal.x = (float) Math.cos(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));
        frontLocal.y = (float) Math.sin(Math.toRadians(pitch));
        frontLocal.z = (float) Math.sin(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));

        front = frontLocal.normalize();
        right = new Vector3f(front).cross(worldUp).normalize();
        up = new Vector3f(right).cross(front).normalize();
    }

    @Override
    public void onMouseMove() {
        mouseInput();
        updateCameraVectors();
    }

    @Override
    public void onMouseClick() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
    }

    @Override
    public void onClick() {
        if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            final Vector3f dif = new Vector3f(right);
            dif.mul(movementSpeed);
            pos.add(dif);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            final Vector3f dif = new Vector3f(right);
            dif.mul(movementSpeed);
            pos.sub(dif);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            final Vector3f dif = new Vector3f(front);
            dif.mul(movementSpeed);
            pos.add(dif);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            final Vector3f dif = new Vector3f(front);
            dif.mul(movementSpeed);
            pos.sub(dif);
        }
    }

    public void update(float dt) {

        float speed = movementSpeed * dt;
        if (KeyListener.isKeyPressed(GLFW_KEY_D)) {
            final Vector3f dif = new Vector3f(right);
            dif.mul(speed);
            pos.add(dif);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_A)) {
            final Vector3f dif = new Vector3f(right);
            dif.mul(speed);
            pos.sub(dif);
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W)) {
            final Vector3f dif = new Vector3f(front);
            dif.mul(speed);
            pos.add(dif);
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_S)) {
            final Vector3f dif = new Vector3f(front);
            dif.mul(speed);
            pos.sub(dif);
        }
    }

}

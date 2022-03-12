
package solo_game.renderer;

import static org.lwjgl.opengl.GL20.*;

/**
 * Mesh
 */
public class ColoredMesh extends Mesh {

    public ColoredMesh(float[] vertexArray, int[] elementArray) {
        super(vertexArray, elementArray);
    }

    @Override
    protected void setup() {
        setupBuffers();

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionsSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);
    }

}

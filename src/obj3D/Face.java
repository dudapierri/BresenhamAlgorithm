package obj3D;

public class Face {
    private int[] vertexIndices;

    public Face(int[] vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    public int[] getVertexIndices() {
        return vertexIndices;
    }
}
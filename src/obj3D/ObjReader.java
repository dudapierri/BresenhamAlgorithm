package obj3D;

import core3d.Ponto3D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjReader {

    public static Obj3D loadObjFile(String filePath) throws IOException {

        List<Ponto3D> vertices = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));


        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                String[] parts = line.split("\\s+");
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                vertices.add(new Ponto3D(x, y, z, 1));
            } else if (line.startsWith("f ")) {
                String[] parts = line.split("\\s+");
                int[] vertexIndices = new int[parts.length - 1];
                for (int i = 1; i < parts.length; i++) {
                    vertexIndices[i - 1] = Integer.parseInt(parts[i].split("/")[0]) - 1;
                }
                faces.add(new Face(vertexIndices));
            }
        }
        reader.close();
        return new Obj3D(vertices, faces);

    }
}


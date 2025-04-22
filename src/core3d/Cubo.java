package core3d;

import java.util.ArrayList;

public class Cubo {
    public ArrayList<Triangulo3D> t = new ArrayList<>();
    
    public Cubo(Ponto3D p1, Ponto3D p2,Ponto3D p3,Ponto3D p4,Ponto3D p5,Ponto3D p6,
                Ponto3D p7,Ponto3D p8){
        t.add(new Triangulo3D(p1,p2,p3));
        t.add(new Triangulo3D(p3,p4,p1));
        t.add(new Triangulo3D(p5,p6,p7));
        t.add(new Triangulo3D(p7,p8,p5));
        t.add(new Triangulo3D(p1,p4,p5));
        t.add(new Triangulo3D(p4,p8,p5));
        t.add(new Triangulo3D(p2,p3,p6));
        t.add(new Triangulo3D(p3,p7,p6));
        t.add(new Triangulo3D(p1,p2,p6));
        t.add(new Triangulo3D(p1,p6,p5));
        t.add(new Triangulo3D(p4,p3,p7));
        t.add(new Triangulo3D(p6,p7,p8));
    }

    public ArrayList<Triangulo3D> getTriangles(){
        return t;
    }

    /*
    *
    criaCubo(100,100,0,100,100,300);
	criaCubo(300,200,0,100,200,300);
	private void criaCubo(float x,float y, float z, float lx,float ly, float lz) {
		Ponto3D p1 = new Ponto3D(x, y, z);
		Ponto3D p2 = new Ponto3D(x+lx, y, z);
		Ponto3D p3 = new Ponto3D(x+lx, y+ly, z);
		Ponto3D p4 = new Ponto3D(x, y+ly, z);

		Ponto3D p5 = new Ponto3D(x, y, z+lz);
		Ponto3D p6 = new Ponto3D(x+lx, y, z+lz);
		Ponto3D p7 = new Ponto3D(x+lx, y+ly, z+lz);
		Ponto3D p8 = new Ponto3D(x, y+ly, z+lz);

		listaDeTriangulos.add(new Triangulo3D(p1,p2,p3));
		listaDeTriangulos.add(new Triangulo3D(p3,p4,p1));

		listaDeTriangulos.add(new Triangulo3D(p5,p6,p7));
		listaDeTriangulos.add(new Triangulo3D(p7,p8,p5));

		listaDeTriangulos.add(new Triangulo3D(p1,p4,p5));
		listaDeTriangulos.add(new Triangulo3D(p4,p8,p5));

		listaDeTriangulos.add(new Triangulo3D(p2,p3,p6));
		listaDeTriangulos.add(new Triangulo3D(p3,p7,p6));

		listaDeTriangulos.add(new Triangulo3D(p1,p2,p6));
		listaDeTriangulos.add(new Triangulo3D(p1,p6,p5));

		listaDeTriangulos.add(new Triangulo3D(p4,p3,p7));
		listaDeTriangulos.add(new Triangulo3D(p6,p7,p8));
	}*/
    
}

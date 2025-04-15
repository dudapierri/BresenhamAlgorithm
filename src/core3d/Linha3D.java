package core3d;

import core2d.*;
import geral.*;

import java.util.List;

public class Linha3D {
    Ponto3D a;
    Ponto3D b;
    public List<int[]> pixeisBresenham = null;

    public Linha3D(Ponto3D a, Ponto3D b) {
        super();
        this.a = a;
        this.b = b;
    }
    public Linha3D(double x1,double y1, double z1, double x2, double y2, double z2) {
        this.a = new Ponto3D(x1, y1, z1);
        this.b = new Ponto3D(x2, y2, z2);
    }

    public void desenhase(PrototipoTela tela) {
        // Projeção paralela simples
        Ponto p1 = projetarPara2D(a);
        Ponto p2 = projetarPara2D(b);

        // Faz clipping nos pontos projetados
        Linha clipada = Clipping.clipping(p1.x, p1.y, p2.x, p2.y, tela);

        if (clipada.a.x != -1) {
            List<int[]> pontos = Bresenham.bresenham(clipada.a.x, clipada.a.y, clipada.b.x, clipada.b.y);
            for (int[] pixel : pontos) {
                tela.desenhaPixel(pixel[0], pixel[1], 0, 0, 0);
            }
        }
    }

    private Ponto projetarPara2D(Ponto3D p) {
        return new Ponto((int)p.x, (int)p.y); // Projeção paralela simples
    }
}

package core3d;

import core2d.*;
import geral.*;

import java.util.List;

public class Triangulo3D {
    Ponto3D pa;
    Ponto3D pb;
    Ponto3D pc;
    public Triangulo3D(Ponto3D a, Ponto3D b, Ponto3D c) {
        super();
        this.pa = new Ponto3D(a);
        this.pb = new Ponto3D(b);
        this.pc = new Ponto3D(c);
    }

    public void desenhase(PrototipoTela tela) {
        // Projeção paralela
        Ponto p1 = projetarPara2D(pa);
        Ponto p2 = projetarPara2D(pb);
        Ponto p3 = projetarPara2D(pc);

        // Clipar cada aresta
        Linha l1 = Clipping.clipping(p1.x, p1.y, p2.x, p2.y, tela);
        Linha l2 = Clipping.clipping(p2.x, p2.y, p3.x, p3.y, tela);
        Linha l3 = Clipping.clipping(p3.x, p3.y, p1.x, p1.y, tela);

        desenhaLinhaSeValida(l1, tela);
        desenhaLinhaSeValida(l2, tela);
        desenhaLinhaSeValida(l3, tela);
    }

    private void desenhaLinhaSeValida(Linha l, PrototipoTela tela) {
        if (l.a.x != -1) {
            List<int[]> pontos = Bresenham.bresenham(l.a.x, l.a.y, l.b.x, l.b.y);
            for (int[] pixel : pontos) {
                tela.desenhaPixel(pixel[0], pixel[1], 0, 0, 0);
            }
        }
    }

    private Ponto projetarPara2D(Ponto3D p) {
        return new Ponto((int)p.x, (int)p.y); // Projeção paralela simples
    }

    public void translacao(float a,float b, float c) {
        Matriz4x4 m = new Matriz4x4();
        m.setTranslate(a, b,c);
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }
    public void escala(float a,float b,float c) {
        Matriz4x4 m = new Matriz4x4();
        m.setSacale(a, b, c);
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }

    public void rotacao(float ang) {
        Matriz4x4 m = new Matriz4x4();
        m.setRotateY(ang);
        //System.out.println("rot Y");
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }

    public void rotacaoY(float ang) {
        Matriz4x4 m = new Matriz4x4();
        m.setRotateY(ang);
        //System.out.println("rot Y");
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }

    public void rotacaoX(float ang) {
        Matriz4x4 m = new Matriz4x4();
        m.setRotateX(ang);
        //System.out.println("rot Y");
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }

    public void rotacaoZ(float ang) {
        Matriz4x4 m = new Matriz4x4();
        m.setRotateZ(ang);
        //System.out.println("rot Y");
        pa.multiplicaMat(m);
        pb.multiplicaMat(m);
        pc.multiplicaMat(m);
    }
}

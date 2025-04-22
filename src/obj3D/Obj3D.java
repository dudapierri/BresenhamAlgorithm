package obj3D;

import core2d.Linha;
import core2d.Ponto;
import core3d.*;
import geral.Bresenham;
import geral.Clipping;
import geral.PrototipoTela;

import java.util.ArrayList;
import java.util.List;

public class Obj3D {

    public List<Ponto3D> listaDePontos;
    public List<Face> listaDeConexoes;
    public Ponto3D centroide;

    public Obj3D(List<Ponto3D> vertices, List<Face> faces){
        this.listaDePontos = vertices;
        this.listaDeConexoes = faces;
        centraliza();
        this.centroide = calcularCentroide();
    }

    private Ponto3D calcularCentroide() {
        double somaX = 0, somaY = 0, somaZ = 0;
        for (Ponto3D ponto : listaDePontos) {
            somaX += ponto.x;
            somaY += ponto.y;
            somaZ += ponto.z;
        }
        double mediaX = somaX / listaDePontos.size();
        double mediaY = somaY / listaDePontos.size();
        double mediaZ = somaZ / listaDePontos.size();

        return new Ponto3D(mediaX, mediaY, mediaZ, 1);
    }


    private void centraliza(){
        for(Ponto3D p : listaDePontos){
            p.x += 375;
            p.y += 275;
        }
    }

    public void desenhase(PrototipoTela tela) {
        for (Face face : listaDeConexoes){
            int[] indices = face.getVertexIndices();
            for (int i = 0; i < indices.length; i++) {
                Ponto3D pontoAtual = listaDePontos.get(indices[i]);
                Ponto3D proximoPonto = listaDePontos.get(indices[(i + 1) % indices.length]);

                Ponto3D pontoAtualPerspectiva = fazPerspectiva(pontoAtual);
                Ponto3D proximoPontoPerspectiva = fazPerspectiva(proximoPonto);

                Linha l = new Linha(pontoAtualPerspectiva,proximoPontoPerspectiva);
                l = Clipping.clipping(l, tela);

                desenhaLinhaSeValida(l, tela);
            }
        }
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

    private Ponto3D fazPerspectiva(Ponto3D p) {
        Matriz4x4 mat = new Matriz4x4();
        mat.setPerspectiva(500);
        Ponto3D pErspectiva =  p.multiplicaMatDesenho(mat);
        pErspectiva.ajustaW();
        return pErspectiva;
    }

    public void translacao(float a,float b, float c) {
        Matriz4x4 m = new Matriz4x4();
        m.setTranslate(a, b,c);
        for(Ponto3D p : listaDePontos){
            p.multiplicaMat(m);
        }
        calcularCentroide();
    }
    public void escala(float a,float b,float c) {
        Matriz4x4 m = new Matriz4x4();
        m.setSacale(a, b, c);
        for(Ponto3D p : listaDePontos){
            p.multiplicaMat(m);
        }
        calcularCentroide();
    }


    public void rotacaoEixoQualquer(float anguloGraus, float ux, float uy, float uz) {
        Matriz4x4 m = new Matriz4x4();
        m.setRotateAxis(anguloGraus, ux, uy, uz);
        for(Ponto3D p : listaDePontos){
            p.multiplicaMat(m);
        }
        calcularCentroide();
    }


}

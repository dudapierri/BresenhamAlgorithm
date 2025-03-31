import java.util.List;

public class Linha {

    public Ponto a;
    public Ponto b;
    public List<int[]> pixeisBresenham = null; // Lista de pixel que o algoritmo de Bresenham definiu que precisa ser pintado

    public Linha(int xInicio, int yInicio, int xFim, int yFim){
        this.a = new Ponto(xInicio, yInicio);
        this.b = new Ponto(xFim, yFim);
    }

    public Linha(Ponto inicio, Ponto fim){
        this.a = inicio;
        this.b = fim;
    }

    public void desenhase(PrototipoTela p) { // Aqui eu quero fazer um for no array de pixeisBresenham e usar a função desenha pixel la do PrototipoTela
        for(int[] pixel : this.pixeisBresenham){
                p.desenhaPixel(pixel[0], pixel[1], 0, 0, 0);
        }
    }

}

import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<int[]> pontosLinha = Bresenham.bresenham(1, 1, 4, 10);
        for (int[] ponto : pontosLinha) {
            System.out.println("(" + ponto[0] + ", " + ponto[1] + ")");
        }
    }

}

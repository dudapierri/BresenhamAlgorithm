package core2d;

public class Ponto {
    public int x;
    public int y;
    public int w = 1;

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void multiplicaMat(Matriz3x3 mat) {
        System.out.println("func multiplicaMat - Ponto");
        int novoX = (int)Math.round(mat.mat[0][0] * x + mat.mat[0][1] * y + mat.mat[0][2] * w);
        int novoY = (int)Math.round(mat.mat[1][0] * x + mat.mat[1][1] * y + mat.mat[1][2] * w);
        int novoW = (int)Math.round(mat.mat[2][0] * x + mat.mat[2][1] * y + mat.mat[2][2] * w);

        System.out.println(x + " "+ y + " " +w);
        x = novoX;
        y = novoY;
        w = novoW;
        System.out.println(x + " "+ y + " " +w);
    }
}

package core3d;

public class Ponto3D {
    public double x;
    public double y;
    public double z;
    public double w;

    public Ponto3D(Ponto3D p) {
        x = p.x;
        y = p.y;
        z = p.z;
        w = p.w;
    }

    public Ponto3D(double x, double y,double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }



    public Ponto3D(double x, double y,double z, double w) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }



    public void multiplicaMat(Matriz4x4 mat) {
        double x1 = mat.mat[0][0]*x +mat.mat[0][1]*y +mat.mat[0][2]*z +mat.mat[0][3]*w;
        double y1 = mat.mat[1][0]*x +mat.mat[1][1]*y +mat.mat[1][2]*z +mat.mat[1][3]*w;
        double z1 = mat.mat[2][0]*x +mat.mat[2][1]*y +mat.mat[2][2]*z +mat.mat[2][3]*w;
        double w1 = mat.mat[3][0]*x +mat.mat[3][1]*y +mat.mat[3][2]*z +mat.mat[3][3]*w;

        //System.out.println("x "+x+" "+x1);
        //System.out.println("y "+y+" "+y1);
        //System.out.println("z "+z+" "+z1);

        x = x1;
        y = y1;
        z = z1;
        w = w1;


    }


}

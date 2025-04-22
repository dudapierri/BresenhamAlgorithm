package geral;

import obj3D.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

//ObjReader.loadObjFile("src/obj3D/tank.obj")
public class Main {
    public static void main(String[] args) throws IOException {
        PrototipoTela meuCanvas = new PrototipoTela();
        JFrame f = new JFrame();
        f.setSize(800, 650);
        f.setVisible(true);
        f.getContentPane().add(meuCanvas);


        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        meuCanvas.start();
    }

}

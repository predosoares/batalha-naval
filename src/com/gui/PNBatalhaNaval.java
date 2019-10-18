package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import com.regras.*;

public class PNBatalhaNaval extends JPanel implements MouseListener
{
    double xCorner = 75.0 ; // x do canto esquerdo superior
    double yCorner = 75.0 ; // y do canto esquerdo superior
    double width = 30.0 ; // largura do retângulo
    double height = 30.0 ; // altura do retângulo
    float lineGap = 1.0f ; // espessura da linha

    Celula tab[][] = new Celula[15][15] ;

    private Rectangle2D.Double box1[][] = new Rectangle2D.Double[15][15] ;
    private Rectangle2D.Double box2[][] = new Rectangle2D.Double[15][15] ;
    private CtrlRegras ctrl ;

    private final int NUM_LINES = 15 ;
    private final int NUM_COLUMNS = 15 ;


    public PNBatalhaNaval(CtrlRegras c)
    {
        double x, y ;
        ctrl = c ;

        addMouseListener(this) ;

        // Tamanho de cada retângulo é de 30px x 30px
        for (int line = 0 ; line < NUM_LINES; line++)
        {
            y = yCorner + ( line * 30.0 ) ;

            for (int column = 0 ; column < NUM_COLUMNS; column++)
            {
                x = xCorner + ( column * 30.0 ) ;
                box1[line][column] = new Rectangle2D.Double(x, y, width, height) ;
                box2[line][column] = new Rectangle2D.Double(x + 550, y, width, height) ;
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int mat[][] = ctrl.getMatrizPlayer1();

        g2d.setStroke(new BasicStroke( lineGap, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f));

        // Iteração para preencher os quadrados de cor
        for(int i = 0 ;i < NUM_LINES; i++)
        {
            for(int j = 0 ; j < NUM_COLUMNS; j++)
            {
                if(mat[i][j] == 0)
                {
                    g2d.setPaint( Color.WHITE );
                    g2d.fill( box1[i][j] );
                    g2d.fill( box2[i][j] );
                }
                else if(mat[i][j] == 1)
                {
                    g2d.setPaint( Color.GRAY );
                    g2d.fill( box1[i][j] );
                }
            }
        }

        // Iteração para desenhar o tabuleiro
        g2d.setPaint(Color.BLACK);
        for ( int i = 0; i < NUM_LINES ; i++)
        {
            String number = (i >= 9) ? Integer.toString(i + 1) : " " + Integer.toString(i + 1) ;
            g2d.drawString(Character.toString(65 + i), 55, 65 + 30*(i + 1)) ;
            g2d.drawString(number, 50 + 30*(i + 1), 65) ;

            g2d.drawString(Character.toString(65 + i), 605, 65 + 30*(i + 1)) ;
            g2d.drawString(number, 600 + 30*(i + 1), 65) ;

            for (int column = 0; column < NUM_COLUMNS; column++)
            {
                g2d.draw( box1[i][column] ) ;
                g2d.draw( box2[i][column] ) ;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int linha, coluna;
        int x = e.getX();
        int y = e.getY();

        linha = ( y - 75) / 30 + 1 ;
        coluna = ( x - 75) / 30 + 1 ;

        System.out.println(linha);
        System.out.println(coluna);

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
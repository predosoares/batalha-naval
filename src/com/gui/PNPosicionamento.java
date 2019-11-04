package com.gui;

import com.regras.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class PNPosicionamento extends JPanel implements MouseListener
{
    double xCorner = 75.0 ; // x do canto esquerdo superior
    double yCorner = 75.0 ; // y do canto esquerdo superior
    double width = 30.0 ; // largura do retângulo
    double height = 30.0 ; // altura do retângulo
    double boxGap = 550.0 ; // distância lateral do campo a borda esquerda
    float lineGap = 1.0f ; // espessura da linha

    private final int NUM_LINES = 15 ;
    private final int NUM_COLUMNS = 15 ;

    private Rectangle2D.Double box[][] = new Rectangle2D.Double[15][15] ;

    public PNPosicionamento() {
        double x, y;

        addMouseListener(this);

        // Tamanho de cada retângulo é de 30px x 30px
        for (int line = 0; line < NUM_LINES; line++) {
            y = yCorner + (line * 30.0);

            for (int column = 0; column < NUM_COLUMNS; column++) {
                x = xCorner + (column * 30.0);
                box[line][column] = new Rectangle2D.Double(x + boxGap, y, width, height);
            }
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;
        Graphics2D g2d = (Graphics2D) g ;

        int mat[][] =  ;

        g2d.setStroke(new BasicStroke( lineGap, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f));

        // Iteração para preencher os quadrados de cor
        for( int i = 0 ; i < NUM_LINES; i++)
        {
            for( int j = 0 ; j < NUM_COLUMNS; j++)
            {
                if( mat[i][j] == 0 )
                {
                    g2d.setPaint( Color.WHITE ) ;
                    g2d.fill( box[i][j] ) ;
                }
                else if( mat[i][j] == 1 )
                {
                    g2d.setPaint( Color.GRAY ) ;
                    g2d.fill( box[i][j] ) ;
                }
            }
        }

        // Iteração para desenhar o tabuleiro
        g2d.setPaint(Color.BLACK);
        for ( int i = 0; i < NUM_LINES ; i++)
        {
            String number = (i >= 9) ? Integer.toString(i + 1) : " " + Integer.toString(i + 1) ;

            g2d.drawString( Character.toString(65 + i), 605, 65 + 30*(i + 1)) ;
            g2d.drawString( number, 600 + 30*(i + 1), 65) ;

            for (int column = 0 ; column < NUM_COLUMNS ; column++ )
            {
                g2d.draw( box[i][column] ) ;
            }
        }
    }

    public void mouseClicked( MouseEvent e )
    {
        int linha, coluna ;
        double x = e.getX() ;
        double y = e.getY() ;

        linha = (int) ( y - ( yCorner )) / 30 + 1 ;
        coluna = (int) ( x - ( xCorner + boxGap )) / 30 + 1 ;

        System.out.println( linha ) ;
        System.out.println( coluna ) ;

        repaint() ;
    }

    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
}

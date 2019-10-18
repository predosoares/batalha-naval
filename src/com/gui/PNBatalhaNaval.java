package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import com.regras.*;

public class PNBatalhaNaval extends JPanel implements MouseListener
{
    double xIni = 75.0, yIni = 75.0, larg = 80.0, alt = 80.0, espLinha = 5.0 ;

    Celula tab[][] = new Celula[3][3] ;

    private Line2D.Double ln[] = new Line2D.Double[4] ;
    private CtrlRegras ctrl ;


    public PNBatalhaNaval(CtrlRegras c)
    {
        double x = xIni, y = yIni ;
        ctrl = c ;

        for( int i = 0 ; i < 3 ; i++ )
        {
            x = xIni ;

            for( int j = 0 ; j < 3 ; j++ )
            {
                tab[i][j] = new Celula(x,y) ;
                x += larg + espLinha ;
            }
            y += alt + espLinha ;
        }

        addMouseListener(this) ;
        ln[0]=new Line2D.Double(155.0,75.0,155.0,325.0);
        ln[1]=new Line2D.Double(240.0,75.0,240.0,325.0);
        ln[2]=new Line2D.Double(75.0,155.0,325.0,155.0);
        ln[3]=new Line2D.Double(75.0,240.0,325.0,240.0);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D) g;

        int mat[][]=ctrl.getMatriz();
        int vez = ctrl.getVez();

        g2d.setStroke(new BasicStroke(5.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,10.0f));

        // inserir aqui o teste do jogador da vez e a defini��o da cor dos segmentos de reta
        Color turnColor = vez == -1 ? Color.RED : Color.GREEN;
        vez = vez == -1 ? 5 : -1;
        ctrl.setVez(vez);

        // inserir aqui os comandos para desenhar os 4 segmentos de reta
        g2d.setPaint(turnColor);
        g2d.draw(ln[0]);
        g2d.draw(ln[1]);
        g2d.draw(ln[2]);
        g2d.draw(ln[3]);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(mat[i][j]!=0)
                {
                    // inserir aqui o c�digo para definir a cor do quadrado a ser desenhado
                    // e o desenho desse quadrado
                    if (mat[i][j] == -1)
                        g2d.setPaint(Color.GREEN);
                    else
                        g2d.setPaint(Color.RED);

                    Rectangle2D rt= new Rectangle2D.Double(tab[i][j].x+25,tab[i][j].y+25,30,30);

                    g2d.fill(rt);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int linha, coluna;
        int x = e.getX();
        int y = e.getY();

        linha = ( y / 75 ) - 1 ;
        coluna = ( x / 75 ) - 1 ;

        if (ctrl.jogadaValida( (int) linha, (int) coluna ) == true)
        {
            ctrl.setCasa( linha , coluna );

            if( ctrl.checaVitoria() == true )
            {
                repaint();
                String resposta = ctrl.getVez() == -1 ?  "Verde" : "Vermelho";
                JOptionPane.showMessageDialog(this,resposta + " ganhou!","Resultado",JOptionPane.DEFAULT_OPTION);
                ctrl.reset();
            }
            else if (ctrl.todasCasasOcupadas() == true)
            {
                ctrl.reset();
            }

            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
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
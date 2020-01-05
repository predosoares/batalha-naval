package com.gui.armas;

import com.gui.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Arma extends JPanel
{
    static int incremental = 0 ;

    protected int width ;
    protected int height ;
    protected TipoArma tipo ;
    protected Color color ;
    protected Color selectedColor = new Color(118, 116, 120) ;
    protected int[][] matriz ;
    protected int identificador ;
    protected boolean isInTab = false ;

    protected Arma()
    {
        this.identificador = setIdentificacao() ;
        new EventosArma(this) ;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;
        Graphics2D g2d = (Graphics2D) g ;

        if (GUIController.getGUIController().getArma() == this || isInTab == true)
            this.setBackground( selectedColor ) ;
        else
            this.setBackground( color ) ;

        g2d.setPaint( new Color(235,235,235) );
        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[i].length; j++)
                if (matriz[i][j] == 0)
                    g2d.fill(new Rectangle2D.Double((30 * j), (30 * i), 30, 30));

    }

    static int setIdentificacao() { return ++incremental ; }
    public int getIdentificacao() { return identificador ; }

    public void setJogada() { isInTab = true ; }

    public void setReposicionada() { isInTab = false ; }

    public boolean foiJogada() { return isInTab == true ; }

    public int[][] getMatriz() { return matriz ; }

    public TipoArma getTipo() { return tipo ; }

    public static int[][] rotacionaMatrizEm90GrausHorario(int[][] matriz)
    {
        int totLinhas = matriz[0].length ; //Total de colunas da matriz original
        int totColunas = matriz.length ; //Total de linhas da matriz original

        int[][] matrizRotacionada = new int[totLinhas][totColunas] ;

        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[0].length; j++)
                matrizRotacionada[j][ (totColunas-1)- i] = matriz[i][j];

        return matrizRotacionada ;
    }
}

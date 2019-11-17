package com.gui.armas;

import java.awt.*;

public class Couracado extends Arma
{
    public Couracado()
    {
        this.tipo = TipoArma.Couracado ;
        this.width = 150 ;
        this.height = 30 ;
        this.color = new Color(230, 88, 132) ;
        this.matriz = new int[][] {{(int) 'a', (int) 'a',(int) 'a', (int) 'a', (int) 'a'}} ;

        this.setSize( width, height) ;
        this.setBackground( color ) ;
    }
}

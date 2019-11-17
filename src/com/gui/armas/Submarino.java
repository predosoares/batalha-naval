package com.gui.armas;

import java.awt.*;

public class Submarino extends Arma
{
    public Submarino()
    {
        this.tipo = TipoArma.Submarino ;
        this.width = 30 ;
        this.height = 30 ;
        this.color = new Color(38, 209, 64) ;
        this.matriz = new int[][] {{ (int) 's' }} ;

        this.setSize( width, height) ;
        this.setBackground( color ) ;
    }
}

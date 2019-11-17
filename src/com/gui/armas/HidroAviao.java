package com.gui.armas;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HidroAviao extends Arma
{
    public HidroAviao()
    {
        this.tipo = TipoArma.HidroAviao ;
        this.width = 90 ;
        this.height = 60 ;
        this.color = new Color(24, 117, 38) ;
        this.matriz = new int[][] {{0, (int) 'h', 0 }, {(int) 'h', 0, (int) 'h'}} ;

        this.setSize( width, height) ;
        repaint() ;
    }
}

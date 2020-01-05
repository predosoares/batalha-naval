package com.gui.armas;

import java.awt.*;

public class Destroier extends Arma
{
    public Destroier()
    {
        this.tipo = TipoArma.Destroier ;
        this.width = 60 ;
        this.height = 30 ;
        this.color = new Color(220, 197, 26) ;
        this.matriz = new int[][] {{ TipoArma.Destroier.ordinal(),
                                     TipoArma.Destroier.ordinal() }} ;

        this.setSize( width, height) ;
        this.setBackground( color ) ;
    }
}

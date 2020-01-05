package com.gui.armas;

import java.awt.*;

public class HidroAviao extends Arma
{
    public HidroAviao()
    {
        this.tipo = TipoArma.HidroAviao ;
        this.width = 90 ;
        this.height = 60 ;
        this.color = new Color(24, 117, 38) ;
        this.matriz = new int[][] {{TipoArma.Vazio.ordinal(), TipoArma.HidroAviao.ordinal(), TipoArma.Vazio.ordinal() },
                                    {TipoArma.HidroAviao.ordinal(), TipoArma.Vazio.ordinal(), TipoArma.HidroAviao.ordinal()}} ;

        this.setSize( width, height) ;
        repaint() ;
    }
}

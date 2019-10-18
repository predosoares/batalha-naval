package com.gui;

import com.regras.*;

import java.awt.*;
import javax.swing.*;

public class FRBatalhaNaval extends JFrame
{
    final int LARG_DEFAULT = 1130 ;
    final int ALT_DEFAULT = 600 ;

    public FRBatalhaNaval(CtrlRegras c)
    {
        Toolkit tk = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = tk.getScreenSize() ;
        int screenWidth = screenSize.width ;
        int screenHeight = screenSize.height ;
        int x = screenWidth/2 - LARG_DEFAULT/2 ;
        int y = screenHeight/2 - ALT_DEFAULT/2 ;

        setBounds( x, y, LARG_DEFAULT, ALT_DEFAULT) ;
        setBackground(Color.CYAN) ;
        setDefaultCloseOperation(EXIT_ON_CLOSE) ;
        getContentPane().add( new PNBatalhaNaval(c) ) ;
        setBackground(Color.CYAN) ;
        setTitle("Batalha Naval");
    }


}

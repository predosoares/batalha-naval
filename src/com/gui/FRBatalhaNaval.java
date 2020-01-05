package com.gui;

import com.regras.*;

import java.awt.*;
import javax.swing.*;

public class FRBatalhaNaval extends JFrame
{
    public FRBatalhaNaval()
    {
        setResizable(false) ;
        setDefaultCloseOperation(EXIT_ON_CLOSE) ;
        setBackground( Color.BLACK ) ;
        setTitle("Batalha Naval") ;

        GUIController.getGUIController().novaSessao( this ) ;
    }
}

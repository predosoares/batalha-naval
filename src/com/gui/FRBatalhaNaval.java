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

        setResizable(false);
        setBounds( x, y, LARG_DEFAULT, ALT_DEFAULT) ;
        setDefaultCloseOperation(EXIT_ON_CLOSE) ;
        setBackground(Color.BLACK) ;
        setTitle("Batalha Naval") ;

        getContentPane().add( new PNNames(c, this) ) ;

//        ImageIcon icon = new ImageIcon("/Users/pedrosoares/Downloads/navalbattlecute.png", "Ship") ;
//        Object[] object = null ;
//        String nome1, nome2 ;



        //nome1 = JOptionPane.showInputDialog("Digite o nome do Jogador 1 ") ;
//        nome1 = (String) JOptionPane.showInputDialog(this,"Digite o nome do Jogador 1 ", "Batalha Naval", JOptionPane.QUESTION_MESSAGE, icon, object,"");
//        nome2 = (String) JOptionPane.showInputDialog(this,"Digite o nome do Jogador 2 ", "Batalha Naval", JOptionPane.QUESTION_MESSAGE, icon, object,"");
//
//        if ( nome1.isEmpty() || nome2.isEmpty() )
//        {
//            System.exit(-1);
//        }
//
//        System.out.println(nome1);
//        System.out.println(nome2);



        //getContentPane().add( new PNBatalhaNaval(c) ) ;


    }
}

package com.gui;

import com.regras.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PNInicio extends JPanel implements ActionListener
{
    JLabel player1Label = new JLabel("Jogador 1 : ") ;
    JLabel player2Label = new JLabel("Jogador 2 : ") ;
    JTextField player1Field = new JTextField(30) ;
    JTextField player2Field = new JTextField(30) ;
    JButton button = new JButton("COMEÇAR") ;

    public PNInicio()
    {
        Font font = new Font("SansSerif", Font.BOLD, 15) ;
        Dimension dText = player1Label.getPreferredSize() ;
        Dimension dButton = button.getPreferredSize() ;

        this.setLayout( null ) ;

        player1Label.setBounds(90, 90, dText.width, dText.height) ;
        player2Label.setBounds( 90, 140, dText.width, dText.height) ;
        player1Field.setBounds(200 - dText.width/2, 80, 200, dText.height + 20) ;
        player2Field.setBounds( 200 - dText.width/2, 130, 200, dText.height + 20) ;

        button.setFont( font ) ;
        button.setBounds( 250 - dButton.width/2 - 10, 200, dButton.width + 10, dButton.height + 10) ;
        button.addActionListener(this) ;

        this.add( player1Label ) ;
        this.add( player2Label ) ;
        this.add( player1Field ) ;
        this.add( player2Field ) ;
        this.add( button ) ;

        this.setVisible( true ) ;
    }

    public void actionPerformed( ActionEvent actionEvent )
    {
        String player1 = player1Field.getText() ;
        String player2 = player2Field.getText() ;

        if( !player1.isEmpty() && !player2.isEmpty() )
        {
            player1Field.setText("") ;
            player2Field.setText("") ;

            Fachada.getFachada().setPlayer1( player1 ) ;
            Fachada.getFachada().setPlayer2( player2 ) ;
            GUIController.getGUIController().goToPainelPosicionamento() ;
        }
    }
}

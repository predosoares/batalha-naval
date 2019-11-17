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
    JButton button = new JButton("Começar") ;

    public PNInicio()
    {
        Dimension dText = player1Label.getPreferredSize() ;
        Dimension dButton = button.getPreferredSize() ;

        this.setLayout( null ) ;

        player1Label.setBounds(400, 200, dText.width, dText.height) ;
        player2Label.setBounds( 400, 270, dText.width, dText.height) ;
        player1Field.setBounds(480, 190, 200, dText.height + 20) ;
        player2Field.setBounds( 480, 260, 200, dText.height + 20) ;

        button.setBounds( 550 - dButton.width/2 - 10, 350, dButton.width, dButton.height) ;
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

        System.out.println("Player 1: " + player1) ;
        System.out.println("Player 2: " + player2) ;

        if( !player1.isEmpty() && !player2.isEmpty() )
        {
            Fachada.getFachada().setPlayer1( player1 ) ;
            Fachada.getFachada().setPlayer2( player2 ) ;
            GUIController.getGUIController().goToPainelPosicionamento() ;
        }
    }
}
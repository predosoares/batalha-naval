package com.gui;

import javax.swing.*;

import com.regras.Fachada;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PNMenu extends JPanel implements ActionListener
{
    JButton buttonNovaPartida = new JButton("NOVA PARTIDA") ;
    JButton buttonCarregarPartida = new JButton("CARREGAR PARTIDA") ;

    public PNMenu()
    {
        Font font = new Font("SansSerif", Font.BOLD, 15) ;
        Dimension dButtonNovaPartida = buttonNovaPartida.getPreferredSize() ;
        Dimension dButtonCarregarPartida = buttonCarregarPartida.getPreferredSize() ;

        this.setLayout( null ) ;

        buttonNovaPartida.setFont( font ) ;
        buttonNovaPartida.setBounds(250 - dButtonNovaPartida.width/2 , 80, dButtonNovaPartida.width + 20, dButtonNovaPartida.height + 20 ) ;
        buttonNovaPartida.addActionListener(this) ;

        buttonCarregarPartida.setFont( font ) ;
        buttonCarregarPartida.setBounds( 250 - dButtonCarregarPartida.width/2  , 170, dButtonCarregarPartida.width + 20, dButtonCarregarPartida.height + 20 ) ;
        buttonCarregarPartida.addActionListener(this) ;

        this.add( buttonNovaPartida ) ;
        this.add( buttonCarregarPartida ) ;

        this.setVisible( true ) ;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if ( actionEvent.getSource() == buttonNovaPartida )
        {
            GUIController.getGUIController().goToPainelInicio() ;
        }
        else if ( actionEvent.getSource() == buttonCarregarPartida )
        {
        	Fachada.getFachada() ;
        	Fachada.getFachada().carregaJogo() ;
        	GUIController.getGUIController().goToPainelBatalhaNaval() ;
        }
    }
}

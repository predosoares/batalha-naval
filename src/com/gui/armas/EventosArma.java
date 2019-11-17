package com.gui.armas;

import com.gui.*;
import java.awt.event.*;

public class EventosArma implements MouseListener, KeyListener
{
    EventosArma(Arma arma)
    {
        arma.addMouseListener(this) ;
        arma.addKeyListener(this) ;
        arma.setFocusable(true);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        Arma aux = ( Arma ) mouseEvent.getComponent() ;
        if (!aux.foiJogada())
            if ( !GUIController.getGUIController().existeArmaSelecionada() )
                GUIController.getGUIController().selectArma( (Arma) mouseEvent.getComponent() );
    }

    public void mousePressed(MouseEvent mouseEvent) { }
    public void mouseReleased(MouseEvent mouseEvent) { }
    public void mouseEntered(MouseEvent mouseEvent) { }
    public void mouseExited(MouseEvent mouseEvent) { }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        if ( keyEvent.getKeyCode() == keyEvent.VK_ESCAPE )
            if (GUIController.getGUIController().existeArmaSelecionada())
                GUIController.getGUIController().unselectArma(false) ;
    }

    public void keyPressed(KeyEvent keyEvent) { }
    public void keyReleased(KeyEvent keyEvent) { }
}

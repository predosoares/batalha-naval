package com.gui;

import com.gui.armas.*;
import com.regras.Fachada;

import javax.swing.*;

public class GUIController
{
    private static GUIController guiCtrl = null ;
    private JFrame mainFrame = null ;
    private JPanel pnInicio ;
    private JPanel pnPosicionamento = null ;
    private JPanel pnBatalhaNaval = null ;
    private Arma armaSelecionada = null ;

    private GUIController() { pnInicio =  new PNInicio() ; }

    public static GUIController getGUIController()
    {
        if ( guiCtrl == null )
            guiCtrl = new GUIController() ;

        return guiCtrl ;
    }

    void novaSessao( JFrame frame )
    {
        mainFrame = frame ;
        mainFrame.getContentPane().add( pnInicio ) ;
    }

    void goToPainelPosicionamento()
    {
        pnInicio.setVisible( false ) ;

        if ( pnPosicionamento == null )
        {
            pnPosicionamento = new PNPosicionamento();
            mainFrame.getContentPane().add(pnPosicionamento);

        } else {
            pnPosicionamento.setVisible(true);
        }

        GUIController.getGUIController().setArmas(Fachada.getFachada().getQtdArmas1());

        mainFrame.revalidate() ;
    }

    void goToPainelBatalhaNaval()
    {
        pnPosicionamento.setVisible( false ) ;

        if ( pnBatalhaNaval == null )
        {
            pnBatalhaNaval = new PNBatalha();
            mainFrame.getContentPane().add(pnBatalhaNaval);

        } else {
            pnBatalhaNaval.setVisible( true );
        }

        mainFrame.revalidate() ;
    }

    void setArmas(int[] armas )
    {
        int width = 30;
        double yCorner = 100.0 ;
        double height = 30.0 ; // altura do retângulo

        JPanel aux = null ;
        int lastArmaHeight = 0 ;

        //INSERE ARMA NO PAINEL
        for (int i = 0; i < armas.length; i++)
        {
            for (int j = 0; j < armas[i]; j++) {
                switch (i)
                {
                    case 0: { // Hidroaviao
                        aux = new HidroAviao() ;
                        break;
                    }
                    case 1: { // Submarino
                        aux = new Submarino() ;
                        break;
                    }
                    case 2: { // Destroier
                        aux = new Destroier() ;
                        break;
                    }
                    case 3: { // Cruzadores
                        aux = new Cruzador() ;
                        break;
                    }
                    case 4: { // Couracado
                        aux = new Couracado() ;
                        break;
                    }
                }
                aux.setLocation( (int)(width + (aux.getWidth()+width)*(j)) , (int) (yCorner + lastArmaHeight + (height)*i));
                pnPosicionamento.add(aux) ;
            }
            lastArmaHeight += aux.getHeight();
        }
    }

    public void selectArma(Arma arma)
    {
        armaSelecionada = arma ;
        armaSelecionada.grabFocus() ;
        armaSelecionada.repaint() ;
    }

    public Arma getArma() { return armaSelecionada ; }

    public void unselectArma( boolean comando )
    {
        Arma aux = armaSelecionada ;

        if (comando == true) aux.setJogada();

        armaSelecionada = null ;
        aux.repaint();
    }

    public boolean existeArmaSelecionada() { return armaSelecionada != null ; }
}

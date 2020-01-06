package com.gui;

import com.gui.armas.*;
import com.regras.Fachada;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIController
{
    final int LARG_DEFAULT = 500 ;
    final int ALT_DEFAULT = 300 ;

    final int LARG_SESSION = 1140 ;
    final int ALT_SESSION = 600 ;

    private static GUIController guiCtrl = null ;

    private JFrame mainFrame = null ;
    private JPanel pnMenu ;
    private JPanel pnInicio = null ;
    private JPanel pnPosicionamento = null ;
    private JPanel pnBatalha = null ;

    private Arma armaSelecionada = null ;
    private int[][] clonedMatriz = null ;
    private List<Arma> listaArmas = new ArrayList<Arma>(15);

    private GUIController() { pnMenu =  new PNMenu() ; }

    public static GUIController getGUIController()
    {
        if ( guiCtrl == null )
            guiCtrl = new GUIController() ;

        return guiCtrl ;
    }

    void novaSessao( JFrame frame )
    {
        mainFrame = frame ;

        Toolkit tk = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = tk.getScreenSize() ;
        int screenWidth = screenSize.width ;
        int screenHeight = screenSize.height ;
        int x = screenWidth/2 - LARG_DEFAULT/2 ;
        int y = screenHeight/2 - ALT_DEFAULT/2 ;

        mainFrame.setBounds( x, y, LARG_DEFAULT, ALT_DEFAULT) ;

        mainFrame.getContentPane().add( pnMenu ) ;
    }

    void goToPainelInicio()
    {
        mainFrame.remove( pnMenu ) ;

        pnInicio = new PNInicio() ;
        mainFrame.getContentPane().add( pnInicio ) ;

        mainFrame.revalidate() ;
        mainFrame.repaint();
    }

    void goToPainelPosicionamento()
    {
        Toolkit tk = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = tk.getScreenSize() ;
        int screenWidth = screenSize.width ;
        int screenHeight = screenSize.height ;
        int x = screenWidth/2 - LARG_SESSION/2 ;
        int y = screenHeight/2 - ALT_SESSION/2 ;

        mainFrame.remove( pnInicio ) ;

        mainFrame.setBounds( x, y, LARG_SESSION, ALT_SESSION) ;

        pnPosicionamento = new PNPosicionamento() ;
        mainFrame.getContentPane().add( pnPosicionamento ) ;

        GUIController.getGUIController().setArmas(Fachada.getFachada().getQtdArmas1()) ;

        mainFrame.revalidate() ;
        mainFrame.repaint();
    }

    void goToPainelBatalhaNaval(Fachada fachada)
    {
    	Toolkit tk = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = tk.getScreenSize() ;
        int screenWidth = screenSize.width ;
        int screenHeight = screenSize.height ;
        int x = screenWidth/2 - LARG_SESSION/2 ;
        int y = screenHeight/2 - ALT_SESSION/2 ;

        mainFrame.setBounds( x, y, LARG_SESSION, ALT_SESSION) ;
        
    	if( pnMenu != null ) 
    	{
    		mainFrame.remove( pnMenu ) ;
    	}
    	
    	if( pnPosicionamento != null ) 
    	{
    		mainFrame.remove( pnPosicionamento ) ;
    	}
    	
        pnBatalha = new PNBatalha( fachada ) ;
        mainFrame.getContentPane().add( pnBatalha ) ;

        mainFrame.revalidate() ;
        mainFrame.repaint() ;
    }

    void goToMenu()
    {
        Toolkit tk = Toolkit.getDefaultToolkit() ;
        Dimension screenSize = tk.getScreenSize() ;
        int screenWidth = screenSize.width ;
        int screenHeight = screenSize.height ;
        int x = screenWidth/2 - LARG_DEFAULT/2 ;
        int y = screenHeight/2 - ALT_DEFAULT/2 ;

        mainFrame.remove( pnBatalha ) ;

        mainFrame.setBounds( x, y, LARG_DEFAULT, ALT_DEFAULT) ;

        pnMenu = new PNMenu() ;
        mainFrame.getContentPane().add( pnMenu ) ;

        mainFrame.revalidate() ;
        mainFrame.repaint() ;
    }

    void setArmas(int[] armas )
    {
        TipoArma[] ordem = { TipoArma.HidroAviao,
                          TipoArma.Submarino,
                          TipoArma.Destroier,
                          TipoArma.Cruzador,
                          TipoArma.Couracado} ;
        int width = 30 ;
        double yCorner = 100.0 ;
        double height = 30.0 ; // altura do retângulo

        Arma aux = null ;
        int lastArmaHeight = 0 ;

        //INSERE ARMA NO PAINEL
        for (int i = 0; i < ordem.length; i++)
        {
            for (int j = 0; j < armas[i]; j++) {
                switch (ordem[i])
                {
                    case HidroAviao: { // Hidroaviao
                        aux = new HidroAviao() ;
                        break;
                    }
                    case Submarino: { // Submarino
                        aux = new Submarino() ;
                        break;
                    }
                    case Destroier: { // Destroier
                        aux = new Destroier() ;
                        break;
                    }
                    case Cruzador: { // Cruzadores
                        aux = new Cruzador() ;
                        break;
                    }
                    case Couracado: { // Couracado
                        aux = new Couracado() ;
                        break;
                    }
                }
                aux.setLocation( (int)(width + (aux.getWidth()+width)*(j)) , (int) (yCorner + lastArmaHeight + (height)*i));
                pnPosicionamento.add(aux) ;
                listaArmas.add(aux) ;
            }
            lastArmaHeight += aux.getHeight();
        }
    }

    public void selectArma(Arma arma)
    {
        armaSelecionada = arma ;
        clonedMatriz = armaSelecionada.getMatriz() ;
        armaSelecionada.grabFocus() ;
        armaSelecionada.repaint() ;
    }

    public void rotateMatriz()
    {
        clonedMatriz = Arma.rotacionaMatrizEm90GrausHorario(clonedMatriz) ;
    }

    public int[][] getMatrizSelecionada() { return clonedMatriz ; }

    public Arma getArma() { return armaSelecionada ; }

    public void unselectArma( boolean comando )
    {
        Arma aux = armaSelecionada ;

        if (comando == true) aux.setJogada();

        armaSelecionada = null ;
        clonedMatriz = null ;
        aux.repaint();
    }

    public boolean existeArmaSelecionada() { return armaSelecionada != null ; }

    public void replace(int identificador)
    {
        Arma aux, selected = null;

        for (int i = 0; i < listaArmas.size(); i++)
        {
            aux = listaArmas.get(i) ;
            if (aux.getIdentificacao() == identificador)
            {
                selected = aux ;
                break;
            }
        }

        selected.setReposicionada();
        armaSelecionada = selected ;
        clonedMatriz = armaSelecionada.getMatriz() ;
    }
}

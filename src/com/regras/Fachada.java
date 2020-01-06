package com.regras;

import java.io.Serializable;

public class Fachada implements Serializable
{
	private static final long serialVersionUID = 1L;
    CtrlRegras ctrl ;
    static Fachada f = null ;

    private Fachada() { ctrl = new CtrlRegras() ; }

    public static Fachada getFachada()
    {
        if ( f == null )
            f = new Fachada() ;

        return f ;
    }

    public void novoJogo() { ctrl.novoJogo() ; }

    public void setPlayer1( String nome ) { ctrl.setPlayer1(nome) ; }

    public void setPlayer2( String nome ) { ctrl.setPlayer2(nome) ; }

    public String getPlayer1( ) { return ctrl.getPlayer1() ; }

    public String getPlayer2(  ) { return ctrl.getPlayer2() ; }

    public No[][] getMatrizPlayer1() { return ctrl.getMatrizPlayer1() ; }

    public No[][] getMatrizPlayer2() { return ctrl.getMatrizPlayer2() ; }

    public int[] getQtdArmas1() { return ctrl.getQtdArmas1() ; }

    public int[] getQtdArmas2() { return ctrl.getQtdArmas2() ; }

    public int getVez() { return ctrl.getVez() ; }

    public void setVez(int vez) { ctrl.setVez(vez); }

    public void setMatrizPlayer1( No[][] mat ) { ctrl.setMatrizPlayer1( mat ) ; }

    public void setMatrizPlayer2( No[][] mat ) { ctrl.setMatrizPlayer2( mat ) ; }

    public void updateValues() { ctrl.updateValues() ; }

    // public void posicionaPeca( int i, int j, int[][] matriz ) { ctrl.posicionaPeca(i, j, matriz) ; }

    public void tiro( int i, int j) { ctrl.tiro( i , j ) ; }

    // public int testaResultado() { return ctrl.testaResultado() ; } ;

    public void register(Observer o) {
        ctrl.addObserver(o);
    }
    
    public void salvaJogo( ) { ctrl.salvaJogo( ) ; }
    
    public Fachada carregaJogo( ) { ctrl = ctrl.carregaJogo( ) ; return f ; }

}

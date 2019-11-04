package com.regras;

public class Fachada
{
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

    public int getVez() { return ctrl.getVez() ; }

    public void celulaClicked( int i, int j) { ctrl.celulaClicked(i, j); }

    public int testaResultado() { return ctrl.testaResultado() ; } ;

    public void register(Observer o) {
        ctrl.addObserver(o);
    }
}

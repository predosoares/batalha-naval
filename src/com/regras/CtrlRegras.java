package com.regras;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class CtrlRegras implements Observable
{
    // 0: indica uma celula não selecionada
    // 1: indica uma celula selecionada porém vazia
    // 2: indica uma celula há uma arma não selecionada
    // 3: indica uma celula selecionada onde há uma arma

    private int tabPlayer1 [][] ;
    private int tabPlayer2 [][] ;
    private String nomePlayer1 ;
    private String nomePlayer2 ;

    // 0: indica a vez do player1
    // 1: indica a vez do player2
    private int vez ;

    private int tiros ;

    List<Observer> lob = new ArrayList<Observer>();

    public CtrlRegras() { novoJogo() ; }

    public void novoJogo()
    {
        vez = 0 ;
        tiros = 3 ;
        nomePlayer1 = null ;
        nomePlayer2 = null ;

        tabPlayer1 = new int[15][15] ;
        for (int i = 0; i < tabPlayer1.length; i++)
            for (int j = 0; i < tabPlayer1[i].length; j++)
                tabPlayer1[i][j] = 0 ;

        tabPlayer2 = new int[15][15] ;
        for (int i = 0; i < tabPlayer2.length; i++)
            for (int j = 0; i < tabPlayer2[i].length; j++)
                tabPlayer2[i][j] = 0 ;
    }

    public int[][] getMatrizPlayer1() { return tabPlayer1; }

    public int[][] getMatrizPlayer2() { return tabPlayer2; }

    public int getVez() { return vez ; }

    public void celulaClicked( int i, int j )
    {
        int tabuleiro [][] = vez == 0 ? tabPlayer1 : tabPlayer2 ;

        if( tabuleiro[i][j] == 0 || tabuleiro[i][j] == 2 )
        {
            tabuleiro[i][j] = tabuleiro[i][j] == 0 ? 1 : 3;
            tiros-- ;

            if ( tiros == 0 ) { vez = vez == 0 ? 1 : 0 ; }

            for(Observer o:lob)
                o.notify(this);
        }
    }

    public int testaResultado() {
        int soma = 0;
        int tabuleiro[][] = vez == 0 ? tabPlayer1 : tabPlayer2;

        for (int i = 0; i < tabuleiro.length; i++)
            for (int j = 0; i < tabuleiro[i].length; j++)
                if (tabuleiro[i][j] == 2)
                    soma += 1;


        if ( vez == 0 && soma == 38 )
            return 100 ;
        else if ( vez == 0 && soma == 38 )
            return 200 ;

        return 0 ;
    }

    public void addObserver( Observer o ) { lob.add(o) ; }

    public void removeObserver( Observer o ) { lob.remove(o) ; }

    public Object get()
    {
        Object dados[] = new Object[7] ;

        dados[0] = new Integer(vez) ;
        dados[1] = tabPlayer1 ;
        dados[2] = tabPlayer2 ;
        dados[3] = nomePlayer1 ;
        dados[4] = nomePlayer2 ;
        dados[5] = tiros ;
        dados[6] = new Integer(testaResultado()) ;

        return dados ;
    }
}

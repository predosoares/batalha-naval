package com.regras;

import com.gui.armas.TipoArma;

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

    // { qtdHidroavioes, qtdSubmarinos, qtdDestroiers, qtdCruzadores, qtdCouracados }
    private int qtdArmas1[] ;
    private int qtdArmas2[] ;

    private String nomePlayer1 ;
    private String nomePlayer2 ;

    // 0: indica a vez do player1
    // 1: indica a vez do player2
    private int vez ;

    private int tiros ;

    List<Observer> lob = new ArrayList<Observer>();

    CtrlRegras() { novoJogo() ; }

    void novoJogo()
    {
        vez = 0 ;
        tiros = 3 ;
        nomePlayer1 = null ;
        nomePlayer2 = null ;

        tabPlayer1 = new int[15][15] ;
        for (int i = 0; i < tabPlayer1.length; i++)
            for (int j = 0; j < tabPlayer1[i].length; j++)
                tabPlayer1[i][j] = 0 ;

        qtdArmas1 = new int[]{5, 4, 3, 2, 1} ;

        tabPlayer2 = new int[15][15] ;
        for (int i = 0; i < tabPlayer2.length; i++)
            for (int j = 0; j < tabPlayer2[i].length; j++)
                tabPlayer2[i][j] = 0 ;

        qtdArmas2 = new int[]{5, 4, 3, 2, 1} ;

    }

    void setPlayer1(String nome) { nomePlayer1 = nome ; }

    void setPlayer2( String nome ) { nomePlayer2 = nome ; }

    String getPlayer1( ) { return nomePlayer1 ; }

    String getPlayer2( ) { return nomePlayer2 ; }

    int[][] getMatrizPlayer1() { return tabPlayer1; }

    int[][] getMatrizPlayer2() { return tabPlayer2; }

    int[] getQtdArmas1() { return qtdArmas1 ; }

    int[] getQtdArmas2() { return qtdArmas2 ; }

    int getVez() { return vez ; }

    void posicionaPeca(int i, int j, int[][] matriz)
    {
        int tabuleiro [][] = this.vez == 0 ? tabPlayer1 : tabPlayer2 ;

        if ( ((i + matriz.length) <= 15)  &&  ((j + matriz[0].length) <= 15) )
        {
            for (int x = 0; x < matriz.length; x++) {
                for (int y = 0; y < matriz[x].length; y++) {
                    tabuleiro[i + x][j + y] += matriz[x][y];
                }
            }

            if (this.vez == 0) {
                tabPlayer1 = tabuleiro;
            } else {
                tabPlayer2 = tabuleiro;
            }

            for (Observer o : lob)
                o.notify(this);
        }
    }

    void tiro( int i, int j )
    {
        int tabuleiro [][] = this.vez == 0 ? tabPlayer1 : tabPlayer2 ;

        if( tabuleiro[i][j] == 0 || tabuleiro[i][j] == 2 )
        {
            tabuleiro[i][j] = tabuleiro[i][j] == 0 ? 1 : 3;
            tiros-- ;

            if ( tiros == 0 ) { vez = vez == 0 ? 1 : 0 ; }

            for(Observer o:lob)
                o.notify(this);
        }
    }

    int testaResultado()
    {
        int soma = 0;
        int tabuleiro[][] = vez == 0 ? tabPlayer1 : tabPlayer2;

        for (int i = 0; i < tabuleiro.length; i++)
            for (int j = 0; j < tabuleiro[i].length; j++)
                if (tabuleiro[i][j] == 2)
                    soma += 1;


        if ( vez == 0 && soma == 38 )
            return 100 ; // Player1 Venceu!
        else if ( vez == 1 && soma == 38 )
            return 200 ; // Player2 Venceu!

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
        dados[3] = tiros ;
        dados[4] = new Integer(testaResultado()) ;

        return dados ;
    }
}

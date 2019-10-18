package com.regras;

import javax.swing.*;

public class CtrlRegras {
    // 0: indica uma casa não preenchida
    // -1: indica uma casa preenchida com um retângulo verde
    // 5:  indica uma casa preenchida com um retângulo vermelho

    int tabuleiro [][]= {{0,0,0},{0,0,0},{0,0,0}};
    int vez=5;

    public CtrlRegras() {
    }

    public int[][] getMatriz() {
        return tabuleiro;
    }

    public void setCasa(int x, int y)
    {
        tabuleiro[x][y] = vez;
    }

    public int getVez() {
        return vez;
    }

    public void setVez(int vez) {
        this.vez = vez;
    }

    public boolean jogadaValida( int x , int y )
    {
        if (x < 3 && x > -1 && y < 3 && y > -1) {
            if (tabuleiro[x][y] != 0)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public boolean checaVitoria()
    {
        int somaDeVitoria = vez == -1 ? -3 : 15 ;
        int somal, somac, somad ;
        boolean ehVitoria = false ;

        for (int i = 0 ; i < 3 ; i++ )
        {
            somal = 0;
            somac = 0;
            somad = 0;

            for (int j = 0; j < 3; j++)
            {
                somal += tabuleiro[i][j];
                somac += tabuleiro[j][i];
                somad += tabuleiro[j][j];
            }

            if (somal == somaDeVitoria) ehVitoria = true;
            if (somac == somaDeVitoria) ehVitoria = true;
            if (somad == somaDeVitoria) ehVitoria = true;
        }

        //Checa vitória em diagonal inversa
        somad = tabuleiro[2][0] + tabuleiro[1][1] + tabuleiro[0][2];
        if (somad == somaDeVitoria) ehVitoria = true;

        return ehVitoria;
    }

    public void reset()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                tabuleiro[i][j] = 0 ;
            }
        }
        vez = 5;
    }

    public boolean todasCasasOcupadas()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (tabuleiro[i][j] == 0) return false ;
            }
        }

        return true;
    }
}

package com.regras;

import com.gui.armas.TipoArma;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.Serializable;

class CtrlRegras implements Observable, Serializable
{
	private static final long serialVersionUID = 1L;
    private No tabPlayer1 [][] ;
    private No tabPlayer2 [][] ;

    // { qtdHidroavioes, qtdSubmarinos, qtdDestroiers, qtdCruzadores, qtdCouracados }
    private int qtdArmas1[] ;
    private int qtdArmas2[] ;

    private String nomePlayer1 ;
    private String nomePlayer2 ;

    // 0: indica a vez do player1
    // 1: indica a vez do player2
    private int vez ;

    private int resultadoPartida ;
    private int resultadoTiro ;

    private int tiros ;

    List<Observer> lob = new ArrayList<Observer>();

    CtrlRegras() { novoJogo() ; }

    void novoJogo()
    {
        vez = 0 ;
        tiros = 3 ;
//        nomePlayer1 = null;
//        nomePlayer2 = null;

        tabPlayer1 = new No[15][15];
        for (int i = 0; i < tabPlayer1.length; i++)
            for (int j = 0; j < tabPlayer1[i].length; j++)
                tabPlayer1[i][j] = new No(0,0) ;


        qtdArmas1 = new int[]{TipoArma.HidroAviao.ordinal(),
                TipoArma.Submarino.ordinal(),
                TipoArma.Destroier.ordinal(),
                TipoArma.Cruzador.ordinal(),
                TipoArma.Couracado.ordinal()};

        tabPlayer2 = new No[15][15];
        for (int i = 0; i < tabPlayer2.length; i++)
            for (int j = 0; j < tabPlayer2[i].length; j++)
                tabPlayer2[i][j] = new No(0,0);

        qtdArmas2 = new int[]{TipoArma.HidroAviao.ordinal(),
                TipoArma.Submarino.ordinal(),
                TipoArma.Destroier.ordinal(),
                TipoArma.Cruzador.ordinal(),
                TipoArma.Couracado.ordinal()};

    }

    void setPlayer1(String nome) { nomePlayer1 = nome ; }

    void setPlayer2( String nome ) { nomePlayer2 = nome ; }

    String getPlayer1( ) { return nomePlayer1 ; }

    String getPlayer2( ) { return nomePlayer2 ; }

    No[][] getMatrizPlayer1() { return tabPlayer1; }

    No[][] getMatrizPlayer2() { return tabPlayer2; }

    int[] getQtdArmas1() { return qtdArmas1 ; }

    int[] getQtdArmas2() { return qtdArmas2 ; }

    int getVez() { return vez ; }

    void setVez( int vez ) { this.vez = vez ; }

    void setMatrizPlayer1( No[][] matrizPlayer1 )
    {
        for (int i = 0; i < matrizPlayer1.length; i++)
            for (int j = 0; j < matrizPlayer1[i].length; j++)
                tabPlayer1[i][j] = matrizPlayer1[i][j] ;
    }

    void setMatrizPlayer2( No[][] matrizPlayer2 )
    {
        for (int i = 0; i < matrizPlayer2.length; i++)
            for (int j = 0; j < matrizPlayer2[i].length; j++)
                tabPlayer2[i][j] = matrizPlayer2[i][j] ;
    }

    void checkIfDestroyed( int tipoArma, No[][] tabuleiro , int linha , int coluna )
    {
        int[] shotsToBeDestroyed = new int[] { 0 , 5 , 4 , 2 , 1 , 3 } ;
        int sum = 0 ;

        int identificador = tabuleiro[linha][coluna].getIdentificador() ;
        int checkSum = shotsToBeDestroyed[ tipoArma ] ;

        if ( identificador != 0 )
        {
            for ( int i = 0; i < tabuleiro.length; i++ )
                for ( int j = 0; j < tabuleiro[i].length; j++ )
                    if ( identificador == tabuleiro[i][j].getIdentificador() && tabuleiro[i][j].getNo() == 10 )
                        sum++ ;

            if ( sum == checkSum )
            {
                for ( int i = 0; i < tabuleiro.length; i++ )
                    for ( int j = 0; j < tabuleiro[i].length; j++ )
                        if ( identificador == tabuleiro[i][j].getIdentificador() )
                            tabuleiro[i][j].setNo(11) ;
            }
        }

        if ( tabuleiro[linha][coluna].getNo() == 9 )
            resultadoTiro = 0 ;
        else if ( tabuleiro[linha][coluna].getNo() == 10 )
            resultadoTiro = 10 ;
        else if ( tabuleiro[linha][coluna].getNo() == 11 )
            resultadoTiro = 20 ;
    }

    void tiro( int linha , int coluna )
    {
        No tabuleiro [][] = vez == 0 ? tabPlayer2 : tabPlayer1 ;

        if( tabuleiro[linha][coluna].getNo() >= 0 && tabuleiro[linha][coluna].getNo() <= 5 )
        {
            int tipoArma =  tabuleiro[linha][coluna].getNo() ;
            tabuleiro[linha][coluna].setNo( tabuleiro[linha][coluna].getNo() == 0 ? 9 : 10 ) ;
            tiros-- ;

            checkIfDestroyed( tipoArma , tabuleiro , linha , coluna ) ;

            resultadoPartida = testaResultado() ;

            if ( tiros == 0 )
            {
                vez = vez == 0 ? 1 : 0 ;
                tiros = 3 ;
            }

            for( Observer o:lob )
                o.notify(this) ;
        }
    }

    int testaResultado()
    {
        No tabuleiro[][] = vez == 0 ? tabPlayer2 : tabPlayer1 ;
        int soma = 0 ;

        for ( int i = 0 ; i < tabuleiro.length; i++ )
            for ( int j = 0; j < tabuleiro[i].length; j++ )
                if ( tabuleiro[i][j].getNo() == 11 )
                    soma += 1 ;

        if ( vez == 0 && soma == 38 )
            return 100 ; // Player1 Venceu!
        else if ( vez == 1 && soma == 38 )
            return 200 ; // Player2 Venceu!

        return 0 ;
    }

    void updateValues()
    {
        for( Observer o:lob )
            o.notify(this) ;
    }
    
    public void salvaJogo()
    {
    	JFileChooser fc = new JFileChooser();
    	int returnVal = fc.showSaveDialog(new JFrame());
    	if(returnVal == JFileChooser.APPROVE_OPTION)
    	{
    		try
    		{
    			PrintStream ps = new PrintStream(fc.getSelectedFile()+ ".dat");
    			ObjectOutputStream object = new ObjectOutputStream(ps);
    			object.writeObject(this);
    			object.flush();
    			object.close();
    		}
    		catch(IOException e)
    		{
    			System.out.println(e);
    		}
    	}
    }
    
    @SuppressWarnings("resource")
	public CtrlRegras carregaJogo()
    {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
		File fileSelected = fc.getSelectedFile();
		FileInputStream fileIn = null;
			try
			{
				fileIn = new FileInputStream(fileSelected);
				ObjectInputStream is = new ObjectInputStream(fileIn);
				CtrlRegras tabsCarregados = (CtrlRegras) is.readObject();
				return tabsCarregados;
			} catch (IOException | ClassNotFoundException e)
			{
					System.out.println(e);
			}
		}
		return null;
	}

    public void addObserver( Observer o ) { lob.add(o) ; }

    public void removeObserver( Observer o ) { lob.remove(o) ; }

    public Object get()
    {
        Object dados[] = new Object[6] ;

        dados[0] = new Integer( vez ) ;
        dados[1] = tabPlayer1 ;
        dados[2] = tabPlayer2 ;
        dados[3] = new Integer( tiros ) ;
        dados[4] = new Integer( resultadoPartida ) ;
        dados[5] = new Integer( resultadoTiro ) ;

        return dados ;
    }
}

package com.regras;

import java.io.Serializable;

public class No implements Serializable
{
	private static final long serialVersionUID = 1L;
    private int no ;
    private int identificador ;

    public No( int no , int identificador )
    {
        this.no = no ;
        this.identificador = identificador ;
    }

    public int getNo( ) { return no ; }

    public void setNo( int no ) { this.no = no ; }

    public int getIdentificador( ) { return identificador ; }

    public void setIdentificador( int identificador ) { this.identificador = identificador ; }

}



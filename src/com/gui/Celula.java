package com.gui;

import java.io.Serializable;

public class Celula implements Serializable
{
	private static final long serialVersionUID = 1L;
    double x, y ;

    Celula( double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}

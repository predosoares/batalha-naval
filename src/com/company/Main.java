package com.company;

import com.gui.FRBatalhaNaval;
import com.regras.CtrlRegras;

public class Main
{
    public static void main(String args[])
    {
        new FRBatalhaNaval(new CtrlRegras()).setVisible(true);
    }
}

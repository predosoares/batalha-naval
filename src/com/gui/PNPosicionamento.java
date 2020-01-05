package com.gui ;

import com.gui.armas.*;
import com.regras.* ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.*;
import java.awt.geom.Rectangle2D ;

class PNPosicionamento extends JPanel implements MouseListener, KeyListener, ActionListener
{
    double xCorner = 60.0 ; // x do canto esquerdo superiorPe
    double yCorner = 100.0 ; // y do canto esquerdo superior
    double width = 30.0 ; // largura do retângulo
    double height = 30.0 ; // altura do retângulo
    double boxGap = 600.0 ; // distância lateral do campo a borda esquerda
    double letterShift = 35.0 ; // desvio necessário para posicionar as letras de forma adequada
    float lineGap = 1.0f ; // espessura da linha
    private Rectangle2D.Double box[][] = new Rectangle2D.Double[15][15] ;

    private final int NUM_LINES = 15 ;
    private final int NUM_COLUMNS = 15 ;

    private Celula tab[][] = new Celula[15][15] ;

    // Representação do tabuleiro
    private No mat[][] = new No[15][15] ;

    // Previsualizaçao do tabuleiro
    private No matView[][] ;

    private int linhaView ;
    private int colunaView ;

    private int armas[] ;
    private boolean flagInvalidPosition = false ;

    JLabel labelNomePlayer = new JLabel() ;
    JLabel labelAuxiliar = new JLabel() ;
    JButton button = new JButton() ;

    Observable obs ; // Objeto sendo observado
    Object lob[] ; // Lista de Objetos atualizados

    public PNPosicionamento()
    {
        double x, y ;
        Dimension dLabel, dButton, dLabelAux ;
        Font fontNome = new Font("SansSerif", Font.BOLD, 20) ;
        Font fontAux = new Font("SansSerif", Font.BOLD, 15) ;

        this.setLayout( null ) ;

        labelNomePlayer.setFont( fontNome ) ;
        labelNomePlayer.setText( Fachada.getFachada().getPlayer1().toUpperCase() ) ;
        dLabel = labelNomePlayer.getPreferredSize() ;
        labelNomePlayer.setBounds(30,30, dLabel.width, dLabel.height ) ;

        this.add(labelNomePlayer) ;

        labelAuxiliar.setFont( fontAux );
        labelAuxiliar.setForeground( new Color(81, 81, 81) );
        labelAuxiliar.setText("Clique em alguma peça para selecionar".toUpperCase());
        dLabelAux = labelAuxiliar.getPreferredSize() ;
        labelAuxiliar.setBounds(30, 55, dLabelAux.width, dLabelAux.height);

        this.add(labelAuxiliar) ;

        button.setFont( fontNome );
        button.setText("Posicionamento do Player2!".toUpperCase());
        dButton = button.getPreferredSize() ;
        button.setBounds(30,500, dButton.width, dButton.height ) ;
        button.addActionListener( this) ;

        this.add(button) ;

        armas = Fachada.getFachada().getQtdArmas1() ;

        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                mat[i][j] = new No( 0 , 0) ;

        matView = clone(mat) ;

        this.addMouseListener(this) ;
        this.addKeyListener(this) ;
        this.setFocusable(true) ;

        //this.setBackground(Color.WHITE) ;

        // Tamanho de cada retângulo é de 30px x 30px
        for (int line = 0; line < NUM_LINES; line++) {
            y = yCorner + (line * 30.0) ;

            for (int column = 0; column < NUM_COLUMNS; column++) {
                x = xCorner + (column * 30.0);
                tab[line][column] = new Celula( x, y );
                box[line][column] = new Rectangle2D.Double(tab[line][column].x + boxGap, tab[line][column].y, width, height);
            }
        }
    }

    private void setLabelAuxiliarAux(String text)
    {
        Dimension dLabelAux ;

        labelAuxiliar.setText(text.toUpperCase()) ;
        dLabelAux = labelAuxiliar.getPreferredSize() ;
        labelAuxiliar.setBounds(30, 55, dLabelAux.width, dLabelAux.height) ;
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;
        Graphics2D g2d = (Graphics2D) g ;

        g2d.setStroke(new BasicStroke( lineGap,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                    20.0f));

        // Iteração para preencher os quadrados de cor
        for( int i = 0 ; i < NUM_LINES; i++)
        {
            for( int j = 0 ; j < NUM_COLUMNS; j++)
            {
                if( matView[i][j].getNo() == TipoArma.Vazio.ordinal() )
                    g2d.setPaint( Color.WHITE ) ;
                else if( matView[i][j].getNo() == TipoArma.Submarino.ordinal() )
                    g2d.setPaint( new Color(38, 209, 64) ) ;
                else if( matView[i][j].getNo() == TipoArma.Destroier.ordinal() )
                    g2d.setPaint( new Color(220, 197, 26) ) ;
                else if( matView[i][j].getNo() == TipoArma.HidroAviao.ordinal() )
                    g2d.setPaint( new Color(24, 117, 38) ) ;
                else if( matView[i][j].getNo() == TipoArma.Cruzador.ordinal() )
                    g2d.setPaint( new Color(230, 116, 0) ) ;
                else if( matView[i][j].getNo() == TipoArma.Couracado.ordinal() )
                    g2d.setPaint( new Color(230, 88, 132) ) ;
                else
                    g2d.setPaint( Color.RED ) ;

                g2d.fill( box[i][j] ) ;
            }
        }

        // Iteração para desenhar o tabuleiro
        g2d.setPaint(Color.BLACK) ;

        for ( int i = 0; i < NUM_LINES ; i++)
        {
            String number = (i >= 9) ? Integer.toString(i + 1) : " " + Integer.toString(i + 1) ;

            g2d.drawString( Character.toString(65 + i), (int) (boxGap + letterShift), (int) yCorner - 10 + 30*(i + 1)) ;
            g2d.drawString( number, (int) (boxGap + letterShift + 30*(i + 1)), (int) yCorner - 10) ;

            for (int column = 0 ; column < NUM_COLUMNS ; column++ )
            {
                g2d.draw( box[i][column] ) ;
            }
        }
    }

    public No[][] clone(No[][] tab)
    {
        No aux[][] = new No[tab.length][tab[0].length] ;

        for (int i = 0; i < tab.length; i++)
            for (int j = 0; j < tab[0].length; j++ )
                aux[i][j] = new No(tab[i][j].getNo(), tab[i][j].getIdentificador()) ;

        return aux ;
    }

    public boolean ehPosicaoValida(int linha, int coluna, int[][] matriz, int identificador)
    {
        int sum = 0 ;
        int checkSum = 0 ;

        for (int i = 0; i < matriz.length; i++)
        {
            for (int j = 0; j < matriz[i].length; j++)
            {
                if ( matriz[i][j] != 0 )
                {
                    checkSum += matriz[i][j] ;
                    sum += matriz[i][j] ;

                    for (int x = linha + i - 1; x < linha + i + 2; x++){
                        for (int y = coluna + j - 1; y < coluna + j + 2; y++) {
                            if (x > -1 && x < 15 && y > -1 && y < 15) {
                                if ( matView[x][y].getIdentificador() != identificador )
                                {
                                    sum += matView[x][y].getNo() ;
                                }
                            }
                        }
                    }
                }
            }
        }

        if ( sum != checkSum )
            return false ;
        else
            return true ;
    }

    public void posicionaView( int i, int j )
    {
        int identificador = GUIController.getGUIController().getArma().getIdentificacao() ;
        int[][] matriz =  GUIController.getGUIController().getMatrizSelecionada() ;
        int sum = 0 ;
        matView = clone(mat) ;

        if ( ((i + matriz.length) <= 15)  &&  ((j + matriz[0].length) <= 15) )
        {
            if (!ehPosicaoValida(i, j, matriz, identificador))
            {
                flagInvalidPosition = true ;
                sum = 100 ;
                setLabelAuxiliarAux("Clique em outra coordenada para reposicionar ou aperte 'ESC' para cancelar a seleção") ;
            }
            else {
                flagInvalidPosition = false ;
                setLabelAuxiliarAux("Clique em outra coordenada para reposicionar ou confirme posicionamento com 'ESC' ");
            }

            for (int x = 0; x < matriz.length; x++) {
                for (int y = 0; y < matriz[x].length; y++){
                    if (matriz[x][y] != 0) {
                        matView[i + x][j + y].setNo(matriz[x][y] + sum);
                        matView[i + x][j + y].setIdentificador(identificador);
                    }
                }
            }
        }
        else
            setLabelAuxiliarAux("Posicionamento de peça fora do tabuleiro!");

        repaint();
    }

    public void retiraPeca()
    {
        int identificador = mat[linhaView][colunaView].getIdentificador() ;

        for (int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[i].length; j++){
                if ( identificador == mat[i][j].getIdentificador() )
                {
                    mat[i][j].setIdentificador(0) ;
                    mat[i][j].setNo(0) ;
                }
            }
        }
    }

    public void mouseClicked( MouseEvent e )
    {
        double x = e.getX() ;
        double y = e.getY() ;

        if ( e.getButton() == MouseEvent.BUTTON1 )
        {
            linhaView = (int) (y - (yCorner)) ;
            colunaView = (int) (x - (xCorner + boxGap)) ;

            if (linhaView < 0) linhaView = -1 ;
            else linhaView /= 30 ;

            if (colunaView < 0) colunaView = -1 ;
            else colunaView /= 30 ;

            if (linhaView > -1 && linhaView < 15 && colunaView > -1 && colunaView < 15)
            {
                if (GUIController.getGUIController().existeArmaSelecionada())
                {
                    this.grabFocus() ;
                    posicionaView(linhaView, colunaView) ;
                }
                else // Reposicionar peça no tabuleiro
                {
                    if (mat[linhaView][colunaView].getIdentificador() != 0)
                    {
                        GUIController.getGUIController().replace( mat[linhaView][colunaView].getIdentificador() ) ;
                        retiraPeca( ) ;
                        armas[5 - GUIController.getGUIController().getArma().getTipo().ordinal() - 1]++ ;
                    }
                }
            }
        }
        else if ( e.getButton() == MouseEvent.BUTTON3 )
        {
            if (GUIController.getGUIController().existeArmaSelecionada())
                GUIController.getGUIController().rotateMatriz();

        }
    }

    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
        if (GUIController.getGUIController().existeArmaSelecionada())
        {
            if (keyEvent.getKeyCode() == keyEvent.VK_ESCAPE && flagInvalidPosition == false )
            {
                int[][] matriz = GUIController.getGUIController().getMatrizSelecionada() ;
                Arma arma = GUIController.getGUIController().getArma() ;

                if ( ((linhaView + matriz.length) <= 15)  &&  ((colunaView + matriz[0].length) <= 15) )
                {
                    setLabelAuxiliarAux("Clique em alguma peça para selecionar") ;
                    mat = clone(matView) ;
                    armas[5 - arma.getTipo().ordinal()]-- ;
                    GUIController.getGUIController().unselectArma(true) ;
                }
            } else if (keyEvent.getKeyCode() == keyEvent.VK_ESCAPE && flagInvalidPosition == true)
            {
                GUIController.getGUIController().unselectArma(false) ;
                flagInvalidPosition = false ;
                matView = clone( mat ) ;
                repaint() ;
            }
        }
    }

    public void keyTyped(KeyEvent keyEvent) { }
    public void keyReleased(KeyEvent keyEvent) { }

    private boolean todasArmasPosicionadas()
    {
        for ( int i = 0; i < armas.length; i++ )
            if ( armas[i] != 0 )
                return false ;

        return true ;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        Dimension dLabel ;

        System.out.println(armas[0]);

        if (todasArmasPosicionadas())
        {
            if ( Fachada.getFachada().getVez() == 0 )
            {
                System.out.println(" Vez do Player 2! ");
                Fachada.getFachada().setVez(1) ;

                labelNomePlayer.setText(Fachada.getFachada().getPlayer2().toUpperCase());
                dLabel = labelNomePlayer.getPreferredSize();
                labelNomePlayer.setBounds(30, 30, dLabel.width, dLabel.height);

                Fachada.getFachada().setMatrizPlayer1(mat) ;

                armas = Fachada.getFachada().getQtdArmas2();

                for (int i = 0; i < 15; i++)
                    for (int j = 0; j < 15; j++)
                        mat[i][j] = new No( 0 , 0) ;

                matView = java.util.Arrays.stream(mat).map(No[]::clone).toArray($ -> mat.clone());

                removeAll() ;
                this.add(labelNomePlayer) ;
                this.add(labelAuxiliar) ;
                this.add(button) ;
                GUIController.getGUIController().setArmas(armas);
                repaint() ;
            }
            else {
                Fachada.getFachada().setMatrizPlayer2(mat) ;
                Fachada.getFachada().setVez(0) ;
                GUIController.getGUIController().goToPainelBatalhaNaval();
            }
        }
    }
}

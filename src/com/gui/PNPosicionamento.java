package com.gui ;

import com.gui.armas.*;
import com.regras.* ;

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.geom.Rectangle2D ;

class PNPosicionamento extends JPanel implements MouseListener, KeyListener, Observer
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

    private int mat[][] ;

    private int matView[][] ;
    private int linhaView ;
    private int colunaView ;

    private int armas[] ;

    JLabel labelNomePlayer = new JLabel();
    JLabel labelAuxiliar = new JLabel();
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

        // ESCREVE O NOME DO PLAYER1
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

        this.add(button) ;

        armas = Fachada.getFachada().getQtdArmas1() ;
        mat = Fachada.getFachada().getMatrizPlayer1() ;
        matView = mat.clone() ;

        this.addMouseListener(this) ;
        this.addKeyListener(this);
        this.setFocusable(true);

        this.setBackground(Color.WHITE) ;

        // Tamanho de cada retângulo é de 30px x 30px
        for (int line = 0; line < NUM_LINES; line++) {
            y = yCorner + (line * 30.0) ;

            for (int column = 0; column < NUM_COLUMNS; column++) {
                x = xCorner + (column * 30.0);
                tab[line][column] = new Celula( x, y );
                box[line][column] = new Rectangle2D.Double(tab[line][column].x + boxGap, tab[line][column].y, width, height);
            }
        }

        Fachada.getFachada().register(this) ;
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
                if( matView[i][j] == 0 )
                {
                    g2d.setPaint( new Color(176, 235, 255) ) ;
                }
                else if( matView[i][j] == (int) 'h' )
                {
                    g2d.setPaint( new Color(24, 117, 38) ) ;
                }
                else if( matView[i][j] == (int) 's' )
                {
                    g2d.setPaint( new Color(38, 209, 64) ) ;
                }
                else if( matView[i][j] == (int) 'd' )
                {
                    g2d.setPaint( new Color(220, 197, 26) ) ;
                }
                else if( matView[i][j] == (int) 'r' )
                {
                    g2d.setPaint( new Color(230, 116, 0) ) ;
                }
                else if( matView[i][j] == (int) 'c' )
                {
                    g2d.setPaint( new Color(230, 88, 132) ) ;
                }
                else
                {
                    g2d.setPaint( Color.RED ) ;
                }
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

    public int getCardinal()
    {
        int valor = 0;

        switch(GUIController.getGUIController().getArma().getTipo()){
            case HidroAviao:
                valor = (int) 'h' ;
               break;
            case Submarino:
                valor = (int) 's' ;
                break;
            case Destroier:
                valor = (int) 'd' ;
                break;
            case Cruzador:
                valor = (int) 'r' ;
                break;
            case Couracado:
                valor = (int) 'a' ;
        }
        return valor ;
    }

    public boolean verificaPosicao(int linha, int coluna, int[][] matriz)
    {
        int sum = 0;
        for (int i = linha - 1; i < linha + matriz.length  ; i++ )
        {
            for (int j = coluna - 1; j < coluna + matriz[0].length; j++)
            {
                if ( i > -1 && i < 15 && j > -1 && j < 15 )
                {
                    sum += matView[i][j] ;
                }
            }
        }

        for (int i = 0; i < matriz.length; i++)
            for (int j = 0; j < matriz[0].length; j++)
                sum += matriz[i][j] ;


        if (sum > (getCardinal() * (matriz[0].length * matriz.length )))
            return false;
        return true;
    }

    public void posicionaView( int i, int j, int[][] matriz )
    {
        int sum = 0;
        matView = java.util.Arrays.stream(mat).map(el -> el.clone()).toArray($ -> mat.clone());


        if ( ((i + matriz.length) <= 15)  &&  ((j + matriz[0].length) <= 15) )
        {
            if (verificaPosicao(i, j, matriz) == false) sum = 1;

                for (int x = 0; x < matriz.length; x++)
            {
                for (int y = 0; y < matriz[x].length; y++)
                {
                    if (matriz[x][y] != 0)
                        matView[i + x][j + y] += ( matriz[x][y] + sum );
                }
            }
        }
        else
            System.out.println("Posicionamento de peça fora do tabuleiro!");

        repaint();
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
                    int[][] matrizPeca =  GUIController.getGUIController().getArma().getMatriz() ;
                    this.grabFocus() ;
                    posicionaView(linhaView, colunaView, matrizPeca) ;
                }
                else // Reposicionar peça no tabuleiro
                {

                }
            }
        }
        else if ( e.getButton() == MouseEvent.BUTTON3 )
        {
            if (GUIController.getGUIController().existeArmaSelecionada())
                GUIController.getGUIController().getArma().rotacionaMatrizEm90GrausHorario() ;
        }
    }

    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void notify(Observable o)
    {
        obs = o ;
        lob = (Object[]) obs.get() ;
        mat = (int) lob[0] == 0 ? (int[][]) lob[1] : (int[][]) lob[2] ;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent)
    {
        if (GUIController.getGUIController().existeArmaSelecionada()) {
            if (keyEvent.getKeyCode() == keyEvent.VK_ESCAPE)
            {
                Arma arma = GUIController.getGUIController().getArma() ;
                if ( ((linhaView + arma.getMatriz().length) <= 15)  &&  ((colunaView + arma.getMatriz()[0].length) <= 15) ) {
                    Fachada.getFachada().posicionaPeca(linhaView, colunaView, arma.getMatriz());
                    armas[arma.getTipo().ordinal()]--;
                    GUIController.getGUIController().unselectArma(true);
                }
            }
        }
    }

    public void keyPressed(KeyEvent keyEvent) { }
    public void keyReleased(KeyEvent keyEvent) { }
}

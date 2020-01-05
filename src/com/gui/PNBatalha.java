package com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import com.gui.armas.TipoArma;
import com.regras.*;

public class PNBatalha extends JPanel implements MouseListener, ActionListener, Observer
{
    int xCorner = 75 ; // x do canto esquerdo superior
    int yCorner = 75 ; // y do canto esquerdo superior
    int width = 25 ; // largura do retângulo
    int height = 25 ; // altura do retângulo
    int matGap = 605 ; // distância da matriz da direita até a borda esquerda
    float lineGap = 1.0f ; // espessura da linha

    private final int NUM_LINES = 15 ;
    private final int NUM_COLUMNS = 15 ;

    private No tabLeft[][] ;
    private No tabRight[][] ;

    private String player1 ;
    private String player2 ;

    Observable obs;
    Object lob[];
    Fachada ctrl ;

    private Rectangle2D.Double boxLeft[][] = new Rectangle2D.Double[15][15] ;
    private Rectangle2D.Double boxRight[][] = new Rectangle2D.Double[15][15] ;

    private int vez = -1 ;
    private int qtdTiros = -1 ;
    private int resPartida = -1 ;
    private int resTiro = -1 ;


    JLabel labelPlayer1 = new JLabel() ;
    JLabel labelPlayer2 = new JLabel() ;
    JLabel labelAuxText = new JLabel() ;
    JButton buttonPass = new JButton() ;
    JButton salva = new JButton() ;

    public PNBatalha()
    {
        double x, y ;
        Dimension dLabelPlayer1, dLabelPlayer2, dButtonPass, dSalva, dLabelAuxText ;
        Font fontName = new Font("SansSerif", Font.BOLD, 15) ;
        Font fontAuxText = new Font("SansSerif", Font.BOLD, 15) ;

        this.setLayout( null ) ;

        player1 = Fachada.getFachada().getPlayer1() ;
        player2 = Fachada.getFachada().getPlayer2() ;

        labelPlayer1.setFont( fontName ) ;
        labelPlayer1.setForeground( new Color(81, 81, 81) );
        labelPlayer1.setText( "TABULEIRO DE " + player1.toUpperCase() ) ;
        dLabelPlayer1 = labelPlayer1.getPreferredSize() ;
        labelPlayer1.setBounds( xCorner, height, dLabelPlayer1.width, dLabelPlayer1.height ) ;

        this.add(labelPlayer1) ;

        labelPlayer2.setFont( fontName ) ;
        labelPlayer2.setForeground( new Color(81, 81, 81) );
        labelPlayer2.setText( "TABULEIRO DE " + player2.toUpperCase() ) ;
        dLabelPlayer2 = labelPlayer2.getPreferredSize() ;
        labelPlayer2.setBounds(matGap + xCorner, height, dLabelPlayer2.width, dLabelPlayer2.height ) ;

        this.add(labelPlayer2) ;

        labelAuxText.setFont( fontAuxText ) ;
        labelAuxText.setForeground( new Color(81, 81, 81) ) ;
        labelAuxText.setText("Visão bloqueada, " + player1.toUpperCase() + " deve apertar o botão para desbloquear sua visão") ;
        dLabelAuxText = labelAuxText.getPreferredSize() ;
        labelAuxText.setBounds(570 - dLabelAuxText.width/2, 475, dLabelAuxText.width, dLabelAuxText.height) ;

        this.add(labelAuxText) ;

        buttonPass.setFont( fontAuxText ) ;
        buttonPass.setText( "COMEÇAR JOGO!") ;
        dButtonPass = buttonPass.getPreferredSize() ;
        buttonPass.setBounds(570 - ( dButtonPass.width + 10 )/2,520, dButtonPass.width + 10, dButtonPass.height + 10 ) ;
        buttonPass.addActionListener( this) ;

        this.add(buttonPass) ;
        
        salva.setFont( fontAuxText ) ;
        salva.setText( "SALVAR JOGO!") ;
        dSalva = salva.getPreferredSize() ;
        salva.setBounds(800 - ( dSalva.width + 10 )/2,520, dSalva.width + 10, dSalva.height + 10 ) ;
        salva.addActionListener( this) ;

        this.add(salva) ;

        tabLeft = Fachada.getFachada().getMatrizPlayer1() ;
        tabRight = Fachada.getFachada().getMatrizPlayer2() ;

        addMouseListener(this) ;

        // Tamanho de cada retângulo é de 30px x 30px
        for (int line = 0 ; line < NUM_LINES; line++)
        {
            y = yCorner  + ( line * width ) ;

            for (int column = 0 ; column < NUM_COLUMNS; column++)
            {
                x = xCorner + ( column * height ) ;
                boxLeft[line][column] = new Rectangle2D.Double( x, y, width, height) ;
                boxRight[line][column] = new Rectangle2D.Double(x + matGap, y, width, height) ;
            }
        }

        Fachada.getFachada().register(this) ;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g) ;
        Graphics2D g2d = (Graphics2D) g ;

        g2d.setStroke(new BasicStroke( lineGap, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f));

        if ( vez == -1 )
        {
            g2d.setPaint(Color.WHITE) ;

            for (int i = 0; i < NUM_LINES; i++) {
                for (int j = 0; j < NUM_COLUMNS; j++) {
                    g2d.fill(boxLeft[i][j]) ;
                    g2d.fill(boxRight[i][j]) ;
                }
            }
        }
        else
        {
            Dimension dButtonPass, dSalva, dLabelAuxText ;
            No[][] currPlayer = vez == 0 ? tabLeft : tabRight ;
            No[][] currEnemy = vez == 0 ? tabRight : tabLeft ;

            Rectangle2D.Double currPlayerTabInterface [][] = vez == 0 ? boxLeft : boxRight ;
            Rectangle2D.Double currEnemyTabInterface [][] = vez == 0 ? boxRight : boxLeft ;

            for ( int i = 0; i < NUM_LINES; i++ )
            {
                for ( int j = 0; j < NUM_COLUMNS; j++ )
                {
                    if ( currPlayer[i][j].getNo() == TipoArma.Vazio.ordinal() )
                        g2d.setPaint( new Color(176, 235, 255) ) ;
                    else if ( currPlayer[i][j].getNo() == TipoArma.Submarino.ordinal() )
                        g2d.setPaint( new Color(38, 209, 64) ) ;
                    else if ( currPlayer[i][j].getNo() == TipoArma.Destroier.ordinal() )
                        g2d.setPaint( new Color(220, 197, 26) ) ;
                    else if ( currPlayer[i][j].getNo() == TipoArma.HidroAviao.ordinal() )
                        g2d.setPaint( new Color(24, 117, 38) ) ;
                    else if ( currPlayer[i][j].getNo() == TipoArma.Cruzador.ordinal() )
                        g2d.setPaint( new Color(230, 116, 0) ) ;
                    else if ( currPlayer[i][j].getNo() == TipoArma.Couracado.ordinal() )
                        g2d.setPaint( new Color(230, 88, 132) ) ;
                    else
                        g2d.setPaint( Color.WHITE ) ;

                    g2d.fill( currPlayerTabInterface[i][j] ) ;

                    if ( currEnemy[i][j].getNo() >= 0 && currEnemy[i][j].getNo() <= 5 )
                        g2d.setPaint( Color.WHITE ) ;
                    else if ( currEnemy[i][j].getNo() == 9 )
                        g2d.setPaint( new Color(42, 202, 209)) ;
                    else if ( currEnemy[i][j].getNo() == 10 )
                        g2d.setPaint( new Color(220, 197, 26)) ;
                    else if ( currEnemy[i][j].getNo() == 11 )
                        g2d.setPaint( new Color(231, 20, 39)) ;

                    g2d.fill( currEnemyTabInterface[i][j] ) ;

                }
            }

            if ( vez == 0 )
            {
                labelPlayer1.setForeground( Color.BLACK ) ;
                labelPlayer2.setForeground( new Color(81, 81, 81 ) ) ;
            }
            else
            {
                labelPlayer1.setForeground( new Color(81, 81, 81 ) ) ;
                labelPlayer2.setForeground( Color.BLACK  );
            }

            if ( resTiro == 0 )
                labelAuxText.setText("Tiro acertou a água") ;
            else if ( resTiro == 10 )
                labelAuxText.setText("Tiro acertou embarcação") ;
            else if ( resTiro == 20 )
                labelAuxText.setText("Tiro destruiu embarcação") ;

            dLabelAuxText = labelAuxText.getPreferredSize() ;
            labelAuxText.setBounds(570 - dLabelAuxText.width/2, 475, dLabelAuxText.width, dLabelAuxText.height) ;

            buttonPass.setText("TIROS DISPONÍVEIS:  " + qtdTiros ) ;
            dButtonPass = buttonPass.getPreferredSize() ;
            buttonPass.setBounds(570 - ( dButtonPass.width + 10 )/2,520,dButtonPass.width + 10, dButtonPass.height + 10) ;
        }

        // Iteração para desenhar o tabuleiro
        g2d.setPaint(Color.BLACK);
        for ( int i = 0; i < NUM_LINES ; i++)
        {
            String number = (i >= 9) ? Integer.toString(i + 1) : " " + Integer.toString(i + 1) ;

            // Esquerda
            g2d.drawString( String.valueOf(65 + i), 55, 65 + (int) height*(i + 1)) ;
            g2d.drawString( number, 55 + (int) width*(i + 1), 65) ;

            // Direita
            g2d.drawString( String.valueOf(65 + i), matGap + 55, 65 +(int) height*(i + 1)) ;
            g2d.drawString( number, matGap + 50 +(int) width*(i + 1), 65) ;

            for (int column = 0 ; column < NUM_COLUMNS ; column++ )
            {
                g2d.draw( boxLeft[i][column] ) ;
                g2d.draw( boxRight[i][column] ) ;
            }
        }
    }

    public void mouseClicked( MouseEvent e )
    {
        int linha, coluna ;
        int x = e.getX() ;
        int y = e.getY() ;

        if ( vez != -1 )
        {
            linha = y - yCorner;
            coluna = vez == 0 ? x - (xCorner + matGap) : x - xCorner;

            if (linha < 0) linha = -1;
            else linha /= height;

            if (coluna < 0) coluna = -1;
            else coluna /= width;

            if (linha > -1 && linha < 15 && coluna > -1 && coluna < 15)
            {
                Fachada.getFachada().tiro( linha , coluna ) ;
            }

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
        lob = (Object[]) o.get() ;
        vez = (int) lob[0] ;
        tabLeft = ( No[][] ) lob[1] ;
        tabRight = ( No[][] ) lob[2] ;
        qtdTiros = (int) lob[3] ;
        resPartida = (int) lob[4] ;
        resTiro = (int) lob[5] ;

        String msg="";

        if ( resPartida == 100 )
            msg = Fachada.getFachada().getPlayer1() + " Venceu a Partida!" ;
        else if ( resPartida == 200 )
            msg = Fachada.getFachada().getPlayer2() + " Venceu a Partida!" ;

        repaint() ;

        if (msg!="")
        {
            JOptionPane.showMessageDialog(this, msg ) ;
            Fachada.getFachada().novoJogo() ;
            GUIController.getGUIController().goToMenu() ;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
    	if(actionEvent.getSource() == salva) {
			ctrl.salvaJogo();
		}
    	else if ( vez == -1 )
        {
            vez = 0 ;
            Fachada.getFachada().updateValues() ;
        }
        else
        {

        }

        repaint() ;
    }
}
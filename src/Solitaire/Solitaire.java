package Solitaire;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Solitaire extends JApplet{
    private static final int suitSize = 13;
	private static final int 
		baseX  = 40,
        baseY  = 10,
        shiftX = 80,
        shiftY = 15;
	public static final int 
		TableWidth = 640,
		TableHeight = 500;
	public static GameStates currentState=GameStates.BAD_STATE;
	
	private static Difficulty difficulty;
	
    private static Talon talon;
    private static WastePile wastePile;
    private static TablePile[] tablePile;
    private static FoundationPile[] foundationPile;
	
    private static JFrame outerFrame;
    private static Solitaire innerFrame;
    private static CardTable table;
    
    private static JMenuBar menubar = new JMenuBar();
    private static JMenu menu_game = new JMenu("Game",true);
    private static JMenu menu_style = new JMenu("Set your own style",true);
    private static JMenu menu_about = new JMenu("About this game",true);
    //Game下的菜单选项
    private static JMenuItem menuitem_newgame = new JMenuItem("New Game");
    private static JMenuItem menuitem_quitgame = new JMenuItem("Quit Game");
    private static JMenuItem menuitem_shuffle = new JMenuItem("Shuffle");
    private static JMenu menu_changedifficulty = new JMenu("Change Difficulty");
    private static JMenuItem menuitem_changedifficulty_soeasy = new JMenuItem("SO EASY(No way to die)");
    private static JMenuItem menuitem_changedifficulty_easy = new JMenuItem("EASY(only number+1)");
    private static JMenuItem menuitem_changedifficulty_normal = new JMenuItem("NORMAL(also correct color)");
    private static JMenuItem menuitem_changedifficulty_hard = new JMenuItem("HARD(also you can not shuffle)");
    //Style下的选项
    private static JMenuItem menuitem_nextcardoutline = new JMenuItem("Shift to next Card outline");
    private static JMenuItem menuitem_precardoutline = new JMenuItem("Shift to previous Card outline");
    private static JMenuItem menuitem_nextcardback = new JMenuItem("Shift to next Card back");
    private static JMenuItem menuitem_precardback = new JMenuItem("Shift to previous Card back");
    //About下的选项
    private static JMenuItem menuitem_aboutthisgame = new JMenuItem("About this game");
    private static void Init_MenuBar(){
    	//添加监听器
    	menuitem_newgame.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				Init_Piles();
				table.repaint();
			} } );
    	menuitem_quitgame.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				System.exit( 0 );
		    } 
		} );
    	menuitem_shuffle.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
				// Shuffle the cards
		    	if(getDifficulty()!=Difficulty.HARD){
					Talon.Shuffle( talon.getCards() );
					table.clearMarkers();
					table.repaint();
		    	}
		    	else{
		    		JOptionPane.showMessageDialog(table,
							"HARD模式下禁止洗牌",
							"BAN",
							JOptionPane.PLAIN_MESSAGE);
		    	}
		    }
		});
    	menuitem_nextcardback.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
				Card.incBack();
				table.repaint();
		    }
		});
    	menuitem_precardback.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
				Card.decBack();
				table.repaint();
		    }
		} );
    	menuitem_nextcardoutline.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
				Card.incOutline();
				table.repaint();
		    }
		});
    	menuitem_precardoutline.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
			Card.decOutline();
			table.repaint();
		    }
		} );
    	menuitem_aboutthisgame.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
		    	JOptionPane.showMessageDialog(table,
						"201400301030 王禹秋",
						"About",
						JOptionPane.PLAIN_MESSAGE);
		    }
		} );
    	menuitem_changedifficulty_soeasy.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
		    	if(JOptionPane.showConfirmDialog(table, 
		    			"重新设置难度将会使游戏重新开始，您确定要这样做吗？", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		    		Init_Piles();
					table.repaint();
			    	setDifficulty(Difficulty.SOEASY);
		    	}
		    }
		} );
    	menuitem_changedifficulty_easy.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
		    	if(JOptionPane.showConfirmDialog(table, 
		    			"重新设置难度将会使游戏重新开始，您确定要这样做吗？", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		    		Init_Piles();
					table.repaint();
			    	setDifficulty(Difficulty.EASY);
		    	}
		    }
		} );
    	menuitem_changedifficulty_normal.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
		    	if(JOptionPane.showConfirmDialog(table, 
		    			"重新设置难度将会使游戏重新开始，您确定要这样做吗？", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		    		Init_Piles();
					table.repaint();
			    	setDifficulty(Difficulty.NORMAL);
		    	}
		    }
		} );
    	menuitem_changedifficulty_hard.addActionListener( new ActionListener() {
		    public void actionPerformed( ActionEvent e ) {
		    	if(JOptionPane.showConfirmDialog(table, 
		    			"重新设置难度将会使游戏重新开始，您确定要这样做吗？", "Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
		    		Init_Piles();
					table.repaint();
			    	setDifficulty(Difficulty.HARD);
		    	}
		    }
		} );
    	
    	//设置menu_game
    	menu_game.add(menuitem_newgame);
    	menu_game.add(menuitem_shuffle);
    	menu_game.add(menuitem_quitgame);
	    	//设置menu_changedifficulty
    		menu_changedifficulty.add(menuitem_changedifficulty_soeasy);
    		menu_changedifficulty.add(menuitem_changedifficulty_easy);
    		menu_changedifficulty.add(menuitem_changedifficulty_normal);
    		menu_changedifficulty.add(menuitem_changedifficulty_hard);
    		menu_game.add(menu_changedifficulty);
    	//设置menu_style
    	menu_style.add(menuitem_nextcardoutline);
    	menu_style.add(menuitem_precardoutline);
    	menu_style.add(menuitem_nextcardback);
    	menu_style.add(menuitem_precardback);
    	//设置menu_about
    	menu_about.add(menuitem_aboutthisgame);
    	//初始化menu_bar
    	menubar.add(menu_game);
    	menubar.add(menu_style);
    	menubar.add(menu_about);
    }
	private static void Init_Piles(){
		currentState=GameStates.INITIALISING;
		talon=new Talon(CardTable.Margin,CardTable.Margin);
		wastePile=new WastePile(CardTable.Margin+CardTable.XShift,CardTable.Margin);
		tablePile = new TablePile[7];
		foundationPile =new FoundationPile[4];
		for(int i=0;i<7;i++){
			tablePile[i]=new TablePile(CardTable.Margin + ( i ) * CardTable.XShift,
			        CardTable.Margin+CardTable.YShift,i + 1 );
		}
		for(int i=0;i<4;i++){
			foundationPile[i]=new FoundationPile( CardTable.Margin +( 3 + i ) * CardTable.XShift,
			        CardTable.Margin, i + 1 );
		}
		currentState = GameStates.GAME_IN_PROGRESS;
	}
	private Solitaire(){
		Init_Piles();
		Init_MenuBar();
		setDifficulty(Difficulty.NORMAL);
	}
	public static void isGameOver() {
		boolean won = true;
		for (int i=0; i<4; i++){
		    if (Solitaire.getFoundationPile()[i].getSize()!=13){
			won = false;
		    }
		}
		if (won) {
		    currentState = GameStates.GAME_OVER;
		    displayGameOverDialog();
		}
	}
	public static void displayGameOverDialog() {
		JOptionPane.showMessageDialog(table,
				"You win! Well done",
				"Congratulations",
				JOptionPane.PLAIN_MESSAGE);
	}
    public static void main(String[] args){
		outerFrame = new JFrame( "Solitaire");
	    innerFrame = new Solitaire();
	    innerFrame.setLayout( new FlowLayout() );
	    outerFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		
	    
	    outerFrame.setJMenuBar(menubar);
	    table = new CardTable();		
	    table.setPreferredSize( new Dimension( TableWidth, TableHeight ) );
	    innerFrame.add( table );
	    outerFrame.add( innerFrame );
	    //outerFrame.setBounds(100,100,TableWidth, TableHeight);//！！！！！！！临时
	    outerFrame.pack();				//！！！！！！还原
	    outerFrame.setVisible( true );
	}



	// Getters and setters
    public static int getSuitsize() {return suitSize;}
    public static int getBasex() {return baseX;}
    public static int getBasey() {return baseY;}
    public static int getShiftx() {return shiftX;}
    public static int getShifty() {return shiftY;}
    public static Difficulty getDifficulty() {return difficulty;}
    public static Talon getTalon() {return talon;}
    public static WastePile getWastePile() {return wastePile;}
    public static TablePile[] getTablePile() {return tablePile;}
    public static FoundationPile[] getFoundationPile() {return foundationPile;}
    
    public static void setTalon(Talon talon) {Solitaire.talon = talon;}
    public static void setWastePile(WastePile wastePile) {Solitaire.wastePile = wastePile;}
    public static void setTablePile(TablePile[] tablePile) {Solitaire.tablePile = tablePile;}
    public static void setFoundationPile(FoundationPile[] foundationPile) {Solitaire.foundationPile = foundationPile;}
    public static void setDifficulty(Difficulty new_difficulty){difficulty=new_difficulty;outerFrame.setTitle("Solitaire ("+difficulty.toString()+")");}
}

package Solitaire;

import java.awt.Graphics;

import javax.swing.JComponent;

public class FoundationPile extends Pile{
	private String suit;
	private int intsuit;//方便循环创建 也方便图片命名= =
	public FoundationPile(int x, int y,int intsuit) {
		super(x, y);
		// TODO 自动生成的构造函数存根
		this.intsuit=intsuit;
		switch (intsuit) {
	    	case 1: suit= "s";break;
	    	case 2: suit= "h";break;
	    	case 3: suit= "c";break;
	    	case 4: suit= "d";break;
	    	default: throw new IndexOutOfBoundsException();
		}
	}
	public boolean accepts( Card someCard ) {
		if ( isEmpty() ) {
		    return someCard.getValue() == 1 && someCard.getSuit() == suit ;
		}
		Card topCard = topCard();
		return ( ( someCard.getSuit() == suit )
			 && someCard.getValue() == 1 + topCard.getValue() );
    }
	@Override
	public void show( Graphics g, JComponent c ){
    	if ( isEmpty() ) {
    		Card.getFoundationBase( suit ).show( g, c, x, y );
    	} else {
    		topCard().show( g, c, x, y );
    	}
    }
	@Override
	public void clickedAt(int xpt, int ypt) {
		// TODO 自动生成的方法存根
		//点一下什么也不发生= = 
	}
	

}

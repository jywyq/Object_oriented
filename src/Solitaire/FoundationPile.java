package Solitaire;

import java.awt.Graphics;

import javax.swing.JComponent;

public class FoundationPile extends Pile{
	private String suit;
	private int intsuit;//����ѭ������ Ҳ����ͼƬ����= =
	public FoundationPile(int x, int y,int intsuit) {
		super(x, y);
		// TODO �Զ����ɵĹ��캯�����
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
		// TODO �Զ����ɵķ������
		//��һ��ʲôҲ������= = 
	}
	

}

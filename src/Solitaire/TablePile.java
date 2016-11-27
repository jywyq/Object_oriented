package Solitaire;

import java.awt.*;

import javax.swing.JComponent;

public class TablePile extends Pile{
    protected final static int tableCardShift = 15;
    protected int cardsShowing;
    public void addShownCards( int more ) {
    	cardsShowing += more;
    }
	public void removeShownCards( int fewer ) {
		cardsShowing -= fewer;
	}
    public int getShownCards() {
    	return cardsShowing;
	}
	public TablePile(int x, int y, int initSize) {
		// TODO 自动生成的构造函数存根
		super(x, y);
		for ( int i = 0 ; i < initSize ; ++i ) {
		    push( Solitaire.getTalon().pop() );
		}
		if( initSize > 0 ) {
		    topCard().turnOver();
		    cardsShowing = 1;
		}
		
	}

	@Override
	public void clickedAt(int xpt, int ypt) {
		// TODO 自动生成的方法存根
		//什么都不会发生
	}

	@Override
	public boolean accepts( Card someCard ) {
		if(Solitaire.getDifficulty()==Difficulty.SOEASY){
			return true;
		}
		else if(Solitaire.getDifficulty()==Difficulty.EASY){
			if ( isEmpty() ) {
			    return someCard.getValue() == 13;//必须是K
			}else{
				Card    topCard       = topCard();
				return topCard.getValue() == someCard.getValue() + 1;
			}
		}
		else{
			if ( isEmpty() ) {
			    return someCard.getValue() == 13;//必须是K
			} else {
				//颜色相反且数值大1
			    Card    topCard       = topCard();
			    Colour  topCardColour = topCard.getColour();
			    boolean correctColour=false;
			    if (topCardColour.equals( Colour.Red ) ) {
		            correctColour = someCard.getColour().equals( Colour.Black );
			    } else if (topCardColour.equals( Colour.Black ) ) {
		            correctColour = someCard.getColour().equals( Colour.Red );
			    } else {
		            throw new IllegalArgumentException( "Bad Card Colour" );
			    }
			    return correctColour && topCard.getValue() == someCard.getValue() + 1;
			}
		}
		
	}
	//重载了。
	public TablePile pickUp( int numberNeeded ) {
		if ( cardsShowing == 0 ) {
		    return null;
		}
		TablePile p = super.pickUp( numberNeeded );
		if ( p != null ) {
	        removeShownCards( numberNeeded );
		}
		return p;
	}
	
	public void putDown( Pile source ) {
		if ( source != null && !source.isEmpty() ) {
		    source.reversePile();
		    int i = source.getSize();
            addShownCards( i );
		    while( i-- > 0 ) push( source.pop() );
		}
	}
	public boolean containsPoint( int xpt, int ypt ) {
		return ( ( x <= xpt ) && ( xpt <= x + Card.width ) &&
				( y <= ypt ) && ( ypt <= y + Card.height + ( tableCardShift * ( size - 1 ) ) ) );
	}
    public void show( Graphics g, JComponent c ) {
    	if ( isEmpty() ) {
	        Card.getCardOutline().show( g, c, x, y );
    	} else {
	        int currentY = y;
	        for ( Card someCard : cards ) {
		        if ( someCard.isFaceUp() ) {
                   someCard.show( g, c, x, currentY );
	 	        } else {
                    Card.getCardBack().show( g, c, x, currentY );
		        }
                currentY+=tableCardShift;
            }
    	}
    }
}

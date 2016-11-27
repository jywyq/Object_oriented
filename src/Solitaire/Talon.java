package Solitaire;

import java.util.Random;
import java.util.Stack;

public class Talon extends Pile{
	//发牌专用：剩余牌堆
	public Talon(int x, int y) {
		super(x, y);
		// TODO 自动生成的构造函数存根
		//把所有牌添加进去然后洗牌
		for ( Suit suit : Suit.values() ) {
		    for ( int j = 1; j <= 13 ; ++j ) {
			    push( new Card( j, suit ) );
		    }
		}
		Shuffle( cards );
	}
	public static void swap(Stack< Card > cards,int i,int j){
	    Card cv = cards.get(i), lv = cards.get(j);
	    cards.set( i, lv);  cards.set( j, cv);
	}
	public static void Shuffle( Stack< Card > cards) {
		int n = cards.size();
		Random rgen = new Random();
		//只有一张牌就不洗了！
		while (n>1) {
		    // 在当前的里面随便选一个 ，和第n-1个交换
		    int cn = (int) (n * rgen.nextFloat());
		    assert cn>=0 && cn < n;//出错就报啊。。。
		    //没有swap方法就自己写了一个
		    swap(cards,cn,n-1);
		    //Card cv = cards.get( cn), lv = cards.get( n-1);
		    //cards.set( cn, lv);  cards.set( n-1, cv);
		    //这里的get和set都是《stack》自带的哦
		    n--;
		}
    }
	@Override
	public void clickedAt(int xpt, int ypt) {
		// TODO 自动生成的方法存根
		//没有的话就复位
		if ( isEmpty() ) {
		    while( !Solitaire.getWastePile().isEmpty() ) {
			    Card c = Solitaire.getWastePile().pop();
			    c.hideFace();//记得隐藏~
			    push( c );
		    }
	    } //有的话就展示3张牌出来
		else {
		    for ( int i = 0 ; i < 3 ; i++ ) {
				if ( !isEmpty() ) { 
				    // Must be checked before every pop in case fewer than 3 cards remained
				    Card c = pop();
				    c.showFace();//翻出来~
				    Solitaire.getWastePile().push( c );
				}
		    }
    	}
	}

}

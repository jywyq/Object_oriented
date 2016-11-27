package Solitaire;

import java.util.Random;
import java.util.Stack;

public class Talon extends Pile{
	//����ר�ã�ʣ���ƶ�
	public Talon(int x, int y) {
		super(x, y);
		// TODO �Զ����ɵĹ��캯�����
		//����������ӽ�ȥȻ��ϴ��
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
		//ֻ��һ���ƾͲ�ϴ�ˣ�
		while (n>1) {
		    // �ڵ�ǰ���������ѡһ�� ���͵�n-1������
		    int cn = (int) (n * rgen.nextFloat());
		    assert cn>=0 && cn < n;//����ͱ���������
		    //û��swap�������Լ�д��һ��
		    swap(cards,cn,n-1);
		    //Card cv = cards.get( cn), lv = cards.get( n-1);
		    //cards.set( cn, lv);  cards.set( n-1, cv);
		    //�����get��set���ǡ�stack���Դ���Ŷ
		    n--;
		}
    }
	@Override
	public void clickedAt(int xpt, int ypt) {
		// TODO �Զ����ɵķ������
		//û�еĻ��͸�λ
		if ( isEmpty() ) {
		    while( !Solitaire.getWastePile().isEmpty() ) {
			    Card c = Solitaire.getWastePile().pop();
			    c.hideFace();//�ǵ�����~
			    push( c );
		    }
	    } //�еĻ���չʾ3���Ƴ���
		else {
		    for ( int i = 0 ; i < 3 ; i++ ) {
				if ( !isEmpty() ) { 
				    // Must be checked before every pop in case fewer than 3 cards remained
				    Card c = pop();
				    c.showFace();//������~
				    Solitaire.getWastePile().push( c );
				}
		    }
    	}
	}

}

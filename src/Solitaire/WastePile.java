package Solitaire;

import java.awt.Graphics;

import javax.swing.JComponent;

public class WastePile extends Pile{
	//���Ͻ��ұߵķ��ƶ�
	protected final static int wasteCardShift = 15;

	public WastePile(int x, int y) {
		super(x, y);
		// TODO �Զ����ɵĹ��캯�����
	}
	public void clickedAt(int x, int y) {
		// TODO �Զ����ɵķ������
		if (isEmpty()) {
			return;
		}
	}
	public boolean containsPoint(int xpt, int ypt) {
		int sz=this.size;if(sz>3)sz=3;
		return ((xpt >= x+(3-sz)*wasteCardShift) && (xpt <= x + 2 * wasteCardShift + Card.width) && (ypt >= y) && (ypt <= y
				+ Card.height));
	}
	public void show(Graphics g, JComponent c) {
		if (isEmpty()) {
			Card.getCardOutline().show(g, c, x, y);
		} 
		//��λչʾ�ƶѵ�ǰ���ţ�����еĻ���
		else {
			Card c1 = pop();
			Card c2 = c1;
			Card c3 = c1;
			if (!isEmpty()) {
				c2 = pop();
			}
			if (!isEmpty()) {
				c3 = pop();
			}
			if (c3 != c1) {
				push(c3);
				c3.show(g, c, x, y);
			}
			if (c2 != c1) {
				push(c2);
				c2.show(g, c, x + wasteCardShift, y);
			}
			push(c1);
			c1.show(g, c, x + 2 * wasteCardShift, y);
		}
	}
	
	

}

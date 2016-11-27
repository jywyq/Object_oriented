package Solitaire;

import java.awt.Graphics;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JComponent;

public abstract class Pile {
	protected int x, y;// 左上角的坐标
	protected Stack<Card> cards;
	protected int size;
	
	public void reposition(int newX, int newY) {
		x = newX;
		y = newY;
	}
	//构造函数
	public Pile(int x,int y){
		this.x = x;
		this.y = y;
		cards = new Stack<Card>();
		size = 0;
	}
	
	public void push(Card someCard) {
		cards.push(someCard);
		++size;
	}
	public final Card pop() {
		try {
			--size;
			return cards.pop();
		} catch (EmptyStackException ese) {
			size = 0;
			return null;
		}
	}
	public final Card topCard() {
		if (!cards.isEmpty())
			return cards.peek();
		return null;
	}
	public final Card bottomCard() {
		if (cards.isEmpty()) {
			return null;
		}
		return cards.get(0);
	}
	public final boolean isEmpty() {
		return cards.isEmpty();
	}

	public void show(Graphics g, JComponent c) {
		if (isEmpty()) {
			Card.getCardOutline().show(g, c, x, y);
		} else {
			if (topCard().isFaceUp()) {
				topCard().show(g, c, x, y);
			} else {
				Card.getCardBack().show(g, c, x, y);
			}
		}
	}
	public boolean containsPoint(int xpt, int ypt) {
		return ((xpt >= x) && (xpt <= x + Card.width) && (ypt >= y) && (ypt <= y
				+ Card.height));
	}
	//拿起numberNeeded个数的牌，返回一个TablePile
	public TablePile pickUp(int numberNeeded) {
		if (!isEmpty()) {
			assert (numberNeeded <= size);
			Pile rev = new TablePile(x, y, 0);
			TablePile p = new TablePile(x, y, 0);
			int i = numberNeeded;
			while (i > 0) {
				p.push(pop());
				--i;
			}
			p.reversePile();
			return p;
		} else {
			return null;
		}
	}
	//放下一堆牌
	public void putDown(Pile source) {
		if (source != null && !source.isEmpty()) {
			source.reversePile();
			int i = source.getSize();
			while (i > 0) {
				push(source.pop());
				--i;
			}
		}
	}

	public void reversePile() {
		Stack<Card> temp = new Stack<Card>();
		while (!cards.empty()) {
			temp.push(cards.pop());
		}
		size = temp.size();
		cards = (Stack<Card>) temp.clone();
		temp = null;
	}
	public boolean accepts(Card someCard){
		return false;
	}
	//抽象方法
	public abstract void clickedAt(int xpt, int ypt);
	
	public String toString() {//debug用
		String s = "Pile: [";
		Object[] l = cards.toArray();
		for (int i = 0; i < l.length - 1; ++i) {
			s += l[i].toString();
			s += ", ";
		}
		s += l[l.length - 1].toString() + "]";
		return s;
	}
	public Stack<Card> getCards() {return cards;}
	public int getX() {return x;}
	public int getY() {return y;}
	public int getSize() {return size;}
}

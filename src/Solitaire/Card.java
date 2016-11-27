package Solitaire;

import java.awt.*;

import javax.swing.JComponent;


public class Card {
	private Image im;// ͼƬ
	private int value;// ��
	private String suit;// ���ƣ�������string��
	private boolean faceUp;// �Ƿ����泯��
	private Colour colour;// ��ɫ

	private final static String directory = "cards", extension = ".gif",
			fpBaseFileName = "fpBase0";
	private final static Image fpShade = Toolkit.getDefaultToolkit().getImage(
			"cards/shade.gif");
	private final static String[] backs = new String[] { "back001", "back101",
			"back102", "back111", "back121", "back131", "back132", "back191",
			"back192" };
	private final static String[] bottoms = new String[] { "bottom01",
			"bottom02", "bottom03" };

	public final static int width = 73;
	public final static int height = 97;
	public static int backIndex = 0;
	public static int bottomIndex = 0;
	public static String cardBackFilename = backs[backIndex],
			cardOutlineFilename = bottoms[bottomIndex];
	//���׹���취
	public static Card getCard(Suit s, int card) {return new Card(directory + CardFile(s, card));}
	public static Card getCardBack() {return new Card(directory + "/" + cardBackFilename + extension);}
	public static Card getCardOutline() {return new Card(directory + "/" + cardOutlineFilename + extension);}
	public static Card getFoundationBase(String suit2) {return new Card(directory + "/" + fpBaseFileName + suit2 + extension);}
	// �����ڲ�ʹ��
	private Card(String name) {
		try {
			im = Toolkit.getDefaultToolkit().getImage(name);
			String[] nameArray = name.split("/");
			String last = nameArray[nameArray.length - 1];// �ļ���
			String n = last.substring(0, 2);

			if (n.equals("ba")) {// ���泯��
				// ����
				value = 14;// ���泯��ʱΪ14
				suit = "x";
				colour = Colour.Neither;
				faceUp = false;
			} else if (n.equals("bo")) {// ���棬û�п�
				// �ײ�
				value = 0;
				suit = "x";
				colour = Colour.Neither;
				faceUp = false;
			} else if (n.equals("fp")) {// foundation_pile
				// ����ƶѵĵײ�
				value = 0;
				faceUp = false;
				n = last.substring(7, 8);
				// �ѻ�ɫд�ڵ�7���ַ���
				switch (n) {
				case "1":
					suit = "s";
					colour = Colour.Black;
					break;
				case "2":
					suit = "h";
					colour = Colour.Red;
					break;
				case "3":
					suit = "c";
					colour = Colour.Black;
					break;
				case "4":
					suit = "d";
					colour = Colour.Red;
					break;

				}
			} else {// ��������
				value = Integer.valueOf(n);
				suit = last.substring(2, 3);
				if (suit.equals("h") || suit.equals("d")) {
					colour = Colour.Red;
				} else {
					colour = Colour.Black;
				}
				faceUp = true; // ��������
			}
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
	}

	Card(int value, Suit suit) {
		// ���췽����ֻ����������
		this.value = value;
		switch (suit) {
			case Clubs:
				this.suit = "c";
				colour = Colour.Black;
				break;
			case Diamonds:
				this.suit = "d";
				colour = Colour.Red;
				break;
			case Hearts:
				this.suit = "h";
				colour = Colour.Red;
				break;
			case Spades:
				this.suit = "s";
				colour = Colour.Black;
				break;
		}
		faceUp = false;
		try {
			im = Toolkit.getDefaultToolkit().getImage(
					directory + CardFile(suit, value));
		} catch (Exception e) {
			System.err.println("Error " + e.getMessage());
		}
	}
	//��g�ϵ�x,y����
	public void show(Graphics g, JComponent c, int x, int y) {
		if (value == 0) {
			g.drawImage(fpShade, x, y, c);
		}
		g.drawImage(im, x, y, c);
	}

	public boolean isFaceUp() {return faceUp;}
	public void showFace() {faceUp = true;}
	public void hideFace() {faceUp = false;}
	public void turnOver() {faceUp = !faceUp;}
	
	public int getValue() {return value;}
	public String getSuit() {return suit;}
	public Colour getColour() {return colour;}
	//��ǰ�л�����
	public static void incBack() {
		backIndex = (backIndex + 1) % backs.length;
		cardBackFilename = backs[backIndex];
	}
	//����л�����
	public static void decBack() {
		backIndex = (backIndex - 1 + backs.length) % backs.length;
		cardBackFilename = backs[backIndex];
	}

	//��ǰ�л���
	public static void incOutline() {
		bottomIndex = (bottomIndex + 1) % bottoms.length;
		cardOutlineFilename = bottoms[bottomIndex];
	}
	//����л���
	public static void decOutline() {
		bottomIndex = (bottomIndex - 1 +bottoms.length ) % bottoms.length;
		cardOutlineFilename = bottoms[bottomIndex];
	}
	//debug��
	public String toString() {
		return "(" + value + " of " + suit + ", " + isFaceUp() + ")";
	}
	// ����ļ�������һ����ͨ�������ļ�����
	protected static String CardFile(Suit s, int card)
			throws IllegalArgumentException {
		char sc;
		if (card < 1 || card > 13)
			throw new IllegalArgumentException("Bad Card Number");

		if (s == Suit.Clubs)sc = 'c';
		else if (s == Suit.Diamonds)sc = 'd';
		else if (s == Suit.Hearts)sc = 'h';
		else if (s == Suit.Spades)sc = 's';
		else throw new IllegalArgumentException("Bad Card Suit");
		if (card < 10)return "/0" + card + sc + extension;
		else return "/" + card + sc + extension;
	}
}

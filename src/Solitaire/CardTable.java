package Solitaire;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class CardTable extends JPanel {
	protected static final int Margin = 40, XShift = 80, YShift = 160,
			CardShift = 15;
	static int anchorX, anchorY, currentX, currentY, dX, dY;
	protected TablePile dragging = null;// 拖拽中的
	protected Pile draggedFrom = null;

	public int pickedFromPileIdx = -1;

	public enum PILE_TYPE {
		FOUNDATION_PILE, TABLE_PILE, WASTE_PILE, TALON, BAD_PILE
	};

	public PILE_TYPE pickedFromPileType = PILE_TYPE.BAD_PILE;

	public void clearMarkers() {
		dragging = null;
		draggedFrom = null;
		pickedFromPileIdx = -1;
		pickedFromPileType = PILE_TYPE.BAD_PILE;
	}
	
	public static class MyMouseListener extends MouseAdapter{
		private static  boolean flag = false;
		// 用来判断是否已经执行双击事件
		private static int clickNum = 0;
		// 用来判断是否该执行双击事件
		public void mouseClicked(MouseEvent e) {
			if (Solitaire.currentState == GameStates.GAME_OVER) {
				return;
			}
			anchorX = e.getX();
			anchorY = e.getY();
			//单击在 Talon上
			if (Solitaire.getTalon().containsPoint(anchorX, anchorY)) {
				Solitaire.getTalon().clickedAt(anchorX, anchorY);
				return;
			}
			
			MyMouseListener.flag = false;
			if (MyMouseListener.clickNum == 1) {// 1时执行双击事件
				for(int i=0;i<7;i++){
					if (!Solitaire.getTablePile()[i].isEmpty() && Solitaire.getTablePile()[i].containsPoint(e.getX(),e.getY())) {
						System.out.println("双击事件：TablePile");
						TablePile tp = Solitaire.getTablePile()[i];
						int shift = anchorY - tp.getY();
						int cardsleft = shift / TablePile.tableCardShift;
						if (cardsleft >= tp.getSize()) {
							cardsleft = tp.getSize() - 1;
						}
						int cardsSelected = tp.getSize() - cardsleft;
						if(cardsSelected==1){
							TablePile tmp = tp.pickUp(1);
							boolean flag=false;//是否释放
							for(int j=0;j<4;j++){
								if (Solitaire.getFoundationPile()[j].accepts(tmp.bottomCard())) {
									System.out.println("释放成功！！");
									Solitaire.getFoundationPile()[j].putDown(tmp);
									flag=true;
									//翻牌
									for (int k = 0; k < 7; ++k) {
										if (!Solitaire.getTablePile()[k].isEmpty() && Solitaire.getTablePile()[k].containsPoint(anchorX,anchorY)) {
											TablePile tpp = Solitaire.getTablePile()[k];
											if (!tpp.isEmpty() && tpp.getY() + (tpp.getSize() - 1)*TablePile.tableCardShift< anchorY
													&& !tpp.topCard().isFaceUp()) {
												tpp.topCard().turnOver();
												tpp.addShownCards(1);
											}
										}
									}
									Solitaire.isGameOver();
									break;
								}
							}
							if(!flag){
								System.out.println("释放失败！！");
								tp.putDown(tmp);
							}
						}
						
					}
				}
				if (!Solitaire.getWastePile().isEmpty() && Solitaire.getWastePile().containsPoint(e.getX(),e.getY())) {
					System.out.println("双击事件：WastePile");
					WastePile wp = Solitaire.getWastePile();
					TablePile tmp = wp.pickUp(1);
					boolean flag=false;//是否释放
					for(int j=0;j<4;j++){
						if (Solitaire.getFoundationPile()[j].accepts(tmp.bottomCard())) {
							System.out.println("释放成功！！");
							Solitaire.getFoundationPile()[j].putDown(tmp);
							flag=true;
							//翻牌
							for (int k = 0; k < 7; ++k) {
								if (!Solitaire.getTablePile()[k].isEmpty() && Solitaire.getTablePile()[k].containsPoint(anchorX,anchorY)) {
									TablePile tpp = Solitaire.getTablePile()[k];
									if (!tpp.isEmpty() && tpp.getY() + (tpp.getSize() - 1)*TablePile.tableCardShift< anchorY
										&& !tpp.topCard().isFaceUp()) {
										tpp.topCard().turnOver();
										tpp.addShownCards(1);
									}
								}
							}
							break;
							}

						Solitaire.isGameOver();
					}
					if(!flag){
						System.out.println("释放失败！！");
						wp.putDown(tmp);
					}
				}
				MyMouseListener.clickNum = 0;
				MyMouseListener.flag = true;
				return;
			}
			// 定义定时器
			Timer timer = new Timer();
			// 定时器开始执行，延时0.2秒后确定是否执行单击事件
			timer.schedule(new TimerTask() {
				private int n = 0;
				// 记录定时器执行次数
				public void run() {
					if (MyMouseListener.flag) {
						MyMouseListener.clickNum = 0;
						this.cancel();
						return;
					}
					if (n == 1) {
						System.out.println("单击不发生事件");
						
						MyMouseListener.flag = true;
						MyMouseListener.clickNum = 0;
						n = 0;
						this.cancel();
						return;
					}
					clickNum++;n++;
					System.out.println(clickNum);
				}
			}, new Date(), 200);
		}
	}
	public CardTable() {
		
		
		
		
		addMouseListener(new MyMouseListener());

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (Solitaire.currentState == GameStates.GAME_OVER) {
					return;
				}
				System.out.println("Pressed");
				
				anchorX = e.getX();
				anchorY = e.getY();
				for (int i = 0; i < 7; ++i) {
					if (!Solitaire.getTablePile()[i].isEmpty() && Solitaire.getTablePile()[i].containsPoint(e.getX(),e.getY())) {
						TablePile tp = Solitaire.getTablePile()[i];
						//求解是点的第几个
						int shift = anchorY - tp.getY();
						int cardsleft = shift / TablePile.tableCardShift;
						if (cardsleft >= tp.getSize()) {
							cardsleft = tp.getSize() - 1;
						}
						int cardsSelected = tp.getSize() - cardsleft;
						if (cardsSelected <= tp.getShownCards()) {
							System.out.println(" 剩余牌堆  #" + i + "   " +cardsleft);
							System.out.println(" 拿起牌堆  #" + i + "   " + cardsSelected);
							dragging = tp.pickUp(cardsSelected);
						}

						if (dragging != null) {
							System.out.println("正在拿起： "+ dragging);

							draggedFrom = Solitaire.getTablePile()[i];
							dX = anchorX - dragging.getX();
							dY = anchorY - dragging.getY() - cardsleft * TablePile.tableCardShift;
							pickedFromPileIdx = i;
							pickedFromPileType = PILE_TYPE.TABLE_PILE;
						} else {
							pickedFromPileIdx = -1;
						}
						break;
					}
				}

				//foundationPile
				for (int i = 0; i < 4; i++) {
					if (!Solitaire.getFoundationPile()[i].isEmpty() && Solitaire.getFoundationPile()[i].containsPoint(e.getX(), e.getY())) {
						dragging = Solitaire.getFoundationPile()[i].pickUp(1);
						dX = anchorX - dragging.getX();
						dY = anchorY - dragging.getY();
						pickedFromPileIdx = i;
						pickedFromPileType = PILE_TYPE.FOUNDATION_PILE;
					}
				}

				//wastePile
				if (!Solitaire.getWastePile().isEmpty() && Solitaire.getWastePile().containsPoint(e.getX() - 2 * WastePile.wasteCardShift, e.getY())
						&& !Solitaire.getWastePile().isEmpty()) {
					dragging = Solitaire.getWastePile().pickUp(1);
					dX = e.getX() - dragging.getX() - 2* WastePile.wasteCardShift;
					dY = e.getY() - dragging.getY();
					pickedFromPileIdx = 0;
					pickedFromPileType = PILE_TYPE.WASTE_PILE;
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (Solitaire.currentState == GameStates.GAME_OVER) {
					return;
				}
				System.out.println("Release");
				currentX = e.getX();
				currentY = e.getY();
				boolean putDownCard = false;
				if (dragging != null && !dragging.isEmpty()) {
					System.out.println("尝试释放：" + dragging);

					//tablePile
					for (int i = 0; i < 7; ++i) {
						if (Solitaire.getTablePile()[i].containsPoint(e.getX(),e.getY())) {
							System.out.println("尝试释放到TablePile# "+ i);

							if (Solitaire.getTablePile()[i].accepts(dragging.bottomCard())) {
								System.out.println("释放成功！！");
								Solitaire.getTablePile()[i].putDown(dragging);
								putDownCard = true;
							}
							break;
						}
					}

					//foundationPile
					for (int i = 0; i < 4; ++i) {
						if (Solitaire.getFoundationPile()[i].containsPoint(e.getX(), e.getY())
								&& dragging.getSize() == 1) {
							System.out.println("尝试释放到FoundationPile# "+ i);

							if (Solitaire.getFoundationPile()[i].accepts(dragging.bottomCard())) {
								System.out.println("释放成功！！");
								Solitaire.getFoundationPile()[i].putDown(dragging);
								putDownCard = true;
							}
							break;
						}
					}
					//以上都不是就放回去。
					switch (pickedFromPileType) {
						case TABLE_PILE:
							Solitaire.getTablePile()[pickedFromPileIdx].putDown(dragging);
							break;
						case FOUNDATION_PILE:
							Solitaire.getFoundationPile()[pickedFromPileIdx].putDown(dragging);
							break;
						case WASTE_PILE:
							Solitaire.getWastePile().putDown(dragging);
							break;
						default:
							break;
					}
				} else {
					System.out.println("No cards were being dragged ");
				}

				// Put the card back where it came from
				if (!putDownCard && pickedFromPileIdx != -1) {
					if (0 < pickedFromPileIdx && pickedFromPileIdx < 7) {
						Solitaire.getTablePile()[pickedFromPileIdx]
								.putDown(dragging);
						System.out.println("Put it back in pile#-"
								+ pickedFromPileIdx);
					}
				}
				else{
					for (int i = 0; i < 7; ++i) {
						if (Solitaire.getTablePile()[i].containsPoint(anchorX,anchorY)) {
							TablePile tp = Solitaire.getTablePile()[i];
							if (!tp.isEmpty() && tp.getY() + (tp.getSize() - 1)*TablePile.tableCardShift< anchorY
									&& !tp.topCard().isFaceUp()) {
								tp.topCard().turnOver();
								tp.addShownCards(1);
							}
						}
					}
				}
				dragging = null;
				repaint();
				Solitaire.isGameOver();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (Solitaire.currentState == GameStates.GAME_OVER) {
					return;
				}
				currentX = e.getX();
				currentY = e.getY();
				if (dragging != null) {
					dragging.reposition(currentX - dX, currentY - dY);
				}
				repaint();
			}
		});

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < 7; ++i) {
			Solitaire.getTablePile()[i].show(g, this);
		}

		for (int i = 0; i < 4; ++i) {
			Solitaire.getFoundationPile()[i].show(g, this);
		}

		Solitaire.getTalon().show(g, this);
		Solitaire.getWastePile().show(g, this);

		// 正在拖动的牌堆
		if (dragging != null) {
			dragging.show(g, this);
		}
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}

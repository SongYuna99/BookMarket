package com.market.page;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.MemberDao;
import com.market.main.MainWindow;
import com.market.vo.BookVo;
import com.market.vo.CartVo;

public class CartItemListPage extends JPanel {
	// Field
	JTable cartTable;
	Object[] tableHeader = { "번호", "도서ID", "도서명", "단가", "수량", "총가격" };
	public static int mSelectRow = -1;
	MemberDao memberDao;
	BookDao bookDao;
	CartDao cartDao;
	ArrayList<CartVo> list;
	JLabel totalPricelabel;

	public CartItemListPage(JPanel panel, Map daoList) {
		memberDao = (MemberDao) daoList.get("memberDao");
		bookDao = (BookDao) daoList.get("bookDao");
		cartDao = (CartDao) daoList.get("cartDao");

		this.setLayout(null);

		Rectangle rect = panel.getBounds();
		this.setPreferredSize(rect.getSize());

		JPanel bookPanel = new JPanel();
		bookPanel.setBounds(0, 0, 1000, 400);
		add(bookPanel);

		list = cartDao.select(MainWindow.member.getMid());
		Object[][] content = new Object[list.size()][tableHeader.length];
		Integer totalPrice = 0;

		for (int i = 0; i < list.size(); i++) {
			CartVo item = list.get(i);

			content[i][0] = item.getRno();
			content[i][1] = item.getIsbn();
			content[i][2] = item.getTitle();
			content[i][3] = item.getSprice(); // 단가
			content[i][4] = item.getQty();
			content[i][5] = item.getStotal_price();
			totalPrice += item.getTotal_price();
		}

		cartTable = new JTable(content, tableHeader);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(600, 350));
		jScrollPane.setViewportView(cartTable);
		bookPanel.add(jScrollPane);

		JPanel totalPricePanel = new JPanel();
		totalPricePanel.setBounds(0, 400, 1000, 50);
		totalPricelabel = new JLabel("총금액: " + priceFormat(totalPrice) + " 원");
		totalPricelabel.setForeground(Color.red);
		MarketFont.getFont(totalPricelabel);
		totalPricePanel.add(totalPricelabel);
		add(totalPricePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBounds(0, 450, 1000, 50);
		add(buttonPanel);

		JLabel buttonLabel = new JLabel("장바구니 비우기");
		MarketFont.getFont(buttonLabel);
		JButton clearButton = new JButton();
		clearButton.add(buttonLabel);
		buttonPanel.add(clearButton);

		/** 장바구니 비우기 버튼 이벤트 처리 **/
		clearButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String mid = MainWindow.member.getMid();

				if (cartDao.getSize(mid) == 0)
					JOptionPane.showMessageDialog(clearButton, "장바구니가 비어 있습니다");
				else {
					int select = JOptionPane.showConfirmDialog(clearButton, "정말로 삭제하시겠습니까? ");
					if (select == 0) {
						TableModel tableModel = new DefaultTableModel(new Object[0][0], tableHeader);
						cartTable.setModel(tableModel);
						totalPricelabel.setText("총금액: " + 0 + " 원");
						if (cartDao.deleteMid(mid) == 1) {
							JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
						}
					}
				}
			}
		});

		JLabel removeLabel = new JLabel("장바구니 항목 삭제하기");
		MarketFont.getFont(removeLabel);
		JButton removeButton = new JButton();
		removeButton.add(removeLabel);
		buttonPanel.add(removeButton);

		/** 장바구니 항목 삭제 버튼 이벤트 처리 **/
		removeButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (cartDao.getSize(MainWindow.member.getMid()) == 0)
					JOptionPane.showMessageDialog(removeButton, "장바구니가 비어있습니다");
				else if (mSelectRow == -1)
					JOptionPane.showMessageDialog(removeButton, "삭제할 항목을 선택해주세요");
				else {
					String mid = MainWindow.member.getMid();
					String cid = list.get(mSelectRow).getCid();

					if (cartDao.delete(cid) == 1) {
						showList();
						mSelectRow = -1;
					}
				}
			}
		});

		cartTable.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				int row = cartTable.getSelectedRow();
				mSelectRow = row;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});

		JLabel updateQtyLabel = new JLabel("장바구니 항목 수량 줄이기");
		MarketFont.getFont(updateQtyLabel);
		JButton updateQtyButton = new JButton();
		updateQtyButton.add(updateQtyLabel);
		buttonPanel.add(updateQtyButton);

		updateQtyButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (cartDao.getSize(MainWindow.member.getMid()) == 0) {
					JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다");
				} else if (mSelectRow == -1) {
					JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요");
				} else {
					String cid = list.get(mSelectRow).getCid();
					String mid = MainWindow.member.getMid();
					int qty = list.get(mSelectRow).getQty();

					if (qty > 1) {
						if (cartDao.delete(cid, qty - 1) == 1) {
							JOptionPane.showMessageDialog(null, "해당 도서의 구매수량을 줄였습니다.");
						}
						mSelectRow = -1;
					} else {
						int select = JOptionPane.showConfirmDialog(null, "2개 이상인 경우에만 수정 가능합니다.\n해당 항목을 삭제하시겠습니까?");
						if (select == 0) {
							cartDao.delete(cid);
						}
					}
					showList();
				} // if
			}
		});

		JLabel updateQtyLabel2 = new JLabel("장바구니 항목 수량 늘리기");
		MarketFont.getFont(updateQtyLabel2);
		JButton updateQtyButton2 = new JButton();
		updateQtyButton2.add(updateQtyLabel2);
		buttonPanel.add(updateQtyButton2);

		updateQtyButton2.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (cartDao.getSize(MainWindow.member.getMid()) == 0) {
					JOptionPane.showMessageDialog(null, "장바구니가 비어있습니다");
				} else if (mSelectRow == -1) {
					JOptionPane.showMessageDialog(null, "수정할 항목을 선택해주세요");
				} else {
					String cid = list.get(mSelectRow).getCid();
					String mid = MainWindow.member.getMid();
					int qty = list.get(mSelectRow).getQty();

					if (cartDao.delete(cid, qty + 1) == 1) {
						JOptionPane.showMessageDialog(null, "해당 도서의 구매수량을 늘렸습니다.");
					}
					mSelectRow = -1;
					showList();
				} // if
			}
		});
	}

	public void showList() {
		list = cartDao.select(MainWindow.member.getMid());
		Object[][] content = new Object[list.size()][tableHeader.length];
		int totalPrice = 0;

		for (int i = 0; i < list.size(); i++) {
			CartVo item = list.get(i);

			content[i][0] = item.getRno();
			content[i][1] = item.getIsbn();
			content[i][2] = item.getTitle();
			content[i][3] = item.getSprice();
			content[i][4] = item.getQty();
			content[i][5] = item.getStotal_price();
			totalPrice += item.getTotal_price();
		}
		TableModel tableModel = new DefaultTableModel(content, tableHeader);
		totalPricelabel.setText("총금액: " + priceFormat(totalPrice) + " 원");
		cartTable.setModel(tableModel);
	}

	public String priceFormat(long price) {
		DecimalFormat df = new DecimalFormat("###,###");
		String sprice = df.format(price);

		return sprice;
	}
}
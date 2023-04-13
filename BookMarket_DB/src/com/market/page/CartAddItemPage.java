package com.market.page;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.main.MainWindow;
import com.market.vo.BookVo;
import com.market.vo.CartVo;
import com.sun.jdi.Value;

public class CartAddItemPage extends JPanel {
	// Field
	ImageIcon imageBook;
	int mSelectRow = 0;
	CartDao cartDao;
	BookDao bookDao;

	// Constructor
	public CartAddItemPage(JPanel panel, BookDao bookDao, CartDao cartDao) {
		ArrayList<BookVo> booklist = bookDao.select();
		this.cartDao = cartDao;
		this.bookDao = bookDao;

		// tablePanel : 도서 정보 출력
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(0, 0, 800, 480);
		tablePanel.setLayout(null);

		Object[] tableHeader = { "ISBN", "제목", "작가", "출판사", "카테고리", "가격" };
		Object[][] content = new Object[booklist.size()][tableHeader.length];
		DecimalFormat df = new DecimalFormat("###,###");

		panel.add(tablePanel);

		for (int i = 0; i < booklist.size(); i++) {
			BookVo bookitem = booklist.get(i);
			content[i][0] = bookitem.getIsbn();
			content[i][1] = bookitem.getTitle();
			content[i][2] = bookitem.getAuthor();
			content[i][3] = bookitem.getCompany();
			content[i][4] = bookitem.getPart();
			content[i][5] = df.format(bookitem.getPrice());
			System.out.println(bookitem.getTitle());
		}

		JTable bookTable = new JTable(content, tableHeader);
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension(800, 480));
		jScrollPane.setViewportView(bookTable);
		tablePanel.add(jScrollPane);

		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(820, 0, 400, 480);
		infoPanel.setLayout(null);

		Choice qtyChoice = new Choice();
		for (int i = 0; i < 10; i++) {
			qtyChoice.add(Integer.toString(i + 1));
		}

		JButton addBtn = new JButton("장바구니에 담기");

//		imageBook = new ImageIcon();
//		imageBook.setImage(imageBook.getImage().getScaledInstance(400, 240, Image.SCALE_DEFAULT));
//		JLabel label = new JLabel(imageBook);

		/** 마우스 이벤트 처리 **/
		bookTable.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				int row = bookTable.getSelectedRow();
				mSelectRow = row;
				String isbn = bookTable.getValueAt(row, 0).toString();
				StringBuffer sb = new StringBuffer(100);
				sb.append("./images/");
				sb.append(isbn);
				sb.append(".jpg");

				imageBook.setImage(imageBook.getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT));
				JLabel label = new JLabel(imageBook);
				infoPanel.add(label);
				infoPanel.add(qtyChoice);
				infoPanel.add(addBtn);

				infoPanel.removeAll();
				infoPanel.revalidate();
				infoPanel.repaint();
				panel.add(infoPanel);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});

		/** 장바구니 담기 **/
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//
//				CartVo cartVo = new CartVo();
//				cartVo.setIsbn(booklist.get(mSelectRow).getIsbn());
//				cartVo.setMid(MainWindow.member.getMid());
//
//				int select = JOptionPane.showConfirmDialog(addButton, "장바구니에 추가하시겠습니까?");
//				if (select == 0) { // yes 선택시
//					int idx = mSelectRow; // JTable에 출력된 index
//
//					if (cartDao.insertCheck(cartVo) == 0) {
//						if (cartDao.insert(cartVo) == 1) {
//							JOptionPane.showMessageDialog(addButton, "장바구니에 추가되었습니다.");
//						}
//					} else {
//						select = JOptionPane.showConfirmDialog(null, "이미 장바구니 안에 담겨있는 도서입니다.\n수량을 추가하시겠습니까?");
//						if (select == 0) {
//							if (cartDao.updateQty(cartVo) == 1) {
//								JOptionPane.showMessageDialog(null, "수량이 추가되었습니다.");
//							} else {
//								JOptionPane.showMessageDialog(null, "수량 추가 실패");
//							}
//						}
//					}
//
//				}
			}
		});
	}
}
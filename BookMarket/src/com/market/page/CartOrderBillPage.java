package com.market.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.MemberDao;
import com.market.dao.OrderMemberDao;
import com.market.main.GuestWindow;
import com.market.main.MainWindow;
import com.market.vo.CartVo;
import com.market.vo.MemberVo;
import com.market.vo.OrderMemberVo;

public class CartOrderBillPage extends JPanel {
	JPanel shippingPanel;
	JPanel radioPanel;
	JPanel mPagePanel;
	MainWindow main;
	CartDao cartDao;
	OrderMemberVo orderMember;
	String mid;

	public CartOrderBillPage(JPanel panel, OrderMemberVo orderMember, CartDao cartDao, MainWindow main) {
		this.main = main;
		this.mPagePanel = panel;
		this.orderMember = orderMember;
		this.cartDao = cartDao;

		setLayout(null);

		mid = main.member.getMid();

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		shippingPanel = new JPanel();
		shippingPanel.setBounds(0, 0, 700, 500);
		shippingPanel.setLayout(null);
		panel.add(shippingPanel);
		printBillInfo();
	}

	public void printBillInfo() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);

		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 30);
		JLabel label01 = new JLabel("--------------------- 배송 받을 고객 정보 -----------------------");
		MarketFont.getFont(label01);
		panel01.add(label01);
		shippingPanel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 30, 500, 30);
		JLabel label02 = new JLabel(
				"고객명 : " + orderMember.getName() + "             연락처 :      " + orderMember.getPhone());
		label02.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label02);
		panel02.add(label02);
		shippingPanel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 60, 500, 30);
		JLabel label03 = new JLabel(
				"배송지 : " + orderMember.getAddr() + "                 발송일 :       " + orderMember.getCdate());
		label03.setHorizontalAlignment(JLabel.LEFT);
		MarketFont.getFont(label03);
		panel03.add(label03);
		shippingPanel.add(panel03);

		JPanel printPanel = new JPanel();
		printPanel.setBounds(0, 100, 500, 300);
		printCart(printPanel);
		shippingPanel.add(printPanel);
	}

	public void printCart(JPanel panel) {
		JPanel panel01 = new JPanel();
		panel01.setBounds(0, 0, 500, 5);
		JLabel label01 = new JLabel("      장바구니 상품 목록 :");
		MarketFont.getFont(label01);
		panel01.add(label01);
		panel.add(panel01);

		JPanel panel02 = new JPanel();
		panel02.setBounds(0, 20, 500, 5);
		JLabel label02 = new JLabel("---------------------------------------------------------------");
		MarketFont.getFont(label02);
		panel02.add(label02);
		panel.add(panel02);

		JPanel panel03 = new JPanel();
		panel03.setBounds(0, 25, 500, 5);
		JLabel label03 = new JLabel("                        도서 ID           |        수량           |      합계        ");
		MarketFont.getFont(label03);
		panel03.add(label03);
		panel.add(panel03);

		JPanel panel04 = new JPanel();
		panel04.setBounds(0, 30, 500, 5);

		JPanel panel05 = new JPanel(new GridLayout(cartDao.getSize(mid), 1));
		int sum = 0;
		ArrayList<CartVo> cartList = cartDao.select(mid);
		for (int i = 0; i < cartList.size(); i++) {
			CartVo item = cartList.get(i);

			panel05.setBounds(50, 25 + (i * 5), 500, 5);
			panel05.setBackground(Color.GRAY);

			JLabel label05 = new JLabel("               " + item.getIsbn() + "                    " + item.getQty()
					+ "                    " + item.getStotal_price());
			MarketFont.getFont(label05);
			panel05.add(label05);
			panel.add(panel05);
			sum += item.getQty() * item.getPrice();
		}

		JPanel panel06 = new JPanel();
		panel06.setBounds(0, 35 + (cartDao.getSize(mid) * 5), 500, 5);
		JLabel label06 = new JLabel("--------------------------------------");
		MarketFont.getFont(label06);
		panel06.add(label06);
		panel.add(panel06);

		JPanel panel07 = new JPanel();
		panel07.setBounds(0, 40 + (cartDao.getSize(mid) * 5), 500, 5);
		JLabel label07 = new JLabel("      주문 총금액 : " + priceFormat(sum) + " 원");
		MarketFont.getFont(label07);
		panel07.add(label07);
		panel.add(panel07);

		/** 주문확정 버튼 **/
		JPanel panel08 = new JPanel();
		panel08.setBounds(0, 40 + (cartDao.getSize(mid) * 5), 500, 5);
		JButton btnOrderFinish = new JButton("주문 확정");
		MarketFont.getFont(btnOrderFinish);
		panel08.add(btnOrderFinish);
		panel.add(panel08);

		btnOrderFinish.addActionListener(e -> {
			int select = JOptionPane.showConfirmDialog(null, "주문을 확정 하시겠습니까?");
			if (select == 0) {
				// 장바구니 비우기
				cartDao.deleteMid(mid);
				JOptionPane.showMessageDialog(null, "주문이 완료되었습니다!!");

				// MainWindow 감추기
				main.setVisible(false);
				main.dispose(); // MainWindow 클래스의 모든 객체를 OS에 반환하고 close

				// GuestWindow 열기
				new GuestWindow("온라인 서점", 0, 0, 1000, 750);
			}
		});
	}

	public String priceFormat(long price) {
		DecimalFormat df = new DecimalFormat("###,###");
		String sprice = df.format(price);

		return sprice;
	}

}

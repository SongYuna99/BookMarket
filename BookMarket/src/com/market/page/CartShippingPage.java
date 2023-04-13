package com.market.page;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.dao.CartDao;
import com.market.dao.DBConn;
import com.market.dao.MemberDao;
import com.market.dao.OrderMemberDao;
import com.market.main.MainWindow;
import com.market.vo.MemberVo;
import com.market.vo.OrderMemberVo;

public class CartShippingPage extends JPanel {
	JPanel shippingPanel;
	JPanel radioPanel;
	MainWindow main;
	MemberDao memberDao;
	BookDao bookDao;
	CartDao cartDao;
	OrderMemberDao orderMemberDao;

	public CartShippingPage(JPanel panel, Map daoList, MainWindow main) {
		this.main = main;
		memberDao = (MemberDao) daoList.get("memberDao");
		bookDao = (BookDao) daoList.get("bookDao");
		cartDao = (CartDao) daoList.get("cartDao");
		orderMemberDao = new OrderMemberDao();

		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		radioPanel = new JPanel();
		radioPanel.setBounds(300, 0, 700, 50);
		radioPanel.setLayout(new FlowLayout());
		add(radioPanel);
		JLabel radioLabel = new JLabel("배송받을 분의 고객정보와 동일합니까?");
		MarketFont.getFont(radioLabel);
		ButtonGroup group = new ButtonGroup();
		JRadioButton radioOk = new JRadioButton("예");
		MarketFont.getFont(radioOk);
		JRadioButton radioNo = new JRadioButton("아니오");
		MarketFont.getFont(radioNo);
		group.add(radioOk);
		group.add(radioNo);
		radioPanel.add(radioLabel);
		radioPanel.add(radioOk);
		radioPanel.add(radioNo);

		shippingPanel = new JPanel();
		shippingPanel.setBounds(200, 50, 850, 500);
		shippingPanel.setLayout(null);
		add(shippingPanel);

		radioOk.setSelected(true);
		radioNo.setSelected(false);
		UserShippingInfo("yes");

		radioOk.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (radioOk.isSelected()) {
					shippingPanel.removeAll();
					UserShippingInfo("yes");
					shippingPanel.revalidate();
					shippingPanel.repaint();
					radioNo.setSelected(false);
				}
			}
		});

		radioNo.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (radioNo.isSelected()) {
					shippingPanel.removeAll();
					UserShippingInfo("no");
					shippingPanel.revalidate();
					shippingPanel.repaint();
					radioOk.setSelected(false);
				}
			}
		});
	}

	public void UserShippingInfo(String select) {
		OrderMemberVo orderMember = orderMemberDao.select(main.member.getMid());

		JPanel namePanel = new JPanel();
		namePanel.setBounds(0, 100, 700, 50);
		JLabel nameLabel = new JLabel("고객명 : ");
		JTextField nameLabel2 = new JTextField(15);

		JPanel phonePanel = new JPanel();
		phonePanel.setBounds(0, 150, 700, 50);
		JLabel phoneLabel = new JLabel("연락처 : ");
		JTextField phoneLabel2 = new JTextField(15);

		JPanel addressPanel = new JPanel();
		addressPanel.setBounds(0, 200, 700, 50);
		JLabel addressLabel = new JLabel("배송지 : ");
		JTextField addressLabel2 = new JTextField(15);

		if (select == "yes") {
			namePanel.add(nameLabel);
			MarketFont.getFont(nameLabel);
			MarketFont.getFont(nameLabel2);
			nameLabel2.setBackground(Color.LIGHT_GRAY);
			nameLabel2.setText(orderMember.getName());
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.add(phoneLabel);
			MarketFont.getFont(phoneLabel);
			MarketFont.getFont(phoneLabel2);
			phoneLabel2.setBackground(Color.LIGHT_GRAY);
			phoneLabel2.setText(orderMember.getPhone());
			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);

			addressPanel.add(addressLabel);
			MarketFont.getFont(addressLabel);
			MarketFont.getFont(addressLabel2);
			addressLabel2.setBackground(Color.LIGHT_GRAY);
			addressLabel2.setText(orderMember.getAddr());
			addressPanel.add(addressLabel2);
			shippingPanel.add(addressPanel);
		} else {
			namePanel.add(nameLabel);
			MarketFont.getFont(nameLabel);
			MarketFont.getFont(nameLabel2);
			namePanel.add(nameLabel2);
			shippingPanel.add(namePanel);

			phonePanel.setBounds(0, 150, 700, 50);
			phonePanel.add(phoneLabel);
			MarketFont.getFont(phoneLabel);
			MarketFont.getFont(phoneLabel2);

			phonePanel.add(phoneLabel2);
			shippingPanel.add(phonePanel);

			addressPanel.add(addressLabel);
			MarketFont.getFont(addressLabel);
			MarketFont.getFont(addressLabel2);
			addressPanel.add(addressLabel2);
			shippingPanel.add(addressPanel);
		}
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 300, 700, 100);

		JLabel buttonLabel = new JLabel("주문완료");
		MarketFont.getFont(buttonLabel);
		JButton orderButton = new JButton();
		orderButton.add(buttonLabel);
		buttonPanel.add(orderButton);
		shippingPanel.add(buttonPanel);

		orderButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				orderMember.setName(nameLabel2.getText());
				orderMember.setAddr(addressLabel2.getText());
				orderMember.setPhone(phoneLabel2.getText());

				radioPanel.removeAll();
				radioPanel.revalidate();
				radioPanel.repaint();
				shippingPanel.removeAll();
				shippingPanel.add(new CartOrderBillPage(shippingPanel, orderMember, cartDao, main));

				shippingPanel.revalidate();
				shippingPanel.repaint();

			}
		});
	}

}
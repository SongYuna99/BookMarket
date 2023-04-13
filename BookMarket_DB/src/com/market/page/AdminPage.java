package com.market.page;

import javax.swing.*;

import com.market.commons.MarketFont;
import com.market.dao.BookDao;
import com.market.vo.BookVo;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.io.FileWriter;

public class AdminPage extends JPanel {
	BookDao bookDao;

	public AdminPage(JPanel panel, BookDao bookDao) {
		this.bookDao = bookDao;
		setLayout(null);

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmmss");
		String strDate = formatter.format(date);

//		JPanel idPanel = new JPanel();
//		idPanel.setBounds(100, 0, 700, 50);
//		JLabel idLabel = new JLabel("도서ID : ");
//		MarketFont.getFont(idLabel);
//		JLabel idTextField = new JLabel();
//		MarketFont.getFont(idTextField);
//		idTextField.setPreferredSize(new Dimension(290, 50));
//		idTextField.setText("ISBN" + strDate);
//		idPanel.add(idLabel);
//		idPanel.add(idTextField);
//		add(idPanel);

		JPanel idPanel = new JPanel();
		idPanel.setBounds(100, 0, 700, 50);
		JLabel idLabel = new JLabel("도서 등록 화면 [관리자]");
		idLabel.setFont(new Font("맑은고딕", Font.BOLD, 20));
		idPanel.add(idLabel);
		add(idPanel);

		JPanel namePanel = new JPanel();
		namePanel.setBounds(100, 50, 700, 50);
		JLabel nameLabel = new JLabel("도서명 : ");
		MarketFont.getFont(nameLabel);
		JTextField nameTextField = new JTextField(20);
		MarketFont.getFont(nameTextField);
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		add(namePanel);

		JPanel pricePanel = new JPanel();
		pricePanel.setBounds(100, 100, 700, 50);
		JLabel priceLabel = new JLabel("가  격 : ");
		MarketFont.getFont(priceLabel);
		JTextField priceTextField = new JTextField(20);
		MarketFont.getFont(priceTextField);
		pricePanel.add(priceLabel);
		pricePanel.add(priceTextField);
		add(pricePanel);

		JPanel authorPanel = new JPanel();
		authorPanel.setBounds(100, 150, 700, 50);
		JLabel authorLabel = new JLabel("저  자 : ");
		MarketFont.getFont(authorLabel);
		JTextField authorTextField = new JTextField(20);
		MarketFont.getFont(authorTextField);
		authorPanel.add(authorLabel);
		authorPanel.add(authorTextField);
		add(authorPanel);

		JPanel descPanel = new JPanel();
		descPanel.setBounds(100, 200, 700, 50);
		JLabel descLabel = new JLabel("설  명 : ");
		MarketFont.getFont(descLabel);
		JTextField descTextField = new JTextField(20);
		MarketFont.getFont(descTextField);
		descPanel.add(descLabel);
		descPanel.add(descTextField);
		add(descPanel);

		JPanel categoryPanel = new JPanel();
		categoryPanel.setBounds(100, 250, 700, 50);
		JLabel categoryLabel = new JLabel("분  야 : ");
		MarketFont.getFont(categoryLabel);
		JTextField categoryTextField = new JTextField(20);
		MarketFont.getFont(categoryTextField);
		categoryPanel.add(categoryLabel);
		categoryPanel.add(categoryTextField);
		add(categoryPanel);

		JPanel datePanel = new JPanel();
		datePanel.setBounds(100, 300, 700, 50);
		JLabel dateLabel = new JLabel("출판일 : ");
		MarketFont.getFont(dateLabel);
		JTextField dateTextField = new JTextField(20);
		MarketFont.getFont(dateTextField);
		datePanel.add(dateLabel);
		datePanel.add(dateTextField);
		add(datePanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(100, 350, 700, 50);
		add(buttonPanel);
		JLabel okLabel = new JLabel("  등 록  ");
		MarketFont.getFont(okLabel);
		JButton okButton = new JButton();
		okButton.add(okLabel);
		buttonPanel.add(okButton);

		okButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				BookVo book = new BookVo();
				book.setTitle(nameTextField.getText());
				book.setPrice(Integer.parseInt(priceTextField.getText()));
				book.setAuthor(authorTextField.getText());
				book.setIntro(descTextField.getText());
				book.setPart(categoryTextField.getText());
				book.setBdate(dateTextField.getText());

				if (bookDao.insert(book) == 1) {
					JOptionPane.showMessageDialog(null, "도서가 등록되었습니다.");

					nameTextField.setText("");
					priceTextField.setText("");
					authorTextField.setText("");
					descTextField.setText("");
					categoryTextField.setText("");
					dateTextField.setText("");
				}
			}
		});

		JLabel noLabel = new JLabel("  취 소  ");
		MarketFont.getFont(noLabel);
		JButton noButton = new JButton();
		noButton.add(noLabel);
		buttonPanel.add(noButton);

		noButton.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				nameTextField.setText("");
				priceTextField.setText("");
				authorTextField.setText("");
				descTextField.setText("");
				categoryTextField.setText("");
				dateTextField.setText("");
			}
		});
	}
}
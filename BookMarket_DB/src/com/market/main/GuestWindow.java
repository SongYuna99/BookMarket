package com.market.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.market.commons.MarketFont;
import com.market.dao.MemberDao;
import com.market.vo.MemberVo;

public class GuestWindow extends JFrame implements ActionListener {
	MemberDao memberDao;
	JButton logoBtn, tfBtn, cartBtn, infoBtn, loginBtn, insertBtn;
	String[] categoriList = { "소설", "동화", "만화책", "IT", "문제집", "기타" };
	JButton[] categoriBtnList = new JButton[categoriList.length];
	JTextField jtf, idField;
	JPasswordField pwField;
	Boolean loginFlag;
	Boolean isAdmin;
	JPanel tablePanel, infoPanel, bottomPanel;

	public GuestWindow(String title, int x, int y, int width, int height) {
//		memberDao = new MemberDao();
		// memberDao 로그인, 관리자 여부 확인
		loginFlag = true;
		isAdmin = true;

		pwField = new JPasswordField();
		loginBtn = new JButton("로그인");

		initContainer(title, x, y, width, height);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initContainer(String title, int x, int y, int width, int height) {
		setTitle(title);
		setBounds(x, y, width, height);
		setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - 1000) / 2, (screenSize.height - 750) / 2);

		// topPanel : 탑패널
		JPanel topPanel = new JPanel();
		topPanel.setBounds(0, 0, 1280, 100);
		topPanel.setBackground(Color.WHITE);
		topPanel.setLayout(null);

		// topPanel : 로고버튼
		logoBtn = new JButton("로고 이미지");
		logoBtn.setBounds(10, 10, 300, 80);
		logoBtn.setBackground(new Color(245, 245, 220));
		topPanel.add(logoBtn);

		// topPanel : 검색텍스트필드
		jtf = new JTextField();
		jtf.setBounds(380, 50, 500, 40);
		jtf.setBackground(new Color(245, 245, 220));
		jtf.setToolTipText("검색어 입력");
		topPanel.add(jtf);

		// topPanel : 검색버튼
		tfBtn = new JButton("검색");
		tfBtn.setBounds(890, 50, 40, 40);
		tfBtn.setBackground(new Color(245, 245, 220));
		topPanel.add(tfBtn);

		// topPanel : 장바구니 버튼
		cartBtn = new JButton("장바구니");
		cartBtn.setBounds(1100, 20, 70, 70);
		cartBtn.setBackground(new Color(245, 245, 220));
		topPanel.add(cartBtn);

		// topPanel : 회원정보 버튼
		infoBtn = new JButton("회원정보");
		infoBtn.setBounds(1180, 20, 70, 70);
		infoBtn.setBackground(new Color(245, 245, 220));
		topPanel.add(infoBtn);

		add(topPanel);

		// categoriPanel : 카테고리 패널
		JPanel categoriPanel = new JPanel();
		categoriPanel.setBounds(20, 110, 800, 50);
		categoriPanel.setBackground(Color.WHITE);
		categoriPanel.setLayout(null);

		// categoriPanel : 카테고리 버튼
		for (int i = 0; i < categoriList.length; i++) {
			categoriBtnList[i] = new JButton(categoriList[i]);
			categoriBtnList[i].setBounds(20 + i * 120, 0, 100, 50);
			categoriBtnList[i].setBackground(new Color(245, 245, 220));
			categoriPanel.add(categoriBtnList[i]);
			categoriBtnList[i].addActionListener(this);
		}

		add(categoriPanel);

		// loginFlag : 로그인 했으면 loginPanel 없애기
		if (!loginFlag) {
			// loginPanel : 로그인 패널
			JPanel loginPanel = new JPanel();
			loginPanel.setBounds(880, 110, 350, 170);
			loginPanel.setBackground(Color.WHITE);
			loginPanel.setLayout(null);

			// loginPanel : 로그인 라벨
			JLabel loginLabel = new JLabel("                         로그인        ");
			loginLabel.setBounds(5, 0, 340, 40);
			loginLabel.setFont(new Font("맑은고딕", Font.BOLD, 20));
			loginPanel.add(loginLabel);

			// loginPanel : 아이디 라벨, 아이디 필드
			JLabel idLabel = new JLabel("아이디");
			idLabel.setBounds(20, 60, 60, 30);
			loginPanel.add(idLabel);

			idField = new JTextField();
			idField.setBounds(90, 60, 150, 30);
			idField.setBackground(new Color(245, 245, 220));
			loginPanel.add(idField);

			// loginPanel : 비밀번호 라벨, 비밀번호 필드
			JLabel pwLabel = new JLabel("비밀번호");
			pwLabel.setBounds(20, 110, 60, 30);
			loginPanel.add(pwLabel);

			pwField.setBounds(90, 110, 150, 30);
			pwField.setBackground(new Color(245, 245, 220));
			loginPanel.add(pwField);

			// loginPanel : 로그인 버튼
			loginBtn.setBounds(260, 65, 70, 70);
			loginBtn.setBackground(new Color(245, 245, 220));
			loginPanel.add(loginBtn);

			add(loginPanel);
		}
		if (isAdmin) {
			// insertBtn : 도서 등록 버튼 (관리자 전용)
			insertBtn = new JButton("    도서 등록");
			insertBtn.setBounds(1050, 110, 200, 50);
			insertBtn.setBackground(new Color(245, 245, 220));
			insertBtn.addActionListener(this);
			add(insertBtn);
		}

		tablePanel = new JPanel();
		tablePanel.setBounds(20, 180, 800, 480);
		tablePanel.setBackground(new Color(245, 245, 220));
		add(tablePanel);

		infoPanel = new JPanel();
		infoPanel.setBounds(850, 180, 400, 480);
		infoPanel.setBackground(new Color(245, 245, 220));
		add(infoPanel);

		// 장바구니, 회원정보, 로그인, 도서등록
		bottomPanel = new JPanel();
		bottomPanel.setBounds(20, 180, 1220, 480);
		bottomPanel.setBackground(new Color(245, 245, 220));
		add(bottomPanel);

		logoBtn.addActionListener(this);
		jtf.addActionListener(this);
		tfBtn.addActionListener(this);
		cartBtn.addActionListener(this);
		infoBtn.addActionListener(this);
		pwField.addActionListener(this);
		loginBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		// 로고 클릭 -> 메인화면
		if (obj == logoBtn) {
			new GuestWindow("온라인서점", 0, 0, 1280, 720);
		}
		// 검색필드 -> 검색화면
		else if (obj == jtf) {

		}
		// 검색버튼 -> 검색화면
		else if (obj == tfBtn) {

		}
		// 장바구니 버튼 -> 1. 로그인화면 2. 장바구니화면
		else if (obj == cartBtn) {

		}
		// 회원정보 버튼 -> 1. 로그인화면 2. 회원정보화면
		else if (obj == infoBtn) {

		}
		// 비밀번호 필드 -> 메인화면
		else if (obj == pwField) {

		}
		// 로그인 버튼 -> 메인화면
		else if (obj == loginBtn) {

		}
		// 도서 등록 버튼 -> 등록 화면
		else if (obj == insertBtn) {

		}

	}
}

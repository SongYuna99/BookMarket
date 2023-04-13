package com.market.page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.market.commons.MarketFont;
import com.market.dao.MemberDao;
import com.market.vo.MemberVo;

public class GuestInfoPage extends JPanel {

	public GuestInfoPage(JPanel panel, MemberVo member, MemberDao memberDao) {
		member = memberDao.select(member.getMid());
		
		setLayout(new GridLayout(6,1));

		Rectangle rect = panel.getBounds();
		setPreferredSize(rect.getSize());
		
//		JPanel namePanel = new JPanel();		
		
		//아이디, 패스워드, 성명, 주소, 폰번호, 가입일자
		String[] nameList = {"아이디","패스워드","성명","주소","폰번호","가입일자"};
		String[] memberValue = new String[nameList.length];
		memberValue[0] = member.getMid();
		memberValue[1] = member.getPass();
		memberValue[2] = member.getName();
		memberValue[3] = member.getAddr();
		memberValue[4] = member.getPhone();
		memberValue[5] = member.getMdate();
		
		for(int i=0;i<nameList.length;i++) {
			JPanel outPanel = new JPanel();
			JLabel nameLabel = new JLabel(nameList[i]);
			MarketFont.getFont(nameLabel);
			nameLabel.setBackground(Color.BLUE);
	
			JLabel nameField = new JLabel();
			nameField.setText(memberValue[i]);
			MarketFont.getFont(nameField);
	
			outPanel.add(nameLabel);
			outPanel.add(nameField);
//			namePanel.add(outPanel);
			add(outPanel);
		}
//		namePanel.setBackground(Color.cyan);
//		namePanel.setBounds(0, 10, 1000, 100);
	}

}











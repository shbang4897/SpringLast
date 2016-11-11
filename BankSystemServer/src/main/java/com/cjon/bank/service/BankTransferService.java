package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankTransferService implements BankService {

	@Override
	public void excute(Model model) {
		
		//BankWithdrawService ws = new BankWithdrawService();
		//BankDepositService ds = new BankDepositService();
		
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		String sendMemberId = (String) model.asMap().get("sendMemberId");
		String receiveMemberId = (String) model.asMap().get("receiveMemberId");
		String transferBalance = (String) model.asMap().get("transferBalance");
		Connection con ;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao = new BankDAO(con);
			
			boolean resultWith =  dao.withdraw(sendMemberId,transferBalance);
			boolean resultDepo =  dao.deposit(receiveMemberId, transferBalance);
			boolean resultTran = false ;
			
			if(resultWith == true && resultDepo == true){
				con.commit();
				resultTran = true;
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT",resultTran);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankWithdrawService implements BankService {

	@Override
	public void excute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		String memberId = (String) model.asMap().get("memberId");
		String memberBalance = (String) model.asMap().get("memberBalance");
		Connection con ;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao = new BankDAO(con);
			
			boolean result =  dao.withdraw(memberId,memberBalance);
			
			if(result == true){
				con.commit();
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT",result);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

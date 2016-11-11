package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankSelectMemberService implements BankService {

	@Override
	public void excute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		String memberId = (String) model.asMap().get("memberId");
		Connection con ;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao = new BankDAO(con);
			
			ArrayList<BankDTO> result =  dao.select(memberId);
			
			if(result != null){
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

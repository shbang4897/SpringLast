package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.cjon.bank.com.dto.AccountDTO;
import com.cjon.bank.dto.BankDTO;

public class BankDAO {
	private Connection con;

	public BankDAO(Connection con) {
		this.con = con;
	}

	public ArrayList<BankDTO> selectAll() {

		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		try {

			String sql = "select * from bank_member_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();

				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public boolean deposit(String memberId, String memberBalance) {

		PreparedStatement pstmt = null;
		boolean result = false;
		System.out.println("id in dao " +memberId+ " id in balance "+memberBalance);
		
		try {
			int memberBalanceInt = Integer.parseInt(memberBalance);

			String sql = "update bank_member_tb set member_balance=member_balance+? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberBalanceInt);
			pstmt.setString(2, memberId);
			

			int count = pstmt.executeUpdate();
			System.out.println(count); 
			
			if(count==1){
				
				String sql1 = "insert into bank_statement_tb values (HISTORY_SQ, ?,'deposit',?)";
				

				PreparedStatement pstmt2 = con.prepareStatement(sql1);
				pstmt2.setString(1, memberId);
				pstmt2.setInt(2, memberBalanceInt);
				int count2  = pstmt2.executeUpdate();
				System.out.println(count2); 
				if (count2 == 0) {

					System.out.println("입출금 기록 불가");
					result=false;

				} else {
					result=true;
				}
				try {
					pstmt2.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else {
				result = false;
			}
		
			
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("last result in DAO : "+result);
		
		return  result;
	}

	public boolean withdraw(String memberId, String memberBalance) {
		PreparedStatement pstmt = null;
		boolean result = false;

		ResultSet rs = null;
		System.out.println("id in dao" +memberId+ " id in balance "+memberBalance);
		
		try {
			int memberBalanceInt = Integer.parseInt(memberBalance);

			String sql = "update bank_member_tb set member_balance=member_balance-? where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, memberBalanceInt);
			pstmt.setString(2, memberId);
			

			int count = pstmt.executeUpdate();

			System.out.println(count); 
			
			if(count>0){
				String sql1 = "select member_balance from bank_member_tb where member_id= ?";
				int balChk = 0;
				

				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, memberId);
				rs = pstmt1.executeQuery();
				System.out.println(balChk); 
				if (rs.next()) {
					balChk = rs.getInt("member_balance");
				}
				
				
				if (balChk< 0) {

					System.out.println("잔액 부족으로 인하여 인출 불가");
					result=false;

				} else {
					
					String sql2 = "insert into bank_statement_tb values (HISTORY_SQ, ?,'withdraw',?)";
					

					PreparedStatement pstmt2 = con.prepareStatement(sql2);
					pstmt2.setString(1, memberId);
					pstmt2.setInt(2, memberBalanceInt);
					int count2  = pstmt2.executeUpdate();
					System.out.println(count2); 
					if (count2 == 0) {

						System.out.println("입출금 기록 불가");
						result=false;

					} else {
						result=true;
					}
					try {
						pstmt2.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					rs.close();
					pstmt1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}else {
				result = false;
			}
		
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("last result in DAO : "+result);
		
		return  result;
	}

	public ArrayList<BankDTO> select(String memberId) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		try {

			String sql = "select * from bank_member_tb where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BankDTO dto = new BankDTO();

				dto.setMemberId(rs.getString("member_id"));
				dto.setMemberName(rs.getString("member_name"));
				dto.setMemberAccount(rs.getString("member_account"));
				dto.setMemberBalance(rs.getInt("member_balance"));
				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<AccountDTO> selectAccount(String memberId) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<AccountDTO> list = new ArrayList<AccountDTO>();
		try {

			String sql = "select * from bank_statement_tb where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AccountDTO dto = new AccountDTO();

				dto.setMemberId(rs.getString("member_id"));
				dto.setKind(rs.getString("kind"));
				dto.setMoney(rs.getInt("money"));
				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}

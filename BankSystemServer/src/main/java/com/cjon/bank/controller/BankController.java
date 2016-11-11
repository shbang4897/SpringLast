package com.cjon.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.com.dto.AccountDTO;
import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSelectAccountService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankSelectMemberService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithdrawService;

@Controller
public class BankController {

	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private BankService service;

	@RequestMapping(value = "/selectAllMember")
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 입력
		String callback = request.getParameter("callback");

		// 로직

		service = new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.excute(model);

		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		ObjectMapper om = new ObjectMapper();
		try {
			String json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/selectMember")
	public void selectMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 입력
		String callback = request.getParameter("callback");
		String id = request.getParameter("id");

		// 로직

		service = new BankSelectMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", id);
		service.excute(model);

		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		ObjectMapper om = new ObjectMapper();
		try {
			String json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	@RequestMapping(value = "/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String callback = request.getParameter("callback");
		String id = request.getParameter("memberId");
		String balance = request.getParameter("memberBalance");
		
		service = new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", id);
		model.addAttribute("memberBalance", balance);
		
		service.excute(model);
		
		
		boolean result = (Boolean) model.asMap().get("RESULT");
		
		System.out.println("last result in server : "+result);
		
		try {
			response.setContentType("text/plain; charset=utf8");
			PrintWriter out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/withdraw")
	public void withdraw(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String callback = request.getParameter("callback");
		String id = request.getParameter("memberId");
		String balance = request.getParameter("memberBalance");
		
		service = new BankWithdrawService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", id);
		model.addAttribute("memberBalance", balance);
		
		service.excute(model);
		
		
		boolean result = (Boolean) model.asMap().get("RESULT");
		
		System.out.println("last result in server : "+result);
		
		try {
			response.setContentType("text/plain; charset=utf8");
			PrintWriter out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/transfer")
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		String callback = request.getParameter("callback");
		String sendMemberId = request.getParameter("sendMemberId");
		String receiveMemberId = request.getParameter("receiveMemberId");
		String transferBalance = request.getParameter("transferBalance");
		
		service = new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("sendMemberId", sendMemberId);
		model.addAttribute("receiveMemberId", receiveMemberId);
		model.addAttribute("transferBalance", transferBalance);
		
		service.excute(model);
		
		
		boolean result = (Boolean) model.asMap().get("RESULT");
		
		System.out.println("last result in server : "+result);
		
		try {
			response.setContentType("text/plain; charset=utf8");
			PrintWriter out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/checkMember")
	public void checkMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 입력
		String callback = request.getParameter("callback");
		String id = request.getParameter("id");

		// 로직

		service = new BankSelectAccountService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", id);
		service.excute(model);

		ArrayList<AccountDTO> list = (ArrayList<AccountDTO>) model.asMap().get("RESULT");

		ObjectMapper om = new ObjectMapper();
		try {
			String json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

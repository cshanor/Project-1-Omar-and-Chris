package com.revature.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.service.ReimbService;
import com.revature.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/reimb")
public class ReimbursementServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ReimbursementServlet.class);
	private static ReimbService reimbService = new ReimbService();
	private static UserService userService = new UserService();
			
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		Reimbursement reimb = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {

			
			reimb = mapper.readValue(req.getInputStream(), Reimbursement.class);
			
			
		} catch (MismatchedInputException mie) {
			mie.printStackTrace();
			log.error(mie.getMessage());
			resp.setStatus(400);
			return;
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
			return;
		}
		
		reimb = reimbService.addReimb(reimb);
		
		try {
			String reimbJson = mapper.writeValueAsString(reimb);
			PrintWriter out = resp.getWriter();
			out.write(reimbJson);
		} catch (Exception e) {
			log.error(e.getMessage());
			resp.setStatus(500);
		}
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		
		
	}	
}
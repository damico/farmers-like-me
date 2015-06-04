package com.scicrop.be.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.traitify.Traitify;
import com.traitify.models.Assessment;
import com.traitify.models.AssessmentData;
import com.traitify.models.AssessmentPersonalityTrait;
import com.traitify.models.AssessmentPersonalityType;
import com.traitify.models.Deck;

/**
 * Servlet implementation class TraitifyConnector
 */
@WebServlet("/TraitifyConnector")
public class TraitifyConnector extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TraitifyConnector() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String action = null;

		action = request.getParameter("action");
		

		String data = null;

		data = request.getParameter("data");
		
		
		System.out.println("action: ====> "+action);
		System.out.println("data: ====> "+data);

		PrintWriter out = response.getWriter();

		Traitify.apiKey = "1qpki7qm7a55ule51amsjcm6cm";
		Traitify.overrideApiBase("https://api-sandbox.traitify.com");

		Assessment assessment = null;

		if(action.equals("assessment")){

			List<Deck> decks = Deck.list();
			Deck deck = null;
			for(Deck _deck:decks){
				System.out.println(">> "+_deck.getName());
				if(_deck.getName().equals("Careers")){
					deck = _deck;
				}
			}

			assessment = Assessment.create(deck.getId());
			System.out.println(assessment.getId());


			out.print(assessment.getId());

		}else if(action.equals("results")){
			
			response.setContentType("application/json");
			
			List<AssessmentData> results_type = new ArrayList<AssessmentData>();
			//AssessmentData trait = new Assessment;
			results_type.add(AssessmentData.TRAITS);
			results_type.add(AssessmentData.TYPES);
			
			//String[] resulst_type = {"traits"};
			assessment = Assessment.results(data, results_type);
			
			List<AssessmentPersonalityTrait> personality_traitsLst = assessment.getPersonality_traits();
			List<AssessmentPersonalityType> personality_typesLst = assessment.getPersonality_types();
			
			StringBuffer sb = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			
			sb.append("[");
			sb2.append("[");

			
			for(int i=0; i < personality_traitsLst.size(); i++){
				
				
				if(personality_traitsLst.get(i).getScore() > 0){
					//sb.append("["+personality_traitsLst.get(i).getScore()+"],");
					sb.append("[\""+personality_traitsLst.get(i).getPersonality_trait().getName()+"\","+personality_traitsLst.get(i).getScore()+"],");
					sb2.append("\""+personality_traitsLst.get(i).getPersonality_trait().getName()+"\",");
				}
			}
			
			sb.append(".]");
			sb2.append(".]");
			
			String result = sb.toString();
			String result2 = sb2.toString();
			
			result = result.replaceAll(",\\.","");
			result2 = result2.replaceAll(",\\.","");
			
			//out.print("{\"scores\":"+result+",\"labels\":"+result2+"}");
			
			out.print("{\"scores\":"+result+"}");
			
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

package co.yedam.coolsms.sms;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import co.yedam.coolsms.Coolsms;


@WebServlet("/SendSMSServlet")
public class SendSMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public SendSMSServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		
		String receipt = request.getParameter("receipt");
		String content = request.getParameter("content");
		
		System.out.println(receipt);
		System.out.println(content);
		
		/*
		 * 서버에서 받은 API_KEY, API_SECRET를 입력해주세요.
		 */
		String api_key = "NCSYISIITOGS18LT";
		String api_secret = "MJXIILHFIMC3U1EAZXU2EMOBLZBAJBU4";
		Coolsms coolsms = new Coolsms(api_key, api_secret);
	
		/*
		 * Parameters
		 * 관련정보 : http://www.coolsms.co.kr/SDK_Java_API_Reference_ko#toc-0
		 */
		HashMap<String, String> set = new HashMap<String, String>();
		set.put("to", receipt); // 수신번호

		// 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다.
		set.put("from", "01045786837"); // 발신번호
		set.put("text", content); // 문자내용
		set.put("type", "sms"); // 문자 타입

		JSONObject result = coolsms.send(set); // 보내기&전송결과받기
		if ((Boolean) result.get("status") == true) {
			// 메시지 보내기 성공 및 전송결과 출력
			System.out.println("성공");			
			System.out.println(result.get("group_id")); // 그룹아이디
			System.out.println(result.get("result_code")); // 결과코드
			System.out.println(result.get("result_message"));  // 결과메시지
			System.out.println(result.get("success_count")); // 성공갯수
			System.out.println(result.get("error_count"));  // 발송실패 메시지 수
		} else {
			// 메시지 보내기 실패
			System.out.println("실패");
			System.out.println(result.get("code")); // REST API 에러코드
			System.out.println(result.get("message")); // 에러메시지
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

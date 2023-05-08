package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.SpecialFieldInfoDAO;
import com.mobileserver.domain.SpecialFieldInfo;

import org.json.JSONStringer;

public class SpecialFieldInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造专业信息业务层对象*/
	private SpecialFieldInfoDAO specialFieldInfoDAO = new SpecialFieldInfoDAO();

	/*默认构造函数*/
	public SpecialFieldInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询专业信息的参数信息*/
			String specialFieldNumber = request.getParameter("specialFieldNumber");
			specialFieldNumber = specialFieldNumber == null ? "" : new String(request.getParameter(
					"specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			String specialFieldName = request.getParameter("specialFieldName");
			specialFieldName = specialFieldName == null ? "" : new String(request.getParameter(
					"specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			String specialCollegeNumber = "";
			if (request.getParameter("specialCollegeNumber") != null)
				specialCollegeNumber = request.getParameter("specialCollegeNumber");
			Timestamp specialBirthDate = null;
			if (request.getParameter("specialBirthDate") != null)
				specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));

			/*调用业务逻辑层执行专业信息查询*/
			List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfo(specialFieldNumber,specialFieldName,specialCollegeNumber,specialBirthDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<SpecialFieldInfos>").append("\r\n");
			for (int i = 0; i < specialFieldInfoList.size(); i++) {
				sb.append("	<SpecialFieldInfo>").append("\r\n")
				.append("		<specialFieldNumber>")
				.append(specialFieldInfoList.get(i).getSpecialFieldNumber())
				.append("</specialFieldNumber>").append("\r\n")
				.append("		<specialFieldName>")
				.append(specialFieldInfoList.get(i).getSpecialFieldName())
				.append("</specialFieldName>").append("\r\n")
				.append("		<specialCollegeNumber>")
				.append(specialFieldInfoList.get(i).getSpecialCollegeNumber())
				.append("</specialCollegeNumber>").append("\r\n")
				.append("		<specialBirthDate>")
				.append(specialFieldInfoList.get(i).getSpecialBirthDate())
				.append("</specialBirthDate>").append("\r\n")
				.append("		<specialMan>")
				.append(specialFieldInfoList.get(i).getSpecialMan())
				.append("</specialMan>").append("\r\n")
				.append("		<specialTelephone>")
				.append(specialFieldInfoList.get(i).getSpecialTelephone())
				.append("</specialTelephone>").append("\r\n")
				.append("		<specialMemo>")
				.append(specialFieldInfoList.get(i).getSpecialMemo())
				.append("</specialMemo>").append("\r\n")
				.append("	</SpecialFieldInfo>").append("\r\n");
			}
			sb.append("</SpecialFieldInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(SpecialFieldInfo specialFieldInfo: specialFieldInfoList) {
				  stringer.object();
			  stringer.key("specialFieldNumber").value(specialFieldInfo.getSpecialFieldNumber());
			  stringer.key("specialFieldName").value(specialFieldInfo.getSpecialFieldName());
			  stringer.key("specialCollegeNumber").value(specialFieldInfo.getSpecialCollegeNumber());
			  stringer.key("specialBirthDate").value(specialFieldInfo.getSpecialBirthDate());
			  stringer.key("specialMan").value(specialFieldInfo.getSpecialMan());
			  stringer.key("specialTelephone").value(specialFieldInfo.getSpecialTelephone());
			  stringer.key("specialMemo").value(specialFieldInfo.getSpecialMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加专业信息：获取专业信息参数，参数保存到新建的专业信息对象 */ 
			SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldNumber(specialFieldNumber);
			String specialFieldName = new String(request.getParameter("specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldName(specialFieldName);
			String specialCollegeNumber = new String(request.getParameter("specialCollegeNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
			Timestamp specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));
			specialFieldInfo.setSpecialBirthDate(specialBirthDate);
			String specialMan = new String(request.getParameter("specialMan").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMan(specialMan);
			String specialTelephone = new String(request.getParameter("specialTelephone").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialTelephone(specialTelephone);
			String specialMemo = new String(request.getParameter("specialMemo").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMemo(specialMemo);

			/* 调用业务层执行添加操作 */
			String result = specialFieldInfoDAO.AddSpecialFieldInfo(specialFieldInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除专业信息：获取专业信息的专业编号*/
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = specialFieldInfoDAO.DeleteSpecialFieldInfo(specialFieldNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新专业信息之前先根据specialFieldNumber查询某个专业信息*/
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfo(specialFieldNumber);

			// 客户端查询的专业信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("specialFieldNumber").value(specialFieldInfo.getSpecialFieldNumber());
			  stringer.key("specialFieldName").value(specialFieldInfo.getSpecialFieldName());
			  stringer.key("specialCollegeNumber").value(specialFieldInfo.getSpecialCollegeNumber());
			  stringer.key("specialBirthDate").value(specialFieldInfo.getSpecialBirthDate());
			  stringer.key("specialMan").value(specialFieldInfo.getSpecialMan());
			  stringer.key("specialTelephone").value(specialFieldInfo.getSpecialTelephone());
			  stringer.key("specialMemo").value(specialFieldInfo.getSpecialMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新专业信息：获取专业信息参数，参数保存到新建的专业信息对象 */ 
			SpecialFieldInfo specialFieldInfo = new SpecialFieldInfo();
			String specialFieldNumber = new String(request.getParameter("specialFieldNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldNumber(specialFieldNumber);
			String specialFieldName = new String(request.getParameter("specialFieldName").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialFieldName(specialFieldName);
			String specialCollegeNumber = new String(request.getParameter("specialCollegeNumber").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
			Timestamp specialBirthDate = Timestamp.valueOf(request.getParameter("specialBirthDate"));
			specialFieldInfo.setSpecialBirthDate(specialBirthDate);
			String specialMan = new String(request.getParameter("specialMan").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMan(specialMan);
			String specialTelephone = new String(request.getParameter("specialTelephone").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialTelephone(specialTelephone);
			String specialMemo = new String(request.getParameter("specialMemo").getBytes("iso-8859-1"), "UTF-8");
			specialFieldInfo.setSpecialMemo(specialMemo);

			/* 调用业务层执行更新操作 */
			String result = specialFieldInfoDAO.UpdateSpecialFieldInfo(specialFieldInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}

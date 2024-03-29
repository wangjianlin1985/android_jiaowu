<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ScoreInfo" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.CourseInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //获取所有的CourseInfo信息
    List<CourseInfo> courseInfoList = (List<CourseInfo>)request.getAttribute("courseInfoList");
    ScoreInfo scoreInfo = (ScoreInfo)request.getAttribute("scoreInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改成绩信息</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="ScoreInfo/ScoreInfo_ModifyScoreInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>记录编号:</td>
    <td width=70%><input id="scoreInfo.scoreId" name="scoreInfo.scoreId" type="text" value="<%=scoreInfo.getScoreId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>学生对象:</td>
    <td width=70%>
      <select name="scoreInfo.studentNumber.studentNumber">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNumber().equals(scoreInfo.getStudentNumber().getStudentNumber()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNumber() %>' <%=selected %>><%=student.getStudentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>课程对象:</td>
    <td width=70%>
      <select name="scoreInfo.courseNumber.courseNumber">
      <%
        for(CourseInfo courseInfo:courseInfoList) {
          String selected = "";
          if(courseInfo.getCourseNumber().equals(scoreInfo.getCourseNumber().getCourseNumber()))
            selected = "selected";
      %>
          <option value='<%=courseInfo.getCourseNumber() %>' <%=selected %>><%=courseInfo.getCourseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>成绩得分:</td>
    <td width=70%><input id="scoreInfo.scoreValue" name="scoreInfo.scoreValue" type="text" size="8" value='<%=scoreInfo.getScoreValue() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生评价:</td>
    <td width=70%><input id="scoreInfo.studentEvaluate" name="scoreInfo.studentEvaluate" type="text" size="30" value='<%=scoreInfo.getStudentEvaluate() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

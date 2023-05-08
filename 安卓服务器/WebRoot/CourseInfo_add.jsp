<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӿγ���Ϣ</TITLE> 
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
/*��֤��*/
function checkForm() {
    var courseNumber = document.getElementById("courseInfo.courseNumber").value;
    if(courseNumber=="") {
        alert('������γ̱��!');
        return false;
    }
    var courseName = document.getElementById("courseInfo.courseName").value;
    if(courseName=="") {
        alert('������γ�����!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="CourseInfo/CourseInfo_AddCourseInfo.action" method="post" id="courseInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>�γ̱��:</td>
    <td width=70%><input id="courseInfo.courseNumber" name="courseInfo.courseNumber" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>�γ�����:</td>
    <td width=70%><input id="courseInfo.courseName" name="courseInfo.courseName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ͽ���ʦ:</td>
    <td width=70%>
      <select name="courseInfo.courseTeacher.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
      %>
          <option value='<%=teacher.getTeacherNumber() %>'><%=teacher.getTeacherName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�Ͽ�ʱ��:</td>
    <td width=70%><input id="courseInfo.courseTime" name="courseInfo.courseTime" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>�Ͽεص�:</td>
    <td width=70%><input id="courseInfo.coursePlace" name="courseInfo.coursePlace" type="text" size="40" /></td>
  </tr>

  <tr>
    <td width=30%>�γ�ѧ��:</td>
    <td width=70%><input id="courseInfo.courseScore" name="courseInfo.courseScore" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="courseInfo.courseMemo" name="courseInfo.courseMemo" rows="5" cols="50"></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

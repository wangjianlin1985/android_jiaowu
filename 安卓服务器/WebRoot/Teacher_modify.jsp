<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Teacher teacher = (Teacher)request.getAttribute("teacher");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ľ�ʦ��Ϣ</TITLE>
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
    var teacherNumber = document.getElementById("teacher.teacherNumber").value;
    if(teacherNumber=="") {
        alert('�������ʦ���!');
        return false;
    }
    var teacherName = document.getElementById("teacher.teacherName").value;
    if(teacherName=="") {
        alert('�������ʦ����!');
        return false;
    }
    var teacherSex = document.getElementById("teacher.teacherSex").value;
    if(teacherSex=="") {
        alert('�������Ա�!');
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
    <TD align="left" vAlign=top ><s:form action="Teacher/Teacher_ModifyTeacher.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��ʦ���:</td>
    <td width=70%><input id="teacher.teacherNumber" name="teacher.teacherNumber" type="text" value="<%=teacher.getTeacherNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��ʦ����:</td>
    <td width=70%><input id="teacher.teacherName" name="teacher.teacherName" type="text" size="12" value='<%=teacher.getTeacherName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="teacher.teacherPassword" name="teacher.teacherPassword" type="text" size="30" value='<%=teacher.getTeacherPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="teacher.teacherSex" name="teacher.teacherSex" type="text" size="2" value='<%=teacher.getTeacherSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat teacherBirthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="teacher.teacherBirthday"  name="teacher.teacherBirthday" onclick="setDay(this);" value='<%=teacherBirthdaySDF.format(teacher.getTeacherBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ְ����:</td>
    <% DateFormat teacherArriveDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="teacher.teacherArriveDate"  name="teacher.teacherArriveDate" onclick="setDay(this);" value='<%=teacherArriveDateSDF.format(teacher.getTeacherArriveDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>���֤��:</td>
    <td width=70%><input id="teacher.teacherCardNumber" name="teacher.teacherCardNumber" type="text" size="20" value='<%=teacher.getTeacherCardNumber() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="teacher.teacherPhone" name="teacher.teacherPhone" type="text" size="20" value='<%=teacher.getTeacherPhone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʦ��Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=teacher.getTeacherPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="teacher.teacherPhoto" value="<%=teacher.getTeacherPhoto() %>" />
    <input id="teacherPhotoFile" name="teacherPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><textarea id="teacher.teacherAddress" name="teacher.teacherAddress" rows=5 cols=50><%=teacher.getTeacherAddress() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="teacher.teacherMemo" name="teacher.teacherMemo" rows=5 cols=50><%=teacher.getTeacherMemo() %></textarea></td>
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

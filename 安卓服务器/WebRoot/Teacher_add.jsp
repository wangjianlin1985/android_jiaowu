<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӽ�ʦ��Ϣ</TITLE> 
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
    <TD align="left" vAlign=top >
    <s:form action="Teacher/Teacher_AddTeacher.action" method="post" id="teacherAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��ʦ���:</td>
    <td width=70%><input id="teacher.teacherNumber" name="teacher.teacherNumber" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��ʦ����:</td>
    <td width=70%><input id="teacher.teacherName" name="teacher.teacherName" type="text" size="12" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="teacher.teacherPassword" name="teacher.teacherPassword" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="teacher.teacherSex" name="teacher.teacherSex" type="text" size="2" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="teacher.teacherBirthday"  name="teacher.teacherBirthday" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ְ����:</td>
    <td width=70%><input type="text" readonly id="teacher.teacherArriveDate"  name="teacher.teacherArriveDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>���֤��:</td>
    <td width=70%><input id="teacher.teacherCardNumber" name="teacher.teacherCardNumber" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="teacher.teacherPhone" name="teacher.teacherPhone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ʦ��Ƭ:</td>
    <td width=70%><input id="teacherPhotoFile" name="teacherPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><textarea id="teacher.teacherAddress" name="teacher.teacherAddress" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="teacher.teacherMemo" name="teacher.teacherMemo" rows="5" cols="50"></textarea></td>
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

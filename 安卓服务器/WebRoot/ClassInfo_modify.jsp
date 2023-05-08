<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="com.chengxusheji.domain.SpecialFieldInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�SpecialFieldInfo��Ϣ
    List<SpecialFieldInfo> specialFieldInfoList = (List<SpecialFieldInfo>)request.getAttribute("specialFieldInfoList");
    ClassInfo classInfo = (ClassInfo)request.getAttribute("classInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸İ༶��Ϣ</TITLE>
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
    var classNumber = document.getElementById("classInfo.classNumber").value;
    if(classNumber=="") {
        alert('������༶���!');
        return false;
    }
    var className = document.getElementById("classInfo.className").value;
    if(className=="") {
        alert('������༶����!');
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
    <TD align="left" vAlign=top ><s:form action="ClassInfo/ClassInfo_ModifyClassInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�༶���:</td>
    <td width=70%><input id="classInfo.classNumber" name="classInfo.classNumber" type="text" value="<%=classInfo.getClassNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�༶����:</td>
    <td width=70%><input id="classInfo.className" name="classInfo.className" type="text" size="20" value='<%=classInfo.getClassName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����רҵ:</td>
    <td width=70%>
      <select name="classInfo.classSpecialFieldNumber.specialFieldNumber">
      <%
        for(SpecialFieldInfo specialFieldInfo:specialFieldInfoList) {
          String selected = "";
          if(specialFieldInfo.getSpecialFieldNumber().equals(classInfo.getClassSpecialFieldNumber().getSpecialFieldNumber()))
            selected = "selected";
      %>
          <option value='<%=specialFieldInfo.getSpecialFieldNumber() %>' <%=selected %>><%=specialFieldInfo.getSpecialFieldName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat classBirthDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="classInfo.classBirthDate"  name="classInfo.classBirthDate" onclick="setDay(this);" value='<%=classBirthDateSDF.format(classInfo.getClassBirthDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="classInfo.classTeacherCharge" name="classInfo.classTeacherCharge" type="text" size="12" value='<%=classInfo.getClassTeacherCharge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="classInfo.classTelephone" name="classInfo.classTelephone" type="text" size="20" value='<%=classInfo.getClassTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="classInfo.classMemo" name="classInfo.classMemo" rows=5 cols=50><%=classInfo.getClassMemo() %></textarea></td>
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

<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="com.chengxusheji.domain.SpecialFieldInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�SpecialFieldInfo��Ϣ
    List<SpecialFieldInfo> specialFieldInfoList = (List<SpecialFieldInfo>)request.getAttribute("specialFieldInfoList");
    ClassInfo classInfo = (ClassInfo)request.getAttribute("classInfo");

%>
<HTML><HEAD><TITLE>�鿴�༶��Ϣ</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>�༶���:</td>
    <td width=70%><%=classInfo.getClassNumber() %></td>
  </tr>

  <tr>
    <td width=30%>�༶����:</td>
    <td width=70%><%=classInfo.getClassName() %></td>
  </tr>

  <tr>
    <td width=30%>����רҵ:</td>
    <td width=70%>
      <%=classInfo.getClassSpecialFieldNumber().getSpecialFieldName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat classBirthDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=classBirthDateSDF.format(classInfo.getClassBirthDate()) %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=classInfo.getClassTeacherCharge() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=classInfo.getClassTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><%=classInfo.getClassMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>

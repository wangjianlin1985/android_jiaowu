<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�ClassInfo��Ϣ
    List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
    Student student = (Student)request.getAttribute("student");

%>
<HTML><HEAD><TITLE>�鿴ѧ����Ϣ</TITLE>
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
    <td width=30%>ѧ��:</td>
    <td width=70%><%=student.getStudentNumber() %></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=student.getStudentName() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=student.getStudentPassword() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=student.getStudentSex() %></td>
  </tr>

  <tr>
    <td width=30%>���ڰ༶:</td>
    <td width=70%>
      <%=student.getStudentClassNumber().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
        <% java.text.DateFormat studentBirthdaySDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=studentBirthdaySDF.format(student.getStudentBirthday()) %></td>
  </tr>

  <tr>
    <td width=30%>������ò:</td>
    <td width=70%><%=student.getStudentState() %></td>
  </tr>

  <tr>
    <td width=30%>ѧ����Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=student.getStudentPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=student.getStudentTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>ѧ������:</td>
    <td width=70%><%=student.getStudentEmail() %></td>
  </tr>

  <tr>
    <td width=30%>��ϵqq:</td>
    <td width=70%><%=student.getStudentQQ() %></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><%=student.getStudentAddress() %></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><%=student.getStudentMemo() %></td>
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

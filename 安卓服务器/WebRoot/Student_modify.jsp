<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.ClassInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的ClassInfo信息
    List<ClassInfo> classInfoList = (List<ClassInfo>)request.getAttribute("classInfoList");
    Student student = (Student)request.getAttribute("student");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改学生信息</TITLE>
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
    var studentNumber = document.getElementById("student.studentNumber").value;
    if(studentNumber=="") {
        alert('请输入学号!');
        return false;
    }
    var studentName = document.getElementById("student.studentName").value;
    if(studentName=="") {
        alert('请输入姓名!');
        return false;
    }
    var studentPassword = document.getElementById("student.studentPassword").value;
    if(studentPassword=="") {
        alert('请输入登录密码!');
        return false;
    }
    var studentSex = document.getElementById("student.studentSex").value;
    if(studentSex=="") {
        alert('请输入性别!');
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
    <TD align="left" vAlign=top ><s:form action="Student/Student_ModifyStudent.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>学号:</td>
    <td width=70%><input id="student.studentNumber" name="student.studentNumber" type="text" value="<%=student.getStudentNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="student.studentName" name="student.studentName" type="text" size="12" value='<%=student.getStudentName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="student.studentPassword" name="student.studentPassword" type="text" size="30" value='<%=student.getStudentPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="student.studentSex" name="student.studentSex" type="text" size="2" value='<%=student.getStudentSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所在班级:</td>
    <td width=70%>
      <select name="student.studentClassNumber.classNumber">
      <%
        for(ClassInfo classInfo:classInfoList) {
          String selected = "";
          if(classInfo.getClassNumber().equals(student.getStudentClassNumber().getClassNumber()))
            selected = "selected";
      %>
          <option value='<%=classInfo.getClassNumber() %>' <%=selected %>><%=classInfo.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>出生日期:</td>
    <% DateFormat studentBirthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="student.studentBirthday"  name="student.studentBirthday" onclick="setDay(this);" value='<%=studentBirthdaySDF.format(student.getStudentBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>政治面貌:</td>
    <td width=70%><input id="student.studentState" name="student.studentState" type="text" size="20" value='<%=student.getStudentState() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生照片:</td>
    <td width=70%><img src="<%=basePath %><%=student.getStudentPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="student.studentPhoto" value="<%=student.getStudentPhoto() %>" />
    <input id="studentPhotoFile" name="studentPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="student.studentTelephone" name="student.studentTelephone" type="text" size="20" value='<%=student.getStudentTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生邮箱:</td>
    <td width=70%><input id="student.studentEmail" name="student.studentEmail" type="text" size="30" value='<%=student.getStudentEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系qq:</td>
    <td width=70%><input id="student.studentQQ" name="student.studentQQ" type="text" size="20" value='<%=student.getStudentQQ() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><textarea id="student.studentAddress" name="student.studentAddress" rows=5 cols=50><%=student.getStudentAddress() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><textarea id="student.studentMemo" name="student.studentMemo" rows=5 cols=50><%=student.getStudentMemo() %></textarea></td>
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

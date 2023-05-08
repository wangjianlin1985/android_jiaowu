<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.News" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    News news = (News)request.getAttribute("news");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�������Ϣ</TITLE>
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
    var newsTitle = document.getElementById("news.newsTitle").value;
    if(newsTitle=="") {
        alert('���������ű���!');
        return false;
    }
    var newsContent = document.getElementById("news.newsContent").value;
    if(newsContent=="") {
        alert('��������������!');
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
    <TD align="left" vAlign=top ><s:form action="News/News_ModifyNews.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="news.newsId" name="news.newsId" type="text" value="<%=news.getNewsId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���ű���:</td>
    <td width=70%><input id="news.newsTitle" name="news.newsTitle" type="text" size="50" value='<%=news.getNewsTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="news.newsContent" name="news.newsContent" rows=5 cols=50><%=news.getNewsContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat newsDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="news.newsDate"  name="news.newsDate" onclick="setDay(this);" value='<%=newsDateSDF.format(news.getNewsDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=news.getNewsPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="news.newsPhoto" value="<%=news.getNewsPhoto() %>" />
    <input id="newsPhotoFile" name="newsPhotoFile" type="file" size="50" /></td>
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

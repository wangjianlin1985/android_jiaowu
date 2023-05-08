<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>467双鱼林安卓Android教务选课成绩管理系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>CollegeInfo/CollegeInfo_FrontQueryCollegeInfo.action" target="OfficeMain">学院信息</a></li> 
			<li><a href="<%=basePath %>SpecialFieldInfo/SpecialFieldInfo_FrontQuerySpecialFieldInfo.action" target="OfficeMain">专业信息</a></li> 
			<li><a href="<%=basePath %>ClassInfo/ClassInfo_FrontQueryClassInfo.action" target="OfficeMain">班级信息</a></li> 
			<li><a href="<%=basePath %>Student/Student_FrontQueryStudent.action" target="OfficeMain">学生信息</a></li> 
			<li><a href="<%=basePath %>Teacher/Teacher_FrontQueryTeacher.action" target="OfficeMain">教师信息</a></li> 
			<li><a href="<%=basePath %>CourseInfo/CourseInfo_FrontQueryCourseInfo.action" target="OfficeMain">课程信息</a></li> 
			<li><a href="<%=basePath %>StudentSelectCourseInfo/StudentSelectCourseInfo_FrontQueryStudentSelectCourseInfo.action" target="OfficeMain">选课信息</a></li> 
			<li><a href="<%=basePath %>ScoreInfo/ScoreInfo_FrontQueryScoreInfo.action" target="OfficeMain">成绩信息</a></li> 
			<li><a href="<%=basePath %>News/News_FrontQueryNews.action" target="OfficeMain">新闻信息</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>

package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.ScoreInfoDAO;
import com.chengxusheji.domain.ScoreInfo;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.CourseInfoDAO;
import com.chengxusheji.domain.CourseInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ScoreInfoAction extends BaseAction {

    /*界面层需要查询的属性: 学生对象*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*界面层需要查询的属性: 课程对象*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int scoreId;
    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }
    public int getScoreId() {
        return scoreId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource StudentDAO studentDAO;
    @Resource CourseInfoDAO courseInfoDAO;
    @Resource ScoreInfoDAO scoreInfoDAO;

    /*待操作的ScoreInfo对象*/
    private ScoreInfo scoreInfo;
    public void setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
    }
    public ScoreInfo getScoreInfo() {
        return this.scoreInfo;
    }

    /*跳转到添加ScoreInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的CourseInfo信息*/
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*添加ScoreInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(scoreInfo.getStudentNumber().getStudentNumber());
            scoreInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(scoreInfo.getCourseNumber().getCourseNumber());
            scoreInfo.setCourseNumber(courseNumber);
            scoreInfoDAO.AddScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo添加失败!"));
            return "error";
        }
    }

    /*查询ScoreInfo信息*/
    public String QueryScoreInfo() {
        if(currentPage == 0) currentPage = 1;
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        scoreInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = scoreInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = scoreInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("scoreInfoList",  scoreInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryScoreInfoOutputToExcel() { 
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber,courseNumber);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ScoreInfo信息记录"; 
        String[] headers = { "记录编号","学生对象","课程对象","成绩得分"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<scoreInfoList.size();i++) {
        	ScoreInfo scoreInfo = scoreInfoList.get(i); 
        	dataset.add(new String[]{scoreInfo.getScoreId() + "",scoreInfo.getStudentNumber().getStudentName(),
scoreInfo.getCourseNumber().getCourseName(),
scoreInfo.getScoreValue() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ScoreInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询ScoreInfo信息*/
    public String FrontQueryScoreInfo() {
        if(currentPage == 0) currentPage = 1;
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        scoreInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = scoreInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = scoreInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("scoreInfoList",  scoreInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "front_query_view";
    }

    /*查询要修改的ScoreInfo信息*/
    public String ModifyScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键scoreId获取ScoreInfo对象*/
        ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("scoreInfo",  scoreInfo);
        return "modify_view";
    }

    /*查询要修改的ScoreInfo信息*/
    public String FrontShowScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键scoreId获取ScoreInfo对象*/
        ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("scoreInfo",  scoreInfo);
        return "front_show_view";
    }

    /*更新修改ScoreInfo信息*/
    public String ModifyScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(scoreInfo.getStudentNumber().getStudentNumber());
            scoreInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(scoreInfo.getCourseNumber().getCourseNumber());
            scoreInfo.setCourseNumber(courseNumber);
            scoreInfoDAO.UpdateScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除ScoreInfo信息*/
    public String DeleteScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            scoreInfoDAO.DeleteScoreInfo(scoreId);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo删除失败!"));
            return "error";
        }
    }

}

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

    /*�������Ҫ��ѯ������: ѧ������*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*�������Ҫ��ѯ������: �γ̶���*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StudentDAO studentDAO;
    @Resource CourseInfoDAO courseInfoDAO;
    @Resource ScoreInfoDAO scoreInfoDAO;

    /*��������ScoreInfo����*/
    private ScoreInfo scoreInfo;
    public void setScoreInfo(ScoreInfo scoreInfo) {
        this.scoreInfo = scoreInfo;
    }
    public ScoreInfo getScoreInfo() {
        return this.scoreInfo;
    }

    /*��ת�����ScoreInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�CourseInfo��Ϣ*/
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*���ScoreInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(scoreInfo.getStudentNumber().getStudentNumber());
            scoreInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(scoreInfo.getCourseNumber().getCourseNumber());
            scoreInfo.setCourseNumber(courseNumber);
            scoreInfoDAO.AddScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯScoreInfo��Ϣ*/
    public String QueryScoreInfo() {
        if(currentPage == 0) currentPage = 1;
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        scoreInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = scoreInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryScoreInfoOutputToExcel() { 
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber,courseNumber);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ScoreInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","ѧ������","�γ̶���","�ɼ��÷�"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ScoreInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯScoreInfo��Ϣ*/
    public String FrontQueryScoreInfo() {
        if(currentPage == 0) currentPage = 1;
        List<ScoreInfo> scoreInfoList = scoreInfoDAO.QueryScoreInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        scoreInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = scoreInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�ScoreInfo��Ϣ*/
    public String ModifyScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������scoreId��ȡScoreInfo����*/
        ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("scoreInfo",  scoreInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ScoreInfo��Ϣ*/
    public String FrontShowScoreInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������scoreId��ȡScoreInfo����*/
        ScoreInfo scoreInfo = scoreInfoDAO.GetScoreInfoByScoreId(scoreId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("scoreInfo",  scoreInfo);
        return "front_show_view";
    }

    /*�����޸�ScoreInfo��Ϣ*/
    public String ModifyScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(scoreInfo.getStudentNumber().getStudentNumber());
            scoreInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(scoreInfo.getCourseNumber().getCourseNumber());
            scoreInfo.setCourseNumber(courseNumber);
            scoreInfoDAO.UpdateScoreInfo(scoreInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ScoreInfo��Ϣ*/
    public String DeleteScoreInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            scoreInfoDAO.DeleteScoreInfo(scoreId);
            ctx.put("message",  java.net.URLEncoder.encode("ScoreInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ScoreInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

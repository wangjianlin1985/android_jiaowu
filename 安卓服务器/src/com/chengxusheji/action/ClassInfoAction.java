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
import com.chengxusheji.dao.ClassInfoDAO;
import com.chengxusheji.domain.ClassInfo;
import com.chengxusheji.dao.SpecialFieldInfoDAO;
import com.chengxusheji.domain.SpecialFieldInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ClassInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: �༶���*/
    private String classNumber;
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
    public String getClassNumber() {
        return this.classNumber;
    }

    /*�������Ҫ��ѯ������: �༶����*/
    private String className;
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return this.className;
    }

    /*�������Ҫ��ѯ������: ����רҵ*/
    private SpecialFieldInfo classSpecialFieldNumber;
    public void setClassSpecialFieldNumber(SpecialFieldInfo classSpecialFieldNumber) {
        this.classSpecialFieldNumber = classSpecialFieldNumber;
    }
    public SpecialFieldInfo getClassSpecialFieldNumber() {
        return this.classSpecialFieldNumber;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String classBirthDate;
    public void setClassBirthDate(String classBirthDate) {
        this.classBirthDate = classBirthDate;
    }
    public String getClassBirthDate() {
        return this.classBirthDate;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource SpecialFieldInfoDAO specialFieldInfoDAO;
    @Resource ClassInfoDAO classInfoDAO;

    /*��������ClassInfo����*/
    private ClassInfo classInfo;
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }

    /*��ת�����ClassInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�SpecialFieldInfo��Ϣ*/
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        return "add_view";
    }

    /*���ClassInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�༶����Ƿ��Ѿ�����*/
        String classNumber = classInfo.getClassNumber();
        ClassInfo db_classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);
        if(null != db_classInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�ð༶����Ѿ�����!"));
            return "error";
        }
        try {
            SpecialFieldInfo classSpecialFieldNumber = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(classInfo.getClassSpecialFieldNumber().getSpecialFieldNumber());
            classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
            classInfoDAO.AddClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯClassInfo��Ϣ*/
    public String QueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber, className, classSpecialFieldNumber, classBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, classSpecialFieldNumber, classBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNumber", classNumber);
        ctx.put("className", className);
        ctx.put("classSpecialFieldNumber", classSpecialFieldNumber);
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classBirthDate", classBirthDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryClassInfoOutputToExcel() { 
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber,className,classSpecialFieldNumber,classBirthDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ClassInfo��Ϣ��¼"; 
        String[] headers = { "�༶���","�༶����","����רҵ","��������","������","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<classInfoList.size();i++) {
        	ClassInfo classInfo = classInfoList.get(i); 
        	dataset.add(new String[]{classInfo.getClassNumber(),classInfo.getClassName(),classInfo.getClassSpecialFieldNumber().getSpecialFieldName(),
new SimpleDateFormat("yyyy-MM-dd").format(classInfo.getClassBirthDate()),classInfo.getClassTeacherCharge(),classInfo.getClassTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ClassInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯClassInfo��Ϣ*/
    public String FrontQueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber, className, classSpecialFieldNumber, classBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, classSpecialFieldNumber, classBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = classInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNumber", classNumber);
        ctx.put("className", className);
        ctx.put("classSpecialFieldNumber", classSpecialFieldNumber);
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classBirthDate", classBirthDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ClassInfo��Ϣ*/
    public String ModifyClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classNumber��ȡClassInfo����*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);

        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classInfo",  classInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ClassInfo��Ϣ*/
    public String FrontShowClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classNumber��ȡClassInfo����*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);

        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classInfo",  classInfo);
        return "front_show_view";
    }

    /*�����޸�ClassInfo��Ϣ*/
    public String ModifyClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SpecialFieldInfo classSpecialFieldNumber = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(classInfo.getClassSpecialFieldNumber().getSpecialFieldNumber());
            classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
            classInfoDAO.UpdateClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ClassInfo��Ϣ*/
    public String DeleteClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            classInfoDAO.DeleteClassInfo(classNumber);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

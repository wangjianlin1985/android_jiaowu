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
import com.chengxusheji.dao.SpecialFieldInfoDAO;
import com.chengxusheji.domain.SpecialFieldInfo;
import com.chengxusheji.dao.CollegeInfoDAO;
import com.chengxusheji.domain.CollegeInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SpecialFieldInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: רҵ���*/
    private String specialFieldNumber;
    public void setSpecialFieldNumber(String specialFieldNumber) {
        this.specialFieldNumber = specialFieldNumber;
    }
    public String getSpecialFieldNumber() {
        return this.specialFieldNumber;
    }

    /*�������Ҫ��ѯ������: רҵ����*/
    private String specialFieldName;
    public void setSpecialFieldName(String specialFieldName) {
        this.specialFieldName = specialFieldName;
    }
    public String getSpecialFieldName() {
        return this.specialFieldName;
    }

    /*�������Ҫ��ѯ������: ����ѧԺ*/
    private CollegeInfo specialCollegeNumber;
    public void setSpecialCollegeNumber(CollegeInfo specialCollegeNumber) {
        this.specialCollegeNumber = specialCollegeNumber;
    }
    public CollegeInfo getSpecialCollegeNumber() {
        return this.specialCollegeNumber;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String specialBirthDate;
    public void setSpecialBirthDate(String specialBirthDate) {
        this.specialBirthDate = specialBirthDate;
    }
    public String getSpecialBirthDate() {
        return this.specialBirthDate;
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
    @Resource CollegeInfoDAO collegeInfoDAO;
    @Resource SpecialFieldInfoDAO specialFieldInfoDAO;

    /*��������SpecialFieldInfo����*/
    private SpecialFieldInfo specialFieldInfo;
    public void setSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
        this.specialFieldInfo = specialFieldInfo;
    }
    public SpecialFieldInfo getSpecialFieldInfo() {
        return this.specialFieldInfo;
    }

    /*��ת�����SpecialFieldInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�CollegeInfo��Ϣ*/
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        return "add_view";
    }

    /*���SpecialFieldInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤רҵ����Ƿ��Ѿ�����*/
        String specialFieldNumber = specialFieldInfo.getSpecialFieldNumber();
        SpecialFieldInfo db_specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);
        if(null != db_specialFieldInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("��רҵ����Ѿ�����!"));
            return "error";
        }
        try {
            CollegeInfo specialCollegeNumber = collegeInfoDAO.GetCollegeInfoByCollegeNumber(specialFieldInfo.getSpecialCollegeNumber().getCollegeNumber());
            specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
            specialFieldInfoDAO.AddSpecialFieldInfo(specialFieldInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSpecialFieldInfo��Ϣ*/
    public String QuerySpecialFieldInfo() {
        if(currentPage == 0) currentPage = 1;
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        specialFieldInfoDAO.CalculateTotalPageAndRecordNumber(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = specialFieldInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = specialFieldInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialFieldInfoList",  specialFieldInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("specialFieldNumber", specialFieldNumber);
        ctx.put("specialFieldName", specialFieldName);
        ctx.put("specialCollegeNumber", specialCollegeNumber);
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialBirthDate", specialBirthDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySpecialFieldInfoOutputToExcel() { 
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber,specialFieldName,specialCollegeNumber,specialBirthDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SpecialFieldInfo��Ϣ��¼"; 
        String[] headers = { "רҵ���","רҵ����","����ѧԺ","��������","��ϵ��","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<specialFieldInfoList.size();i++) {
        	SpecialFieldInfo specialFieldInfo = specialFieldInfoList.get(i); 
        	dataset.add(new String[]{specialFieldInfo.getSpecialFieldNumber(),specialFieldInfo.getSpecialFieldName(),specialFieldInfo.getSpecialCollegeNumber().getCollegeName(),
new SimpleDateFormat("yyyy-MM-dd").format(specialFieldInfo.getSpecialBirthDate()),specialFieldInfo.getSpecialMan(),specialFieldInfo.getSpecialTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SpecialFieldInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSpecialFieldInfo��Ϣ*/
    public String FrontQuerySpecialFieldInfo() {
        if(currentPage == 0) currentPage = 1;
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        specialFieldInfoDAO.CalculateTotalPageAndRecordNumber(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = specialFieldInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = specialFieldInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialFieldInfoList",  specialFieldInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("specialFieldNumber", specialFieldNumber);
        ctx.put("specialFieldName", specialFieldName);
        ctx.put("specialCollegeNumber", specialCollegeNumber);
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialBirthDate", specialBirthDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SpecialFieldInfo��Ϣ*/
    public String ModifySpecialFieldInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������specialFieldNumber��ȡSpecialFieldInfo����*/
        SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);

        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialFieldInfo",  specialFieldInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SpecialFieldInfo��Ϣ*/
    public String FrontShowSpecialFieldInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������specialFieldNumber��ȡSpecialFieldInfo����*/
        SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);

        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialFieldInfo",  specialFieldInfo);
        return "front_show_view";
    }

    /*�����޸�SpecialFieldInfo��Ϣ*/
    public String ModifySpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CollegeInfo specialCollegeNumber = collegeInfoDAO.GetCollegeInfoByCollegeNumber(specialFieldInfo.getSpecialCollegeNumber().getCollegeNumber());
            specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
            specialFieldInfoDAO.UpdateSpecialFieldInfo(specialFieldInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SpecialFieldInfo��Ϣ*/
    public String DeleteSpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            specialFieldInfoDAO.DeleteSpecialFieldInfo(specialFieldNumber);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

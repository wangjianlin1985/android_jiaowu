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
import com.chengxusheji.dao.CollegeInfoDAO;
import com.chengxusheji.domain.CollegeInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CollegeInfoAction extends BaseAction {

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

    private String collegeNumber;
    public void setCollegeNumber(String collegeNumber) {
        this.collegeNumber = collegeNumber;
    }
    public String getCollegeNumber() {
        return collegeNumber;
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

    /*��������CollegeInfo����*/
    private CollegeInfo collegeInfo;
    public void setCollegeInfo(CollegeInfo collegeInfo) {
        this.collegeInfo = collegeInfo;
    }
    public CollegeInfo getCollegeInfo() {
        return this.collegeInfo;
    }

    /*��ת�����CollegeInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���CollegeInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCollegeInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤ѧԺ����Ƿ��Ѿ�����*/
        String collegeNumber = collegeInfo.getCollegeNumber();
        CollegeInfo db_collegeInfo = collegeInfoDAO.GetCollegeInfoByCollegeNumber(collegeNumber);
        if(null != db_collegeInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("��ѧԺ����Ѿ�����!"));
            return "error";
        }
        try {
            collegeInfoDAO.AddCollegeInfo(collegeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CollegeInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CollegeInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCollegeInfo��Ϣ*/
    public String QueryCollegeInfo() {
        if(currentPage == 0) currentPage = 1;
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryCollegeInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        collegeInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = collegeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = collegeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collegeInfoList",  collegeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCollegeInfoOutputToExcel() { 
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryCollegeInfoInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CollegeInfo��Ϣ��¼"; 
        String[] headers = { "ѧԺ���","ѧԺ����","��������","Ժ������","��ϵ�绰"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<collegeInfoList.size();i++) {
        	CollegeInfo collegeInfo = collegeInfoList.get(i); 
        	dataset.add(new String[]{collegeInfo.getCollegeNumber(),collegeInfo.getCollegeName(),new SimpleDateFormat("yyyy-MM-dd").format(collegeInfo.getCollegeBirthDate()),collegeInfo.getCollegeMan(),collegeInfo.getCollegeTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"CollegeInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCollegeInfo��Ϣ*/
    public String FrontQueryCollegeInfo() {
        if(currentPage == 0) currentPage = 1;
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryCollegeInfoInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        collegeInfoDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = collegeInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = collegeInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("collegeInfoList",  collegeInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CollegeInfo��Ϣ*/
    public String ModifyCollegeInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������collegeNumber��ȡCollegeInfo����*/
        CollegeInfo collegeInfo = collegeInfoDAO.GetCollegeInfoByCollegeNumber(collegeNumber);

        ctx.put("collegeInfo",  collegeInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CollegeInfo��Ϣ*/
    public String FrontShowCollegeInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������collegeNumber��ȡCollegeInfo����*/
        CollegeInfo collegeInfo = collegeInfoDAO.GetCollegeInfoByCollegeNumber(collegeNumber);

        ctx.put("collegeInfo",  collegeInfo);
        return "front_show_view";
    }

    /*�����޸�CollegeInfo��Ϣ*/
    public String ModifyCollegeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            collegeInfoDAO.UpdateCollegeInfo(collegeInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CollegeInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CollegeInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CollegeInfo��Ϣ*/
    public String DeleteCollegeInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            collegeInfoDAO.DeleteCollegeInfo(collegeNumber);
            ctx.put("message",  java.net.URLEncoder.encode("CollegeInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CollegeInfoɾ��ʧ��!"));
            return "error";
        }
    }

}

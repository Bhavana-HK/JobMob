package com.cvmaker.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cvmaker.bean.FileUploadBean;
import com.cvmaker.entity.FileUploadEntity;
import com.cvmaker.resources.HibernateUtility;

public class FileUploadDao {
	
	public String uploadFile(InputStream inputStream) throws Exception{
		
		Session session=null;
		Integer userId=null;
		SessionFactory sessionFactory=HibernateUtility.getSessionFactory();
		session = sessionFactory.openSession();
        FileUploadEntity entity = new FileUploadEntity();
        entity.setResume(Hibernate.getLobCreator(session).createBlob(IOUtils.toByteArray(inputStream)));
        session.beginTransaction();
        userId=(Integer)session.save(entity);
		session.getTransaction().commit();
		if(userId>0) {
			getFile(userId);
			return "Success";
		}
			
		return "Failure";
	}
	
	public void getFile(int userId)throws Exception{
		
		SessionFactory sessionFactory=HibernateUtility.getSessionFactory();
		Session session=sessionFactory.openSession();
		FileUploadEntity fp=(FileUploadEntity) session.get(FileUploadEntity.class, userId);
        if(fp!=null){
              FileUploadBean bean = new FileUploadBean();
              bean.setUserId(fp.getUserId());
              bean.setResume(fp.getResume());
              System.out.println("bgjh"+fp.getResume());
              byte[] data = bean.getResume().getBytes(1, (int) bean.getResume().length());
              System.out.println("bgjh 2 "+data);
              System.out.println(Arrays.toString(data));
              
              File file = new File("/CVMaker/WebContent/outputfile.doc");
              FileOutputStream fos = null;
              fos = new FileOutputStream(file);
              fos.write(data);
              if (fos != null) {
            	  fos.close();
            	  }

        }
	}

}

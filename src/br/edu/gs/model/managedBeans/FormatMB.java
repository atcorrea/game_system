package br.edu.gs.model.managedBeans;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class FormatMB {
	
	public String transformToFormatedDate(Date d){
		String f = "dd/MM/yyyy";
        SimpleDateFormat formatas = new SimpleDateFormat(f );
        String data = formatas.format( d );
        return data;
	}
}

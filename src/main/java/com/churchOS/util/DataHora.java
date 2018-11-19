package com.churchOS.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataHora {

	public Calendar getData() {
		Date hoje = Calendar.getInstance().getTime();
		// DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(hoje);
		return cal;
	}

	public String getHora() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}

}

package com.bikram.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
@Component
public class TemparatureReader {

	@Autowired
	private TempBean bean;
	
	@Autowired
	private HumidityBean hbean;
	
	private static String line;
	private static String[] data;
	static double humidity=0;
	static int temperature=0;
public  TempBean getTemperature(){
		
        W1Master w1Master = new W1Master();

        System.out.println(w1Master);

        for (TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)) {
           // System.out.printf("%-20s %3.1f°C %3.1f°F\n", device.getName(), device.getTemperature(),
              //      device.getTemperature(TemperatureScale.CELSIUS));
            if(device.getName().contains("28-030e97944ced")) {
            	bean.setUnit("Celsius");
            	bean.setValue(device.getTemperature(TemperatureScale.CELSIUS));
            	String pattern = "MM-dd-yyyy hh:mm:ss";
            	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            	String date = simpleDateFormat.format(new Date());
            	bean.setTime(date);
            }
        	//return 32;
        } 
		return bean;
	}

public HumidityBean getHumidity() {
	
	Runtime rt= Runtime.getRuntime();
	Process p = null;
	try {
		p = rt.exec("python /home/pi/readhumidity.py");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(p!=null) {
	 BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 try {
		if((line = bri.readLine()) != null){
			 if(!(line.contains("ERR_CRC") || line.contains("ERR_RNG"))){

				 data=line.split("ABC");
			      	System.out.println(data[0]);
				
				 humidity=Double.parseDouble(data[0]);
			 }
			 else 
				 System.out.println("Data Error");
		 }
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
      try {
		bri.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	try {
		p.waitFor();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	System.out.println("Humidity is :"+ humidity+" %RH");
  	System.out.println("Done.");
  	String pattern = "MM-dd-yyyy hh:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	String date = simpleDateFormat.format(new Date());
	hbean.setTime(date);
  	hbean.setValue(humidity);
	}
	return hbean;
}

}

package com.test.automation.bigtable.emulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hbase.shaded.org.apache.commons.lang.RandomStringUtils;


public class MockDataInjector {
	BtConnector connector;
	
	public MockDataInjector(String table,Boolean clean) throws IOException {
		if(clean) {
			new BtConnector(table).teardown();
		}
		connector=new BtConnector(table);
		
	}

	public void inject(String data_type, String bioId, String lable) throws IllegalArgumentException, IOException  {
		Map<Long, byte[]> records=new HashMap<>();
		 
      long epoch =DateUtils.getTimestamp(DateUtils.DATE_FORMAT, "2020-08-15T00:00:00");
    		  
        for (long i=0;i<(60*24);i++) {//Minutes in a day
            String data = generateData(data_type);
				try {
					records.put(epoch+i, data.getBytes()) ;
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
        }
       System.out.println("records generated "+ records);
       connector.write(bioId, lable, records);
       
      
		
	}

	private String generateData(String data_type) {
		return data_type+new RandomStringUtils().random(10);
	}

}

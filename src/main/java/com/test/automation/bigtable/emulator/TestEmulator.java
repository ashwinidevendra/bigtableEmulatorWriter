package com.test.automation.bigtable.emulator;

public class TestEmulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			new MockDataInjector("EventData",true).inject("alerts", "12345", "COUGH");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   /* MockDataInjector("EventData").inject("alerts", "12345", "COUGH")
	    MockDataInjector("EventData").inject("alerts", "12345", "COUGH")
	    MockDataInjector("EventData").inject("alerts", "12345", "COUGH")
	    MockDataInjector("ExtendedSensorDataTest", clean=True).inject("resp_band_energy_entry", "12345", "RESP_BAND_ENERGY")
	    MockDataInjector("ExtendedSensorDataTest").inject("on_off_body", "12345", "ONOFF_BODY")*/
	}

}

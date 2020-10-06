package com.test.automation.bigtable.emulator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.ByteString;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import com.google.cloud.bigtable.hbase.BigtableOptionsFactory;


public class BtConnector {
	String table;
	String instance="timeseries";
	String project="striiv-api-staging";
	String emulatorhost="http://localhost";
	int emulatorPort=8086;
	String credentials="";
	static Connection	connection;


	public BtConnector(String table) throws IOException {


		this.table=table;
		Configuration conf = BigtableConfiguration.configure(project, instance);

		conf.set(BigtableOptionsFactory.BIGTABLE_HOST_KEY, emulatorhost);
		conf.set(BigtableOptionsFactory.BIGTABLE_PORT_KEY, Integer.toString(emulatorPort));

		System.out.println("Writing to table: " + table);
		try {
			connection = BigtableConfiguration.connect(conf);
			Admin admin = connection.getAdmin();
			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(table));
			descriptor.addFamily(new HColumnDescriptor("cf1"));
			admin.createTable(descriptor);


		} catch (Exception e) {
			System.out.println("Error during client service initialization: \n" + e.toString());
		}
	}

	public void teardown() throws IOException {
		connection.getAdmin().deleteTable(TableName.valueOf(table));

	}

	public void write(String bioId, String lable, Map<Long, byte[]> records) throws IllegalArgumentException, IOException {

		Map<Long, Object> rows=new HashMap<>();
		long timestamp ;

		for ( Long datakey : records.keySet() ) {
			System.out.println( datakey );
			timestamp=datakey;
			String rowKey = bioId+":"+lable+":"+String.valueOf(timestamp);

			org.apache.hadoop.hbase.client.Table table1= connection.getTable(TableName.valueOf(Bytes.toBytes(table)));
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(
					"cf1".getBytes(StandardCharsets.UTF_8),
					"alt".getBytes(StandardCharsets.UTF_8),
					timestamp,
					records.get(datakey));
			
			
			table1.put(put);

			System.out.printf("Successfully wrote row %s", rowKey);


		}

	}
}

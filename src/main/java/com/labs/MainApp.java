package com.labs;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

	public static void main(String[] args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:data/source?noop=true").process(new Processor() {
					public void process(Exchange arg0) throws Exception {
						Student student = new Student();
						student.setName("Rahul");
						student.setRollNo("01234567897536");
						
						arg0.getIn().setBody(student);
					}
				}).marshal(
						new BindyCsvDataFormat(
								com.labs.Student.class))
				.to("file://C:/data?fileName=student.csv");
			}
		});
		context.start();
		Thread.sleep(3000);
		context.stop();
		System.out.println("Done");
	}

}

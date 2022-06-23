package com.cqupt.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class CustomAnnotationDemo1ApplicationTests {

	@Test
	public void contextLoads() {
		Student student = new Student();
		student.setName("zhangxintong");
		System.out.println(student);
	}

}

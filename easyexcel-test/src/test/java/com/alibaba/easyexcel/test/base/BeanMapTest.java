package com.alibaba.easyexcel.test.base;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.beans.BeanMap;

/**
 * @Description: cglib 修改对象属性 (依赖Bean的set方法)
 * @Author: yichengdong
 * @CreateDate: 2024/11/18 15:29
 */
public class BeanMapTest {

	@Test
	public void test(){
		// 创建一个 User 对象
		User user = new User();
		user.setName("John Doe");
//		user.setAge(30);
		user.age = 30;

		// 将 User 对象转换为 BeanMap
		BeanMap beanMap = BeanMap.create(user);

		// 通过键值对访问属性
		System.out.println("Name: " + beanMap.get("name")); // 输出: Name: John Doe
		System.out.println("Age: " + beanMap.get("age"));  // 输出: Age: 30

		// 修改属性
		beanMap.put("name", "Jane Doe new");
		beanMap.put("age", 25);

		// 输出修改后的 User 对象
		System.out.println(user); // 输出: User{name='Jane Doe', age=25}
	}

	public static class User {
		private String name;
		private int age;

		// Getter and Setter methods
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

//		public void setAge(int age) {
//			this.age = age;
//		}

		@Override
		public String toString() {
			return "User{name='" + name + "', age=" + age + "}";
		}
	}
}

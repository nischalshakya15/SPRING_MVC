package np.edu.persidential;

import np.edu.persidential.model.Employee;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext.xml");
        Employee employee = (Employee) factory.getBean("employeeConstructorInjection");
        System.out.println(employee.getId());

        Employee employeeSetter = (Employee) factory.getBean("employeeSetterInjection");
        System.out.println(employeeSetter.getId());
        System.out.println(employeeSetter.getName());
    }
}


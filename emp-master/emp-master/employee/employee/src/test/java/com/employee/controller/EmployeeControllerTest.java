package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.pojo.EmployeePojo;
import com.employee.service.EmployeeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;


@SpringBootTest
class EmployeeControllerTest {
    @InjectMocks
    EmployeeController controller;

    @Mock
    EmployeeService service;

    @Test
    public void saveEmployeeTest(){
        EmployeePojo pojo = getEmpDetails();
        Employee empData =getEmpEntity();
        ResponseEntity<?> response= null;
        Mockito.when(service.createEmployee(any(EmployeePojo.class))).thenReturn(empData);
        response=controller.saveEmployee(pojo);
        Assert.assertNotNull(response);
    }
    @Test
    public void getAllTest(){
        List<Employee> list = new ArrayList<>();
        List<Employee> getAllList=null;
        Employee emp = getEmpEntity();
        list.add(emp);
        Mockito.when(service.getAllEmployee()).thenReturn(list);
        getAllList= controller.getAll();
        Assert.assertEquals(12345678,getAllList.get(0).getEmpId());
    }

    @Test
    public void getAllEmployeeGraterTest(){
        List<Employee> list = new ArrayList<>();
        List<Employee> getAllList=null;
        Employee emp = getEmpEntity();
        list.add(emp);
        Mockito.when(service.employeeSalaryGrater()).thenReturn(list);
        getAllList = controller.getAllEmployeeGrater();
        Assert.assertEquals(60000,getAllList.get(0).getSalary());
    }

    @Test
    public void getEmployeeTest(){
        Employee empData =getEmpEntity();
        ResponseEntity<?> entity = null;
        Mockito.when(service.getEmployeeById(anyInt())).thenReturn(empData);
        entity = controller.getEmployee(12345678);
        Assert.assertEquals(HttpStatus.OK,entity.getStatusCode());
    }

   /* @Test
    public void deleteTest(){
        Mockito.doNothing().when(service.deleteEmployee(anyInt()));
    }*/
    private Employee getEmpEntity() {
        Employee emp = new Employee();
        emp.setEmpId(12345678);
        emp.setFirstName("abx");
        emp.setLastName("asc");
        emp.setAddress("bangalore");
        emp.setEmail("abc@gmail.com");
        emp.setPhone("098765432");
        emp.setSalary(60000);
        return emp;
    }

    private EmployeePojo getEmpDetails() {
        EmployeePojo pojo = new EmployeePojo();
        pojo.setEmpId(12345678);
        pojo.setFirstName("abx");
        pojo.setLastName("asc");
        pojo.setAddress("bangalore");
        pojo.setEmail("abc@gmail.com");
        pojo.setPhone("098765432");
        pojo.setSalary(60000);
        return pojo;
    }

}
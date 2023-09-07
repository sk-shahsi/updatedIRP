package com.employee.service;

import com.employee.entity.Employee;
import com.employee.pojo.EmployeePojo;
import com.employee.repo.EmployeeRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class EmployeeServiceTest {
    @InjectMocks
    EmployeeService service;

    @Mock
    EmployeeRepo repo;

    @Test
    public void getAllEmployeeTest(){
        List<Employee> list= new ArrayList<>();
        List<Employee> output= null;
        Employee emp= getEmpEntity();
        list.add(emp);
        Mockito.when(repo.findAll()).thenReturn(list);
        output= service.getAllEmployee();
        Assert.assertEquals("abx",output.get(0).getFirstName());
    }
    @Test
    public void getEmployeeByIdTestWithExcWhenIdisSmall(){
        Employee emp = getEmpEntity();
        AtomicReference<Employee> out=null;
        assertThrows(RuntimeException.class,
                ()->{
                    Mockito.when(repo.findById(anyInt())).thenReturn(java.util.Optional.of(emp));
                    out.set(service.getEmployeeById(1234567));
                    Assert.assertEquals("bangalore", out.get().getAddress());
                });
    }
    @Test
    public void getEmployeeByIdTestWithExcWhendataNull(){
        Employee emp =null;
        AtomicReference<Employee> out=null;
        assertThrows(RuntimeException.class,
                ()->{
            Mockito.when(repo.findById(anyInt())).thenReturn(java.util.Optional.of(emp));
            out.set(service.getEmployeeById(12345678));
            Assert.assertEquals("bangalore", out.get().getAddress());
                });
    }

    @Test
    public void getEmployeeByIdTestWithSuccess(){
        Employee getEmp = getEmpEntity();
        Employee out = null;
        Mockito.when(repo.findById(anyInt())).thenReturn(java.util.Optional.of(getEmp));
        out = service.getEmployeeById(12345678);
        Assert.assertEquals("abc@gmail.com",out.getEmail());
    }

    @Test
    public void employeeSalaryGraterTest(){
        List<Employee> emp = new ArrayList<>();
        List<Employee> out = null;
        Employee getEmp = getEmpEntity();
        emp.add(getEmp);
        Mockito.when(repo.findAll()).thenReturn(emp);
        out= service.employeeSalaryGrater();
        Assert.assertEquals(60000,out.get(0).getSalary());
    }

    @Test
    public void createEmployeeTsetWithExcep(){
        EmployeePojo pojo = getEmpDetailsidless();
        pojo.setEmpId(1234567);
        Employee entity = getEmpEntity();
        AtomicReference<Employee> out=null;
        assertThrows(RuntimeException.class,
                ()->{
            Mockito.when(repo.save(any(Employee.class))).thenReturn(entity);
            out.set(service.createEmployee(pojo));
            Assert.assertEquals("asc",out.get().getLastName());
                });
    }
    @Test
    public void createEmployeeTsetWithFirstNameExcp(){
        EmployeePojo pojo = getEmpDetailsidless();
        pojo.setEmpId(12345678);
        pojo.setFirstName("");
        Employee entity = getEmpEntity();
        AtomicReference<Employee> out=null;
        assertThrows(RuntimeException.class,
                ()->{
                    Mockito.when(repo.save(any(Employee.class))).thenReturn(entity);
                    out.set(service.createEmployee(pojo));
                    Assert.assertEquals("asc",out.get().getLastName());
                });
    }
    @Test
    public void createEmployeeTsetWithEmailExcep(){
        EmployeePojo pojo = getEmpDetailsidless();
        pojo.setEmpId(12345678);
        pojo.setEmail("");
        Employee entity = getEmpEntity();
        AtomicReference<Employee> out=null;
        assertThrows(RuntimeException.class,
                ()->{
                    Mockito.when(repo.save(any(Employee.class))).thenReturn(entity);
                    out.set(service.createEmployee(pojo));
                    Assert.assertEquals("asc",out.get().getLastName());
                });
    }
    @Test
    public void createEmployeeTsetSuccess(){
        Employee entity = getEmpEntity();
        EmployeePojo pojo = getEmpDetailsidless();
        Employee out= null;
        Mockito.when(repo.save(any(Employee.class))).thenReturn(entity);
        out = service.createEmployee(pojo);
        Assert.assertNotNull(out);

    }

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

    private EmployeePojo getEmpDetailsidless() {
        EmployeePojo emp = new EmployeePojo();
        emp.setEmpId(12345678);
        emp.setFirstName("abx");
        emp.setLastName("asc");
        emp.setAddress("bangalore");
        emp.setEmail("abc@gmail.com");
        emp.setPhone("098765432");
        emp.setSalary(60000);
        return emp;
    }
}
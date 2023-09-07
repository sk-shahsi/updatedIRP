package com.employee.service;

import com.employee.entity.Employee;
import com.employee.pojo.EmployeePojo;
import com.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeRepo;

    public List<Employee> getAllEmployee(){return employeRepo.findAll();}

    public Employee getEmployeeById(int empId){
        if (!String.valueOf(empId).matches("\\d{8}")){
            throw new RuntimeException("id should contain only number of size 8");
        }
        Employee employee=null;
        employee=employeRepo.findById(empId).get();
        if (employee==null){
            throw new RuntimeException("No Record Found");
        }
        return employee;
    }
    public List<Employee> employeeSalaryGrater(){
        return employeRepo.findAll().stream().filter(x->x.getSalary()>5000).collect(Collectors.toList());
    }
    public Employee createEmployee(EmployeePojo employee){
        validateEmployee(employee);
        Employee saveEmp=null;
        Employee entity= convertPojoToEntity(employee);
        saveEmp=employeRepo.save(entity);
        return entity;
    }

    private  Employee convertPojoToEntity(EmployeePojo employee){
        Employee entity =new Employee();
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        entity.setAddress(employee.getAddress());
        entity.setEmail(employee.getEmail());
        entity.setPhone(employee.getPhone());
        entity.setSalary(employee.getSalary());
        entity.setEmpId(employee.getEmpId());
        return entity;
    }
    public void deleteEmployee(int empId){
        employeRepo.deleteById(empId);
    }

    private void validateEmployee(EmployeePojo employee) {
        String id = String.valueOf(employee.getEmpId());
        if (!id.matches("\\d{8}")) {
            throw new RuntimeException("id should contain only number of size 8");
        }
        if (employee.getFirstName().isEmpty() || employee.getLastName().isEmpty()) {
            throw new RuntimeException("first name or last name is empty");
        }
        if (employee.getEmail().isEmpty()) {
            throw new RuntimeException("email address can't be blank");
        }

    }

    public List<Employee> getEmpByFirstName(String firstName, String lastName) {
        List<Employee> employee=null;
        employee=employeRepo.findByFirstNameOrLastName(firstName, lastName);
        return employee;
    }
}

package com.mukesh.corejava.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mukesh Bhoge
 **/
public class Practice1 {

    public static void main(String[] args) {
        Practice1 p1 = new Practice1();
        System.out.println("################ List of Employees By Department #################");
        p1.printEmployeeByDept(p1.getEmployeeList());
        System.out.println("################ Employee count by department ################");
        p1.printEmployeeCountByDept(p1.getEmployeeList());
        System.out.println("################ List of active of inactive employee ################");
        p1.printActiveAndInActiveEmployee(p1.getEmployeeList());
        System.out.println("################ Print Min and Max Salary ################");
        p1.printMinAndMaxSalary(p1.getEmployeeList());
        p1.printMaxSalaryInDept(p1.getEmployeeList());
    }

    //Write a program to print the max salary of an employee from each department
    private void printMaxSalaryInDept(List<Employee> employeeList) {
        Map<String,Optional<Employee>> empMap = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment
        ,Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary))
        )));
        empMap.entrySet().forEach(
                e->{
                    System.out.println(e.getKey()+" department maximum salary is :: "+e.getValue().get().getSalary());
                }
        );
    }


    //Example 1 : Write a program to print employee details working in each department
    private void printEmployeeByDept(List<Employee> employeeList) {
        Map<String, List<Employee>> empList = employeeList.stream().
                collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
        empList.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " :: " + e.getValue());
        });
    }

    //Write a program to print employees count working in each department
    private void printEmployeeCountByDept(List<Employee> employeeList) {
        Map<String, Long> departmentMap = employeeList.stream().
                collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        departmentMap.entrySet().forEach(e -> {
            System.out.println(e.getKey() + " :: " + e.getValue());
        });
    }

    //Write a program to print active and inactive employees in the given collection
    private void printActiveAndInActiveEmployee(List<Employee> employeeList) {
        Map<Boolean, List<Employee>> empMap = employeeList.stream().collect(Collectors.groupingBy(Employee::isActive, Collectors.toList()));
        empMap.entrySet().forEach(e -> {
            System.out.println("IsActive:: " + e.getKey() + " :: " + e.getValue());
        });
        System.out.println("<<<<< $$$$$$$$ List of Active employee $$$$$$$$ >>>>>");
        // Print only active Employee
        employeeList.stream().filter(e -> e.isActive() == true).forEach(e -> {
            System.out.println(e.getFirstname() + " " + e.getLastname());
        });
    }

    //Write a program to print Max/Min employee salary from the given collection
    private void printMinAndMaxSalary(List<Employee> employeeList) {
        Optional<Employee> maxSalaryEmp = employeeList.stream().max(Comparator.comparing(Employee::getSalary));
        System.out.println(maxSalaryEmp.get().getFirstname() + " has maximum salary :: " + maxSalaryEmp.get().getSalary());
        Optional<Employee> minSalaryEmp = employeeList.stream().min(Comparator.comparing(Employee::getSalary));
        System.out.println(minSalaryEmp.get().getFirstname() + " has minimum salary :: " + minSalaryEmp.get().getSalary());
    }

    private List<Employee> getEmployeeList() {
        Employee e1 = Employee.builder()
                .firstname("Mukesh")
                .lastname("Bhoge")
                .department("Electronics")
                .isActive(false)
                .salary(10000)
                .build();

        Employee e2 = Employee.builder()
                .firstname("Sachin")
                .lastname("D")
                .department("IT")
                .isActive(true)
                .salary(25000)
                .build();
        Employee e3 = Employee.builder()
                .firstname("Sandip")
                .lastname("H")
                .department("Electronics")
                .isActive(true)
                .salary(40000)
                .build();
        Employee e4 = Employee.builder()
                .firstname("Harshad")
                .lastname("C")
                .department("Electronics")
                .isActive(false)
                .salary(55000)
                .build();
        Employee e5 = Employee.builder()
                .firstname("Swapnil")
                .lastname("P")
                .department("Computer")
                .isActive(true)
                .salary(150000)
                .build();
        return Stream.of(e1, e2, e3, e4, e5).collect(Collectors.toList());
    }
}

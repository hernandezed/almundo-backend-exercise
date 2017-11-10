package com.callcenter.almundo.comparator;

import com.callcenter.almundo.domain.Employee;
import java.util.Comparator;

public class EmployeePriorityComparator implements Comparator<Employee> {

    @Override
    public int compare(Employee employee, Employee otherEmployee) {
        if (employee.getPriority() < otherEmployee.getPriority()) {
            return -1;
        } else if (employee.getPriority() > otherEmployee.getPriority()) {
            return 1;
        }
        return 0;
    }

}

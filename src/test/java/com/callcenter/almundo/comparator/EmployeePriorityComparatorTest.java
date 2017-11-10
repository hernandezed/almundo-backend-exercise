package com.callcenter.almundo.comparator;

import com.callcenter.almundo.domain.Director;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.domain.Operator;
import com.callcenter.almundo.domain.Supervisor;
import java.util.Comparator;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Before;
import org.junit.Test;

public class EmployeePriorityComparatorTest {

    private Employee operator, otherOperator, supervisor, otherSupervisor, director, otherDirector;
    private Comparator<Employee> comparator;

    @Before
    public void setUp() {
        comparator = new EmployeePriorityComparator();
        operator = new Operator("Operator");
        supervisor = new Supervisor("Supervisor");
        director = new Director("Director");
        otherOperator = new Operator("Other Operator");
        otherSupervisor = new Supervisor("Other Supervisor");
        otherDirector = new Director("Other Director");
    }

    @Test
    public void compare_comparingOperatorWithOperator_returnEquals() {
        int result = comparator.compare(operator, otherOperator);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void compare_comparingOperatorWithSupervisor_returnLower() {
        int result = comparator.compare(operator, supervisor);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void compare_comparingOperatorWithDirector_returnLower() {
        int result = comparator.compare(operator, director);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void compare_comparingSupervisorWithSupervisor_returnEquals() {
        int result = comparator.compare(supervisor, otherSupervisor);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void compare_comparingSupervisorWithDirector_returnLower() {
        int result = comparator.compare(supervisor, director);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    public void compare_comparingSupervisorWithOperator_returnHigher() {
        int result = comparator.compare(supervisor, operator);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void compare_comparingDirectorWithDirector_returnEquals() {
        int result = comparator.compare(director, otherDirector);
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void compare_comparingDirectorWithSupervisor_returnHigher() {
        int result = comparator.compare(director, supervisor);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void compare_comparingDirectorWithOperator_returnHigher() {
        int result = comparator.compare(director, operator);
        assertThat(result).isEqualTo(1);
    }

}

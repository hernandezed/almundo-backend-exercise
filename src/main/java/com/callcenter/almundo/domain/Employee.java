package com.callcenter.almundo.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import java.util.Objects;

@JsonTypeInfo(use = Id.NAME, property = "priority")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Operator.class, name = "0"),
    @JsonSubTypes.Type(value = Supervisor.class, name = "1"),
    @JsonSubTypes.Type(value = Director.class, name = "2")
})
public abstract class Employee {

    protected String name;
    protected int priority;

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + this.priority;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return (this.priority == other.priority) && Objects.equals(this.name, other.name);
    }

}

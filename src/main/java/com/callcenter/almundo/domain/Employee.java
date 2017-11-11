package com.callcenter.almundo.domain;

import java.util.Objects;

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

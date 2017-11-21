package com.callcenter.almundo.domain;

import java.io.Serializable;

public class Call implements Serializable {

    private long id;
    private Boolean wasInStandBy;
    private int duration;
    private Employee employee;

    public Call(long id) {
        this.id = id;
    }

    public Call() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getWasInStandBy() {
        return wasInStandBy;
    }

    public void setWasStandBy(Boolean isInStandBy) {
        this.wasInStandBy = isInStandBy;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass() || obj == null) {
            return false;
        }
        final Call other = (Call) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Call{" + "id=" + id + ", standBy=" + wasInStandBy + '}';
    }

}

package com.callcenter.almundo.domain;

public class Call {

    private long id;
    private boolean standBy;
    private int duration;
    private Employee employee;

    public Call(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isStandBy() {
        return standBy;
    }

    public void setStandBy(boolean standBy) {
        this.standBy = standBy;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Call other = (Call) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Call{" + "id=" + id + ", standBy=" + standBy + '}';
    }

}

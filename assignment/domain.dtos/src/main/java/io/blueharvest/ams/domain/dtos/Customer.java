package io.blueharvest.ams.domain.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer extends Entity {

    private String name;
    private String surname;

    public Customer() {}

    @JsonCreator
    public Customer(@JsonProperty(value = "id", required = true) String id,
                    @JsonProperty(value = "name", required = true) String name,
                    @JsonProperty(value = "surname", required = true) String surname) {
        setId(id);
        setName(name);
        setSurname(surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int temp1 = Helper.cantorPair(super.hashCode(), name.hashCode());
        return Helper.cantorPair(temp1, surname.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Customer) {
            Customer another = (Customer)obj;
            return super.equals(obj) &&
                    this.name.equals(another.name) &&
                    this.surname.equals(another.surname);
        }
        return false;
    }
}

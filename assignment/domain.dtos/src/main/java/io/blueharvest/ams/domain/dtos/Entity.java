package io.blueharvest.ams.domain.dtos;

public class Entity {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null ) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof Entity) {
            Entity another = (Entity)obj;
            return this.id == another.id;
        }
        return false;
    }
}

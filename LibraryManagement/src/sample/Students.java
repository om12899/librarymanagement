package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Students {
    private StringProperty name;
    private StringProperty id;
    private StringProperty dept;
    private StringProperty year;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDept() {
        return dept.get();
    }

    public StringProperty deptProperty() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept.set(dept);
    }

    public String getYear() {
        return year.get();
    }

    public StringProperty yearProperty() {
        return year;
    }

    public void setYear(String year) {
        this.year.set(year);
    }

    public Students() {
        name = new SimpleStringProperty();
        id = new SimpleStringProperty();
        dept = new SimpleStringProperty();
        year = new SimpleStringProperty();
    }

}

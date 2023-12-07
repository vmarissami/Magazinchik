package tp.magazinchik.model;

public class Sections {
    private int id;
    private String name;
    private String workingHours;

    public Sections(int id, String name, String workingHours) {
        this.id = id;
        this.name = name;
        this.workingHours = workingHours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public String toString() {
        return "Отдел: " + name + " Время работы: " + workingHours;
    }
}

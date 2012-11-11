package dk.esmann.DroidBugz.FogBugz;

public class Case {
    private Integer number;
    private String title;

    public Case(Integer number, String title) {
        this.number = number;
        this.title = title;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + " (" + number + ")";
    }
}

package nhung.nguyen.infoage;

public class ClassInfo {
    String className;
    String description;
    String instructor;
    String language;
    public ClassInfo(String name, String des, String ins, String lang){
        this.className=name;
        this.description= des;
        this.instructor= ins;
        this.language=lang;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}

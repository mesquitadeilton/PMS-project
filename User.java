import java.util.*;

public class User {
    private String name;
    private String lastName;
    private String email;
    private String office;
    private String password;

    private List<Project> projects = new ArrayList<Project>();

    public User(String name, String lastName, String email, String office, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.office = office;
        this.password = password;
    }

    public String getFullName() {
        return name+" "+lastName;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getOffice() {
        return office;
    }

    public void setProject(Project project) {
        this.projects.add(project);
    }
    public List<Project> getProjects() {
        return projects;
    }

    public String getPassword() {
        return password;
    }
}
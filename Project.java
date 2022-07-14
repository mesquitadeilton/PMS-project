import java.util.*;

public class Project {
    private String status;
    private String identification;
    private String description;
    private String begin;
    private String timeBegin;
    private String end;
    private String timeEnd;
    private User author;

    private List<User> participants = new ArrayList<User>();
    private List<Project> activities = new ArrayList<Project>();

    public Project(String idetification, String description, String begin, String timeBegin, String end, String timeEnd) {
        this.identification = idetification;
        this.description = description;
        this.begin = begin;
        this.timeBegin = timeBegin;
        this.end = end;
        this.timeEnd = timeEnd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getBegin() {
        return begin;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getEnd() {
        return end;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setPaticipant(User participant) {
        this.participants.add(participant);
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setActivity(Project activity) {
        this.activities.add(activity);
    }

    public List<Project> getActivities() {
        return activities;
    }
}
package it.univaq.agilehub.model;

public class TeacherBooking {
    private int id, userId, teacherId;
    private String dayOfBooking;
    private Sport sport;

    public TeacherBooking() {
    }

    public TeacherBooking(int id, int userId, int teacherId, String dayOfBooking, Sport sport) {
        this.id = id;
        this.userId = userId;
        this.teacherId = teacherId;
        this.dayOfBooking = dayOfBooking;
        this.sport = sport;
    }

    public TeacherBooking(int userId, int teacherId, String dayOfBooking, Sport sport) {
        this.id = id;
        this.userId = userId;
        this.teacherId = teacherId;
        this.dayOfBooking = dayOfBooking;
        this.sport = sport;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getDayOfBooking() {
        return dayOfBooking;
    }

    public void setDayOfBooking(String dayOfBooking) {
        this.dayOfBooking = dayOfBooking;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "TeacherBooking{" +
                "id=" + id +
                ", user_id=" + userId +
                ", numberOfPeople=" + teacherId +
                ", dateOfBirth='" + dayOfBooking + '\'' +
                ", sport=" + sport +
                '}';
    }

    public static String dateOfBirthConverter(String paramFromFX){
        String dateOfBirth = "";
        String[] arrOfSplit = paramFromFX.split("-");
        for (int i = arrOfSplit.length - 1 ; i > -1; i--){
            dateOfBirth += arrOfSplit[i] + "/";
        }
        dateOfBirth = (dateOfBirth.substring(0, dateOfBirth.length() - 1));
        return dateOfBirth;
    }
}

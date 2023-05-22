package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.TeacherBookingDao;
import it.univaq.agilehub.dao.TeacherBookingDaoImpl;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.TeacherBookingInfo;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherViewBookingController extends DataInitializable<User> implements Initializable {

    @FXML
    private TableColumn<TeacherBookingInfo, LocalDate> dateColumn;

    @FXML
    private TableColumn<TeacherBookingInfo, String> studentColumn;

    @FXML
    private TableView<TeacherBookingInfo> teacherBookingTable;

    @FXML
    private TableColumn<TeacherBookingInfo, String> timeColumn;

    private User userLogged;

    @Override
    public void initializeData(User user) throws ViewException {
        super.initializeData(user);
        this.userLogged = user;

        studentColumn.setCellValueFactory(new PropertyValueFactory<TeacherBookingInfo, String>("studentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TeacherBookingInfo, LocalDate>("bookingDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<TeacherBookingInfo, String>("bookingTime"));

        TeacherBookingDao teacherBookingDao = new TeacherBookingDaoImpl();
        UserDao userDao = new UserDaoImpl();
        try {
            List<TeacherBooking> teacherBookings = teacherBookingDao.getAllTeacherBookingsForMaestro(userLogged.getId());
            ObservableList<TeacherBookingInfo> teacherBookingInfoList = FXCollections.observableArrayList();
            for (TeacherBooking teacherBooking :teacherBookings) {
                TeacherBookingInfo teacherBookingInfo = new TeacherBookingInfo();
                teacherBookingInfo.setStudentName(userDao.getUserById(teacherBooking.getUserId()).toString());
                teacherBookingInfo.setBookingDate(teacherBooking.getDayOfBooking());
                teacherBookingInfo.setBookingTime(teacherBookingDao.getTimeTeacherBooking(teacherBooking.getId()).getTime_slot());
                teacherBookingInfoList.add(teacherBookingInfo);
            }
            teacherBookingTable.setItems(teacherBookingInfoList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
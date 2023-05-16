package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.TeacherBookingDao;
import it.univaq.agilehub.dao.TeacherBookingDaoImpl;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TeacherReservationController extends DataInitializable<User> implements Initializable {

    @FXML
    private Label confermaPrenotazioneMaestro;

    @FXML
    private ListView<String> listaMaestri;

    @FXML
    private DatePicker dataPrenotazioneMaestro;

    @FXML
    private Button prenotaMaestroButton;

    @FXML
    private ChoiceBox<String> selezioneSport;

    private ArrayList<User> teacherList;

    private int teacher_id;

    @FXML
    void prenotaMaestroAction(ActionEvent event) {

        String dayOfBooking =  TeacherBooking.dayOfBookingConverter(dataPrenotazioneMaestro.getValue().toString());
        Sport sport = Enum.valueOf(Sport.class, selezioneSport.getValue());

        TeacherBooking teacherBooking = new TeacherBooking(1,teacher_id,dayOfBooking,sport);
        TeacherBookingDao teacherBookingDao = new TeacherBookingDaoImpl();
        try {
            teacherBookingDao.insertTeacherBooking(teacherBooking);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherList = new ArrayList<User>();

        prenotaMaestroButton.disableProperty()
                .bind(dataPrenotazioneMaestro.valueProperty().isNull());

        for (Sport sport : Sport.values()){
            selezioneSport.getItems().add(sport.name());
        }

        selezioneSport.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                listaMaestri.getItems().clear();
                teacherList.clear();
                UserDao userDao = new UserDaoImpl();
                teacherList = userDao.getTeacherBySport(t1);
                for(User teacher : teacherList) {
                    listaMaestri.getItems().addAll(teacher.getName() + " " + teacher.getSurname());
                    teacher_id = teacher.getId();
                }
            }
        });

        listaMaestri.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (!teacherList.isEmpty() && (t1.intValue() != -1)) {
                    //System.out.println(teacherList.get(t1.intValue()));
                    //[TO DO] set calendar dates
                }

            }
        });

    }
}

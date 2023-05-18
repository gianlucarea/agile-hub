package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.TeacherBookingDao;
import it.univaq.agilehub.dao.TeacherBookingDaoImpl;
import it.univaq.agilehub.dao.UserDao;
import it.univaq.agilehub.dao.UserDaoImpl;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;
import it.univaq.agilehub.view.ViewException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private User userLogged;

    @Override
    public void initializeData(User user) throws ViewException {
        super.initializeData(user);
        this.userLogged = user;
    }

    @FXML
    void prenotaMaestroAction(ActionEvent event) {
        String dayOfBooking =  Utility.dateOfBirthConverter(dataPrenotazioneMaestro.getValue().toString());
        System.out.println(dayOfBooking);
        LocalDate dayOfBookingTolocalDate = LocalDate.parse(dayOfBooking, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Sport sport = Enum.valueOf(Sport.class, selezioneSport.getValue());

        TeacherBookingDao teacherBookingDao = new TeacherBookingDaoImpl();
        try {
            TeacherBooking teacherBooking = new TeacherBooking(userLogged.getId(), teacher_id,dayOfBookingTolocalDate,sport);
            if (teacherBookingDao.doesTeacherBookingAlreadyExist(teacherBooking)) {
                confermaPrenotazioneMaestro.setText("Prenotazione già effettuata\nScegli un altra data");
            } else if (teacherBookingDao.isTeacearBookingFull(teacher_id, dayOfBooking)) {
                confermaPrenotazioneMaestro.setText("Prenotazioni piene\nScegli un altra data");
            } else {
                teacherBookingDao.insertTeacherBooking(teacherBooking);
                confermaPrenotazioneMaestro.setText("Prenotazione effettuata");
                dataPrenotazioneMaestro.setValue(null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        teacherList = new ArrayList<User>();
        prenotaMaestroButton.disableProperty()
                .bind(dataPrenotazioneMaestro.valueProperty().isNull());

        dataPrenotazioneMaestro.disableProperty().bind(listaMaestri.getSelectionModel().selectedItemProperty().isNull());

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
                    dataPrenotazioneMaestro.setDayCellFactory(datePicker -> new DateCell() {
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);
                            LocalDate today = LocalDate.now();
                            setDisable(empty || date.compareTo(today) < 0 || date.isAfter(today.plusWeeks(1)));
                        }
                    });
                }

            }
        });

    }
}

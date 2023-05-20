package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.*;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.TeacherBooking;
import it.univaq.agilehub.model.TimeSlot;
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
import java.util.List;
import java.util.ResourceBundle;

public class TeacherReservationController extends DataInitializable<User> implements Initializable {

    TeacherBookingDao teacherBookingDao = new TeacherBookingDaoImpl();
    TimeSlotDao timeSlotDao = new TimeSlotDaoImpl();
    @FXML
    private Label confermaPrenotazioneMaestro;

    @FXML
    private ListView<User> listaMaestri;

    @FXML
    private DatePicker dataPrenotazioneMaestro;

    @FXML
    private Button prenotaMaestroButton;

    @FXML
    private ChoiceBox<String> selezioneSport;

    private ArrayList<User> teacherList;

    @FXML
    private ChoiceBox<TimeSlot> selezioneOrario;

    private int teacher_id;

    private User userLogged;

    @Override
    public void initializeData(User user) throws ViewException {
        super.initializeData(user);
        this.userLogged = user;
    }

    @FXML
    void showAvailableSlots(ActionEvent event) {
        selezioneOrario.getItems().clear();

        if(dataPrenotazioneMaestro.getValue() != null) {
            User teacher = listaMaestri.getSelectionModel().getSelectedItem();
            String converted = Utility.dateOfBirthConverter(dataPrenotazioneMaestro.getValue().toString());
            try {
                ArrayList<Integer> unavailableTS = timeSlotDao.unavailableTimeSlotTeacherId(converted,teacher);
                ArrayList<TimeSlot> allTimeSlot = timeSlotDao.getAllTimeSlots();

                if(unavailableTS.isEmpty()){
                    for (TimeSlot t : allTimeSlot) {
                        selezioneOrario.getItems().add(t);
                    }
                } else {
                    // OVERHERE A BETTER METHOD BUT OK FOR NOW
                    List<TimeSlot> found = new ArrayList<TimeSlot>();
                    for(TimeSlot timeSlot : allTimeSlot){
                        for(int i : unavailableTS){
                            if(timeSlot.getId() == i){
                                found.add(timeSlot);
                            }
                        }
                    }
                    allTimeSlot.removeAll(found);
                    for (TimeSlot t : allTimeSlot) {
                        selezioneOrario.getItems().add(t);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void prenotaMaestroAction(ActionEvent event) {
        String dayOfBooking =  Utility.dateOfBirthConverter(dataPrenotazioneMaestro.getValue().toString());
        LocalDate dayOfBookingTolocalDate = LocalDate.parse(dayOfBooking, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Sport sport = Enum.valueOf(Sport.class, selezioneSport.getValue());

        try {
            TeacherBooking teacherBooking = new TeacherBooking(userLogged.getId(), teacher_id, dayOfBookingTolocalDate,sport);
            if (teacherBookingDao.doesTeacherBookingAlreadyExist(teacherBooking)) {
                confermaPrenotazioneMaestro.setText("Prenotazione già effettuata\n Scegli un altra data");
            } else if (teacherBookingDao.isTeacherBookingFull(teacher_id, dayOfBooking)) {
                confermaPrenotazioneMaestro.setText("Prenotazioni piene\n Scegli un altra data");
            } else {
                int teacher_bookingid = teacherBookingDao.insertTeacherBooking(teacherBooking);
                teacherBookingDao.insertTimeTeacherBooking(listaMaestri.getSelectionModel().getSelectedItem().getId(),teacher_bookingid,dayOfBooking,selezioneOrario.getValue().getId());
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
                .bind(dataPrenotazioneMaestro.valueProperty().isNull()
                        .or(selezioneOrario.getSelectionModel().selectedIndexProperty().lessThan(0))
                        .or(selezioneSport.getSelectionModel().selectedIndexProperty().lessThan(0))
                        .or(listaMaestri.getSelectionModel().selectedItemProperty().isNull()));

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
                    listaMaestri.getItems().add(teacher);
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

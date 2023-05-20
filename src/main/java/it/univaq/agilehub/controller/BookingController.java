package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.*;
import it.univaq.agilehub.model.*;
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
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static it.univaq.agilehub.model.Sport.*;
import static it.univaq.agilehub.model.Type.NORMALE;

public class BookingController extends DataInitializable<User> implements Initializable {
    private BookingDao bookingService = new BookingDaoImpl();
    private PitchDao pitchService = new PitchDaoImpl();

    private TimeSlotDao timeSlotService = new TimeSlotDaoImpl();
    private User userLogged;
    @FXML
    Label bookingLabel = new Label();

    @FXML
    Label errorLabel = new Label();

    @FXML
    private ChoiceBox<String> selezioneTipologia;

    @FXML
    private ChoiceBox<Pitch> selezioneCampo;

    @FXML
    private ChoiceBox<TimeSlot> selezioneOrario;

    @FXML
    private DatePicker data;

    @FXML
    private TextField numeroPartecipanti;

    @FXML
    private Button prenota;

    @Override
    public void initializeData(User user) throws ViewException {
        super.initializeData(user);
        this.userLogged = user;
    }

    @FXML
    void dataAction(ActionEvent event) {
        selezioneOrario.getItems().clear();
        if(selezioneCampo.getValue() != null){
            String converted = Utility.dateOfBirthConverter(data.getValue().toString());
            try {
                ArrayList<Integer> unavailableTS = timeSlotService.unavailableTimeSlotId(converted, selezioneCampo.getValue());
                ArrayList<TimeSlot> allTimeSlot = timeSlotService.getAllTimeSlots();
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
    void numeroPartecipantiAction(ActionEvent event) {}

    private int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }


    @FXML
    void prenotaAction(ActionEvent event)  throws ViewException  {
        errorLabel.setText("");
        int max = 0;
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        currentDate.get(weekFields.weekOfWeekBasedYear());
        String sport = selezioneTipologia.getValue();

        Booking booking = new Booking();
        booking.setUserId(userLogged.getId());
        booking.setDateBooking(LocalDate.parse(Utility.dateOfBirthConverter(data.getValue().toString()) , DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        booking.setNumberPlayers(Integer.parseInt(numeroPartecipanti.getText()));
        booking.setSport(valueOf(sport));


        switch (booking.getSport()){
            case CALCETTO:
                max = 10;
                break;
            case PALLAVOLO:
                max = 12;
                break;
            case TENNIS:
                max = 2;
                break;
            case PADEL:
                max = 4;
                break;
            case BASKET:
                max = 10;
                break;
            default:
                break;
        }
        if(userLogged.getType() == NORMALE){
           try{
                if(booking.getDateBooking().get(weekFields.weekOfWeekBasedYear()) == (currentDate.get(weekFields.weekOfWeekBasedYear())) && booking.getDateBooking().isAfter(currentDate) && Integer.parseInt(numeroPartecipanti.getText()) <= max){
                    int booking_id = bookingService.insertBooking(booking);
                    bookingService.insertTimeBooking(selezioneCampo.getValue().getId(),booking_id,booking.getDateBooking().toString(), selezioneOrario.getValue().getId());
                    bookingLabel.setText("Prenotazione efettuata!");

                    selezioneCampo.setValue(null);
                    selezioneOrario.setValue(null);
                } else {
                    errorLabel.setText("Errore controllare numero prenotati");
                }
            }catch(Exception e){
               errorLabel.setText("Errore nella prenotazione");
               e.printStackTrace();
            }
        }


    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        prenota.disableProperty()
                        .bind(data.valueProperty().isNull()
                                .or(numeroPartecipanti.textProperty().isEmpty())
                                .or(selezioneTipologia.getSelectionModel().selectedIndexProperty().lessThan(0))
                                .or(selezioneCampo.getSelectionModel().selectedIndexProperty().lessThan(0))
                                .or(selezioneOrario.getSelectionModel().selectedIndexProperty().lessThan(0))
                        );

        for (Sport sport : Sport.values()) {
            selezioneTipologia.getItems().add(sport.name());
        }

        selezioneTipologia.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                        selezioneCampo.getItems().clear();
                        ArrayList<Pitch> pitchList;
                        try {
                            pitchList = (ArrayList<Pitch>) pitchService.getPitchBySport(selezioneTipologia.getItems().get(newValue.intValue()));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        for(Pitch pitch : pitchList){
                            selezioneCampo.getItems().add(pitch);
                        }
                    }
                });

        selezioneCampo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pitch>() {

            @Override
            public void changed(ObservableValue<? extends Pitch> observable, Pitch oldValue, Pitch newValue) {
                if(data.getValue() != null && newValue != null){
                    selezioneOrario.getItems().clear();
                    String converted = Utility.dateOfBirthConverter(data.getValue().toString());
                    try {
                        ArrayList<Integer> unavailableTS = timeSlotService.unavailableTimeSlotId(converted, newValue);
                        ArrayList<TimeSlot> allTimeSlot = timeSlotService.getAllTimeSlots();
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
        });

    }
}

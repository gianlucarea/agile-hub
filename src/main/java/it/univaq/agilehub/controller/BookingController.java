package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.BookingDao;
import it.univaq.agilehub.dao.BookingDaoImpl;
import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.utility.Utility;
import it.univaq.agilehub.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

import static it.univaq.agilehub.model.Sport.*;
import static it.univaq.agilehub.model.Type.NORMALE;

public class BookingController extends DataInitializable<User> implements Initializable {
    private BookingDao bookingService = new BookingDaoImpl();
    private User userLogged;
    @FXML
    Label bookingLabel = new Label();

    @FXML
    Label errorLabel = new Label();

    @FXML
    private ChoiceBox<String> campo;

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
    void dataAction(ActionEvent event) {}

    @FXML
    void numeroPartecipantiAction(ActionEvent event) {}

    private int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    @FXML
    void prenotaAction(ActionEvent event)  throws ViewException  {
        int max = 0;
        LocalDate currentDate = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        currentDate.get(weekFields.weekOfWeekBasedYear());
        String sport = campo.getValue();

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
                    bookingService.createBooking(booking);
                    bookingLabel.setText("Prenotazione efettuata!");
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
                        .bind(data.valueProperty().isNull().or(numeroPartecipanti.textProperty().isEmpty())); ;

        for (Sport sport : Sport.values()) {
            campo.getItems().add(sport.name());

        }
    }
}

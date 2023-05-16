package it.univaq.agilehub.controller;

import it.univaq.agilehub.dao.BookingDao;
import it.univaq.agilehub.dao.BookingDaoImpl;
import it.univaq.agilehub.model.Booking;
import it.univaq.agilehub.model.Sport;
import it.univaq.agilehub.model.User;
import it.univaq.agilehub.view.ViewException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrenotazioneController extends DataInitializable<User> implements Initializable {
    private BookingDao bookingService = new BookingDaoImpl();

    @FXML
    Label bookingLabel = new Label();
    @FXML
    private ChoiceBox<String> campo;

    @FXML
    private DatePicker data;

    @FXML
    private TextField numeroPartecipanti;

    @FXML
    private Button prenota;

    @FXML
    void dataAction(ActionEvent event) {

    }

    @FXML
    void numeroPartecipantiAction(ActionEvent event) {

    }



    @FXML
    void prenotaAction(ActionEvent event)  throws ViewException  {
        Booking booking = new Booking();
        String sport = campo.getValue();
        booking.setDateBooking(LocalDate.parse(data.getValue().toString()));
        booking.setNumberPlayers(Integer.parseInt(numeroPartecipanti.getText()));
        booking.setSport(Sport.valueOf(sport));
        bookingService.createBooking(booking);
        bookingLabel.setText("Prenotazione efettuata!");



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

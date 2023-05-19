package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.Pitch;

import java.sql.SQLException;
import java.util.List;

public interface PitchDao {

    List<Pitch> getPitchBySport(String Type) throws SQLException;

}

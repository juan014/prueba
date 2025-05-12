package com.example.desafioBackend.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;

//cosa pedida a chatGPT no sabia como resolverlo(no sirvio y no lo termine usando, tuve q cambiar banda de cosas por q
// se me rompia todo a cada rato
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {
    @Override
    public String convertToDatabaseColumn(LocalDate date) {
        return date != null ? date.toString() : null;  // formato ISO "yyyy-MM-dd"
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return dbData != null ? LocalDate.parse(dbData) : null;
    }
}

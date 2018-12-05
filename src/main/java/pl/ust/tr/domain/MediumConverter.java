package pl.ust.tr.domain;

import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MediumConverter implements AttributeConverter<Medium, String> {

    @Override
    public String convertToDatabaseColumn(Medium medium) {
        System.out.println("1. /////////////////////////////////////------------- CONVERTING_ABC");
        return medium.getValueForDb();
    }

    @Override
    public Medium convertToEntityAttribute(String dbValue) {
        System.out.println("2. /////////////////////////////////////------------- CONVERTING_XYZ");
        return Medium.fromDbValue(dbValue);
    }
}

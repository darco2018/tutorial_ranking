package pl.ust.tr.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MediumConverter implements AttributeConverter<Medium, String> {

    @Override
    public String convertToDatabaseColumn(Medium medium) {
        return medium.toDbValue();
    }

    @Override
    public Medium convertToEntityAttribute(String dbValue) {
        return Medium.fromDbValue(dbValue);
    }
}

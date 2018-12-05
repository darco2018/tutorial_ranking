package pl.ust.tr.domain;

public enum Medium {

    VIDEO("Video"), ARTICLE("Article"), COURSE("Course");

    private String dbValue;

    private Medium(String dbValue) { this.dbValue = dbValue;
    }

    public String getValueForDb() {
        return dbValue;
    }

    public static Medium fromDbValue(String dbValue){
        for (Medium medium : Medium.values()) { // UPPERCASE VALUES
            if(medium.dbValue.equalsIgnoreCase(dbValue))
                return medium;
        }
        return null;
    }
}

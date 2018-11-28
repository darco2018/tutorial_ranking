package pl.ust.tr.domain;

public enum Type {

    VIDEO("Video"), ARTICLE("Article"), COURSE("Course");
    private String label;

    Type(String label) {
        this.label = label;
    }

    public static Type findByLabel(String byLabel){
        for (Type type : Type.values()) {
            if(type.label.equalsIgnoreCase(byLabel))
                return type;
        }

        return null;
    }
}

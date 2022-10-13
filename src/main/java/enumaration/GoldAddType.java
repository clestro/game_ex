package enumaration;

public enum GoldAddType {
    USER("USER"),
    TASK("TASK");


    private String addType;

    GoldAddType(String addType) {
        this.addType = addType;
    }

    public String getAddType() {
        return addType;
    }
}

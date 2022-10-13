package enumaration;

public enum TaskEnum {
    CAPTURE_THE_VILLAGE("CAPTURE_THE_VILLAGE"),
    ROB_MERCHANTS("ROB_MERCHANTS"),
    ARRANGE_A_FAIR("ARRANGE_A_FAIR");

    private String task;

    TaskEnum(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }
}

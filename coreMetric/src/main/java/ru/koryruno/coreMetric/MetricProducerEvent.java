package ru.koryruno.coreMetric;

public class MetricProducerEvent {

    public String id;
    public String name;
    public String value;
    public String timestamp;

    public MetricProducerEvent() {}

    public MetricProducerEvent(String id, String name, String value, String timestamp) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}

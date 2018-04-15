package com.system.sys;

public class EventResult {
    private String system_code;
    private String city;
    private String event_id;
    private  Result result;

    public String getSystem_code() {
        return system_code;
    }

    public void setSystem_code(String system_code) {
        this.system_code = system_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

	@Override
	public String toString() {
		return "EventResult [system_code=" + system_code + ", city=" + city + ", event_id=" + event_id + ", result="
				+ result + "]";
	}
    
}

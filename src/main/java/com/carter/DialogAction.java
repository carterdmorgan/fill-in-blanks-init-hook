package com.carter;

import java.util.HashMap;
import java.util.Map;

/**
 * Model for returning data to Lex in the format it expects.
 *
 * @author carterdmorgan
 */
public class DialogAction {
    private String type;
    private Map<String, String> slots;

    public DialogAction(){
        slots = new HashMap<>();
    }

    public DialogAction(String type, Map<String, String> slots) {
        this.type = type;
        this.slots = slots;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, String> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "DialogAction{" +
                "type='" + type + '\'' +
                ", slots=" + slots +
                '}';
    }
}

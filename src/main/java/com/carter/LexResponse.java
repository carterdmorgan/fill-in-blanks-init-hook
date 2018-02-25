package com.carter;

import java.util.Map;

public class LexResponse {
    Map<String, Object> sessionAttributes;
    DialogAction dialogAction;

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }

    public void setDialogAction(DialogAction dialogAction) {
        this.dialogAction = dialogAction;
    }

    public LexResponse(Map<String, Object> sessionAttributes, DialogAction dialogAction) {
        this.sessionAttributes = sessionAttributes;
        this.dialogAction = dialogAction;
    }

    @Override
    public String toString() {
        return "LexResponse{" +
                "sessionAttributes=" + sessionAttributes +
                ", dialogAction=" + dialogAction +
                '}';
    }
}

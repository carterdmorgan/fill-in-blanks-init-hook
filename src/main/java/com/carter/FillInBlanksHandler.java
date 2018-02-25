package com.carter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class for receiving data from Lex, modifying it, and returning it in the format Lex expects.
 *
 * @author carterdmorgan
 */
public class FillInBlanksHandler implements RequestHandler<Map<String, Object>, Object> {
    /**
     * Lex can't distinguish between "Tell me more about it" and "Tell me more about
     * [Made Up Ride], so this method catches some of the common phrases someone might
     * use when looking for additional information and then populates the slot with its
     * counterpart from SessionAttributes.  This allows Lex to maintain context throughout
     * a conversation, so if a user asks "What's the wait time for Peter Pan?" and then
     * follows that up with "How tall do I have to be to ride it?" Lex knows the "it" in
     * this scenario refers to the last attraction mentioned, which would be Peter Pan.
     *
     * @param input     The map Lex sends which contains all of the input data
     * @param context   To be honest, I've never used this.
     * @return          A LexResponse with the slots properly filled from SessionAttributes
     *                  (if applicable) and set to delegate the rest to the Lex bot
     */
    @Override
    public Object handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> sessionAttributes = (Map<String, Object>) input.get("sessionAttributes");
        Map<String, Object> currentIntent = (Map<String, Object>) input.get("currentIntent");
        Map<String, Object> slots = (Map<String, Object>) currentIntent.get("slots");
        Map<String, Object> slotDetails = (Map<String, Object>) currentIntent.get("slotDetails");
        String originalValue = "null";

        ArrayList<String> slotTypes = new ArrayList<>();
        ArrayList<String> sessionInformation = new ArrayList<>();
        ArrayList<String> pertinent = new ArrayList<>();

        // All slots that need to be filled by current intent
        slotTypes.addAll(slots.keySet());

        // All information that the session attributes contain
        sessionInformation.addAll(sessionAttributes.keySet());

        for(String current : slotTypes){
            for(String current2 : sessionInformation){
                if(current2.equals(current)){
                    pertinent.add(current2);
                }
            }
        }

        DialogAction dialogAction = new DialogAction();

        for(String current : slotTypes){
            dialogAction.getSlots().put(current, (String) slots.get(current));
        }

        for(String current : pertinent){
            if(slots.get(current) == null) {
                Map<String, Object> slot = (Map<String, Object>) slotDetails.get(current);

                try{
                    originalValue = (String) slot.get("originalValue");
                    originalValue = originalValue.toLowerCase();
                }catch(NullPointerException e){
                    // Do nothing
                }

                try{
                    System.out.println("originalValue: " + originalValue);
                }catch(NullPointerException e){
                    System.out.println("originalValue: didn't work");
                }

                if(originalValue != null){
                    if (originalValue.equals("it") || originalValue.equals("this")
                            || originalValue.equals("that")) {
                        dialogAction.getSlots().put(current, (String) sessionAttributes.get(current));
                    } else {
                        dialogAction.getSlots().put(current, null);
                    }
                }
            }
        }

        dialogAction.setType("Delegate"); // This lets Lex decide what to do with the response

        return new LexResponse(sessionAttributes,dialogAction);
    }

}
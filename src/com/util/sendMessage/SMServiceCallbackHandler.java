
/**
 * SMServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */

    package com.util.sendMessage;

import com.util.sendMessage.SMServiceStub.SmAuthResponse;
import com.util.sendMessage.SMServiceStub.SmCheckSubmitResponse;

 
    /**
     *  SMServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SMServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SMServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SMServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for smAuth method
            * override this method for handling normal response from smAuth operation
            */
           public void receiveResultsmAuth(
                    SmAuthResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from smAuth operation
           */
            public void receiveErrorsmAuth(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for smCheckSubmit method
            * override this method for handling normal response from smCheckSubmit operation
            */
           public void receiveResultsmCheckSubmit(
                    SmCheckSubmitResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from smCheckSubmit operation
           */
            public void receiveErrorsmCheckSubmit(java.lang.Exception e) {
            }
                


    }
    
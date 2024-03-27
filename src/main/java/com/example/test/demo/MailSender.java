package com.example.test.demo;

import java.time.Duration;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.communication.email.models.EmailSendStatus;
import com.azure.core.util.Context;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;

public class MailSender {

    String connectionString = "endpoint=https://amlansmsservice.india.communication.azure.com/;accesskey=B+oF5sMpjzAus/DHSgOmx3VpqI6xMANyF/KLo3XSNvrgy44xpzydMnEz82xaBG5VamM5tTVPSwfQmavAWmgcWg==";

    public void sendMail(String message) {
        
        EmailClient emailClient = new EmailClientBuilder().connectionString(connectionString).buildClient();
        
        String[] emailobj = message.split("\\s*,\\s*");

        if(!emailobj[0].contains("com")){
            System.out.println("EmailId- "+emailobj[0]+" not valid.");
            return;
        }
        EmailMessage emailmessage = new EmailMessage()
        .setSenderAddress("DoNotReply@048791c5-80f4-4395-8955-89ee11d82a5b.azurecomm.net")
        .setToRecipients(emailobj[0])
        .setSubject("Appointnment with Doctor "+emailobj[1]+" cancelled for date "+emailobj[2])
        .setBodyPlainText("This email message is sent from Kala Paani Hospital. Please donot reply to this mail. You can visit our nearest centre and also book appointment online. For any queries, please contact abc@gmail.com");

        try
        {
            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailmessage, Context.NONE);

            PollResponse<EmailSendResult> pollResponse = null;

            Duration timeElapsed = Duration.ofSeconds(0);
            Duration POLLER_WAIT_TIME = Duration.ofSeconds(10);

            while (pollResponse == null
                    || pollResponse.getStatus() == LongRunningOperationStatus.NOT_STARTED
                    || pollResponse.getStatus() == LongRunningOperationStatus.IN_PROGRESS)
            {
                pollResponse = poller.poll();
                System.out.println("Email send poller status: " + pollResponse.getStatus());

                Thread.sleep(POLLER_WAIT_TIME.toMillis());
                timeElapsed = timeElapsed.plus(POLLER_WAIT_TIME);

                if (timeElapsed.compareTo(POLLER_WAIT_TIME.multipliedBy(18)) >= 0)
                {
                    throw new RuntimeException("Polling timed out.");
                }
            }

            if (poller.getFinalResult().getStatus() == EmailSendStatus.SUCCEEDED)
            {
                System.out.printf("Successfully sent the email (operation id: %s)", poller.getFinalResult().getId());
            }
            else
            {
                throw new RuntimeException(poller.getFinalResult().getError().getMessage());
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }

    }
}

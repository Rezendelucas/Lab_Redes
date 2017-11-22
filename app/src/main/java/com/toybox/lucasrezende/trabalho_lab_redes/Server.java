package com.toybox.lucasrezende.trabalho_lab_redes;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import ArquivoBase.AES;

/**
 * Created by LucasRezende on 22/11/2017.
 */

public class Server {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        String newString[] = new String[1];
        String secretKey = "secretKey";
        String hash;
        String newHash;

        ServerSocket welcomeSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("FROM CLIENT: " + clientSentence);
            newString = clientSentence.split(";");
            hash = AES.decrypt(newString[0], secretKey);

            if (hash.equals(newString[1])) {
                capitalizedSentence = newString[1].toUpperCase();
                System.out.println(capitalizedSentence);
                outToClient.writeBytes(AES.encrypt(capitalizedSentence, secretKey) + ";" + capitalizedSentence + '\n');
            }
        }
    }
}

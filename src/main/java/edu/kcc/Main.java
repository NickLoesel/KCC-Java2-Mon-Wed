/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kcc;

import edu.kcc.animal.Animal;
import edu.kcc.ui.UIUtility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author k0519415
 */
public class Main {
    private static final int PORT = 5555;
    private static final String HOST_NAME = "localhost"; 
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException, UnknownHostException, ParseException {
        UIUtility.showMessage("Program starting...");

        String menuTitle = "Main Menu";
        String[] menuOptions = {
            "1) Find an Animal",
            "2) Show lookup history",
            "3) Exit"
        };
        String prompt = "Your choice:";
        String errorMessage = "Invalid option.  Please try again.";
        String userChoice;

        // Start the primary program logic
        boolean quit  = true;
        while (quit) {
            userChoice = UIUtility.showMenuOptions(menuTitle,
                    prompt, menuOptions);
            switch (userChoice) {
                case "1":
                    UIUtility.showSectionTitle("Find an Animal");

                    String question = "Enter Animal name:";
                    String AnimalName = UIUtility.getUserString(question);
                    UIUtility.showMessage("Searching for Animal " + AnimalName
                            + "...");
                    System.out.println(getAnimalFromServer(AnimalName));
                    
                    break;
                case "2":
                    
                    break;
                    
                case "3":
                    quit = false; 
                    break;
                default:
                    UIUtility.showErrorMessage(errorMessage, true);
            }
        }
        UIUtility.showMessage("\nProgram complete.");
    }

    private static Animal getAnimalFromServer(String AnimalName)
            throws UnknownHostException, IOException, ParseException {
        Socket socket = new Socket(HOST_NAME, PORT);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .withLocale(Locale.US);
        DateTimeFormatter DateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            .withLocale(Locale.US);
        outputStream.writeUTF(AnimalName);
        outputStream.flush();
        
        String animalID = inputStream.readUTF();
        String animalName = inputStream.readUTF();
        String animalGender = inputStream.readUTF();
        String animalSpecies = inputStream.readUTF();

        int animalAge = inputStream.readInt();
        boolean animalFixed = inputStream.readBoolean();
        int animalLegs = inputStream.readInt();
        BigDecimal animalWeight = new BigDecimal
        (inputStream.readDouble(), MathContext.DECIMAL32);
        
        String animalDateAddedUnformmated = inputStream.readUTF();
        String animalLastFeedingTimeUnformatted = inputStream.readUTF();
        LocalDate animalDateAddedFormatted = LocalDate.parse(animalDateAddedUnformmated, Dateformatter);
        LocalDateTime animalLastFeedingTimeFormatted = LocalDateTime.parse(animalLastFeedingTimeUnformatted, DateTimeformatter);
        System.out.println(animalDateAddedFormatted);
        inputStream.close();
        outputStream.close();
        
            Animal animal = new Animal( animalID, animalName,
                    animalSpecies, animalGender, animalAge, animalFixed, animalLegs, animalWeight,
                animalDateAddedFormatted, animalLastFeedingTimeFormatted);
            
        return animal;
    }
    
}

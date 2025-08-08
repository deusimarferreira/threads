package com.example.threads.test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

interface IActions{
    public String unlockTheDoor();
    public String hackSecretPin(final String passcodeOfDoor) throws InterruptedException, ExecutionException;
    public String figureOutSafeDepositBoxNumber(final String passcodeOfDoor) throws InterruptedException, ExecutionException;
    public void openSafeDepositLock(final String safetyDespositBoxNumber, final String safetyDepositBoxPin);
    public boolean getRobberyVerdict();
}

class SafeCracking {
    public void rob(IActions actions) throws InterruptedException, ExecutionException {
        final String passcodeOfDoor = actions.unlockTheDoor();
        final String[] safeDespositBoxPin = {""};
        final String[] safeDespositBoxNumber = {""};
        
        Runnable task1 = () -> {
            try {
                safeDespositBoxPin[0] = actions.hackSecretPin(passcodeOfDoor);
            } catch (InterruptedException | ExecutionException ex) {
                
            }
        };

        Runnable task2 = () -> {
            try {
                safeDespositBoxNumber[0] = actions.figureOutSafeDepositBoxNumber(passcodeOfDoor);
            } catch (InterruptedException | ExecutionException ex) {
                
            }
        };

        CompletableFuture features[] = new CompletableFuture[2];
        features[0] = CompletableFuture.runAsync(task1);
        features[1] = CompletableFuture.runAsync(task2);
        CompletableFuture.allOf(features).join();

        // Thread thread1 = new Thread(task1);
        // Thread thread2 = new Thread(task2);
        
        // thread1.start();
        // thread2.start();
        
        // thread1.join();
        // thread2.join();
        actions.openSafeDepositLock(safeDespositBoxNumber[0], safeDespositBoxPin[0]);
    }
}

public class Solution {
    public static void main(String[] args) throws InterruptedException, ExecutionException{
        IActions actions = Actions.actions; 
        new SafeCracking().rob(actions);
        System.out.println(actions.getRobberyVerdict());
    }
}

class Actions implements IActions{
    public static IActions actions = new Actions();

    private String passcodeOfDoor;
    private String safetyBoxNumber;
    private String pinOfSafetyBox;

    private String passedPasscodeOfDoor;
    private String passedSafetyBoxNumber;
    private String passedPinOfSafetyBox;

    private Actions() {
        this.passcodeOfDoor = randomStringGenerator(10);
        this.safetyBoxNumber = randomStringGenerator(10);
        this.pinOfSafetyBox = randomStringGenerator(10);
    }

    private String randomStringGenerator(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        return generatedString;
    }
    
    public boolean getRobberyVerdict(){
        return (passcodeOfDoor == passedPasscodeOfDoor) && (pinOfSafetyBox == passedPinOfSafetyBox)
            && (safetyBoxNumber == passedSafetyBoxNumber);
    }

    public String unlockTheDoor() {
        return this.passcodeOfDoor;
    }
  
    public String hackSecretPin(final String passcodeOfDoor) throws InterruptedException, ExecutionException {
        this.passedPasscodeOfDoor = passcodeOfDoor;
        Thread.sleep(2000);
        return this.pinOfSafetyBox;
    }
  
    public  String figureOutSafeDepositBoxNumber(final String passcodeOfDoor) throws InterruptedException, ExecutionException {
        Thread.sleep(2000);
        return this.safetyBoxNumber;
    }
  
    public void openSafeDepositLock(final String safetyDespositBoxNumber, final String safetyDepositBoxPin) {
        this.passedSafetyBoxNumber = safetyDespositBoxNumber;
        this.passedPinOfSafetyBox = safetyDepositBoxPin;
    }
}
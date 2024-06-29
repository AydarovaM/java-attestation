// Реализуйте структуру телефонной книги с помощью HashMap.
// Программа также должна учитывать, что в во входной структуре будут повторяющиеся имена с разными телефонами, их необходимо считать, как одного человека с разными телефонами. Вывод должен быть отсортирован по убыванию числа телефонов.

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Phonebook {

    //region attributes
    private static HashMap<String, ArrayList<Integer>> phoneBook = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    //endregion

    //region main
    public static void main(String[] args) {
        System.out.println("Welcome to our phonebook");
                
        //TODO: REMOVE! JUST FOR TESTS
        SetTestData();

        boolean exit = false;
        while(!exit){
            System.out.println("Choose option: \n 0 - exit \n 1 - print phonebook \n 2 - add phone \n 3 - delete phone \n 4 - delete name \n 5 - find by name");
            System.out.println(">");
            int option = scanner.nextInt();
            exit = option ==0;
            switch(option){
                case 1: printPhonebook(); break;
                case 2: addPhoneInteractive(); break;
                case 3: deletePhoneInteractive(); break;
                case 4: deleteNameInteractive(); break;
                case 5: findByNameInteractive(); break;
            }
        }

        scanner.close();   
    }
    //endregion 

    //region TEST
    private static void SetTestData(){
        addNamePhone("Marina", 93939393);
        addNamePhone("Marina", 76949430);

        addNamePhone("Petya", 595840);
        addNamePhone("Petya", 45854984);
        addNamePhone("Petya", 5844954);
        addNamePhone("Petya", 5959858);

        addNamePhone("Vasya", 3453452);
        addNamePhone("Vasya", 678567867);
        addNamePhone("Vasya", 2342523);
        addNamePhone("Vasya", 912874575);

        addNamePhone("Irma", 030303030);
        addNamePhone("Irma", 02020202);
        addNamePhone("Irma", 01010101);
    }
    //endregion

    //region Basic Functions
    public static void printPhonebook() {
        HashMap<Integer, ArrayList<String>> phoneCountsToNames = new HashMap<>();
        phoneBook.forEach((name, numbers) -> {
            if(phoneCountsToNames.containsKey(numbers.size())){
                ArrayList<String> value = phoneCountsToNames.get(numbers.size());
                value.add(name);
            } else {
                    ArrayList<String> value = new ArrayList<>();
                    value.add(name);
                    phoneCountsToNames.put(numbers.size(), value);
            }
            
        });
        List<Integer> phoneCounts = new ArrayList<>(phoneCountsToNames.keySet());
        Collections.sort(phoneCounts);
        Collections.reverse(phoneCounts);

        for(int phoneCount : phoneCounts)
        {
            ArrayList<String> namesWithSameCount = phoneCountsToNames.get(phoneCount); 
            for(String name : namesWithSameCount)
            {
                System.out.println(name + ": ");
                ArrayList<Integer> phoneNumbers = phoneBook.get(name);
                for(Integer phoneNum : phoneNumbers)
                {
                    System.out.println("   - " + phoneNum);
                }
            }
        }
    }

    public static void addNamePhone(String name, Integer phoneNum) {
        if(phoneBook.containsKey(name)){
            ArrayList<Integer> value = phoneBook.get(name);
            value.add(phoneNum);
        } else {
            ArrayList<Integer> nums = new ArrayList<>();
            nums.add(phoneNum);
            phoneBook.put(name, nums);
        }
    }
       
    public static void deleteNamePhone(String name, Integer num) {
        //Check name existance
        if(!phoneBook.containsKey(name)){
            System.out.println("Can't find name: " + name);
        } else {
            ArrayList<Integer> nums = phoneBook.get(name);
            //Check index's correctness
            if(!nums.contains(num))
            {
                System.out.println("Can't delete num " + num + " for name \"" + name + "\".");
                return;
            }
            nums.remove(num);
            //if name has no numbers, delete entry
            if(nums.size() == 0)
                phoneBook.remove(name);
        }
    }

    public static void deleteName(String name) {
        //Check name existance
        if(!phoneBook.containsKey(name)){
            System.out.println("Can't find name: " + name);
        } else {
            phoneBook.remove(name);
        }
    }

    public static ArrayList<Integer> findByName(String name) {
        if(phoneBook.containsKey(name)){
           return phoneBook.get(name);
        } else{ 
            return new ArrayList<>();
        }
    }

    public static Integer getNumByNameAndIndex(String name, int numIndex) {
        if(phoneBook.containsKey(name)){
            ArrayList<Integer> phoneNums = phoneBook.get(name);
            if( numIndex < phoneNums.size())
                return phoneNums.get(numIndex);
            return -1;
        } else{ 
            return -1;
        }
    }
    //endregion

    //region Interactive Methods
    private static void addPhoneInteractive() {
        //TODO: restrict possibles nums: non-negative
        //Communication with user
        System.out.println("Enter name to add");
        System.out.println(">");
        String name = scanner.next();
        System.out.println("Enter num to add");
        System.out.println(">");
        int num = scanner.nextInt();
        System.out.println("Add num " + num + " to name \"" + name + "\"? (y/n)" );
        System.out.println(">");
        String addNum = scanner.next();

        //Call basic function
        if(addNum.equals("y"))
            addNamePhone(name, num);
    }   

    private static void findByNameInteractive() {
        //Communication with user
        System.out.println("Enter name to find");
        System.out.println(">");
        String name = scanner.next();
        if(!phoneBook.containsKey(name)){
            System.out.println("No entry was found for name \"" + name + "\"");
            return;
        }

        //Call basic function
        ArrayList<Integer> nums = findByName(name);

        //Output
        System.out.println(name + ":");
        for(int index = 0; index < nums.size(); index++){
            System.out.println("   " + index + " - " + nums.get(index));
        }
    }

    private static void deletePhoneInteractive() {
        //Communication with user
        System.out.println("Enter name to delete phone from");
        System.out.println(">");
        String name = scanner.next();
        System.out.println("Enter phone index");
        System.out.println(">");
        int numIndex = scanner.nextInt();
        int foundNum = getNumByNameAndIndex(name, numIndex);
        if(foundNum == -1){
            System.out.println("Can't delete phone num by index " + numIndex + " for name \"" + name + "\". No such name or num");
            return;
        }
        System.out.println("Delete num " + foundNum + " from name \"" + name + "\"? (y/n)" );
        System.out.println(">");
        String deleteNum = scanner.next();

        //Call basic function
        if(deleteNum.equals("y"))
            deleteNamePhone(name, foundNum);
    }

    private static void deleteNameInteractive() {
        //Communication with user
        System.out.println("Enter name to delete");
        System.out.println(">");
        String name = scanner.next();
        System.out.println("Delete entry \"" + name + "\"? (y/n)" );
        System.out.println(">");
        String deleteName = scanner.next();

        //Call basic function
        if(deleteName.equals("y"))
            deleteName(name);
    }
    //endregion
}
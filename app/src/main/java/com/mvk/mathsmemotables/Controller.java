package com.mvk.mathsmemotables;

import android.util.Log;

/**
 * Created by marcin on 10/08/2017.
 */

class Controller {
    //booleans to change tables
    boolean one;
    boolean two;
    boolean three;
    boolean four;
    boolean five;
    boolean six;
    boolean seven;
    boolean eight;
    boolean nine;

    private static final Controller ourInstance = new Controller();

    static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
        //sets to default value of true
        one = two = three = four = five = six = seven = eight = nine = true;
    }

    //disable or enable selected table
    //return true if changed / false if can not be changed
    public boolean enableDisableTable(String number){
        boolean toReturn;
        switch (number){
            case "one":
                toReturn = flipValue(one);
                break;
            case "two":
                toReturn = flipValue(two);
                break;
            case "three":
                toReturn = flipValue(three);
                break;
            case "four":
                toReturn = flipValue(four);
                break;
            case "five":
                toReturn = flipValue(five);
                break;
            case "six":
                toReturn = flipValue(six);
                break;
            case "seven":
                toReturn = flipValue(seven);
                break;
            case "eight":
                toReturn = flipValue(eight);
                break;
            case "nine":
                toReturn = flipValue(nine);
                break;
            default:
                toReturn = false;
                break;
        }
        return toReturn;
    }

    //TODO: primitives are passed by value not referenced change the method!
    //changes value of table opposite value if at least one more is selected
    //return true if changed / false if can not be changed
    private boolean flipValue(boolean table){
        if (checkIfAtLeastTwoTablesAreEnable()){
            if (table==true){
                table=false;
                Log.d("TAG", one+" one?");
            }
            else
                table=true;
            return true;
        }
        else
            return false;
    }

    //returns true if we can disable table
    private boolean checkIfAtLeastTwoTablesAreEnable(){
        int counter = 0;
        if (one)
            counter++;
        if (two)
            counter++;
        if (three)
            counter++;
        if (four)
            counter++;
        if (five)
            counter++;
        if (six)
            counter++;
        if (seven)
            counter++;
        if (eight)
            counter++;
        if (nine)
            counter++;
//TODO: allow to do single table (need to change layout to max 18 cards!!!)
        if (counter>2)
            return true;
        else
            return false;
    }

    //checks if number passed in is within the table that is selected
    //returns true if number is within rane / false if its not
    public boolean checkNumber(int number){
        Log.d("TAG", "numberChecking "+number);
        boolean toReturn = false;
        if (number == 1)
            if (one)
                toReturn = true;
        if (number == 2)
            if (two)
                toReturn = true;
        if (number == 3)
            if (three)
                toReturn = true;
        if (number == 4)
            if (four)
                toReturn = true;
        if (number == 5)
            if (five)
                toReturn = true;
        if (number == 6)
            if (six)
                toReturn = true;
        if (number == 7)
            if (seven)
                toReturn = true;
        if (number == 8)
            if (eight)
                toReturn = true;
        if (number == 9)
            if (nine)
                toReturn = true;
        Log.d("TAG", "returning "+toReturn);
        return toReturn;
    }
}

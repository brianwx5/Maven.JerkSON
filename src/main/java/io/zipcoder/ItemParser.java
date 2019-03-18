package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    public String regexForKeyValue = "[!|:|@|\\^|\\*|\\%|;]";
    public int numberOfErrors = 0;

    public String patternForSingleItem =
              "naMe" + regexForKeyValue + "(\\w*)" +regexForKeyValue
            + "price" + regexForKeyValue + "(\\d*.?\\d*)" + regexForKeyValue
            + "type"  + regexForKeyValue + "(\\w*)"       + regexForKeyValue
            + "expiration" + regexForKeyValue + "(\\d{1,2}/\\d{1,2}/\\d{2,4})#";



    public List<Item> parseItemList(String valueToParse) {
        ArrayList<Item> itemList = new ArrayList<>();
        String[] itemByLine = valueToParse.split("(?<=#)#");
        for (String s: itemByLine) {
            try {
                itemList.add(parseSingleItem(s));
            } catch (ItemParseException e) {
                e.printStackTrace();
                numberOfErrors++;

            }
        } return itemList;
    }




    public Item parseSingleItem(String singleItem) throws ItemParseException {
        singleItem = singleItem.replaceAll("(?<=[a-zA-Z])0(?=[a-zA-Z])","o");
        Item item = null;
        try{
        Pattern pattern = Pattern.compile(patternForSingleItem,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(singleItem);

        matcher.find();
        String name = matcher.group(1).toLowerCase();
        if(name.equals("")) {
            throw new ItemParseException();
        }
        String price = matcher.group(2);
            if(price.equals("")) {
                throw new ItemParseException();
            }
        String type = matcher.group(3).toLowerCase();
            if(type.equals("")) {
                throw new ItemParseException();
            }
        String expiration = matcher.group(4);
            if(expiration.equals("")) {
                throw new ItemParseException();
            }

        Double priceToDouble = Double.parseDouble(price);


        item = new Item(name,priceToDouble,type,expiration);}

        catch (Exception e) {
            e.printStackTrace();
            throw new ItemParseException();
        }

        return item;


    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }
}

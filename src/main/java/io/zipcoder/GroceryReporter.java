package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryReporter {
    private final String originalFileText;
    ItemParser itemParser = new ItemParser();
    HashMap<String, ArrayList<Item>> itemHashMap = new HashMap<>();

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }


    public HashMap<String, ArrayList<Item>> itemToHashMap(String originalFileText) {
        List<Item> itemList = itemParser.parseItemList(originalFileText);
        for (Item item : itemList) {
            ArrayList<Item> temp = itemHashMap.getOrDefault(item.getName(), new ArrayList<>());
            temp.add(item);
            itemHashMap.put(item.getName(), temp);
        }
        return itemHashMap;
    }

    public HashMap<Double, Integer> priceAndCount(ArrayList<Item> arrayListOfItems) {
        HashMap<Double, Integer> priceCountHashMap = new HashMap<>();
        for (Item item : arrayListOfItems) {
            Integer count = priceCountHashMap.getOrDefault(item.getPrice(),0);
            priceCountHashMap.put(item.getPrice(), count + 1);
        }
        return priceCountHashMap;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.itemHashMap = itemToHashMap(originalFileText);

        for (Map.Entry<String, ArrayList<Item>> entry : itemHashMap.entrySet()) {
            sb.append("name:    " + entry.getKey() + "        seen:" + entry.getValue().size() + " times\n"
                    + "=============        =============\n\n");

            ArrayList<Item> subArray = entry.getValue();
            HashMap<Double, Integer> priceAndCount = priceAndCount(subArray);
            for (Map.Entry<Double, Integer> price : priceAndCount.entrySet()) {
                sb.append("price:   " + price.getKey() + "         seen:" + price.getValue() + " times\n"
                        + "-------------        -------------\n");
            }

        }

        sb.append("Errors                    Seen:" + itemParser.getNumberOfErrors() +" times\n");
        return sb.toString();
    }

}



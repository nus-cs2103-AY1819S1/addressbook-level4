package seedu.souschef.model.ingredient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IngredientDictionary {
    public static ArrayList<String> dictionary;
    // read and create dictionary

    BufferedReader br;

    public void getDictionary(BufferedReader br, ArrayList<String> dictionary) {
        try {
            br = new BufferedReader(new FileReader("ingredient_dictionary.txt"));
            String line;
            while ((line = br.readLine()) != null) {

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

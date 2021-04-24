/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        Random rand = new Random();
        if (prefix == "") {
            return words.get(rand.nextInt(words.size()));
        }
        else {
            int low = 0;
            int high = words.size() - 1;
            String word =  binarySearch(prefix, null, low, high);
            return word;
        }
    }



    private String binarySearch(String prefix, String foundWord, int low, int high) {
        int index;
        if (low > high) {
            return null;
        }
        index = (low + high) / 2;
        if ((words.get(index).length() > prefix.length()) &&
        (prefix.compareTo(words.get(index).substring(0, prefix.length())) == 0)) {
            Log.d("found word", words.get(index));
            foundWord = words.get(index);
        }
        else if (prefix.compareTo(words.get(index)) < 0) {
            foundWord = binarySearch(prefix, foundWord, low, index - 1);
        }
        else {
            foundWord = binarySearch(prefix, foundWord, index + 1, high);
        }
        return foundWord;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;
        return selected;
    }
}


package talos.index.logic;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author castillobg
 */
public class WordMap {
    
    TreeMap<String, Word> wordMap;
    
    public WordMap(String[] docs){
        wordMap = scanDocs(docs);
    }
    
    private TreeMap<String, Word> scanDocs(String[] docs){
        this.wordMap = new TreeMap<>();
        for(int i = 0; i < docs.length; i++){
            String[] parsedDoc = parseDocument(docs[i]);
            for(int j = 0; j < parsedDoc.length; j++){
                String parsed = parsedDoc[j].toLowerCase();
                Index index = new Index(j, i);
                if(!parsed.equals("")){
                    if(!wordMap.containsKey(parsed)){
                        Word word = new Word(parsed);
                        word.addIndex(index);
                        wordMap.put(parsed, word);
                    }
                    else{
                        wordMap.get(parsed).addIndex(index);
                    }
                }
            }
        }
        return wordMap;
    }
    
    /**
     * 
     * @param doc The document to parse into words
     * @return The list of words
     */
    private String[] parseDocument(String doc){
        String[] parsedDoc = doc.replaceAll("[,]", "").split("[\\s]");
        return parsedDoc;
    }
    
    /**
     *
     * @param query Array of strings to look for.
     * @return
     */
    public ArrayList<Word> searchWords(String[] query){
        ArrayList<Word> results = new ArrayList<>();
        
        for(int i = 0; i < query.length; i++){
        	Word res = searchWord(query[i]);
        	if(res != null){
        		results.add(res);
        	}
        }
        return results;
    }
    
    public Word searchWord(String query){
        String formattedStr = query.toLowerCase();
    	if(wordMap.containsKey(formattedStr)){
            return wordMap.get(formattedStr);
        }
    	return null;
    }
}

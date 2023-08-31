package PPClean.Data;

import java.util.List;

import static PPClean.Configuration.DATA_SEPARATOR;
import static PPClean.Configuration.DATA_SEPARATOR;

/**
 * Data structure to represent a table row
 * It can be augmented with a key
 */
public class Record {
    private List<String> content;
    private String key;

    public Record(List<String> content, String key) {
        this.content = content;
        this.key = key;
    }

    public Record(List<String> content) {
        this.content = content;
    }

    /**
     * Generates and sets a key based on the content of the Record
     * For key generation, whitespaces are omitted
     * The key components are concatenated in the order they appear in the Record's content
     * @param keyComponents Each component indicates how many characters of the content
     *                      value at the same list position are integrated in the key
     *                      Enter 0 to omit a Record value
     */
    public void generateKey(int[] keyComponents) {
        StringBuilder key = new StringBuilder();
        // BEGIN SOLUTION

        //check if key components and content have the same length -> throw exception if not
        if(keyComponents.length != content.size()){
            throw new IllegalArgumentException("keyComponents and content have different length");
        }
        //generate key
        for(int i = 0; i < keyComponents.length; i++){
            //remove space
            content.set(i, content.get(i).replaceAll("\\s", ""));

            //check if keyComponents[i] is in range -> throw exception if not
            if(keyComponents[i] < 0 ){
                throw new IllegalArgumentException("keyComponents out of range");
            } else if (keyComponents[i] > content.get(i).length()){
                keyComponents[i] = content.get(i).length();
            }
            //add key component to key
            key.append(content.get(i).substring(0, keyComponents[i]));
        }

        // END SOLUTION
        this.key = key.toString();
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < content.size(); i++) {
            str.append(content.get(i).toString());
            if (i + 1 < content.size()) { // not the last element
                str.append(DATA_SEPARATOR);
            } else { // last element, add newline character
                str.append("\n");
            }
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Record) {
            Record r = (Record) obj;
            if (r.getContent().size() != this.content.size()) {
                return false;
            }
            for (int i = 0; i < this.content.size(); i++) {
                if (!this.content.get(i).equals(r.getContent().get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.getContent().get(0).hashCode();
    }
}

package src.ga.problems;

public class Item {
    // Attributes
    String desc;
    int weight;
    int value;
    // Constructor
    private Item(String desc, int weight, int value) {
        this.desc = desc;
        this.weight = weight;
        this.value = value;
    }

    //getter and Setter
    public String getDesc() {
        return desc;
    }
    public int getWeight() {
        return weight;
    }
    public int getValue() {
        return value;
    }

    public String setDesc(String desc) {
        return this.desc = desc;
    }
    public int setWeight(int weight) {
        return this.weight = weight;
    }
    public int setValue(int value) {
        return this.value = value;
    }

    /**
     * method to create one item
     * @param weight the weight of the item
     * @param value the value of the item
     * @return the item
     */
    public static Item createItem(String desc, int weight, int value) {
        return new Item(desc, weight, value);
    }

}

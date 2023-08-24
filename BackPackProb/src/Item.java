public class Item {

    int weight;
    int value;
    // Constructor
    private Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    //getter and Setter
    public int getWeight() {
        return weight;
    }
    public int getValue() {
        return value;
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
    public static Item createItem(int weight, int value) {
        return new Item(weight, value);
    }

}

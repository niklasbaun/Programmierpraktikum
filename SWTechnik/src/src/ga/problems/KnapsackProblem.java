package src.ga.problems;

import src.ga.framework.model.NoSolutionException;
import src.ga.framework.model.Problem;
import src.ga.framework.model.Solution;

import java.util.ArrayList;
import java.util.List;

public class KnapsackProblem implements Problem {

    int weightLimit;

    List<Item> itemList = new ArrayList<>();

    //Constructor
    public KnapsackProblem (int weightLimit){
        this.weightLimit = weightLimit;
    }

    //getter and setter
    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }
    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getWeightLimit(){
        return this.weightLimit;
    }

    public void addItem(Item item){
        itemList.add(item);
    }

    public Item getItemFromPos(int pos){
        return itemList.get(pos);
    }
    public List<Item> getItemList(){
        return itemList;
    }

    /**
     * method to get a solution for the knapsackProblem
     * @return a posssible Solution
     * @throws NoSolutionException
     */
    @Override
    public Solution createNewSolution() throws NoSolutionException {
        KnapsackSolution solution = new KnapsackSolution(this, new ArrayList<>());
        List<Item> items = this.getItemList();
        int weight = 0;
        int value = 0;

        //insert items into the knapsack till weightLimit is reached
        for (int i = 0; i < items.size(); i++) {
            if (weight + items.get(i).getWeight() <= this.getWeightLimit()) {
                weight += items.get(i).getWeight();
                value += items.get(i).getValue();
                //add item to solution
                solution.content.add(items.get(i));
                //remove item from items
                items.remove(i);
            }
        }
        //if no item fits into the knapsack throw exception
        if (solution.content.isEmpty()) {
            throw new NoSolutionException("No possible Solution was found. Knapsack is too small.");
        }
        //set fitness
        solution.setFitness(value);
        //set the itemlist
        this.itemList = items;
        return solution;
    }
}

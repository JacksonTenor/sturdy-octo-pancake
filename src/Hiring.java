
import sun.awt.image.ImageWatched;

import java.util.*;

public class Hiring {
    private static ArrayList<Queue<Integer>> companies = new ArrayList<>();
    private static ArrayList<Stack<Integer>> programmers = new ArrayList<>();

    public static void main(String[] args){
        Integer[][] companyArr = {{2,5,1,3,4}, {1,2,3,4,5}, {5,3,2,1,4}, {1,3,2,4,5}, {2,3,5,4,1}};
        Integer[][] programmerArr = {{5,1,4,2,3},{4,5,2,1,3},{4,2,3,5,1},{3,2,4,1,5},{1,4,2,3,5}};
        companies = makePreferencesQueue(companyArr);
        programmers = makePreferencesStack(programmerArr);

        System.out.println(companies);
        System.out.println(programmers);
        hireEmployees(companies, programmers);

    }

    static ArrayList<Queue<Integer>> makePreferencesQueue(Integer[][] arr){
        ArrayList<Queue<Integer>> list = new ArrayList<>();
        for(Integer[] innerArr: arr){
            Queue<Integer> queue = new LinkedList<>();
            Collections.addAll(queue, innerArr);
            list.add(queue);
        }
        return list;
    }
    static ArrayList<Stack<Integer>> makePreferencesStack(Integer[][] arr){
        ArrayList<Stack<Integer>> list = new ArrayList<>();
        for(Integer[] innerArr: arr){
            Stack<Integer> stack = new Stack<>();
            Collections.addAll(stack, innerArr);
            list.add(stack);
        }
        return list;
    }
    static void hireEmployees(ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers){
        Integer[] hired = new Integer[companies.size()];
        for(int i=0; i<companies.size(); i++){
            Queue<Integer> company = companies.get(i);
            Integer toBeHired = hireEmployee(company, programmers, i + 1);
            Integer newSearchCompany = null;
            for(Integer j: hired){
                if(toBeHired.equals(j)){
                    newSearchCompany = j;
                    hired[j] = null;
                }
            }
            hired[i] = toBeHired;
            if(newSearchCompany != null) {
                hired[newSearchCompany] = hireEmployee(companies.get(newSearchCompany), programmers, newSearchCompany + 1);
            }

        }
        System.out.println(Arrays.toString(hired));
    }

    static int hireEmployee(Queue<Integer> company, ArrayList<Stack<Integer>> programmers, int companyName){
        Integer employeeNum = null;
        while(employeeNum == null) {
            Stack<Integer> candidate = programmers.get(company.peek() -1);
            if (candidate.contains(companyName)) { //determines if the programmer would prefer this company to their current, or always true if programmer has no job.
                employeeNum = company.peek();
                while (candidate.peek() != companyName) {
                    candidate.pop();
                }
            }else{
                company.poll();
            }
        }
        return employeeNum;
    }
}

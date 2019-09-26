
import sun.awt.image.ImageWatched;

import java.util.*;

public class Hiring {
    private static ArrayList<Queue<Integer>> companies = new ArrayList<>();
    private static ArrayList<Stack<Integer>> programmers = new ArrayList<>();

    public static void main(String[] args){
        //Integer[][] companyArr = {{2,5,1,3,4}, {1,2,3,4,5}, {5,3,2,1,4}, {1,3,2,4,5}, {2,3,5,4,1}};
        //Integer[][] programmerArr = {{5,1,4,2,3},{4,5,2,1,3},{4,2,3,5,1},{3,2,4,1,5},{1,4,2,3,5}};
        Integer[][] companyArr = {{3,2,1}, {3,2,1}, {2,3,1}};
        Integer[][] programmerArr = {{2,1,3}, {1,2,3}, {2,1,3}};
        companies = makePreferencesQueue(companyArr);
        programmers = makePreferencesStack(programmerArr);

        System.out.println("Companies: " + companies);
        System.out.println("Programmers: " + programmers);
        System.out.println("----------------------------");
        Integer[] solution = hireEmployees(companies, programmers);
        System.out.println(checkSolution(companies,programmers, solution));
        //Generate random data
        Random rng = new Random();
        int size = 3;
        companies = new ArrayList<>();
        programmers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            LinkedList<Integer> q = new LinkedList<>();
            LinkedList<Integer> s = new LinkedList<>();
            for (int j = 1; j <= size; j++) {
                s.add(j);
                q.add(j);
            }
            Collections.shuffle(q);
            Collections.shuffle(s);
            companies.add(q);
            Stack programmerStack = new Stack();
            programmerStack.addAll(s);
            programmers.add(programmerStack);
        }
        System.out.println(companies);
        System.out.println(programmers);
        solution = hireEmployees((ArrayList<Queue<Integer>>) companies.clone(), (ArrayList<Stack<Integer>>) programmers.clone());
        System.out.println(checkSolution(companies, programmers, solution));

        System.out.println(Arrays.toString(solution));

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
    static Integer[] hireEmployees(ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers){
        Integer[] hired = new Integer[companies.size()];
        for(int i=0; i<companies.size(); i++){
            Queue<Integer> company = companies.get(i);
            hireEmployee(company, programmers, hired, i);

        }
        return hired;
    }

    static void hireEmployee(Queue<Integer> company, ArrayList<Stack<Integer>> programmers,  Integer[] hired, Integer companyIndex){
        Integer employeeNum = null;
        while(employeeNum == null) {
            Stack<Integer> candidate = programmers.get(company.peek() -1);
            if (candidate.contains(companyIndex + 1)) { //determines if the programmer would prefer this company to their current, or always true if programmer has no job.
                employeeNum = company.peek();
                while (candidate.peek() != companyIndex + 1) {
                    candidate.pop();
                }
            }else{
                company.poll();
            }
        }
        replaceEmployee(hired, companyIndex, employeeNum,  companies, programmers);

    }

    static void replaceEmployee(Integer[] hired, Integer companyHiring, Integer programmerHired, ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers){
        Integer nowHiring = null;
        for (int j = 0; j < hired.length; j++) {
            if (programmerHired.equals(hired[j])) {
                nowHiring = j;
                hired[j] = null;
                break;
            }
        }
        hired[companyHiring] = programmerHired;
        if (nowHiring != null) {
            hireEmployee(companies.get(nowHiring), programmers, hired, nowHiring );
        }

    }

    static boolean checkSolution(ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers, Integer[] solution){
        for(int i = 0; i<solution.length; i++){
            for(int j=0; j<solution.length; j++){
                if(i == j){
                    continue;
                }
                if(companyWantsSwap(new LinkedList<>(companies.get(i)), solution[i] - 1, solution[j] - 1) && programmerWantsSwap((Stack<Integer>) programmers.get(solution[j] - 1).clone(), i, j)){
                    System.out.println(j + " should take the job of " + i);
                    return false;
                }
            }
        }

        return true;
    }

    static boolean companyWantsSwap(Queue<Integer> company, int current, int option){
        while(!company.isEmpty()){
            Integer preference = company.poll();
            if(current == preference){
                return true;
            }
            if(option == preference){
                return false;
            }
        }
        return false;
    }
    static boolean programmerWantsSwap(Stack<Integer> programmer, int current, int option){
        while(!programmer.isEmpty()){
            Integer preference = programmer.pop();
            if(current == preference){
                return false;
            }
            if(option == preference){
                return true;
            }
        }
        return false;
    }

}

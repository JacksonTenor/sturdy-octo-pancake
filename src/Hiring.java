import java.util.*;

public class Hiring {
    private static ArrayList<Queue<Integer>> companies = new ArrayList<>();
    private static ArrayList<Stack<Integer>> programmers = new ArrayList<>();

    public static void main(String[] args){
        Integer[][] companyArr = {{2,3,1}, {2,3,1}, {1,3,2}};
        Integer[][] programmerArr = {{1,2,3}, {3,2,1}, {2,1,3}};

        companies = makePreferencesQueue(companyArr);  //DATA: Companies is a Queue
        programmers = makePreferencesStack(programmerArr);  //DATA: Programmers is a stack

        System.out.println("Company A, B, C; read left to right amongst each as highest priority");
        System.out.println("Companies: " + companies + "\n");
        System.out.println("Programmer 1, 2, 3; read left to right amongst each as highest priority");
        System.out.println("Programmers: " + programmers+ "\n");
        Integer[] solution = hireEmployees(companies, programmers);
        System.out.println("Solution is valid: " + checkSolution(companies,programmers, solution) + "\n");
        System.out.println("Solution from the perspective of the employer's preferences of programmers");
        System.out.println("solution: " + Arrays.toString(solution) + "\n");

        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("random data below, separated by one line each");
        System.out.println("----------------------------");
        System.out.println("----------------------------" + "\n");
        //Generate random data
        for(int runXTests=0; runXTests<5; runXTests++) { //TESTING: Change R to change amount of test runs
            Random rng = new Random();
            int size = rng.nextInt(7) + 3;  //TESTING: Determines amount of companies/programmers, from
                                                  //sizes 3 to 9 (nextInt from 0 [incl] to specified number [excl])
            companies = new ArrayList<>();
            programmers = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                LinkedList<Integer> comp = new LinkedList<>();
                LinkedList<Integer> prog = new LinkedList<>();
                for (int j = 1; j <= size; j++) {
                    prog.add(j);
                    comp.add(j);
                }
                Collections.shuffle(comp);  //TESTING: Shuffles company preference list
                Collections.shuffle(prog);  //TESTING: Shuffles programmer preference list
                companies.add(comp);
                Stack programmerStack = new Stack();
                programmerStack.addAll(prog);
                programmers.add(programmerStack);
            }

            System.out.println("c's " + companies);
            System.out.println("p's " + programmers);
            solution = hireEmployees((ArrayList<Queue<Integer>>) companies.clone(), (ArrayList<Stack<Integer>>) programmers.clone());
            System.out.println("Solution is valid: " + checkSolution(companies, programmers, solution));
            System.out.println("solution: " + Arrays.toString(solution));
            System.out.println("----------------------------");
            if(!checkSolution(companies, programmers, solution)){
                System.out.println("ABORT");
                break;
            }
        }

    }

    //-----------------------------------------------------------------------------------------------------------------
    //DATA: Makes preference lists for the company queue and programmer stack
    //-----------------------------------------------------------------------------------------------------------------

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

    //-----------------------------------------------------------------------------------------------------------------
    //ALGORITHM: Does the picking of employees to each company
    //-----------------------------------------------------------------------------------------------------------------

    // Facilitates the macro level of hiring employees
    static Integer[] hireEmployees(ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers){
        Integer[] hired = new Integer[companies.size()];
        for(int i=0; i<companies.size(); i++){
            Queue<Integer> company = companies.get(i);
            hireEmployee(company, programmers, hired, i);
        }
        return hired;
    }

    // The meat of the hiring process. Company claims employee at the head of their remaining queue, and claimed employees
    // knock companies that are less desired off of their stack
    static void hireEmployee(Queue<Integer> company, ArrayList<Stack<Integer>> programmers,  Integer[] hired, Integer companyIndex){
        Integer employeeNum = null;
        while(employeeNum == null) {
            Stack<Integer> candidate = programmers.get(company.peek() - 1);
            if (candidate.contains(companyIndex + 1)) { //determines if the programmer would prefer this company to their current, or always true if programmer has no job.
                employeeNum = company.peek();
                while (candidate.peek() != companyIndex + 1) {
                    candidate.pop();  // If there are company choices less desirable to an employee, pop off their preference stack
                }
            } else {
                company.poll();  // Narrows down available employees in a company's want list
            }
        }
        replaceEmployee(hired, companyIndex, employeeNum,  companies, programmers);  // figures out conflicts of companies wanting employees
    }

    // Where conflicts of what company wants which employee is settled, ending in hireEmployee narrowing down preferences
    // via employee pops or company polls
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
            hireEmployee(companies.get(nowHiring), programmers, hired, nowHiring);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // TESTING: Solution checking.
    //-----------------------------------------------------------------------------------------------------------------

    // Master solution checker.
    static boolean checkSolution(ArrayList<Queue<Integer>> companies, ArrayList<Stack<Integer>> programmers, Integer[] solution){
        for(int company=0; company<solution.length; company++){
            for(int programmer=0; programmer<solution.length; programmer++){
                boolean companySwap = companyWantsSwap(companies.get(company), solution[company], programmer);
                boolean programmerSwap = programmerWantsSwap(programmers.get(programmer), getPosition(solution, programmer + 1) + 1, company);
                if(companySwap && programmerSwap){  //If both a company and a programmer want a swap, the solution is wrong
                    return false;
                }
            }
        }
        return true;
    }

    // If the company prefers someone higher up in the queue than the actual programmer option, the company wants a swap
    static boolean companyWantsSwap(Queue<Integer> icompany, int current, int option){
        Queue<Integer> company = new LinkedList<>(icompany);
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

    // If a programmer can be offered better (higher in the stack), they would want to swap in that deal
    static boolean programmerWantsSwap(Stack<Integer> iprogrammer, int current, int option){
        Stack<Integer> programmer = (Stack<Integer>) iprogrammer.clone();
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

    // Maps the value of the solution (given from perspective of company picks of programmers) to what company a programmer
    // is in, for checking if a programmer wants a swap from the current solution.
    static int getPosition(Integer[] arr, Integer value){
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals(value)){
                return i;
            }
        }
        return -1;
    }
    //-----------------------------------------------------------------------------------------------------------------

}

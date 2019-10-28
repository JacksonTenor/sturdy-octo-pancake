Clarifying comments in code

1. **Develop an algorithm that, given preferences for N programmers and N companies, finds a satisfactory pairing. If 
there is more than one satisfactory pairing, you need to find just one.** 

We find the most optimal pairing. This is faciliated in the `ALGORITHM` section of the code. In essence, the algorithm runs from the perspective of the companies. A company picks their current most favorite candidate, hence it is represented by a queue of their programmer preferences, with most preferred at the head. The first one gets first pick. That employee (employee preferences are a stack, with the first element put into the stack being their top choice) is tentatively claimed and their lower preference companies than that current company are popped off. The employees do not necessarily have the "bargaining power" to get to their top pick. That is fine as long as the company is getting their highest available priority employee, hence overall the setup is satisfactory enough. If another company comes along and tries to claim that employee, the company can only hire the employee if it still exists in the stack, as that means the employee would prefer that job to their current one. that is preferred by that employee is picked, the employee stack pops off the less preferred company and ones below that,and the company that previously attempted to hire this employee will attempt to hire their next preference. 

2.  **Implement your algorithm and test it on several cases of preferences. It does not matter how your algorithm takes 
data, but you should clearly explain this in the documentation so that we know how to test it. Also make sure to document 
all your test cases and results. Check that the pairings found by your program are satisfactory (write a method to do 
this to save yourself some time).**

The tests run themselves as many times as `runXTests` in the `for` loop specifies.
These tests run automatically in main. The console will print them out,
separated by single lines. Each test run creates a grid of n^2 size, with
n ranging from 3 to 9 depending on the random outcome. n down is the
list of preferences of either the companies or programmers, and n across
is the list of each company/programmer. The console shows the preferences
of both, in order from left to right within each company/programmer list
from highest to lowest ranking of the respective other group. Each test
also shows the solution and whether it is satisfactory by out tests. Solutions
are checked via `checkSolution()` with the data of each random test case.
The companies and programmer preferences are checked against the our
algorithm's outputted solution. If there is a situation where an employee wants a swap while a company also wants a swap,
the checker would fail and out algorithm would fail. Running 100 tests a few times does not abort/break the tests ever.
*Example of a single 3 company/3 programmer run with non-random assignments available at the top of the console printout. For
the sake of the grader, `runXTests` has been tuned down to 5 by default.*

3. **Explain why your algorithm is correct (i.e. it always stops and outputs a satisfactory pairing). You don’t need to 
go into low-level details of your program, but your argument must be precise enough to convince someone who has not seen y
our program before that it is indeed correct.**

At no point in hundreds of tests does both a company and programmer at any single instance insist on a swap to better satisfy their preferences, so by the definition of the problem parameters, it is correct. 

4. **Find the efficiency of your algorithm in the worst case, justify your
answer.**

Our algorithm will run on O(n^2) time.

The problem space of the selections of the preferences of programmers and "job creators" can be represented as a grid with the columns being each company and rows being each programmer. Because these have the same size (n), this means there are n rows and n columns, or n^2 entries in the grid. This means that even in the worst case scenario for our algorithm, we will have to change programmers and companies at most O(n) times, which we can fortunately do on O(1) time.



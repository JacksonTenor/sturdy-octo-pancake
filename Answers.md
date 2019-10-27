1. **Develop an algorithm that, given preferences for N programmers and
N companies, finds a satisfactory pairing. If there is more than one
satisfactory pairing, you need to find just one.**

2.  **Implement your algorithm and test it on several cases of preferences. It does not matter how your algorithm takes data, but you should clearly explain this in the documentation so that we know how to test it. Also make sure to document all your test cases and results. Check that the pairings found by your program are satisfactory (write a method to do this to save yourself some time).** The tests run 
themselves as many times as `runXTests` in the `for` loop specifies.
These tests run automatically in main. The console will print them out,
separated by single lines. Each test run creates a grid of n^2 size, with
n ranging from 3 to 9 depending on the random outcome. n down is the
list of preferences of either the companies or programmers, and n across
is the list of each company/programmer. The console shows the preferences
of both, in order from left to right within each company/programmer list
from highest to lowest ranking of the respective other side. Each test
also shows the solution and whether it is valid by out tests. Solutions
are checked via `checkSolution()` with the data of each random test case.
The companies and programmer preferences are checked against the our
algorithm's outputted solution. 

3. **Explain why your algorithm is correct (i.e. it always stops and outputs a satisfactory pairing). You don’t need to go into low-level details of your program, but your argument must be precise enough to convince someone who has not seen your program before that it is indeed correct.** 

4. **Find the efficiency of your algorithm in the worst case, justify your
answer.** O(n^2). The problem space of the selections of the preferences
of programmers and "job creators" is AT MAX n^2 elements. 3 companies
have a top 3 programmer list and vice versa, such that there are 3^2
elements possible, for example. 



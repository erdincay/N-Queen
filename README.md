N-Queen
=============

CS420 AI
--------
N-Queen game solved with HillClimbing and genetic algorithm

Compile
-------
All the main program is built on standard java 8 library. 
Only the [test] package has a dependency on JUnit 4.12, but it is separate from the main program. You can exclude the [test] package from the source code to compile the main program if you do not have JUnit.

Run
---
solution.Main class has the main function.

Run the program as: "Main.class alg=value output=value size=value".

alg : 	        String type, set the algorithm to solve the N-Queen game.
                "genetic" for the genetic algorithm.
                "hillclimbing" for the pure hill climbing algorithm.
                It's optional, default value will be "hillclimbing".

output :        String type, show the result in the output file, the value is the name of that file.
                It's optional, default value will be empty then the result will not show.

size :          Integer type, set the number of the queens.
                It's optional, default value is 15.

You can ignore all the optional parameters or some of them. The order of parameter does not matter.
eg, "Main.class"
    "Main.class alg=genetic"
    "Main.class alg=genetic output=step.log"
    "Main.class output=step.log alg=hillclimbing"
    "Main.class size=8"
are all valid format of running parameters.

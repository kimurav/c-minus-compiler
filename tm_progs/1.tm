* Starting standard prelude
0:     LD  6,0(0)	load gp with maxaddr
1:     LDA  5,0(6)	copy gp to fp
2:     ST  0,0(0)	clear content at loc 0
* Jump around i/o code here
* Standard input function code:
4:     ST  0,-1(5)	store return
5:     IN  0,0,0	input
6:     LD  7,-1(5)	return to caller
* Standard output function code:
7:     ST  0,-1(5)	store return
8:     LD  0,-2(5)	load output level
9:     OUT  0,0,0	output
10:     LD  7,-1(5)	return to caller
3:     LDA  7,7(7)	jump around i/o code

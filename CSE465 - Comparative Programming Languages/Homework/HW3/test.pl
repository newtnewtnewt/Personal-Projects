query(quadratic(2.0, 5.0, 2.0, _L)).
query(quadratic(1.0, 0.0, 0.0, _L)).
query(quadratic(2.0, 0.0, 2.0, _L)).

query(isLargerCube(2,2,2,1,3,2)).
query(isLargerCube(2,2,20,5,3,2)).
query(isLargerCube(1,3,2,2,2,2)).
query(isLargerCube(2,2,2,2,2,2)).

query(clamp(1, 0, 2, _X)).
query(clamp(-1, 0, 2, _X)).
query(clamp(10, 0, 2, _X)).
query(clamp(1, 0, 2, 4)).

query(isConstant([])).
query(isConstant([1,1])).
query(isConstant([a,a,a])).
query(isConstant([a,a,1])).
query(isConstant([a,1,a])).
query(positives([], _L)).
query(positives([1], _L)).
query(positives([-1], _L)).
query(positives([-1,2,5,-9,4,0], _L)).

query(splitable([1,2,3,4,10])).
query(splitable([2,1,1])).
query(splitable([0])).
query(splitable([1,4,8])).
query(splitable([1,3,2])).
query(splitable([2,2,1,1])).


query(removeAll([], a, [])).
query(removeAll([], a, L)).        
query(removeAll([a], a, L)).       
query(removeAll([a,b,c], d, L)).   
query(removeAll([a,a,c,a], a, L)). 
query(removeAll([], a, [a])).


query(mthCousinNTimesRemoved(self, c3, 3, 0)).
query(mthCousinNTimesRemoved(self, c31, 3, 1)).
query(mthCousinNTimesRemoved(self, c32, 3, 2)).

query(mthCousinNTimesRemoved(self, c2, 2, 0)).
query(mthCousinNTimesRemoved(self, c21a, 2, 1)).
query(mthCousinNTimesRemoved(self, c21b, 2, 1)).
query(mthCousinNTimesRemoved(self, c22, 2, 2)).

query(mthCousinNTimesRemoved(self, c1, 1, 0)).
query(mthCousinNTimesRemoved(self, c11a, 1, 1)).
query(mthCousinNTimesRemoved(self, c11b, 1, 1)).
query(mthCousinNTimesRemoved(self, c12a, 1, 2)).
query(mthCousinNTimesRemoved(self, c12b, 1, 2)).

query(mthCousinNTimesRemoved(c1, c2, 2, 0)).
query(mthCousinNTimesRemoved(c2, c1, 2, 0)).
query(mthCousinNTimesRemoved(c11b, c32, 4, 1)).
query(mthCousinNTimesRemoved(c32, c11b, 4, 1)).

query(mthCousinNTimesRemoved(self, gn, 1, 2)).
query(mthCousinNTimesRemoved(self, s, 1, 0)).
query(mthCousinNTimesRemoved(gd, ggm, 1, 1)).

writeln(T) :- write(T), nl.

main :- consult(hw3S20),
	forall(query(Q), (Q->writeln(yes:Q) ; writeln(no:Q))),
	halt.
:- initialization(main).

% Instance
vertex(1). vertex(2). vertex(3). vertex(4). vertex(5). vertex(6).
edge(1,2). edge(1,3). edge(2,4). edge(3,5). edge(4,5). edge(4,6).
threshold(3).

% One vertice must be selected from each edge
select(V) | select(V2) :- edge(V, V2), vertex(V), vertex(V2).

% Number of vertices cannot exceed the threshold
:- threshold(T), #count{ V : select(V) } > T.

% Test the Output
#show select/1.

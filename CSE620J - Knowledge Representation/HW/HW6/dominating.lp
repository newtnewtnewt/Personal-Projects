% Instance
vertex(1). vertex(2). vertex(3). vertex(4). vertex(5). vertex(6).
edge(1,2). edge(1,3). edge(2,4). edge(3,5). edge(4,5). edge(4,6).
threshold(2).

% Select the first one from each set or nothing
{ select(V) : edge(V, V2)}.
{ select(V2) : edge(V, V2)}.

% Number of vertices cannot exceed the threshold
:- threshold(T), #count{ V : select(V) } > T.

% CWA for selection
-select(V) :- not select(V), vertex(V).

% Something is adjacent to a selected vertex if it is selected, or if has an edge
% running to it 
touches_selected(V) :- select(V).
touches_selected(V) :- edge(V, V2), select(V2).
touches_selected(V) :- edge(V2, V), select(V2).

% CWA for touching selected
-touches_selected(V) :- not touches_selected(V), vertex(V).

% If a vertex is not selected, it must touch something that is selected
:- vertex(V), -touches_selected(V).

#show select/1.
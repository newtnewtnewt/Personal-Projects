all: arrays.exe params.exe ns.exe ds.exe cont.exe io.exe del.exe null.exe pairs.exe

arrays.exe:
	mcs -unsafe -out:arrays.exe Arrays.cs
params.exe:
	mcs -unsafe -out:params.exe ParamsAndEnums.cs
ns.exe:
	mcs -out:ns.exe NamespaceClassAndStruct.cs
ds.exe:
	mcs -out:ds.exe DataStructures.cs complex.cs
cont.exe:
	mcs -out:cont.exe List.cs Adjustable.cs
io.exe:
	mcs -out:io.exe IO.cs
del.exe:
	mcs -out:del.exe Delegates.cs
null.exe:
	mcs -out:null.exe NullableExample.cs
pairs.exe:
	mcs -out:pairs.exe Pairs.cs

clean:
	rm *.exe

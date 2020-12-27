//  A program with a few problems. What it intends to  do?
//
%include<stdio>
int pw (int x, int y) ;{
int i, res=1;
i=0;
while (i<y) {
   res *=x;
   i++; };
   return res;
}


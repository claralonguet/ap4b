1;b=1;b>1
2;b<3;b=3;b>3
3;j<3;j=3;j>3
4;j<4;j=4;j>4
5;b%2;b%1
6;j%2;j%1
7;v%2;v%1
8;0*1;1*1;2*1;3*1
9;0*3;1*3;2*3;3*3
10;0*4;1*4;2*4;3*4
11;b<j;b=j;b>j
12;b<v;b=v;b>v
13;j<v;j=v;j>v
14;b<v&b<j;j<b&j<v;v<j&v<b
15;b>v&b>j;j>b&j>v;v>j&v>b
16;p>i;p<i
17;p=0;p=1;p=2;p=3
18;b+j+v%2;b+j+v%1
19;b+j<6;b+j=6;b+j>6
20;3*x;2*x;1*x  // les 3 chiffres sont identiques, 2 sont identique, aucun son identique
21;!2*x;2*x
22;b<j&j<v;b>j&j>v;!b<j&j<v&!b>j&j>v
23;b+j+v<6;b+j+v=6;b+j+v>6
24;b+1=j&j+1=v;b+1=j^j+1=v;b+1!=j&j+1!=v // ^ ou logique
25;b+1!=j&j+1!=v&b!=j+1&j!=v+1;b+1=j^j+1=v^b=j+1^j=v+1;b+1=j&j+1=v^b=j+1&j=v+1
26;b<3;j<3;v<3
27;b<4;j<4;v<4
28;b=1;j=1;v=1
29;b=3;j=3;v=3
30;b=4;j=4;v=4
31;b>1;j>1;v>1
32;b>3;j>3;v>3
33;b%2;b%1;j%2;j%1;v%2;v%1
34;b<=v&b<=j;j<=b&j<=v;v<=j&v<=b  // <= inferieur ou egal
35;b>=v&b>=j;j>=b&j>=v;v>=j&v>=b  // >= superieur ou egal
36;b+j+v=%3;b+j+v=%4;b+j+v=%5
37;b+j=4;b+v=4;j+v=4
38;b+j=6;b+v=6;j+v=6
39;b=1;b>1;j=1;j>1;v=1;v>1
40;b<3;b=3;b>3;j<3;j=3;j>3;v<3;v=3;v>3
41;b<4;b=4;b>4;j<4;j=4;j>4;v<4;v=4;v>4
42;b<v&b<j;b>v&b>j;j<b&j<v;j>b&j>v;v<j&v<b;v>j&v>b
43;b<j;b<v;b=j;b=v;b>j;b>v
44;j<b;j<v;j=b;j=v;j>b;j>v
45;
48;b<j;b=j;b>j;b<v;b=v;b>v;j<v;j=v;j>v;

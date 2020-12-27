int[] data = new int[40];
for (int i = 0; i < data.length; i++) {
  data[i] = i;
}
//  Fisher-Yates Algorithm where we swap 
//  item 0 with a random location, and then 1 with a random location , etc. Always choosing the random location starting
//  at a.
for (int a = 0; a < data.length - 1; a++) {
  // shuffle 
  int b = (int)random(a, data.length); 

  // Swap elements a and b
  int temp = data[a];
  data[a] = data[b];
  data[b] = temp;
}

println(data);

CFLAGS=-std=c99
Programme : code.o bitmap.o
	gcc -std=c99 -Wall -pthread code.o bitmap.o -o Programme
 
main.o : code.c bitmap.c
	gcc -std=c99 -Wall -pthread -c main.c -o main.o
 
bitmap.o : bitmap.c
	gcc -std=c99 -Wall -pthread -c bitmap.c -o bitmap.o
clear:
	rm -rf *.o Programme
show:
	eog *.bmp&

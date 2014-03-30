#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pthread.h"

#define NUM 100
#define NTHREAD 4

typedef enum State{
	EMPTY,
	FULL,
	READY,
	FINISHED,
}state_t;

typedef struct data{
	enum State state;
	int val;
	
}data_t;


typedef struct work_on{
	int x;
	int y;
	data_t *data;
}work_on_t;


typedef struct Matrix{
	data_t *mat[NUM][NUM];
	int valx;
	int valy;
	pthread_mutex_t mutx;
	pthread_mutex_t muty;
}matrix_t;


typedef struct Argv{
	matrix_t *begin;
	matrix_t *slot;
} argv_t;



work_on_t* getWork( matrix_t *begin){ // changer la methode d'acces au tableau avec les valeurs
	work_on_t *ret = (work_on_t*)malloc(sizeof(work_on_t));
	pthread_mutex_lock(&(begin->mutx));
	pthread_mutex_lock(&(begin->muty));
	if(begin->valy==NUM){
		pthread_mutex_unlock(&(begin->muty));
		pthread_mutex_unlock(&(begin->mutx));
		printf("exit\n");
		return NULL;
	}
	ret->x = begin->valx;
	ret->y = begin->valy;
	ret->data = (data_t*) malloc(sizeof(data_t));
	ret->data->state = begin->mat[ret->x][ret->y]->state;
	if(begin->valx==(NUM-1)){
		begin->valx=0;
		begin->valy++;
	}
	else {
		begin->valx++;
	}
	pthread_mutex_unlock(&(begin->muty));
	pthread_mutex_unlock(&(begin->mutx));
	return ret;
}

matrix_t* matrix_init(state_t ini){
	matrix_t *ret = (matrix_t *) malloc(sizeof(matrix_t));
	ret->valx=0;
	ret->valy=0;
	pthread_mutex_init(&(ret->muty), NULL);
	pthread_mutex_init(&(ret->mutx) , NULL);
	for(int i=0 ; i<NUM ; i++){
		for(int j=0; j<NUM;j++){
			ret->mat[i][j]=(data_t*)malloc(sizeof(data_t));
			ret->mat[i][j]->val=0;
			ret->mat[i][j]->state=ini;
		}
	}
	return ret;
}

void put(matrix_t *slot , work_on_t *w){
		slot->mat[w->x][w->y] = w->data;
}

void work(work_on_t *w){
	w->data->val++;
	w->data->state=READY;
}
void* act(void *a){
	argv_t *arg = (argv_t *)a;
	work_on_t* w = getWork(arg->begin);
	while(w!=NULL){
	work(w);
	put(arg->slot , w);
	w=getWork(arg->begin);
	}
}

int main( int argv , char *argc[]){
		
	matrix_t *mat1=matrix_init(READY);
	matrix_t *mat2=matrix_init(EMPTY);
	
	argv_t arg;
	arg.begin = mat1;
	arg.slot = mat2;
	
	pthread_t thread_tab[NTHREAD];
 
	for(int i=0; i<NTHREAD ; i++){
	pthread_create(&thread_tab[i] , NULL , act , &arg);
	}		

	for(int i=0; i<NTHREAD ; i++){
	pthread_join(thread_tab[i] , NULL);
	}
	
	printf("start printing slots \n");

	for(int i=0; i<NUM ; i++){
		for(int j=0; j<NUM; j++){
			printf("%d" , mat2->mat[i][j]->val);
		}
	printf("\n");
	}
	printf("The End \n");
}	

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pthread.h"
#include <unistd.h>
#include "code.h"
int main( int argv , char *argc[]){
		
	matrix_t *mat1=matrix_init(READY);
	matrix_t *mat2=matrix_init(EMPTY);
	matrix_t *mat3=matrix_init(EMPTY);
	matrix_t *mat4=matrix_init(EMPTY);	
	printf("missing %d \n", mat2->mat[5][5]->missing[3]->x);

	printf("begin working\n");
	mat1->num = 1;
	mat2->num = 2;
	mat3->num = 3;
	mat4->num = 4;
	
	argv_t arg;
	arg.begin = mat1;
	arg.slot = mat2;

	
	argv_t arg2;
	arg2.begin = mat2;
	arg2.slot = mat3;
	
	argv_t arg3;
	arg3.begin = mat3;
	arg3.slot = mat4;

	arg.num=1;
	arg2.num=2;
	arg3.num=3;

	pthread_t thread_tab[NTHREAD];
	pthread_t thread_tab2[NTHREAD];
	pthread_t thread_tab3[NTHREAD];
 
	for(int i=0; i<NTHREAD ; i++){
	pthread_create(&thread_tab[i] , NULL , act , &arg);
	pthread_create(&thread_tab2[i] , NULL , act , &arg2);
	pthread_create(&thread_tab3[i] , NULL , act  , &arg3);
	}		

	for(int i=0; i<NTHREAD ; i++){
	pthread_join(thread_tab[i] , NULL);
	pthread_join(thread_tab2[i] , NULL);
	
	pthread_join(thread_tab3[i] , NULL);
	}
	
	printf("start printing slot \n");

	for(int i=0; i<NUM ; i++){
		for(int j=0; j<NUM; j++){
			printf("%d " , mat2->mat[i][j]->val);
		}
	printf("\n");
	}

	printf("\n \n second \n \n");

	for(int i=0; i<NUM ; i++){
		for(int j=0; j<NUM; j++){
			printf("%d " , mat3->mat[i][j]->val);
		}
	printf("\n");
	}

	printf("\n \n \n");
	for(int i=0; i<NUM ; i++){
		for(int j=0; j<NUM; j++){
			printf("%d " , mat4->mat[i][j]->val);
		}
	printf("\n");
	}
	printf("The End DUDE \n");
}


void addReady(matrix_t *slot, data_t *data, int x , int y){
	//printf("in add READY x: %d y:%d num:%d \n" , x , y ,slot->num);
	slot->p[slot->valNext]=(pair_t *) malloc(sizeof(pair_t));
	slot->mat[x][y]->state=READY;
	if(slot->p[slot->valNext] == NULL){
			printf("pair not enough memory\n");
	}
	slot->p[slot->valNext]->x = x;
	slot->p[slot->valNext]->y = y;
	//printf("valNext : %d num: %d \n" , slot->valNext , slot->num);
	slot->valNext++;
}

int removeMissing2(matrix_t *slot, int x , int y){
	int count=0;
	for(int i=0 ; i<8; i++){
		slot->mat[x][y]->missing[i]->x=-1;
		slot->mat[x][y]->missing[i]->y=-1;
	}
	for(int i=x-1; i<=+1;i++){
		for(int j=y-1; j<=y+1; j++){
			if(j>=0 && j<NUM && i>=0 && i<NUM && !(i==x && j==y)){
				switch(slot->mat[i][j]->state){
					case EMPTY: slot->mat[x][y]->missing[count]->x=i;
							slot->mat[x][y]->missing[count]->y=j;
							count++; 
							break;
					default:; 
				}
			}
		}
 	}
	slot->mat[x][y]->valMissing=count;
	return count;
}

int removeMissing(matrix_t *slot, int xData , int yData, int xToremove, int yToremove){
	int count=0;
	while(count<8){
		if(slot->mat[xData][yData]->missing[count]->x == xToremove && slot->mat[xData][yData]->missing[count]->y == yToremove){
			//printf("removing slot-> x , y : %d %d // data->missing-> x, y : %d , %d address data %p num:%d \n", x , y , data->missing[count]->x , data->missing[count]->y , data ,slot->num);
			slot->mat[xData][yData]->missing[count]->x=-1;
			slot->mat[xData][yData]->missing[count]->y=-1;
			slot->mat[xData][yData]->valMissing--;
			if(slot->mat[xData][yData]->valMissing==0){
				addReady(slot , slot->mat[xData][yData], xData , yData);
				return 0;
			}
		return 0;
		}
		count++;
	}
	return 1;
}

int update(matrix_t *slot , work_on_t *data){
	int count=0;
	slot->mat[data->x][data->y]->val = data->data->val;
	slot->mat[data->x][data->y]->state=FULL;
	int countNotReady=0;
	for(int i=data->x-1; i<=data->x+1;i++){
		for(int j=data->y-1; j<=data->y+1; j++){
			if(j>=0 && j<NUM && i>=0 && i<NUM){
				int ret;
				switch(slot->mat[i][j]->state){
				case READY: ret++; break;
				default:
					ret=removeMissing2(slot , i , j);
					if (ret==0 && slot->mat[i][j]->state==FULL)
						addReady(slot , slot->mat[i][j] , i , j);
				}
			}
		}
 	}

}

work_on_t* getWork(matrix_t *begin , matrix_t *slot , int *countNotFinished){ 
	*countNotFinished = 0;
	work_on_t *ret = (work_on_t*)malloc(sizeof(work_on_t));
	if(ret==NULL)
		printf("NULL error\n");
	pthread_mutex_lock(&(begin->mutx));
	int valp=begin->valp;
	if(valp>=NUM*NUM){
		printf("one \n");
		pthread_mutex_unlock(&(begin->mutx));
		return NULL;
	}
	else if(begin->p[valp]==NULL){
		//printf("valp %d valNext %d \n", valp , begin->valNext);
		*countNotFinished=1;
		pthread_mutex_unlock(&(begin->mutx));
		return NULL;
	}
	else if(begin->p[valp] !=NULL){
		ret->x = begin->p[valp]->x;
		ret->y = begin->p[valp]->y;
		ret->data = slot->mat[ret->x][ret->y];
		if(begin->mat[ret->x][ret->y] ==NULL || ret->data ==NULL){
			printf("x %d y %d num: %d valp %d valNext %d adress: %p \n" , ret->x , ret->y , begin->num , valp , begin->valNext , begin->mat[0][0]);
			printf("error NUL num %d \n" , begin->num);
		}
		ret->data->val = begin->mat[ret->x][ret->y]->val;
		free(begin->p[valp]);
		begin->p[valp]=NULL;
		*countNotFinished = 1;
		begin->valp++;
	}
	
	pthread_mutex_unlock(&(begin->mutx));
	//printf("work found %d \n", begin->num);
	return ret;
}

/*permet d'initisialiser une matrix avec comme etat pour chaque data_t la vlauer ini.
si celle-ci est READY, alors chaque position est placée dans le vecteur p.
*/

matrix_t* matrix_init(state_t ini){
	matrix_t *ret = (matrix_t *) malloc(sizeof(matrix_t));
	if(ret==NULL)
		printf("error init \n");
	ret->valx=0;
	ret->valy=0;
	int err;
	data_t ***tab=(data_t***) malloc(sizeof(data_t*)*NUM);
	//printf("2\n");
	pthread_mutex_init(&(ret->mutx) , NULL);
	err =pthread_mutex_lock(&(ret->mutx));
	//ret->mat = (data_t *[NUM][NUM]) malloc(sizeof(data_t*[NUM][NUM]));
	int count=0;
	for(int i=0 ; i<NUM ; i++){
			tab[i]=(data_t**)malloc(sizeof(data_t**)*NUM);
		for(int j=0; j<NUM;j++){
			tab[i][j]=(data_t*)malloc(sizeof(data_t));
			if(tab[i][j] == NULL)
				printf("not enough memory\n");
			tab[i][j]->val=1;
			tab[i][j]->state=ini;
			ret->valp;
			ret->valp=0;
			ret->valNext;
			ret->valNext=0;
			int mcount=0;
			int k=0;
			switch(ini){
			case READY:
				tab[i][j]->valMissing=0;
				ret->p[count]=(pair_t *) malloc(sizeof(pair_t));
				if (ret->p[count] ==NULL)
					printf("ERROR BITCH\n");
				ret->p[count]->x=i;
				ret->p[count]->y=j;
				ret->valNext=NUM;
				break;
			default:
				tab[i][j]->missing = (pair_t **) malloc(sizeof(pair_t*)*8);
				for(int mi=i-1; mi<=i+1;mi++){
					for(int mj=j-1; mj<=j+1;mj++){
						tab[i][j]->missing[k]=(pair_t *)malloc(sizeof(pair_t)*8);
						if(mj>=0 && mj<NUM && mi>=0 && mi<NUM && !(mi==i && mj==j)){
							tab[i][j]->missing[k]->x=mi;
							tab[i][j]->missing[k]->y=mj;
							mcount++;
						}
						else{
							tab[i][j]->missing[k]->x=-1;
							tab[i][j]->missing[k]->y=-1;
						}
						k++;			
					}
				}
				tab[i][j]->valMissing=mcount;
				ret->p[count]=NULL;
			}
			count++;
		}
	}
	ret->mat = tab;
	err =pthread_mutex_unlock(&(ret->mutx) );
	return ret;
}

/*permet de placer un element dans le slot
  place la position du w dans le p du slot en question.
*/

void put(matrix_t *slot , work_on_t *w){
		int err;
		err = pthread_mutex_lock(&(slot->mutx));
		update(slot , w); //mettre dans le slot suivant
		err = pthread_mutex_unlock(&(slot->mutx));

}

void work(work_on_t *w , matrix_t *slot){
	int i=w->x;
	int j=w->y;
	int count=0;
	for(int mi=i-1; mi<=i+1;mi++){
			for(int mj=j-1; mj<=j+1;mj++){
						if(mj>=0 && mj<NUM && mi>=0 && mi<NUM ){
							count = count+slot->mat[mi][mj]->val;					
						}			
				}
	}
	w->data->val=count;
}


void* act(void *a){
	argv_t *arg = (argv_t *)a;
	int err = 0;
	work_on_t* w = getWork(arg->begin, 	arg->slot,&err);
	while(err!=0){
		if(w !=NULL){
			work(w , arg->begin);
			put(arg->slot , w);
		}
		w=getWork(arg->begin ,arg->slot, &err);
	}
}

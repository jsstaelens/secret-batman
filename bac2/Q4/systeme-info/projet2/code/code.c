#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pthread.h"
#include <unistd.h>
#include "code.h"
#include "bitmap.h"

int NUMX;
int NUMY;
struct image *im;
void write_back(data_t *data){
	int count=0;
	for(int i=data->ymin ; i<=data->ymax; i++){
		for(int j=data->xmin ; j<=data->xmax; j++){

			
			im->pixels[(im->width * i) + j].b=data->pi[count]->b;
			im->pixels[(im->width * i) + j].r=data->pi[count]->r;
			im->pixels[(im->width * i) + j].g=data->pi[count]->g;
			count++;
		}
	}

}

int main( int argv , char *argc[]){

 
	im = (struct image *) malloc( sizeof( struct image));

	int err;
	err = load_bmp("image.bmp" , &im);
	NUMX=10;
	NUMY=10;
	printf("numX: %d numy: %d width: %d height %d\n" , NUMX , NUMY, im->width , im->height);


	matrix_t *mat1=matrix_init(READY);
	matrix_t *mat2=matrix_init(EMPTY);
	matrix_t *mat3=matrix_init(EMPTY);
	matrix_t *mat4=matrix_init(EMPTY);	
	printf("start working on slot \n");
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
	//pthread_create(&thread_tab2[i] , NULL , act , &arg2);
	//pthread_create(&thread_tab3[i] , NULL , act  , &arg3);
	}		

	for(int i=0; i<NTHREAD ; i++){
	pthread_join(thread_tab[i] , NULL);
	//pthread_join(thread_tab2[i] , NULL);
	
	//pthread_join(thread_tab3[i] , NULL);
	}
	printf("writting image back \n");
	int count=0;
	for(int i=0; i<NUMX ; i++){
		for(int j=0; j<NUMY; j++){
			write_back(mat2->mat[i][j]);
		}
	}
	err = write_bmp(im , "finit.bmp");


	/*for(int i=0; i<NUMX ; i++){
		for(int j=0; j<NUMY; j++){
			printf("%d " , mat3->mat[i][j]->val);
		}
	printf("\n");
	}

	printf("\n \n \n");
	for(int i=0; i<NUMX ; i++){
		for(int j=0; j<NUMY; j++){
			printf("%d " , mat4->mat[i][j]->val);
		}
	printf("\n");
	}
	printf("The End DUDE \n");*/
}


void addReady(matrix_t *slot, data_t *data, int x , int y){

	slot->p[slot->valNext]=(pair_t *) malloc(sizeof(pair_t));

	if(slot->p[slot->valNext] == NULL){
			printf("pair not enough memory\n");
	}
	slot->p[slot->valNext]->y = y;
	slot->p[slot->valNext]->x = x;
	slot->mat[x][y]->state=READY;
	slot->valNext++;
}

int removeMissing2(matrix_t *slot, int x , int y){
	int count=0;
	for(int i=0 ; i<8; i++){
		slot->mat[x][y]->missing[i]->x=-1;
		slot->mat[x][y]->missing[i]->y=-1;
	}
	for(int i=x-1; i<=x+1;i++){
		for(int j=y-1; j<=y+1; j++){
			if(j>=0 && j<NUMY && i>=0 && i<NUMX && !(i==x && j==y)){
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


int update(matrix_t *slot , work_on_t *data){
	int count=0;
	slot->mat[data->x][data->y]->pi = data->data->pi;
	slot->mat[data->x][data->y]->state=FULL;
	int countNotReady=0;
	for(int i=data->x-1; i<=data->x+1;i++){
		for(int j=data->y-1; j<=data->y+1; j++){
			if(j>=0 && j<NUMY && i>=0 && i<NUMX){
				int ret;
				switch(slot->mat[i][j]->state){
				case READY:; break;
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
	if(valp>=NUMX*NUMY){
		pthread_mutex_unlock(&(begin->mutx));
		return NULL;
	}
	else if(begin->p[valp]==NULL){
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
		ret->data->pi = begin->mat[ret->x][ret->y]->pi;
		free(begin->p[valp]);
		begin->p[valp]=NULL;
		*countNotFinished = 1;
		begin->valp++;
	}
	
	pthread_mutex_unlock(&(begin->mutx));
	return ret;
}
void pixel_init(data_t *data){
	int count=0;

	//printf("xmin: %d xmax: %d ymin= %d ymax =%d %p %p\n" ,data->xmin , data->xmax, data->ymin , data->ymax ,data , data->pi);
	int sizeToFree= (data->xmax - data->xmin+1)*(data->ymax - data->ymin+1);
	data->pi=(struct pixel**)malloc(sizeof(struct pixel*)*sizeToFree);
	if(data->pi==NULL){
		printf("error \n");
	}

	for(int i=data->ymin ; i<=data->ymax; i++){
		for(int j=data->xmin ; j<=data->xmax; j++){
			data->pi[count]=(struct pixel *) malloc(sizeof(struct pixel));
			data->pi[count]->b=im->pixels[(im->width * i) + j].b;
			data->pi[count]->r=im->pixels[(im->width * i) + j].r;
			data->pi[count]->g=im->pixels[(im->width * i) + j].g;
			count++;
			/*data->pi[count]->b=200;
			data->pi[count]->r=0;
			data->pi[count]->g=0;
			count++;*/
		}
	}
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

	data_t ***tab=(data_t***) malloc(sizeof(data_t*)*(NUMY+1));
	pthread_mutex_init(&(ret->mutx) , NULL);
	err =pthread_mutex_lock(&(ret->mutx));
	ret->p=(pair_t **)malloc(sizeof(pair_t)*(NUMX)*(NUMY));
	int count=0;
	int countx=0;
	int county=0;
	for(int i=0 ; i<NUMY ; i++){

			tab[i]=(data_t**)malloc(sizeof(data_t*)*(NUMX+1));
			countx=0;
		for(int j=0; j<NUMX;j++){
			tab[i][j]=(data_t*)malloc(sizeof(data_t));
			tab[i][j]->xmin=countx;
			countx=countx+ im->width/NUMX;
			if(countx>=im->width){
				("end of line \n");
				countx=im->width+1;
			}
			tab[i][j]->xmax=countx-1;
			tab[i][j]->dx= tab[i][j]->xmax - tab[i][j]->xmin;
			tab[i][j]->ymin=county;
			if(county+(im->height/NUMY) >= im->height){
				printf("last line\n");
				tab[i][j]->ymax = im->height;
			}
			else
				tab[i][j]->ymax = county+ im->height/NUMY;
			tab[i][j]->dy= tab[i][j]->ymax - tab[i][j]->ymin;
			if(tab[i][j] == NULL)
				printf("not enough memory\n");
			tab[i][j]->state=ini;
			pixel_init(tab[i][j]);
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
				ret->p[count]->x=j;
				ret->p[count]->y=i;
				ret->valNext=NUMX;
				break;
			default:
				tab[i][j]->missing = (pair_t **) malloc(sizeof(pair_t*)*8);
				for(int mi=i-1; mi<=i+1;mi++){
					for(int mj=j-1; mj<=j+1;mj++){
						tab[i][j]->missing[k]=(pair_t *)malloc(sizeof(pair_t)*8);
						if(mj>=0 && mj<NUM && mi>=0 && mi<NUM && !(mi==i && mj==j)){
							tab[i][j]->missing[k]->x=mj;
							tab[i][j]->missing[k]->y=mi;
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
		county=county + im->height/NUMY;
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
	pthread_mutex_lock(&(slot->mutx));
	int xmin=w->data->xmin;
	int ymin=w->data->ymin;
	int xmax=w->data->xmax;
	int ymax=w->data->ymax;
	int dx= xmax-xmin;
	int dy= ymax-ymin;
	struct pixel **ret =(struct pixel**)malloc(sizeof(struct pixel*)*(dx+1)*(dy+1));
	float tab[3][3]={{1 , 1 , 1},{1 , 1 , 1}, {1 , 1 ,1}};
	int count=0;
	int cx=0;
	int cy=0;
	struct pixel **up;
	struct pixel **down;
	struct pixel **left;
	struct pixel **right;
	if(w->y!=0){
		int tx=slot->mat[w->x][w->y-1]->dx;
		int ty=slot->mat[w->x][w->y-1]->dy;
		up= (struct pixel**)malloc(sizeof(struct pixel*)*(tx+1));
		int c=0;
		for(int i = tx*(ty-1)+1; i<=tx*(ty);i++){
			up[c]= slot->mat[w->x][w->y]->pi[i];
			c++;
		}
	}
	if(w->y!=NUMY-1){
		slot->mat[w->x][w->y+1];
		int tx=slot->mat[w->x][w->y+1]->dx;
		int ty=slot->mat[w->x][w->y+1]->dy;
		down= (struct pixel**)malloc(sizeof(struct pixel*)*(tx+1));
		int c=0;
		for(int i=0 ;i<tx;i++){
			down[c]= slot->mat[w->x][w->y]->pi[i];
			c++;
		}
	}
	if(w->x!=0){
		int tx=slot->mat[w->x-1][w->y]->dx;
		int ty=slot->mat[w->x-1][w->y]->dy;
		left= (struct pixel**)malloc(sizeof(struct pixel*)*(ty+1));
		int c=0;
		for(int i = 0 ; i<ty;i++){
			left[c]= slot->mat[w->x][w->y]->pi[i*(tx+1)];
			c++;
		}
	}
	if(w->x!=NUMX-1){
		int tx=slot->mat[w->x+1][w->y]->dx;
		int ty=slot->mat[w->x+1][w->y]->dy;
		right = (struct pixel**)malloc(sizeof(struct pixel*)*(ty+1));
		int c=0;
		for(int i = 0 ; i<ty;i++){
			right[c]= slot->mat[w->x][w->y]->pi[(i*(tx+1))+tx];
			c++;
		}
	}

	for(int i=ymin ; i<=ymax; i++){
		cx=0;
		for(int j=xmin ; j<=xmax; j++){
			ret[count]=(struct pixel *) malloc(sizeof(struct pixel));
			if(w->y==0 || w->x==0 || w->y==NUMY-1 || w->x==NUMX-1){
			
			}
			else{
				float vb=0;
				float vr=0;
				float vg=0;
				int l=0;
				for(int i2=-1 ; i2<=1;i2++){
					for(int j2=-1 ; j2<=1;j2++){
						int posi = count+(dx*i2) + j2;
						if(i==ymin && w->y !=0 && i2==-1){
							if( cx!=0 && cx<dx-1){
							vb=vb+(tab[i2+1][j2+1]/9 *(float)(down[cx+j2]->b));
							vr=vr+(tab[i2+1][j2+1]/9 *(float)(down[cx+j2]->r));
							vg=vg+(tab[i2+1][j2+1]/9 *(float)(down[cx+j2]->g));l++;
							}
						}
						else if(i==ymax && w->y!=NUMY-1 && i2==1){
							if(cx!=0 && cx<dx-1){
							vb=vb+(tab[i2+1][j2+1]/9 *(float)(up[cx+j2]->b));
							vr=vr+(tab[i2+1][j2+1]/9 *(float)(up[cx+j2]->r));
							vg=vg+(tab[i2+1][j2+1]/9 *(float)(up[cx+j2]->g));l++;
							}
						
							
						}
						else if(j==xmin && w->x!=0 && j2==-1){ 
							if( cy!=0 && cy<dy-1){
							vb=vb+(tab[i2+1][j2+1]/9)*(float)(left[cy+i2]->b);
							vr=vr+(tab[i2+1][j2+1]/9)*(float)(left[cy+i2]->r);
							vg=vg+(tab[i2+1][j2+1]/9)*(float)(left[cy+i2]->g); l++;
							}
						}
						else if(j==xmax && w->x!=NUMX-1 && j2==1){
							if( cy!=0 && cy<dy-1){
							vb=vb+(tab[i2+1][j2+1]/9)*(float)(right[cy+i2]->b);
							vr=vr+(tab[i2+1][j2+1]/9)*(float)(right[cy+i2]->r);l++;
							vg=vg+(tab[i2+1][j2+1]/9)*(float)(right[cy+i2]->g);
							}
						}
						else{
							vb = vb+(tab[i2+1][j2+1]/9 * (float)(w->data->pi[posi]->b));
							vg = vg+(tab[i2+1][j2+1]/9 * (float)(w->data->pi[posi]->g));
							vr = vr+(tab[i2+1][j2+1]/9 * (float)(w->data->pi[posi]->r));l++;
						}
					}
				}
				//printf("l %d \n " , l);
				ret[count]->b=(int)vb;
				ret[count]->r=(int)vr;
				ret[count]->g=(int)vg;
			}
			cx++;
			count++;
			/*data->pi[count]->b=200;
			data->pi[count]->r=0;
			data->pi[count]->g=0;
			count++;*/
		}
		cy++;
	}
	w->data->pi=ret;

	pthread_mutex_unlock(&(slot->mutx));
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

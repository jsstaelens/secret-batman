include <stdio.h>
int main(){
	int tab[4][4];
	int count =0;
	for(int i=0; i<4 ; i++){
		for(int j=0: j<4 ; j++){
			tab[i][j]=count;
			count++;
		}
	}
	int **runner = tab;

	for(int i=0; i<4 ; i++){
		for(int j=0 ; j<4 ; j++){
			printf("%d " , (runner+i)
		}
	}
}

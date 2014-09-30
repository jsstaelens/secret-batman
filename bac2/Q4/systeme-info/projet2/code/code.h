#define NUM 30
#define NTHREAD 1

typedef enum State{
        EMPTY,
        FULL,
        READY,
        FINISHED,
}state_t;

typedef struct Pair{
        int x;
        int y;
} pair_t;


typedef struct data{
	struct pixel **pi;
	int xmin;
	int xmax;
	int ymin;
	int dx;
	int dy;
	int ymax;
	enum State state;

	int valMissing;
	pair_t **missing;
	pthread_mutex_t mutx;
}data_t;


typedef struct work_on{
	int x;
	int y;
	data_t *data;
}work_on_t;

typedef struct Matrix{
	data_t ***mat;
	pair_t **p;
	int valp;
	int valNext;
	int valx;
	int valy;
	int num;
	pthread_mutex_t mutx;
}matrix_t;


typedef struct Argv{
	int num;
	int **tab;
	matrix_t *begin;
	matrix_t *slot;
} argv_t;

void* act(void *a);
void work(work_on_t *w, matrix_t *slot);
void put(matrix_t *slot , work_on_t *w);
matrix_t* matrix_init(state_t ini);
work_on_t* getWork(matrix_t *begin ,matrix_t *slot, int *countNotFinished);

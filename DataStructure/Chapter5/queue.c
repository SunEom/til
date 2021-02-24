#include <stdio.h>
#include <stdlib.h>
#define MAX_QUEUE_SIZE 5

typedef int element;
typedef struct {
	int front;
	int rear;
	element queue[MAX_QUEUE_SIZE];
} QueueType;

//���� �Լ�
void error(char* message) {
	fprintf(stderr, "%s\n", message);
	exit(1);
}

void init_queue(QueueType* q) {
	q->front = -1;
	q->rear = -1;
}

void queue_print(QueueType* q) {
	for (int i = 0; i < MAX_QUEUE_SIZE; i++) {
		if (i <= q->front || i > q->rear)
			printf(" | ");
		else
			printf("%d | ", q->queue[i]);
	}
	printf("\n");
}

int is_full(QueueType* q) {
	if (q->rear == MAX_QUEUE_SIZE - 1) return 1;
	else return 0;
}

int is_empty(QueueType* q) {
	if (q->front == q->rear) return 1;
	else return 0;
}

void enqueue(QueueType* q,element item) {
	if (is_full(q)) {
		error("Queue over flow");
		return;
	}
	q->queue[++(q->rear)] = item;
}

element dequeue(QueueType* q) {
	if (is_empty(q)) {
		error("Queue is empty!");
		exit(1);
	}
	return q->queue[++(q->front)];
}


int main(void) {
	int item = 0;
	QueueType q;

	init_queue(&q);

	enqueue(&q, 10); queue_print(&q);
	enqueue(&q, 20); queue_print(&q);
	enqueue(&q, 30); queue_print(&q);

	item = dequeue(&q); queue_print(&q);
	item = dequeue(&q); queue_print(&q);
	item = dequeue(&q); queue_print(&q);
	return 0;
}
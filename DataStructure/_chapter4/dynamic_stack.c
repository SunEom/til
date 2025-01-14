#include <stdio.h>
#include <stdlib.h>

typedef int element;
typedef struct {
    int top;
    int capacity;
    element *data;
} StackType;


void init_stack(StackType *s){
    s->top =-1;
    s->capacity = 1;
    s->data = (element *)malloc(s->capacity * sizeof(element));
}

int is_empty(StackType *s){
    if(s->top == -1) return 1;
    else return 0;
}

int is_full(StackType *s){
    if(s->top == s->capacity -1) return 1;
    else return 0;
}

void push (StackType *s,element e){
    if(is_full(s)){
        s->capacity *=2;
        s->data = (element *)realloc(s->data,s->capacity * sizeof(element));
    }
    s->data[++(s->top)] = e;
}

element pop(StackType *s){
    if(is_empty(s)){
        fprintf(stderr,"Stack is empty \n");
        exit(1);
    }
    else return s->data[(s->top)--];
}

element peek(StackType *s){
    if(is_empty(s)){
        fprintf(stderr,"Stack is empty \n");
        exit(1);
    }
    else return s->data[(s->top)];
}

int main(void){
    StackType s;
    init_stack(&s);
    push(&s,1);
    push(&s,2);
    push(&s,3);
    printf("%d\n",pop(&s));
    printf("%d\n",pop(&s));
    printf("%d\n",pop(&s));
    free(s.data);
    return 0;
}
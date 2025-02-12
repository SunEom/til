#include <stdio.h>
#include <stdlib.h>

typedef int element;
typedef struct ListNode {
	element data;
	struct ListNode* link;
}ListNode;

void error(char *message){
	fprintf(stderr, "%s\n", message);
	exit(1);
}

ListNode* insert_first(ListNode* head, element value) {
	ListNode* p = (ListNode*)malloc(sizeof(ListNode));
	p->data = value;
	p->link = head;
	head = p;
	return head;
}

ListNode* insert(ListNode* head, ListNode* pre, element value) {
	ListNode* p = (ListNode*)malloc(sizeof(ListNode));
	p->data = value;
	p->link = pre->link;
	pre->link = p;
	return head;
}

ListNode* delete_first(ListNode* head) {
	ListNode* removed;
	if (head == NULL) return NULL;
	removed = head;
	head = removed->link;
	free(removed);
	return head;
}

ListNode* delete(ListNode* head, ListNode* pre) {
	ListNode* removed;
	removed = pre->link;
	pre->link = removed->link;
	free(removed);
	return head;
}

void print_list(ListNode* head) {
	for (ListNode* p = head; p != NULL; p = p->link)
		printf("%d->", p->data);
	printf("NULL \n");
}

int get_length(ListNode* head) {
	int length = 0;
	for (ListNode* p = head; p != NULL; p = p->link)
		length++;
	return length;
}

int main(void) {
	ListNode* head = NULL;
	
	for (int i = 0; i < 5; i++) {
		head = insert_first(head, i);
		print_list(head);
		printf("Length : %d\n\n", get_length(head));
	}

	for (int i = 0; i < 5; i++) {
		head = delete_first(head, i);
		print_list(head);
		printf("Length : %d\n\n", get_length(head));
	}
	return 0;
}
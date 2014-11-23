#include <stdio.h>
#include <stdlib.h>

typedef struct Link {
	int value;
	struct Link *next;
	struct Link *previous;
} Link;

typedef struct Deque {
	Link *head;
	int size;
} Deque;

Deque *initDeque();
void enqueueHead(Deque *deque, Link* link);
void enqueueTail(Deque *deque, Link* link);
void printDeque(Deque *deque, Link* link) ;
Link *dequeHead (Deque *deque);
Link *dequeTail (Deque *deque);

main() {
Deque *deck = initDeque();
Link *link1 = (Link*) malloc(sizeof(Link));
Link *link2 = (Link*) malloc(sizeof(Link));
Link *link3 = (Link*) malloc(sizeof(Link)); 
Link *link4 = (Link*) malloc(sizeof(Link)); 
link4->value=4;
link1->value = 1;
link2->value=2;
link3->value=3;
enqueueHead(deck,link1);
enqueueHead(deck,link2);
enqueueHead(deck,link3); 
enqueueTail(deck,link4);



printf("size : %d\n" ,deck->size);
printDeque(deck,deck->head);

}

Deque *initDeque() {
	Deque *newDeque = (Deque*) malloc(sizeof(Deque));
	newDeque->head=NULL; 
	newDeque->size=0;
}

void enqueueHead(Deque *deque, Link* link) {
	if(deque->head!=NULL) {
		Link* temp = deque->head;
		link->next=temp;
		deque->head=link;   // we are changing deque->head
		link->previous=temp->previous;       // because the first link in list had previous pointer to last link in list.
		temp->previous = link;
		
		(link->previous)->next = link; // the last link's next pointer now points to the new head link.
		deque->size++;
	} 
	else {
			deque->head = link;
			link->next = deque->head;
			link->previous = link;   // because its the only thing in the list thus far
			deque->size++;
		}
}
		
void enqueueTail(Deque *deque, Link* link) {
	if(deque->head!=NULL) {
		Link *lastlink = deque->head->previous;
		lastlink->next = link;
		link->next = deque->head;
		link->previous=lastlink;
		deque->head->previous=link;
		deque->size++;
	}
	else {
		enqueueHead(deque,link);
		deque->size++;
	}
}
		
void printDeque(Deque *deque, Link* link) { // pass it the first link (i.e. deque->head)
		if((deque->size)>=1 && link!=deque->head->previous) {
			printf("%d\n" , link->value);
			printDeque(deque, link->next);
		}
		else if(deque->size==0) {
			printf("Deque Empty\n");
		}
		else printf("%d\n" , link->value); // this is the last link that we missed.
	}
	
Link *dequeHead (Deque *deque) {
	if((deque->size)>1) {
		Link *firstlink = deque->head;
		deque->head = firstlink->next;   
		deque->head->previous = firstlink->previous;
		deque->head->previous->next = deque->head; // makes the last link point to the new first link.
		deque->size--;
		
		return firstlink; 
	}
	else if(deque->size==1) {
		Link *onlyLink = deque->head;
		deque->head=NULL;
		deque->size--;
		return onlyLink;
	}
	else printf("Deque is already empty\n");
}

Link *dequeTail(Deque *deque) {
	if((deque->size)>1) {
		Link *lastlink = deque->head->previous;
		lastlink->previous->next = deque->head;
		deque->head->previous=lastlink->previous; 
		deque->size--;
		return lastlink;
	}
	else  {
		dequeHead(deque);
	}
}
	
	
	
	
	
		
	
	
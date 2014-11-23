#include <stdio.h>
#include <stdlib.h>


typedef struct node {
	int key ;
	struct node *leftChild, *rightChild ;
	int NPL; // null path length
} node ;

typedef struct Tree {
	node *root ;
} Tree ;

node *merge(node *subtree1, node *subtree2);
Tree *createTree();
void printNode( node* Node );
node *makeLeftist(node *root);
node *deleteMin(node *root);

int min(int x, int y) {
	if (x<=y) return x;
	else return y;
}

Tree *createTree() {
	
	Tree *tree = (Tree*) malloc(sizeof(Tree));
	tree->root = NULL;
	return tree;
}

// going to have to convert this method into first get and return min, then do the merge.
node *deleteMin(node *root) {
	node *result = merge(root->leftChild,root->rightChild);
	return result;
}


node *merge(node *subtree1, node *subtree2) { // alternative name for merge could be swapNodes

	if(subtree1==NULL) {        // base case // because we're moving down the rightChildren of subtree 1, we will eventually approach null node.
		subtree1=subtree2;    
		return subtree1;
	}
	if(subtree2==NULL) { // only if the  tree you want to merge with the other tree is empty.. then just return theother tree.
		return subtree1;
	}
	else {                                                  // recursive case (problem getting simpler)
		if(subtree2->key<subtree1->key) {           // we do this only if required.
			node *temp = subtree1;
			subtree1=subtree2;
			subtree2=temp; // up til this point we have taken a step further towards the simpler solution and in the next line we do the remaining work
		}
		subtree1->rightChild = merge(subtree1->rightChild,subtree2); // let your friend take care of the rest of the merging.
		/* can do if NPL of RC is > NPL of LC, swap them here if we like. but we made seperate fcn for it.( the merge function would include sorting AND making leftist then. we can expect that our friend that returns merge also does this for the argument it was asked to do it for*/
		return subtree1;
	
	}
}  // make sure to call makeLeftist next.
	
int NPL(node *root) {  // calculates the null path length of a tree
		int npl;
		if(root==NULL)  {      // simplest case to handle is if argument == NULL, then we know for sure that NPL is 0.
			return (npl=0);        
		}
		else {
		npl = min(NPL(root->leftChild),NPL(root->rightChild)) + 1 ; // null path length of the tree is simply the 1 + the NPL of NPL of the LT or the RT depending on which is smaller
		return npl;
		}
}
	
 int isLeftist(node *root) {
		if(root==NULL) {                // simplest input to handle, requires no recursion if this input is the argument
			return 1; //is Leftist
		}
		if((NPL(root->leftChild))<(NPL(root->rightChild))) {
				return 0; // is not Leftist
			}
			else {
				if(isLeftist(root->leftChild) && isLeftist(root->rightChild))
					return 1;
				return 0; // otherwise return 0, one or both of the subtree's themselves aren't leftist
				}
		}

			
			
	
/* now create a function that makes it leftist again  after merging, or incorporate it inside the merge.. but i want to try how to do it seperately...*/
/* then write a min function, and then try out all my fcns, it shud be good for a complete heap implementation */
	
node *makeLeftist(node *root) {    // turns a binary tree into a leftist tree. 
	if(root==NULL) {
		//do nothing
	return root;     //  a empty tree is already a leftist tree.
	}
	else {
		if(NPL(root->leftChild)<NPL(root->rightChild)) {   // swap leftChild and rightChild if necessary
			node *temp = root->leftChild;
			root->leftChild=root->rightChild;
			root->rightChild=temp;
		}
		root->leftChild = makeLeftist(root->leftChild);            // now have ur friend do the same for the children and then return the root.
		root->rightChild = makeLeftist(root->rightChild);
		return root;
	}
}


int main()  { 

Tree *treeA = createTree();
treeA->root = (node*) malloc(sizeof(node));
treeA->root->key = 1;
treeA->root->leftChild=NULL;
treeA->root->rightChild=NULL;
node* Tree1 = treeA->root;

Tree *treeB = createTree();
treeB->root = (node*) malloc(sizeof(node));
treeB->root->key = 2;
treeB->root->leftChild=NULL;
treeB->root->rightChild=NULL;
node* Tree2 = treeB->root;

Tree *treeC = createTree();
treeC->root = (node*) malloc(sizeof(node));
treeC->root->key = 0;
treeC->root->leftChild=NULL;
treeC->root->rightChild=NULL;
node* Tree3 = treeC->root;

Tree *treeD = createTree();
treeD->root = (node*) malloc(sizeof(node));
treeD->root->key = 2;
treeD->root->leftChild=NULL;
treeD->root->rightChild=NULL;
node* Tree4 = treeD->root;

node* TreeResult = merge(Tree1,Tree2);

node *TreeResult2= merge(TreeResult,Tree3);
node *TreeResult3=merge(TreeResult2,Tree4);

printf("is it leftist? %d\n\n", isLeftist(TreeResult3));
node *result = makeLeftist(TreeResult3);
printf("is it leftist now? %d\n\n", isLeftist(TreeResult3));
printf("%d\n", TreeResult3->key);
printf("%d\n", TreeResult3->leftChild->key);
printf("%d\n", TreeResult3->leftChild->leftChild->key);
printf("%d\n\n", TreeResult3->leftChild->leftChild->leftChild->key);

node *newTree = deleteMin(TreeResult3);
newTree = makeLeftist(newTree);
printf("%d\n", newTree->key);
printf("%d\n", newTree->leftChild->key);
printf("%d\n", newTree->leftChild->leftChild->key);


}
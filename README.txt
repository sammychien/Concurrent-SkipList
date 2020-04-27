README:
Good sources:

Introduction: 
A skiplist is a sorted linked list which contains levels to help speed up the process of searching for a particular value within the list. A skiplist is constructed with a base level which contain all the value present in the skiplist, certain nodes on the bottom level is promoted to higher levels so that during searching a program is able to first search through the higher levesl and once they come to a node whose value is greater than the one they are looking for then they look down a level and continue searching for the value. 
In our implementation we build a iterative skiplist, a lockfree and a fine grained skiplist. The iterative skiplist is basically built like the description about from the bottom up. Whenever a new node to added to the bottom level of the skiplist a coin is flipped to determine whether to promote that node to a higher level. The node keeps getting promoted to a higher level so long as a coin comes up heads so in a sense a skiplist is not a deterministic algorithm and could have infinite levels but that would never happen because we are using a fair coin.

The lockfree skiplist is based on using atomic instructions to update references between nodes at various levels and in betwen nodes. With a lockfree implementation each node has properties that track whether it is has been fully inserted into the skiplist or whether it is marked for deletion by other threads. This is to help ensure that when multiple threads are manipulating the skiplist concurrently they are each aware of that state of a node before they try to do any atomic operations that might relate to that node.

The fine grained implementation is similar to a fine-grained linked list implementation in that every node has a reentrant lock and only a small portion of the skiplist is locked at any given moment instead of locking the entirety of the linked list for any particular operation. The implementation also uses similar variable to the lockfree skiplist to help other threads know whether a node is able to deleted or if the node has not been fully linked inside the skiplist.

Finally we also tested our implementations of the concurrent and iterative skiplist against java inherenet ConcurrentSkipListSet to see if the timing our implemenations performed similar to better to java's inherent class.

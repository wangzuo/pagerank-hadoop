## About PageRank (review)
**Overall**: The ranking of a page is determined by its estimated importance (determined by link structure) instead of by its content.

A variation of **Eigenvector centrality**. 

![PageRank equation](http://upload.wikimedia.org/math/3/0/1/301ac52562803bc429fe3d8dace97b6b.png) ![](http://upload.wikimedia.org/math/0/3/1/03114db8703c7f3b642c7c43a7c5b2c0.png)

**each iteration: PageRank divided by the number of outbound links L(j)**

**probability distribution:** the bigger the PageRank, the more important the page

d = 0.85 [introduction to PageRank](http://michaelnielsen.org/blog/lectures-on-the-google-technology-stack-1-introduction-to-pagerank/)

## PageRank Convergence
Since we are calculating PageRank for a network in an iterative approach. In practise, there is a need to tell the program when to stop.

## Computing PageRank using MapReduce

#### 1. Parse documents (web pages) for links
##### map
+ input: index.html
+ output: list (index.html, out.html)
#####reduce
+ input: list (index.html, out.html)
+  (index.html, (**1.0**, 1.html, 2.html)) 1.0 is the initial PageRank

#### 2. Compute PageRank (Iteratively compute PageRank)
##### map
+ input: index.html, (<PageRank> 1.html 2.html)
+ output: 1.html, (index.html <PageRank> <number of outerlinks)
##### reduce
+ input: 1.html, (index.html <PageRank> <number of outerlinks)
+ output: 1.html, (<updated PageRank>, index.html, 2.html)
##### Updating PageRank
formula

#### 3. Sort the documents by PageRank
MapReduce sorts all outputs by key using a distributed mergesort (very fase and scalable)
##### map
+ input: index.html, (<PageRank>, <outlinks>)
+ output: <PageRank>, index.html
##### reduce
#### 4. Create Index

## Simple Webpage Crawler
only for domain **cuhk.edu.hk**

sqlite3 database

links (url, title)

it behaves a litte bit like PageRank alogirithm, so it can be done at the same time, i guess.

## MapReduce PageRank illustration
```
A, D	(A, (1, D))		(D, 1/1)	(A, (1/2, D))
B, C	(B, (1, C))		(C, 1/1)	(B, (1/1, C))
C, A	(C, (1, AD))	(A, 1/2)	(C, (1/1, AD))
D, B	(D, (1, B))		(D, 1/2)	(D, (1/1+1/2, B))
C, D					(B, 1/1)
(A, D)
(B, C)
(C, AD)
(D, B)
```
## MapReduce for iterative computation
R(t) - R(t-1) < delta

Apache Mahout: multiple Map() and Reduce()

simple run

multiple phases for PageRank

http://blog.xebia.com/2011/09/27/wiki-pagerank-with-hadoop/

script
###### Phase 1
input: 	graph_input
output: phase2_output_0

###### Phase 2
iteration 1
input:	phase2_output_0
output:	phase2_output_1
iteration 2
input: 	phase2_output_1
output:	phase2_output_2

...


## PEGASUS
iteration 10




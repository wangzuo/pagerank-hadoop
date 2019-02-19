## Introduction

The program implments a PageRank iterative calculation on a graph with Hadoop. The input data is a directed network (not html documents). There are mainly two phases of map-reduce. The second phase is iterative.

## Usage

A bash script `pr` is provided.
set `$HADOOP_INSTALL` to the directory of hadoop installation.

```
$ ./pr 1
# run phase 1, input folder: graph_input, output folder: phase1_output
$ ./pr 2 [iteration]
# run phase 2 for [iterative] times, input folder: phase2_output_[i], output_folder: phase2_output_[i+1]
```

## About PageRank (review)

**Overall**: The ranking of a page is determined by its estimated importance (determined by link structure) instead of by its content.
A variation of **Eigenvector centrality**.
![PageRank equation](http://upload.wikimedia.org/math/3/0/1/301ac52562803bc429fe3d8dace97b6b.png) ![](http://upload.wikimedia.org/math/0/3/1/03114db8703c7f3b642c7c43a7c5b2c0.png)

**each iteration: PageRank divided by the number of outbound links L(j)**
**probability distribution:** the bigger the PageRank, the more important the page
d = 0.85 [introduction to PageRank](http://michaelnielsen.org/blog/lectures-on-the-google-technology-stack-1-introduction-to-pagerank/)

PageRank Convergence: It may never reach convergence for loops. Ususally 10 iterations will be enough.

## Computing PageRank using MapReduce

#### Phase 1 - Parse documents (web pages) for links

This phase is simplified by replacing web pages with a network file.

##### map

```
A\tD 	(A, D)
B\tC 	(B, C)
C\tA 	(C, A)
D\tB	(D, B)
C\tD	(C, D)
```

#### reduce

```
(A, D)	(A, (1.0:D))
(B, C)	(B, (1.0:C))
(C, A)	(C, (1.0:A,D))
(D, B)	(D, (1.0:B))
(C, D)
```

#### notice

PageRank results usually present in a probability distribution. In MapReduce program, there is no idea of number of nodes in the graph, all PageRank values are set to be 1.0 on initialization.

#### Phase 2 - Compute PageRank (Iteratively approach)

##### map

```
(A, (1.0:D))	(D, 1:1)
(B, (1.0:C))	(C, 1:1)
(C, (1.0:A,D))	(A, 1:2)
(D, (1.0:B))	(D, 1:2)
				(B, 1:1)
				**(A, D)**
				**(B, C)**
				**(C, AD)**
				**(D, B)**
```

##### reduce

```
(D, 1:1)		(A, (0.5:D))
(C, 1:1)		(B, (1.0:C))
(A, 1:2)		(C, (1.0:A,D))
(D, 1:2)		(D, (1.5:B))
(B, 1:1)
(A, D)
(B, C)
(C, AD)
(D, B)
```

##### notice

In mapping process, the graph structure is kept such that it can be iterative.
updaing PageRank

[new pr] = (dumping_value) \* [old pr] + (1 - dumping_value)

#### Phase 3 - Sort the nodes by PageRank

Sort by PageRank value.

## About Iterative MapReduce

The program implements simple bash script to do iterative MapReduce. It has to restart the hadoop in each iteration.

[Apache Mahout](http://mahout.apache.org/): restarting needed

[PEGASUS](http://www.cs.cmu.edu/~pegasus/): restarting needed

[haloop](http://code.google.com/p/haloop/): An modified version of Hadoop to support efficient iterative data processing on large commodity clusters

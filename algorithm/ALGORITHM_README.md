# LadyBug - An Similarity Detection Model
### How to Use

When creating an instance of `SimilaryityDecectionModel`, the detection model start running. 

- The constructor input variable is an `ArrayList<String>`, where its first element is the `[CodePath]`, 
and others are `[CodeName]`.

- `1` will be returned when it is finished successfully, while for other cases it will return `0`.

- The result stores in three `ArrayList` of `SimilarPiece` for three similar classes respectively
. Each instance of `SimilarPiece` contains the information that the `[lines1]` of `[file1]` is 
similar to the `[lines2]`of `[file2]`.


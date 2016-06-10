# Takes a dataframe with results and plots a boxplot for it. 

computeBoxplot <- function(results) {
  
  boxplot(results$RelativeDistance ~ results$Method, results);
}
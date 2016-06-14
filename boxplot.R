# Takes a dataframe with results and plots a boxplot for it. 

computeBoxplot <- function(results) {
  
  
  algos <- unique(results$algoId);
  
  for(algo in algos) {
    
    algoFrame = results[results$algoId == algo,]
    
    png(filename=paste('D:/Plots/',algo,'.png', sep =""))
  
    plot <- xyplot(algoFrame$estimation ~ algoFrame$expected, groups = algoFrame$graph, 
         scales = list(x = list(log = 2), y = list(log = 2)),
         panel = function(...) {
           panel.xyplot(...)
           panel.abline(a=0, b=1)
         }, auto.key = list(columns = nlevels(algoFrame$graph)), xlab = 'Expected', ylab='Estimation',
         main = algo)
    
    print(plot)
    
    dev.off()
    
    
  }
  
  
}
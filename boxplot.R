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

plotMemResults <- function(memDataset) {
  
  graphs <- unique(memDataset$graph)
  
  for(graph in graphs) {
    graphdf <- memDataset[memDataset$graph == graph,]
    
    memUsages <- sort(unique(graphdf$memUsage))
    
    xVals <- vector('list', length(memUsages))
    yVals <- vector('list', length(memUsages))
    
    i <<- 1
    
    for(memUsage in memUsages) {
      
      memDf <- graphdf[graphdf$memUsage == memUsage,]
      
      accuracies <<- vector('list', nrow(memDf))
      
      j <<- 1
      
      by(memDf, 1:nrow(memDf), function(row) {
        
        if(row$expected == 0 && row$estimation == 0) {
          avg <- 1
        } else {
          avg <- (row$expected - (row$expected - row$estimation)) / row$expected
        }
        
        
        accuracies[[j]] <<- avg
        
        j <<- j + 1
      })
      
      avgAcc <- mean(unlist(accuracies))
      
      yVals[[i]] <- avgAcc
      xVals[[i]] <- memUsage / graphdf[graphdf$memUsage == memUsage,]$filesize[[1]];
      
      i <<- i + 1
    }
    
    png(filename=paste('D:/Plots/',graph,'.png', sep =""))
    
    plot(x = xVals, y = yVals, type="o")
    
    dev.off()
  }
  
  
}
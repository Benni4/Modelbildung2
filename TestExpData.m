
function TestExpData()
    function doTest(id, lambda)
        fid = fopen(sprintf('data%d.txt',id),'r'); 
        data = fscanf(fid, '%f,');
        fclose(fid);
        norm = exprnd(lambda,1000,1);

        subplot(3,2,id*2-1);
        probplot('exp',[norm data]);
        legend('exprnd',sprintf('data%d',id), 'Location','NW');
        title(sprintf('Test #%d exponential distribution',id));
        subplot(3,2,id*2);
        histogram(data);
        title(sprintf('Test #%d histogram',id));
    end

    figure();
    doTest(1,100);
    doTest(2,1000);
    doTest(3,20);
end
